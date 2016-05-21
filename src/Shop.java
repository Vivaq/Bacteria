import javax.swing.*;
import java.awt.*;

/**
 *Klasa do tworzenia okna sklepu
 */
public class Shop extends JFrame {
    JPanel panel;
    public Shop(){
        panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.add(new JButton("Item1"));
        panel.add(new JButton("Item2"));
        panel.add(new JButton("Item3"));
        add(panel);
        setPreferredSize(new Dimension(500, 600));
        pack();
        setVisible(true);
    }
}