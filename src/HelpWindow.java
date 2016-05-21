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
     * Inicjalizacja 2 obiekt�w klasy BufferedImage dzi�ki kt�rym b�dziemy mogli narysowa� obrazy pobrane przez imageFileVirus oraz imageFileEnemy
     */
    private BufferedImage virusImage, enemyImage;

    /**
     * Inicjalizacja 2 obiekt�w klasy File do kt�rych zapiszemy obrazy
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
        String tresc1 = "Witam serdecznie w grze Bakterioza! Znajdujesz si� w sekcji Pomoc. Wszelkie pro�by o pomoc, opinie oraz";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc2 = "sugestie odno�nie gry prosimy kierowa� na e-mail: support.bakterioza@gmail.com";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc3 = "R�wnie� wszelkie informacje na temat zauwa�onych b��d�w, nieprawid�owo�ci oraz bug�w prosz� niezw�ocznie";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc4 = "kierowa� na powy�ej wymieniony e-mail.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc5 = "Og�lna koncepcja gry polega na zjadaniu przeciwnik�w mniejszych od naszego bohatera. Za zdobyte punkty";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc6 = "mo�emy kupi� r�ne bonusy, kt�re s� dost�pne w sklepie ju� podczas samej rozgrywki. U�atwiaj� one znacznie";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc7 = "nasz� rozgrywk� poniewa� mo�emy dzi�ki nim szybciej lub/i efektywniej zabija� naszych przeciwnik�w co";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc8 = "przek�ada si� na wi�ksz� pul� punkt�w, kt�re mo�emy zdoby�.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc9 = "�eby uzyska� dost�p do kolejnych plansz nale�y zwyci�y� w poprzednich tzn. zdoby� wymagan� liczb� punkt�w.";

        /**
         *  Zmienna typu String uzywana do wypisana napisu na ekranie
         */
        String tresc10 = "          Rozgrywka na danej planszy ko�czy si� wraz z up�ywem czasu wy�wietlanego podczas gry.";

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
             * Metoda rysuj�ca na ekranie porz�dane przez nas elementy
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