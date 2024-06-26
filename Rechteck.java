import java.awt.Color;
import java.util.*;

public class Rechteck extends Figur
{
    private int breite;
    private int laenge;

    public Rechteck() {}
    
    public Rechteck(Punkt position, int breite, int laenge, String bezeichnung, Color farbe) 
    {
        super(position, bezeichnung, farbe);
        this.breite = breite;
        this.laenge = laenge;
    } 
    
    public boolean ueberlappt(Rechteck testRechteck)
    {
        HashSet<Integer> basisRechteckX = new HashSet<Integer>();
        HashSet<Integer> basisRechteckY = new HashSet<Integer>();
        
        for(int x = position.getX(); x <= (position.getX() + breite); x++)
        {
            basisRechteckX.add(x);
        }
        
        for(int y = position.getY(); y <= (position.getY() + laenge); y++)
        {
            basisRechteckY.add(y);
        }
        
        HashSet<Integer> testRechteckX = new HashSet<Integer>();
        HashSet<Integer> testRechteckY = new HashSet<Integer>();
        
        for(int x = testRechteck.getPosition().getX(); x <= (testRechteck.getPosition().getX() + testRechteck.getBreite()); x++)
        {
            testRechteckX.add(x);
        }
        
        for(int y = testRechteck.getPosition().getY(); y <= (testRechteck.getPosition().getY() + testRechteck.getLaenge()); y++)
        {
            testRechteckY.add(y);
        }
        
        basisRechteckX.retainAll(testRechteckX);
        basisRechteckY.retainAll(testRechteckY);
        
        if(basisRechteckX.size() > 0 && basisRechteckY.size() > 0)
        {
            return true;
        }

        return false;
        
    } 
    
    public void ausgabeAttribute()
    {
        System.out.println(position + "\n" + laenge + "\n" + breite + "\n" + bezeichnung + "\n" + farbe);

    }
    
    public void setBreite(int breite) 
    {
        this.breite = breite;
    }
    
    public void setLaenge(int laenge) 
    {
        this.laenge = laenge;
    }
    
    public int getBreite() 
    {
        return breite;
    }
    
    public int getLaenge() 
    {
        return laenge;
    }
    
    public int minX()
    {
        return position.getX();
    }
    
    public int minY()
    {
         return position.getY();   
    }
    
    public int maxX()
    {
         return position.getX() + breite;   
    }
    
    public int maxY()
    {
        return position.getY() + laenge;
    }
}
