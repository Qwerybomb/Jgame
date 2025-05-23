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
    JPanel p1 = new JPanel();
    JFrame f1 = new JFrame();
    JButton b1 = new JButton("fml");
    JTextField keyboard = new JTextField();
    public void constructGame() {
        //properly create the check for key presses
        keyboard.addKeyListener(new Keycheck(this));

        // Size the button properly
        b1.setBounds(0, 0, 20, 20);

        // create JPanel and add the button (and keyboard)
        p1.setSize(new Dimension(400, 300));
        p1.add(b1);
        p1.add(keyboard);
        p1.setLayout(null);

        // Make the JFrame work
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(400, 300);
        f1.setResizable(false);
        f1.setLayout(null);
        f1.add(p1);
        f1.setVisible(true);
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
         // code to run when Key press event happens goes here

    }
}
