import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *Klasa do tworzenia menu opcji
 */
public class OptionWindow extends JFrame{
    /**
     *panel do rysowania przyciskow
     */
    public ButtonPanel panel = new ButtonPanel();
    /**
     *konstruktor menu
     */
    public OptionWindow(){
        super("Menu");
        panel.prepareButtons();
        setPreferredSize(new Dimension(1000, 700));
        add(panel);

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

/**
 *Klasa do umieszczenia przyciskow
 */
class ButtonPanel extends JPanel{
    /**
     *okno gry
     */
    public GameWindow gameWindow;
    /**
     *przycisk powrotu do menu
     */
    CButton returnBtton;
    /**
     *przycisk wyboru planszy
     */
    public JButton chooseBoardButton;
    /**
     *tekst znajdujacy sie w pomocy/opcjach
     */
    String[] helpText;
    /**
     *tworzy przyciski
     */
    public void prepareButtons(){
        removeAll();
        revalidate();
        repaint();
        setLayout(new GridLayout(0, 2, 10, 10));

        /**
         *Przycisk do dodania
         */
        JButton button = getButton("Graj");
        button.addActionListener(e -> createBoards());
        add(button);

        /**
         *Panel do umieszczenia dodatkowych przyciskow
         */
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(10, 10));

        JTextField textFeld = new JTextField();
        textFeld.setFont(new Font("Arial", Font.BOLD, 30));
        textFeld.setText("Nick");
        p.add(textFeld, BorderLayout.NORTH);

        button = getButton("Wyniki");

        button.addActionListener(e -> new ScoresWindow());
        p.add(button);

        p.setBackground(Color.WHITE);
        add(p);

        button = getButton("Pomoc");
        button.addActionListener(e -> help());
        add(button);

        button = getButton("Opcje");
        button.addActionListener(e -> options());
        add(button);

        setBackground(Color.WHITE);
        repaint();
    }
    /**
     *zwraaca przycisk
     */
    private JButton getButton(String text){
        JButton button = new JButton();
        button.setText(text);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        return button;
    }
    /**
     *tworzy pomoc
     */
    public void help(){
        new HelpWindow();
    }
    /**
     *tworzy opcje
     */
    public void options(){
        helpText = new String[]{"W - ruch w górę", "S,X - ruch w dół", "D - ruch w prawo", "A - ruch w lewo", "E - ruch skośnie góra-prawo",
    "Q - ruch skośnie góra-lewo", "C - ruch skośnie dół-prawo", "Z - ruch skośnie dol-lewo", "Mozna rowniez poruszac sie myszka"};
        nextWindow();
    }
    /**
     *odswieza okno i wyswietla opcje/pomoc
     */
    public void nextWindow(){
        removeAll();
        revalidate();
        repaint();

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBackground(Color.WHITE);
        returnBtton = new CButton();
        returnBtton.addActionListener(e -> {
            prepareButtons();
            returnBtton = null;
        });
        add(returnBtton);
    }
    /**
     *Tworzy przyciski wyboru planszy
     */
    public void createBoards(){
        removeAll();
        revalidate();
        repaint();

        setLayout(new GridLayout(0, 3, 5, 5));

        String[] boardsNames = {"Plansza 1", "Plansza 2", "Plansza 3", "Plansza 4", "Plansza 5", "Plansza 6"};
        for(int i = 0; i < 6; i++)
            add(getBoard(boardsNames[i]));

        repaint();
    }
    /**
     *Zwraca przycisk do uruchomienia
     */
    public JButton getBoard(String text) {
        chooseBoardButton = getButton(text);
        chooseBoardButton.addActionListener(e -> {
            gameWindow = new GameWindow();
            AccessGameWindow.setGameWindow(gameWindow);
            prepareButtons();
            Main.myWindow.dispose();
        });
        return chooseBoardButton;
    }
    /**
     *Callback do rysowania elementow
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = getWidth();
        if(returnBtton!=null)
        {
            returnBtton.paintComponent(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, w/20));
            int i = 0;
            for (String s : helpText)
                g.drawString(s, w / 9, ++i*w / 20);
        }
    }
}

/**
 *Klasa okraglego przycisku
 */
class CButton extends JButton{
    /**
     *Zmienne dla szerokosci i wysokosci okna
     */
    int w, h;
    /**
     *Do wczytania obrazka strza³ki
     */
    BufferedImage arrow;
    CButton(){
        File imageFile = new File("./arrow.png");
        try {
            arrow = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }
    /**
     *Callback do rysowania elementow
     */
    protected void paintComponent(Graphics g){
        w = Main.myWindow.getWidth();
        h = Main.myWindow.getHeight();
        g.setColor(Color.WHITE);
        if (getModel().isRollover()) {
            g.setColor(Color.LIGHT_GRAY);
        }
        if(getModel().isArmed()) {
            g.setColor(Color.RED);
        }
        g.fillOval(0, 0, w / 10, h / 10);
        g.drawImage(arrow, w / 40, h / 40, w / 20, h / 20, this);
    }
    /**
     *Sprawdza, czy klikniecie zawiera sie w polu
     */
     public boolean contains(int x, int y){
         int w = Main.myWindow.getWidth();
         int h = Main.myWindow.getHeight();
         Shape shape = new Ellipse2D.Float(0, 0, w/10, h/10);
         repaint();
         return shape.contains(x, y);
     }
    /**
     *Callback do rysowania krawedzi
     */
     protected void paintBorder(Graphics g){
         g.setColor(Color.BLACK);
         g.drawOval(0, 0, w / 10, h / 10);
     }
 }
