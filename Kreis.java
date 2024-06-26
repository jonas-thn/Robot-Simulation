import java.awt.Color;
import java.util.*;

public class Kreis extends Figur
{
    protected int radius;

    public Kreis() {}
    
    public Kreis(Punkt position, int radius, String bezeichnung, Color farbe) 
    {
        super(position, bezeichnung, farbe);
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

