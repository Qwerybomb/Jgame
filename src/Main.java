import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Window win = new Window();
    }
}
class Window extends JFrame {
    //create the base components
    JPanel p1 = new JPanel();
    JFrame f1 = new JFrame();
    JButton b1 = new JButton("fml");
    JTextField keyboard = new JTextField();
    Window() {
        //properly create the check for key presses
        keyboard.addKeyListener(new Keycheck());

        // Size the button properly
        b1.setBounds(50, 50, 20, 20);

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
    public void updateButtonPositions(int x, int y, JButton b) {
        b.setLocation(x, y);
    }
}
//class that aids the JTextField in searching for keys
 class Keycheck extends KeyAdapter {
    public char ch;
    @Override
    public void keyPressed(KeyEvent event) {
         ch = event.getKeyChar();
         // code to run when Key press event happens goes here
        Path path = Paths.get("Main.java");
        Path cool = path.toAbsolutePath();
        System.out.println(cool);

    }
}
