import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        Window win = new Window();
        win.constructGame();
    }
}
class Window extends JFrame {
    //create the base components
    JPanel mainPanel = new JPanel();
    JFrame frame = new JFrame();
    JButton paddleP = new JButton();
    JButton paddleE = new JButton();
    JButton ball = new JButton();
    JTextField keyboard = new JTextField();

    public void constructGame() {
        //properly create the check for key presses
        keyboard.addKeyListener(new Keycheck(this));

        // Size the players paddle properly
        paddleP.setBounds(0, 0, 20, 70);
        paddleP.setIcon(new ImageIcon());

        // create JPanel and add the button (and keyboard)
        mainPanel.setSize(new Dimension(400, 300));
        mainPanel.add(paddleP);
        mainPanel.add(keyboard);
        mainPanel.setLayout(null);

        // Make the JFrame work
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    public void updateButtonPosition(int x, int y, JButton b) {
        b.setLocation(x, y);
    }
    public void beginGame() {

    }
}
//class that aids the JTextField in searching for keys
 class Keycheck extends KeyAdapter {
    Window win;
    public char ch;
    // Takes the main constructed window object and puts it here MIGHT be a bad idea
    Keycheck(Window w) {
        win = w;
    }
    @Override
    public void keyPressed(KeyEvent event) {
         ch = event.getKeyChar();
         // code to run when Key press event happens goes here

    }
}
