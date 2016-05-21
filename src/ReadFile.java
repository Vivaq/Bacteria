import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

/**
 *Klasa do czytania z pliku
 */
public class ReadFile {
    /**
     * przechowuje linijki z pliku
     */
    String textLine = "";
    /**
     *polozenie stalych obiektow
     */
    public static JSONObject objectsCoordinates;
    /**
     *wymiary okna
     */
    public static JSONArray wDimensions;
    /**
     *max przeciwnikow w jednym momencie
     */
    public static int maxEnemies;
    /**
     *max czas gry na planszy
     */
    public static int time;
    /**
     *punktacja
     */
    public static double scoring;
    /**
     *Odczytje konfiguracje z pliku
     * @param levelNr konfiguracja dla danego poziomu
     */
    public ReadFile(String filePath, int levelNr) throws IOException {
        /**
         *obiekt do odczytywania z pliku
         */
        FileReader fileReader = new FileReader(filePath);//obiekt do czytania pliku

        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (!textLine.startsWith("#" + levelNr)) textLine = bufferedReader.readLine();
            JSONObject data = new JSONObject(bufferedReader.readLine());
            maxEnemies = (int) data.get("maximum enemies");
            time = (int) data.get("time");
            scoring = (double) data.get("scoring");
            if(data.has("objects"))
                objectsCoordinates = (JSONObject) data.get("objects");
            wDimensions = (JSONArray) data.get("dim");
        }
        catch (NullPointerException ignored){}
    }
}

