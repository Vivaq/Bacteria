import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;
/**
 *Klasa przeciwnika
 */
public class Enemy extends JPanel {
    /**
     *Szybkosc przeciwnika
     */
    private static final int SPEED = 15;
    //public static PriorityQueue<Integer> missingIndexes = new PriorityQueue<>();
    /**
     *Indeks przeciwnika w tablicy
     */
    private int index;
    /**
     * Do uruchamiania ruchu przeciwnika caly czas
     */
    Timer timer;
    /**
     *Panel przeciwnika
     */
    GamePanel enemyPanel;
    /**
     *Kierunek przewnika
     */
    private int direction;
    /**
     *Pozycja przeciwnika x'owa
     */
    private double xPosition = 0;
    /**
     *Pozycja przeciwnika y'owa
     */
    private double yPosition = 0;
    /**
     *O ile zmieni sie polozenie przeciwnika
     */
    private double changePos = 0.001;
    /**
     *Szerokosc i wysokosc okna
     */
    int w, h;
    /**
     *Sprawdza, czy wywolanie jest pierwsze
     */
    boolean fir = true;
    /**
     *Do losowania
     */
    Random random = new Random();
    /**
     *Do przechowywania obrazka przeciwnika
     */
    BufferedImage enemyImage;
    File imageFile = new File("./enemy.png");

    /**
     *Konstruktor
     */
    public Enemy(GamePanel gp, int index) {
        enemyPanel = gp;
        this.index = index;
        try {
            enemyImage = ImageIO.read(imageFile);
            timer = new Timer(SPEED, e -> {
                if(GameWindow.isPaused()) return;
                if(xPosition > 1 || xPosition < 0 || yPosition > 1  || yPosition < 0 || fir)
                {
                    do {
                        xPosition = random.nextDouble();
                        double[] arrX = {0, 1, 1/xPosition};
                        xPosition *= arrX[random.nextInt(3)];

                        yPosition = random.nextDouble();
                        double[] arrY = {0, 1, 1/yPosition};
                        yPosition *= arrY[random.nextInt(3)];
                    }while (xPosition != 0 && yPosition != 0 && xPosition != 1 && yPosition != 1);

                    direction = random.nextInt(8);
                    fir = false;
                }

                checkCollision();

                switch (direction){
                    case 0:
                        xPosition += changePos;
                        break;
                    case 1:
                        yPosition += changePos;
                        break;
                    case 2:
                        xPosition -= changePos;
                        break;
                    case 3:
                        yPosition -= changePos;
                        break;
                    case 4:
                        xPosition += changePos;
                        yPosition += changePos;
                        break;
                    case 5:
                        xPosition += changePos;
                        yPosition -= changePos;
                        break;
                    case 6:
                        xPosition -= changePos;
                        yPosition += changePos;
                        break;
                    case 7:
                        xPosition -= changePos;
                        yPosition -= changePos;
                        break;
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     *Sprawdza, czy bakteria zderzyla sie z przeciwnikiem
     */
    private void checkCollision(){
        w = enemyPanel.getWidth();
        h = enemyPanel.getHeight();
        Shape shape = new Ellipse2D.Float(enemyPanel.virus.wx, enemyPanel.virus.wy, 200, 200);
        if (shape.contains((int)(xPosition*w) + w/30, (int)(yPosition*h) + w/30)){
            System.out.println("!\n");
            GamePanel.animatedEnemy[index] = null;
            enemyPanel.virus.changeSize();
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        w = enemyPanel.getWidth();
        h = enemyPanel.getHeight();
        g.drawImage(enemyImage, (int)(w*xPosition), (int)(h*yPosition), w / 15, h / 15, this);
    }
}