import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.*;

/**
 *Klasa uruchamiajaca
 */
public class Main {
    /**
     *Menu
     */
    public static JFrame myWindow;
    /**
     * Funkcja zaczynajaca program
     */
    public static void main(String[] args) throws InterruptedException {
        EventQueue.invokeLater(() -> {
            String temp = "./input.txt";
            try {
                new ReadFile(temp, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            myWindow = new OptionWindow();
        });
    }
}