
public class AccessGameWindow {
    private static GameWindow gameWindow;
    public static void setGameWindow(GameWindow gw) {gameWindow = gw; }
    public static void close(){ gameWindow.dispose(); }
    public static int getWidth(){
        return gameWindow.getWidth();
    }
    public static int getHeight(){ return gameWindow.getHeight(); }
}
