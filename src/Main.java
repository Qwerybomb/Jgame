import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        Path baseParent = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Path assetLocation = baseParent.resolve("Assets");
        game gameObj = new game();
        gameObj.constructGame(assetLocation);
    }
}
class baseLogic {
    //create the base components
    JPanel mainPanel = new JPanel();
    JFrame mainFrame = new JFrame();
    JButton paddleP1 = new JButton();
    JButton paddleP2 = new JButton();
    JButton ball = new JButton();
    JTextField keyboard = new JTextField();
    JLabel coolBg = new JLabel();

    // allow a Component Button to be repositioned
    public void updateButtonPosition(int x, int y, JButton b) {
        b.setLocation(x, y);
    }

}
class game extends baseLogic {
    // function to construct the game with correct properties
    public void constructGame(Path imageFolder) {
        // Format the paddles
        paddleP1.setBounds(0, 0, 20, 70);
        paddleP1.setMargin(new Insets(0, 0, 0, 0));
        paddleP1.setBorder(null);
        paddleP2.setBounds(365,0,20,70);
        paddleP2.setMargin(new Insets(0,0,0,0));
        paddleP2.setBorder(null);
        paddleP1.setIcon( new ImageIcon(String.valueOf(imageFolder.resolve("paddle.png"))));
        paddleP2.setIcon( new ImageIcon(String.valueOf(imageFolder.resolve("paddle.png"))));

        // make cool background
        coolBg.setBounds(100,100,1000,1000);
        coolBg.setBorder(null);
        coolBg.setIcon(new ImageIcon(String.valueOf(imageFolder.resolve("ball.png"))));
       System.out.println(coolBg.getHorizontalAlignment());


        // create JPanel and add the button (and keyboard)
        mainPanel.setSize(new Dimension(400, 300));
        mainPanel.add(paddleP1);
        mainPanel.add(paddleP2);
        mainPanel.add(keyboard);
        mainPanel.add(coolBg);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);
        
        // Make the JFrame work
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.addKeyListener(new Keycheck());
        mainFrame.requestFocusInWindow();

        //properly setting up the listener
        keyboard.addKeyListener(new Keycheck());
        keyboard.requestFocusInWindow();
    }
    public void beginGame() {
    }
}
//class that aids the JTextField in searching for keys
 class Keycheck extends KeyAdapter {
    baseLogic game = new baseLogic();
    public char ch;
    // Takes the main constructed window object and puts it here MIGHT be a bad idea
    @Override
    public void keyPressed(KeyEvent event) {
        // code to run when Key press event happens goes here
        ch = event.getKeyChar();
        System.out.print(ch);
    }
    @Override
    public void keyReleased(KeyEvent event) {
        // code to run when Key release event happens goes here
        System.out.print("|");
    }
    // thread for enabling keyEvents to work while running primary game logic
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            /* code to run for key logic here */
              if (ch == 'w' || ch == 's') {
                  game.paddleP1.setLocation(game.paddleP1.getLocation());
              }
            if (ch == 'i' || ch == 'k') {

              }
            }
        };
}
