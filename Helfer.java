import java.util.*;
import java.awt.Color;

public class Helfer 
{
    private static Random zufall = new Random();
    
    public static int zufallszahl(int von, int bis)
    {
        return (zufall.nextInt(bis - von + 1) + von);
    }
    
    public static Color zufallsfarbe()
    {
        return new Color(zufallszahl(128, 255), zufallszahl(128, 255), zufallszahl(128, 255), 255);
    }
    
    public static Punkt zufallsPunkt()
    {
        return new Punkt(zufallszahl(0, 900), zufallszahl(0, 900)); //vereinfacht um code zu sparen
    }
    
    /**
     * Die angegebenen Millisekunden warten
     * 
     * Verwenden Sie diese Methode z.B., wenn sich der Roboter zu schnell bewegt.
     */
    public static void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }
}
