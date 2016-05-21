import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;

/**
 *Okno do wyswietlania wynikow
 */
public class ScoresWindow extends JFrame{
    /**
     *Panel dla wynikow
     */
    JPanel panel;
    /**
     *Najlepsze yniki gry
     */
    String[] ScoresTab = {"1. Viva", "2. Abc"};
    /**
     *Konstruktor
     */
    public ScoresWindow(){
        super("Wyniki");
        setPreferredSize(new Dimension(300, 500));

        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setFont(new Font("Arial", Font.PLAIN, getWidth()/10));
                int i = 0;
                for(String s : ScoresTab)
                    g.drawString(s, (int)getLocationOnScreen().getX(), (i++ + 1)*getWidth()/10 + (int)getLocationOnScreen().getY());
            }
        };
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        add(panel);

        pack();
        setVisible(true);
    }
}
