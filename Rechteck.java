import java.awt.Color;
import java.util.*;

//rechteck erbt von figur
public class Rechteck extends Figur
{
    private int breite;
    private int laenge;

    public Rechteck() {}
    
    public Rechteck(Punkt position, int breite, int laenge, String bezeichnung, Color farbe) 
    {
        super(position, bezeichnung, farbe); //base constructor aufrufen
        this.breite = breite;
        this.laenge = laenge;
    } 
    
    public boolean ueberlappt(Rechteck testRechteck) //prüfen ob dieses rechteck mit einem anderen rechteck überlappt mit hilfe von sets/mengen und deren schnittmenge
    {
        HashSet<Integer> basisRechteckX = new HashSet<Integer>(); //dieses rechteck in x und y pixel aufgeteilt
        HashSet<Integer> basisRechteckY = new HashSet<Integer>(); //2D daten-strucktur in x und y aufteilen um verwirrung zu vermeiden
        //kein HashSet<Punkt> verwenden wegen reference type comparison (wir sind nicht sicher ob die "retainAll" Methode mit komplexen Dtentypen funktioniert oder doch nur die pointer vergleicht)        
        for(int x = position.getX(); x <= (position.getX() + breite); x++)
        {
            basisRechteckX.add(x);
        }
        
        for(int y = position.getY(); y <= (position.getY() + laenge); y++)
        {
            basisRechteckY.add(y);
        }
        
        HashSet<Integer> testRechteckX = new HashSet<Integer>(); //fremdes Rechteck in x und y pixel aufgeteilt
        HashSet<Integer> testRechteckY = new HashSet<Integer>(); //2D daten-strucktur in X und Y aufteilen um verwirrung zu vermeiden 
        //kein HashSet<Punkt> verwenden, wegen reference type comparison
        
        for(int x = testRechteck.getPosition().getX(); x <= (testRechteck.getPosition().getX() + testRechteck.getBreite()); x++)
        {
            testRechteckX.add(x);
        }
        
        for(int y = testRechteck.getPosition().getY(); y <= (testRechteck.getPosition().getY() + testRechteck.getLaenge()); y++)
        {
            testRechteckY.add(y);
        }
        
        basisRechteckX.retainAll(testRechteckX); //schnittmenge zwischen basis X und test X bilden und in basisX speichern
        basisRechteckY.retainAll(testRechteckY); //schnittmenge zwischen basis x und test x bilden und in basisX speichern
        
        return (basisRechteckX.size() > 0 && basisRechteckY.size() > 0);
        //wenn beide schnittmengen leer sind, dann keine überlappung
        //sonst überlappung
    } 

    public void ausgabeAttribute()
    {
        System.out.println(position + "\n" + laenge + "\n" + breite + "\n" + bezeichnung + "\n" + farbe);

    }
    
    //getter, setter, usw...

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
