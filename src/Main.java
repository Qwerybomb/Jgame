import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("Assets").toAbsolutePath();
        Window win = new Window();
        win.constructGame();
    }
}
class Window {
    //create the base components
    JPanel mainPanel = new JPanel();
    JFrame frame = new JFrame();
    JButton paddleP = new JButton();
//    JButton paddleE = new JButton();
//    JButton ball = new JButton();
    JTextField keyboard = new JTextField();

    public void constructGame() {
        //properly create the check for key presses


        // Size the players paddle properly
        paddleP.setBounds(0, 0, 20, 70);

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
        frame.addKeyListener(new Keycheck(this));
        frame.requestFocusInWindow();

        //properly setting up the listener
        keyboard.addKeyListener(new Keycheck(this));
        keyboard.requestFocusInWindow();
    }
    public void updateButtonPosition(int x, int y, JButton b) {
        b.setLocation(x, y);
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
         System.out.println(ch);
         // code to run when Key press event happens goes here
    }
}
