import java.awt.Color;
/**
 * Klasse Figur, dessen Datenfelder und Methoden an Rechteck und Kreis vererbt werden.
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public abstract class Figur //figur basis Klasse
{
    protected Punkt position; //gemeinsame Attribute
    protected String bezeichnung;
    protected Color farbe;
    
    protected Figur() {}
    
    protected Figur(Punkt position, String bezeichnung, Color farbe) //base constructor
    {
        this.position = position;
        this.bezeichnung = bezeichnung;
        this.farbe = farbe;
    }
    
    public void bewegeUm(int dx, int dy) //bewege um x/y verschiebung
    {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
    }
    
    public void bewegeRechts() //bewege rechts wrapper
    {
        bewegeUm(1, 0);
    }
    
    public void bewegeLinks() //bewege links wrapper
    {
        bewegeUm(-1, 0);
    }
    
    public void bewegeOben() //bewege oben wrapper
    {
        bewegeUm(0, -1);
    }
    
    public void bewegeUnten() //bewege unten wrapper
    {
        bewegeUm(0, 1);
    }
    
    public void bewegeUm(Punkt verschiebeVektor) //bewege um Punkt mit x/y Verschiebung
    {
        position.setX(position.getX() + verschiebeVektor.getX());
        position.setY(position.getY() + verschiebeVektor.getY());
    }
    
    //getters & setters
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
    
    //abstakte methoden
    protected abstract int minX();
    protected abstract int minY();
    protected abstract int maxX();
    protected abstract int maxY();
}
