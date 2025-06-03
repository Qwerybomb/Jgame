import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.concurrent.atomic.*;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        Path baseParent = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
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
    JButton ball = new JButton();
    JTextField keyboard = new JTextField();
    JLabel coolBg = new JLabel();

    // create global variables (atomic may be needed due to the nature of interfaces)
    static int speed = 4;
    AtomicBoolean moveToggleP1 = new AtomicBoolean(false);
    AtomicBoolean moveToggleP2 = new AtomicBoolean(false);
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
        paddleP1.setBounds(0, 0, 20, 70);
        paddleP1.setBorder(null);
        paddleP2.setBounds(365,0,20,70);
        paddleP2.setBorder(null);
        paddleP1.setIcon(paddleTexture);
        paddleP2.setIcon(paddleTexture);

        // make cool background
        ImageIcon bgImg = new ImageIcon(String.valueOf(imageFolder.resolve("grid.png")));
        coolBg.setBounds(25,0,bgImg.getIconWidth(),bgImg.getIconHeight());
        coolBg.setBorder(null);
        coolBg.setIcon(bgImg);

        // create JPanel and add the button (and keyboard)
        mainPanel.setSize(new Dimension(400, 300));
        mainPanel.add(coolBg);
        mainPanel.add(paddleP1);
        mainPanel.add(paddleP2);
        mainPanel.add(keyboard);
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
        while (true) {
            // paddle Movement Logic
        if (moveToggleP1.get()) {
            baseLogic.paddleP1.setLocation(0, baseLogic.paddleP1.getY() + speed * upOrDownMPp1.get());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




        }
    }
}
//class that aids the JTextField in searching for keys
 class Keycheck extends KeyAdapter implements baseLogic {
    char ch = '0';
     @Override
     public void keyPressed(KeyEvent event) {
        // code to run when Key press event happens goes here
        ch = event.getKeyChar();
        moveToggleP1.set(true);
        if (ch == 'w') upOrDownMPp1.set(1);
        if (ch == 's') upOrDownMPp1.set(-1);
    }
     @Override
     public void keyReleased(KeyEvent event) {
         moveToggleP1.set(false);
 }
}
