
/**
 * Beschreiben Sie hier die Klasse Punkt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

import java.lang.Math;

public class Punkt
{
    public int x,y;
    
    /**
     * Konstruktor für Objekte der Klasse Punkt
     */
    public Punkt() {}
    public Punkt(int x, int y)
    {
     this.x = x;
     this.y = y;
    
    }

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void ausgabeAttribute() 
    {
        System.out.println ("Hallo Welt");
    }
    
    public void bewegeUm(int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    
    public double gibAbstand(Punkt andererPunkt)
    {
        int dx = andererPunkt.x - this.x;
        int dy = andererPunkt.y - this.y;
        return Math.hypot(dx, dy);
    }
}
