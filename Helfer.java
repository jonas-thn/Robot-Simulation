import java.awt.Color;
import java.util.*;

//Helfter Klasse mit statischen Methoden, die wir häufig von überall benötigen
public class Helfer 
{
    private static Random zufall = new Random();
    
    public static int zufallszahl(int von, int bis) //zufällige Zahl (inklusive)
    {
        return (zufall.nextInt(bis - von + 1) + von);
    }
    
    public static Color zufallsfarbe() //zufällige farbe mit werten zwischen 128 und 256
    {
        return new Color(zufallszahl(128, 255), zufallszahl(128, 255), zufallszahl(128, 255), 255);
    }
    
    public static Punkt zufallsPunkt() //zufälliger Punkt im Spielfeld
    {
        return new Punkt(zufallszahl(0, 900), zufallszahl(0, 900)); //vereinfacht um code zu sparen
    }
    
    //main thread für bestimmte millisekunden stoppen
    public static void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }
}
