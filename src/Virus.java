import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *Klasa do tworzenia wirusa
 */
public class Virus extends JComponent{
    /**
     *Sciezka obrazka dla wirusa
     */
    String imagePath;
    /**
     *Rozmiar wirusa
     */
    double size = 0.2;
    /**
     *Panel, w ktorym bedzie wirus
     */
    GamePanel virusPanel;
    /**
     *Do odczytania obrazka wirusa
     */
    private BufferedImage virusIm;
    /**
     *Polozenie wirusa
     */
    public int wx, wy;
    /**
     *Konstruktor
     */
    public Virus(String imagePath, GamePanel gp){
        virusPanel = gp;
        this.imagePath = imagePath;
        File imageFile = new File("./virus.png");
        try {
            virusIm = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }
    /**
     *Zwraca szerkosc obrazka
     */
    public int getWidth(){
        return virusIm.getWidth();
    }
    /**
     *Zwraca dlugosc obrazka
     */
    public int getHeight(){
        return virusIm.getHeight();
    }
    /**
     *Zmienia wielkosc bakterii
     */
    public double getVirusSize() {return size;}
    public void changeSize(){
        size += 0.05;
    }
    public int getHalfSizeW(){
        return (int)(virusPanel.getWidth()*size/2);
    }
    public int getHalfSizeH(){
        return (int)(virusPanel.getHeight()*size/2);
    }
    @Override
    protected void paintComponent(Graphics g){
        int w = virusPanel.getWidth();
        int h = virusPanel.getHeight();
        g.drawImage(virusIm, wx, wy, (int)(w *size), (int)(h *size) , this);
    }
}
