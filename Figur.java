import java.awt.Color;
import java.util.*;

public abstract class Figur
{
    protected Punkt position;
    protected String bezeichnung;
    protected Color farbe;
    
    public Figur() {}
    
    public Figur(Punkt position, String bezeichnung, Color farbe)
    {
        this.position = position;
        this.bezeichnung = bezeichnung;
        this.farbe = farbe;
        if(farbe == Color.white) 
        {
            System.out.println("Error color is white");
        } 
    }
    
    public void bewegeUm(int dx, int dy)
    {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
    }
    
    public void bewegeRechts()
    {
        bewegeUm(1, 0);
    }
    
    public void bewegeLinks()
    {
        bewegeUm(-1, 0);
    }
    
    public void bewegeOben()
    {
        bewegeUm(0, -1);
    }
    
    public void bewegeUnten()
    {
        bewegeUm(0, 1);
    }
    
    public void bewegeUm(Punkt verschiebeVektor)
    {
        position.setX(position.getX() + verschiebeVektor.getX());
        position.setY(position.getY() + verschiebeVektor.getY());
    }
    
    public void setPosition(Punkt position) 
    {
        this.position = position;
    }
    
    public void setBezeichnung(String bezeichnung) 
    {
        this.bezeichnung = bezeichnung;
    }
    
    public void setFarbe(Color farbe) 
    {
        this.farbe = farbe;
    }
    
    public Punkt getPosition() 
    {
        return position;
    }
    
    public String getBezeichnung() 
    {
        return bezeichnung;
    }
    
    public Color getFarbe() 
    {
        return farbe;
    }
    
    abstract int minX();
    abstract int minY();
    abstract int maxX();
    abstract int maxY();
}
