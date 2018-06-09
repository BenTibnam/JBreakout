import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Main {
    public void run() {
        JFrame frame = new JFrame("Breakout");
        ImageIcon img = new ImageIcon("ball.png");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ball.png")));
        Gameplay gameplay = new Gameplay();
        frame.setBounds(10, 10, 700, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(gameplay);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
