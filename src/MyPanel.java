/*import javafx.scene.ImageCursor;
import sun.swing.ImageIconUIResource;
import sun.swing.icon.SortArrowIcon;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MyPanel extends JPanel{
    private BufferedImage virusIm;
    private BufferedImage korWIN;

    Random random = new Random();
    CircleButton returnButton;
    static CircleButton pauseButton;
    CircleButton optionButton;
    CircleButton shopButton;
    Enemy[] animatedEnemy = new Enemy[5];

    JPanel gP;
    public int wx;
    public int wy;
    TempGame accessTempGame;
    public MyPanel(){
        returnButton = new CircleButton("arrow.jpg", 0, 0);
        pauseButton = new CircleButton("pause.png", 0.9, 0);
        optionButton = new CircleButton("dot.png", 0, 0.9);
        shopButton = new CircleButton("shop.png", 0.9, 0.9);

        setLayout(null);

        File imageFile = new File("./virus.png");
        try {
            virusIm = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        imageFile = new File("./korwin.jpg"); // nasze chwilowe t³o planszy
        try {
            korWIN = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessTempGame.dispose();
                Main.myWindow.setVisible(true);
            }
        });
        add(returnButton);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TempGame.flag = !TempGame.flag;
                makeButtons();
                repaint();
            }
        });
        add(pauseButton);

        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Shop();
            }
        });
        add(shopButton);
        for(int i = 0; i < 5; i++)
        {
            animatedEnemy[i] = new Enemy(random.nextInt((int)ReadFile.wDimensions.get(0)), random.nextInt((int)ReadFile.wDimensions.get(1)));
            add(animatedEnemy[i]);
        }
    }
    private void makeButtons(){
        if(TempGame.flag)
        {
            gP = new GridPanel();
            gP.setBounds(0, 0, 200, 200);
            add(gP);
        }
        else
        {
            remove(gP);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        accessTempGame = ((OptionWindow)Main.myWindow).panel.tempGame;
        //setBounds(0, 0, accessTempGame.getWidth(), accessTempGame.getHeight());
        int w = getWidth();
        int h = getHeight();
        g.drawImage(korWIN, 0, 0, w, h, this); // dodanie t³a planszy - jak jest rysowane przed przeciwnikiem i tym co sterujemy to jest zajebiœcie
        g.drawImage(virusIm, w / 2 + wx, h / 2 + wy, w / 5, h / 5 , this);
        //double[] r = {random.nextDouble(), random.nextDouble()};
        //System.out.println(this.getLocationOnScreen().getX());
        //System.out.println(this.getLocationOnScreen().getY());
        for(int i = 0; i < 5; i++)
            animatedEnemy[i].paintComponent(g);
        returnButton.paintComponent(g);
        pauseButton.paintComponent(g);
        optionButton.paintComponent(g);
        shopButton.paintComponent(g);
        repaint();
        if(TempGame.flag) gP.updateUI();
    }
}

class CircleButton extends JButton{
    TempGame accessTempGame;
    Shape shape;
    double x, y;
    int w, h;
    int i = 0;
    String text;
    BufferedImage arrow;
    double size = 0.1;

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
    public void changeSize(double size){
        this.size = size;
    }

    protected void paintComponent(Graphics g){
        accessTempGame = ((OptionWindow)Main.myWindow).panel.tempGame;
        //g.setColor(new Color(30, 134, 145, 123));
        w = accessTempGame.getWidth();
        h = accessTempGame.getHeight();
        g.setFont(new Font("Arial", Font.PLAIN, w/50));
        g.drawOval((int) (w * x - 20 * x), (int) (h * y - 53 * y), (int)(w *size), (int)(h *size));
        g.drawImage(arrow, (int) (w * x - 20 * x)+ (int)(w*size/4), (int) (h * y - 53 * y) + (int)(h*size/4), (int)(w*size/2), (int)(h*size/2), new Color(0, 0, 0, 0), this);
    }

    public boolean contains(int x, int y){
        if(i++ == 0) return false;
        accessTempGame = ((OptionWindow)Main.myWindow).panel.tempGame;
        w = accessTempGame.getWidth();
        h = accessTempGame.getHeight();
        shape = new Ellipse2D.Float((int) (w*this.x - 20*this.x), (int) (h*this.y - 53*this.y), (int)(w *size), (int)(h *size));
        return shape.contains(x, y);
    }

    protected void paintBorder(Graphics g){}
}

class GridPanel extends JPanel{
    public GridPanel(){
        setLayout(new GridLayout(2, 2, 5, 5));
        setBackground(Color.WHITE);
        JButton k = new JButton("Koniec");
        k.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((OptionWindow)Main.myWindow).panel.tempGame.dispose();
            }
        });
        add(k);
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBackground(Color.WHITE);
        CircleButton c = new CircleButton("./arrow.jpg", 0, 0);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyPanel.pauseButton.doClick();
            }
        });
        c.changeSize(0.2);
        p.add(c);
        JButton r = new JButton("Restart");
        r.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((OptionWindow)Main.myWindow).panel.tempGame.dispose();
                ((OptionWindow)Main.myWindow).panel.startGameButton.doClick();
            }
        });
        p.add(r);
        add(p);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        TempGame accessTempGame = ((OptionWindow)Main.myWindow).panel.tempGame;
        int w = accessTempGame.getWidth();
        int h = accessTempGame.getHeight();
        setBounds(w/4, h/4, w/2, h/2);
    }
}
*/