import java.awt.Color;

/**
* Klasse Kreis ist eine Figur
* @author Thorben Paap
* @author Jonas Thaun
*/
public class Kreis extends Figur
{
    private int radius;
    
    public Kreis() {}

    public Kreis(Punkt position, int radius, String bezeichnung, Color farbe) 
    {
        super(position, bezeichnung, farbe); //figur base konstruktor aufrufen
        this.radius = radius;
    } 
    
    public void ausgabeAttribute()
    {
        System.out.println(position + "\n" + radius + "\n" + "\n" + bezeichnung + "\n" + farbe);
    }
    
    public void setRadius(int radius) 
    {
        this.radius = radius;
    }
    
    //getter, setter, usw...
    public int getRadius() 
    {
        return radius;
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
         return position.getX() + (2*radius);   
    }
    
    public int maxY()
    {
        return position.getY() + (2*radius);
    }
    
    public int getMidX()
    {
         return position.getX() + radius;   
    }
    
    public int getMidY()
    {
        return position.getY() + radius;
    }
}

