import java.awt.Color;
import java.util.Random;

/**
* Klasse für zusätzliche allgmemeine Methoden, wie Zufallszahlen/-farben. Die Klasse ist statisch damit alle anderen Klassen darauf zugreifen können.
* @author Thorben Paap
* @author Jonas Thaun
*/
public class Helfer 
{
    private static Random zufall = new Random();
    
    public static int zufallszahl(int von, int bis) //zufällige Zahl (inklusive)
    {
        return (zufall.nextInt(bis - von + 1) + von);
    }
    
    public static Color zufallsfarbe() //zufällige farbe mit werten zwischen 100 und 200 (ganz dunkle und ganz helle farben vermeiden)
    {
        return new Color(zufallszahl(100, 200), zufallszahl(100, 200), zufallszahl(100, 200), 255);
    }
    
    public static Punkt zufallsPunkt(int x, int y) //zufälliger Punkt im Spielfeld
    {
        return new Punkt(zufallszahl(0, x), zufallszahl(0, y)); 
    }
    
    //warten bzw. main thread für bestimmte millisekunden stoppen
    public static void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }
}
