import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.concurrent.atomic.*;

public class Pong {

    public static void main(String[] args) throws URISyntaxException {
        Path baseParent = Paths.get(Pong.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Path assetLocation = baseParent.resolve("Assets");
        game gameObj = new game();
        gameObj.constructGame(assetLocation);
        gameObj.beginGame();
    }
}
interface baseLogic { // contains the base swing components to be implemented and used anywhere
    //create the base components
    JPanel mainPanel = new JPanel();
    JFrame mainFrame = new JFrame();
    JLabel paddleP1 = new JLabel();
    JLabel paddleP2 = new JLabel();
    JLabel ball = new JLabel();
    JTextField keyboard = new JTextField();
    JLabel coolBg = new JLabel();
    JLabel coolBgL2 = new JLabel();

    // create global variables (atomic may be needed due to the nature of interfaces)
    static int speed = 4;
    static int tickLengthInMs = 10;
    static int ballTravelConstant = 5;
    AtomicInteger upOrDownMPp1 = new AtomicInteger(0);
    AtomicInteger upOrDownMPp2 = new AtomicInteger(0);
    // allow a Component Label to be repositioned

    public static void updateButtonPosition(int x, int y, JLabel b) {
        b.setLocation(x, y);
    }

}
class game implements baseLogic {
    // function to construct the game with correct properties
    public void constructGame(Path imageFolder) {
        // Format the paddles
        ImageIcon paddleTexture = new ImageIcon(String.valueOf(imageFolder.resolve("paddle.png")));
        paddleP1.setBounds(0, 50, 20, 70);
        paddleP1.setBorder(null);
        paddleP2.setBounds(365,50,20,70);
        paddleP2.setBorder(null);
        paddleP1.setIcon(paddleTexture);
        paddleP2.setIcon(paddleTexture);

        // make cool background
        ImageIcon bgImg = new ImageIcon(String.valueOf(imageFolder.resolve("grid.png")));
        coolBg.setBounds(0,0,bgImg.getIconWidth(),bgImg.getIconHeight());
        coolBg.setBorder(null);
        coolBg.setIcon(bgImg);
        coolBgL2.setBounds(-5,-5,bgImg.getIconWidth(),bgImg.getIconHeight());
        coolBgL2.setBorder(null);
        coolBgL2.setIcon(bgImg);

        // make the ball for the gmae :3
        ImageIcon ballIcon = new ImageIcon(String.valueOf(imageFolder.resolve("ball.png")));
        ball.setBounds(100,100,ballIcon.getIconWidth(),ballIcon.getIconHeight());
        ball.setIcon(ballIcon);
        ball.setBorder(null);

        // create JPanel and add the button (and keyboard)
        mainPanel.setSize(new Dimension(400, 300));
        mainPanel.add(coolBg);
        mainPanel.add(coolBgL2);
        mainPanel.add(paddleP1);
        mainPanel.add(paddleP2);
        mainPanel.add(keyboard);
        mainPanel.add(ball);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        // Make the JFrame work
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(400, 300));
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.addKeyListener(new keyCheck());
        mainFrame.requestFocusInWindow();

        //properly setting up the listener
        keyboard.addKeyListener(new keyCheck());
        keyboard.requestFocusInWindow();
    }

    // function for starting the game
    public void beginGame() {
        // paddle Movement Logic
        Thread Paddles = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    baseLogic.paddleP1.setLocation(0, baseLogic.paddleP1.getY() + speed * upOrDownMPp1.get());
                    baseLogic.paddleP2.setLocation(365, baseLogic.paddleP2.getY() + speed * upOrDownMPp2.get());
                    if (paddleP1.getY() < 0) paddleP1.setLocation(0, 0);
                    if (paddleP1.getY() > 200) paddleP1.setLocation(0, 200);
                    if (paddleP2.getY() < 0) paddleP2.setLocation(365, 0);
                    if (paddleP2.getY() > 200) paddleP2.setLocation(365, 200);

                    // wait
                    try {
                        Thread.sleep(tickLengthInMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // background animation
        Thread background = new Thread(new Runnable() {
            boolean moveMode = false;
            final int farLimit = 30;
            final int minLimit = -30;
            @Override
            public void run() {
                while (true) {
                  coolBg.setLocation(ball.getX() / 30, (paddleP1.getY() + paddleP2.getY()) / 30);
                  coolBgL2.setLocation(ball.getX() / 50, (paddleP1.getY() + paddleP2.getY()) / 50);
                }
            }
        });

        //Ball calculations
        Thread ballMove = new Thread(new Runnable() {
            double ballAngle = 20;
            @Override
            public void run() {
                while (true) {
                    ball.setLocation(ball.getX() + (int) Math.round(Math.cos(Math.toRadians(ballAngle)) * ballTravelConstant),
                    ball.getY() + (int) Math.round(Math.sin(Math.toRadians(ballAngle)) * ballTravelConstant));
                    if (ball.getX() > 360 || ball.getX() < 20) {
                        ballAngle *= -1;
                        ballAngle -= 180;
                        ball.setLocation(Math.clamp((ball.getX()),20, 360), ball.getY());
                    }
                    if (ball.getY() > 240 || ball.getY() < 0) {
                      //  ballAngle *= -1;
                        ballAngle -= 180;
                        ball.setLocation(ball.getX(),Math.clamp((ball.getY()),0, 240));
                    }
                    try {
                        Thread.sleep(tickLengthInMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // begin
        Paddles.start();
        background.start();
        ballMove.start();
    }
}
//class that aids the JTextField in searching for keys
 class keyCheck extends KeyAdapter implements baseLogic {
    char ch = '0';
    @Override
    public void keyPressed(KeyEvent event) {
        // code to run when Key press event happens goes here
        ch = event.getKeyChar();
        if (ch == 's') upOrDownMPp1.set(1);
        if (ch == 'w') upOrDownMPp1.set(-1);
        if (ch == 'k') upOrDownMPp2.set(1);
        if (ch == 'i') upOrDownMPp2.set(-1);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        ch = event.getKeyChar();
        if (ch == 's') upOrDownMPp1.set(0);
        if (ch == 'w') upOrDownMPp1.set(0);
        if (ch == 'k') upOrDownMPp2.set(0);
        if (ch == 'i') upOrDownMPp2.set(0);
    }
}
