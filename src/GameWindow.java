import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
/**
 *Klasa okna gry
 */
public class GameWindow extends JFrame implements MouseMotionListener {
    /**
     *Flaga do pauzowania gry
     */
    public static boolean flag = false;
    //private static final int SPEED = 3*(int)Math.sqrt(2), DIAGONAL_SPEED = 3;
    /**
     *Panel do umieszczania elementow gry
     */
    public static GamePanel panel = new GamePanel();
    /**
     *Konstruktor
     */
    public GameWindow() {
        super("KeyListener Test");
        add(panel);
        setPreferredSize(new Dimension((int)ReadFile.wDimensions.get(0), (int)ReadFile.wDimensions.get(1)));
        addMouseMotionListener(this);

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /*@Override
    public void keyPressed(KeyEvent evt) {
        if(GameWindow.isPaused()) return;
        switch (evt.getKeyChar()){
            case 'a':
                panel.virus.wx-=SPEED;
                break;
            case 'd':
                panel.virus.wx+=SPEED;
                break;
            case 's':
                panel.virus.wy+=SPEED;
                break;
            case 'w':
                panel.virus.wy-=SPEED;
                break;
            case 'z':
                panel.virus.wx-=DIAGONAL_SPEED;
                panel.virus.wy+=DIAGONAL_SPEED;
                break;
            case 'c':
                panel.virus.wx+=DIAGONAL_SPEED;
                panel.virus.wy+=DIAGONAL_SPEED;
                break;
            case 'q':
                panel.virus.wx-=DIAGONAL_SPEED;
                panel.virus.wy-=DIAGONAL_SPEED;
                break;
            case 'e':
                panel.virus.wx+=DIAGONAL_SPEED;
                panel.virus.wy-=DIAGONAL_SPEED;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }*/

    /**
     *Zmienia z pauzy na start i odwrotnie
     */
    public static void togglePause(){
        GameWindow.flag = !GameWindow.flag;
    }

    /**
     *Sprawdza, czy gra jest spauzowana
     */
    public static boolean isPaused(){
        return flag;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }
    /**
     *Callback, wywolywany przy przesunieciu myszy
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        panel.virus.wx = e.getX() - (int)panel.getLocationOnScreen().getX() - panel.virus.getHalfSizeW();
        panel.virus.wy = e.getY() - (int)panel.getLocationOnScreen().getY() - panel.virus.getHalfSizeH();
    }
}

/**
 *Klasa do umieszczania elementow gry
 */
class GamePanel extends JPanel{
    /**
     *Do wczytania tla gry
     */
    private BufferedImage korWIN;

    Random random = new Random();
    /**
     *Przycisk do powrotu
     */
    CircleButton returnButton;
    /**
     *Przycisk do pauzy
     */
    static CircleButton pauseButton;
    /**
     *Przycisk do wywolania opcji
     */
    CircleButton optionButton;
    /**
     *Przycisk do wywolania sklepu
     */
    CircleButton shopButton;
    /**
     *Tablica przeciwnikow
     */
    static Enemy[] animatedEnemy = new Enemy[5];
    /**
     *Nasz wirus
     */
    Virus virus = new Virus("./virus.png", this);

    /**
     *Panel wzwolywany po przycisnieciu pauzy
     */
    JPanel pp;
    /**
     *Konstruktor
     */
    public GamePanel(){
        returnButton = new CircleButton("arrow.png", 0, 0);
        pauseButton = new CircleButton("pause.png", 0.9, 0);
        optionButton = new CircleButton("dot.png", 0, 0.9);
        shopButton = new CircleButton("shop.png", 0.9, 0.9);

        setLayout(null);

        File imageFile = new File("./tlo.jpg"); // nasze chwilowe t³o planszy
        try {
            korWIN = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        returnButton.addActionListener(e -> {
            AccessGameWindow.close();
            Main.myWindow.setVisible(true);
        });
        add(returnButton);

        pauseButton.addActionListener(e -> {
            GameWindow.togglePause();
            prepareButtons();
            repaint();
        });
        add(pauseButton);

        optionButton.addActionListener(e->new HelpWindow());
        add(optionButton);

        shopButton.addActionListener(e -> new Shop());
        add(shopButton);
        for(int i = 0; i < 5; i++)
        {
            animatedEnemy[i] = new Enemy(this, i);
            add(animatedEnemy[i]);
        }
    }
    /**
     *Przygowtowuje przyciski
     */
    private void prepareButtons(){
        if(GameWindow.isPaused())
        {
            pp = new PausePanel();
            pp.setBounds(0, 0, 200, 200);
            add(pp);
        }
        else
        {
            remove(pp);
        }
    }

    /**
     *Callback do rysowania elementow gry
     */
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        //setBounds(0, 0, AccessGameWindow.getWidth(), AccessGameWindow.getHeight());
        int w = getWidth();
        int h = getHeight();
        g.drawImage(korWIN, 0, 0, w, h, this); // dodanie t³a planszy - jak jest rysowane przed przeciwnikiem i tym co sterujemy to jest zajebiœcie
        //double[] r = {random.nextDouble(), random.nextDouble()};
        //System.out.println(this.getLocationOnScreen().getX());
        //System.out.println(this.getLocationOnScreen().getY());
        for(int i = 0; i < 5; i++)
        {
            if(animatedEnemy[i] == null) continue;
            animatedEnemy[i].paintComponent(g);
        }
        virus.paintComponent(g);
        returnButton.paintComponent(g);
        pauseButton.paintComponent(g);
        optionButton.paintComponent(g);
        shopButton.paintComponent(g);
        repaint();
        if(GameWindow.isPaused()) pp.updateUI();
    }
}
/**
 *Klasa do tworzenia okraglych przyciskow
 */
class CircleButton extends JButton{
    /**
     *Do tworzenia ksztaltu
     */
    Shape shape;
    double x, y;
    /**
     *Do przechowywania wysokosci i szerokosci okna
     */
    int w, h;
    int i = 0;
    String text;
    /**
     *Do przechowywania wczytanego obrazka
     */
    BufferedImage arrow;
    /**
     *Rozmiar, o ktory zwiekszy sie bakteria po zjedzeniu przeciwnika
     */
    double size = 0.1;
    /**
     *Konstruktor
     */
    public CircleButton(String text, double x, double y){
        setFocusable(false);
        this.text = text;
        setContentAreaFilled(false);
        this.x = x;
        this.y = y;
        File imageFile = new File("./"+text);
        if(!text.equals("")) {
            try {
                arrow = ImageIO.read(imageFile);
            } catch (IOException e) {
                System.err.println("Blad odczytu obrazka");
                e.printStackTrace();
            }
        }
    }
    /**
     *Do zmiany rozmiaru bakterii
     */
    public void changeSize(double size){
        this.size = size;
    }

    /**
     *Callback do rysowania elementow gry
     */
    protected void paintComponent(Graphics g){
        //g.setColor(new Color(30, 134, 145, 123));
        w = AccessGameWindow.getWidth();
        h = AccessGameWindow.getHeight();
        g.setFont(new Font("Arial", Font.PLAIN, w/50));
        g.setColor(Color.WHITE);
        if (getModel().isRollover()) {
            g.setColor(Color.LIGHT_GRAY);
        }
        if(getModel().isArmed()) {
            g.setColor(Color.RED);
        }
        g.fillOval((int) (w * x - 20 * x), (int) (h * y - 53 * y), (int) (w * size), (int) (h * size));
        g.drawImage(arrow, (int) (w * x - 20 * x)+ (int)(w*size/4), (int) (h * y - 53 * y) + (int)(h*size/4), (int)(w*size/2), (int)(h*size/2), this);
    }

    /**
     *Callback, ktory sprawdza, czy klikniecie zawiera sie w obszarze
     */
    public boolean contains(int x, int y){
        if(i++ == 0) return false;
        w = AccessGameWindow.getWidth();
        h = AccessGameWindow.getHeight();
        shape = new Ellipse2D.Float((int) (w*this.x - 20*this.x), (int) (h*this.y - 53*this.y), (int)(w *size), (int)(h *size));
        return shape.contains(x, y);
    }

    /**
     *Callback, ktory rysuje krawedzie
     */
    protected void paintBorder(Graphics g){
        g.setColor(Color.BLACK);
        g.drawOval((int) (w * x - 20 * x), (int) (h * y - 53 * y), (int) (w * size), (int) (h * size));
    }
}
/**
 *Panel do rysowania przyciskow, gdy zostanie wywolana pauza
 */
class PausePanel extends JPanel{
    /**
     *Konstruktor
     */
    public PausePanel(){
        setLayout(new GridLayout(2, 2, 5, 5));
        setBackground(Color.WHITE);
        JButton k = new JButton("Koniec");
        k.addActionListener(e -> ((OptionWindow)Main.myWindow).panel.gameWindow.dispose());
        add(k);
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBackground(Color.WHITE);
        CircleButton c = new CircleButton("./arrow.png", 0, 0);
        c.addActionListener(e -> GamePanel.pauseButton.doClick());
        c.changeSize(0.2);
        p.add(c);
        JButton r = new JButton("Restart");
        r.addActionListener(e -> {
            ((OptionWindow)Main.myWindow).panel.gameWindow.dispose();
            ((OptionWindow)Main.myWindow).panel.chooseBoardButton.doClick();
        });
        p.add(r);
        add(p);
    }
    /**
     *Callback, ktory rysuje przyciski
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = AccessGameWindow.getWidth();
        int h = AccessGameWindow.getHeight();
        setBounds(w/4, h/4, w/2, h/2);
    }
}