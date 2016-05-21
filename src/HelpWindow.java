import com.sun.deploy.panel.JSmartTextArea;
import com.sun.prism.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *  Klasa odpowadajaca za wyswietlenie i zawartosc sekcji "Pomoc"
 */
public class HelpWindow extends JFrame{
    /**
     * Inicjalizacja obiektu klasy JPanel
     */
    private JPanel panel;

    /**
     * Inicjalizacja 2 obiektów klasy BufferedImage dziêki którym bêdziemy mogli narysowaæ obrazy pobrane przez imageFileVirus oraz imageFileEnemy
     */
    private BufferedImage virusImage, enemyImage;

    /**
     * Inicjalizacja 2 obiektów klasy File do których zapiszemy obrazy
     */
    private File imageFileVirus, imageFileEnemy;

    public HelpWindow()
    {
        super("Pomoc");
        setPreferredSize(new Dimension(700, 500));
        setLocation(50,50);


        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String naglowek = "POMOC";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc1 = "Witam serdecznie w grze Bakterioza! Znajdujesz siê w sekcji Pomoc. Wszelkie proœby o pomoc, opinie oraz";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc2 = "sugestie odnoœnie gry prosimy kierowaæ na e-mail: support.bakterioza@gmail.com";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc3 = "Równie¿ wszelkie informacje na temat zauwa¿onych b³êdów, nieprawid³owoœci oraz bugów proszê niezw³ocznie";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc4 = "kierowaæ na powy¿ej wymieniony e-mail.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc5 = "Ogólna koncepcja gry polega na zjadaniu przeciwników mniejszych od naszego bohatera. Za zdobyte punkty";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc6 = "mo¿emy kupiæ ró¿ne bonusy, które s¹ dostêpne w sklepie ju¿ podczas samej rozgrywki. U³atwiaj¹ one znacznie";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc7 = "nasz¹ rozgrywkê poniewa¿ mo¿emy dziêki nim szybciej lub/i efektywniej zabijaæ naszych przeciwników co";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc8 = "przek³ada siê na wiêksz¹ pulê punktów, które mo¿emy zdobyæ.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc9 = "¯eby uzyskaæ dostêp do kolejnych plansz nale¿y zwyciê¿yæ w poprzednich tzn. zdobyæ wymagan¹ liczbê punktów.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc10 = "          Rozgrywka na danej planszy koñczy siê wraz z up³ywem czasu wyœwietlanego podczas gry.";

        imageFileVirus = new File("./virus.png");
        try {
            virusImage = ImageIO.read(imageFileVirus);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        imageFileEnemy = new File("./enemy.png");
        try {
            enemyImage = ImageIO.read(imageFileEnemy);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }


        panel = new JPanel(){
            @Override
            /**
             * Metoda rysuj¹ca na ekranie porz¹dane przez nas elementy
             */
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setFont(new Font("Arial", Font.PLAIN, getWidth()/50 + getHeight()/50));
                g.drawString(naglowek, getWidth()/2 - naglowek.length() - 30,getHeight()/20);

                g.setFont(new Font("Arial", Font.PLAIN, getWidth()/85 + getHeight()/85));
                g.drawString(tresc1, getWidth()/100,getHeight()/11);
                g.drawString(tresc2, getWidth()/100,getHeight()/8);
                g.drawString(tresc3, getWidth()/100,(int)(getHeight()/6.2));
                g.drawString(tresc4, getWidth()/100,(int)(getHeight()/5.1));
                g.drawString(tresc5, getWidth()/100,(int)(getHeight()/4.3));
                g.drawString(tresc6, getWidth()/100,(int)(getHeight()/3.75));
                g.drawString(tresc7, getWidth()/100,(int)(getHeight()/3.35));
                g.drawString(tresc8, getWidth()/100,(int)(getHeight()/3.0));
                g.drawString(tresc9, getWidth()/100,(int)(getHeight()/2.70));

                g.setFont(new Font("Arial", Font.BOLD, getWidth()/85 + getHeight()/85));
                g.drawString(tresc10, getWidth()/100,(int)(getHeight()/2.2));


                g.drawImage(enemyImage,getWidth()/500,(int)(getHeight()/2),(int)(getWidth()/3),(int)(getHeight()/2.5),this); // 15 0raz 2.9
                g.drawImage(virusImage,(int)(getWidth()/3.7),(int)(getHeight()/2.3),(int)(getWidth()/1.2),(int)(getHeight()/1.5),this);
            }

        };


        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        add(panel);
        pack();
        // setResizable(false); // wylaczenie mozliwosci zmiany wielkosci okna
        setVisible(true);
    }
}