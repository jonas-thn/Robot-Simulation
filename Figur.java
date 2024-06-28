import java.awt.Color;

public abstract class Figur //figur base classe
{
    protected Punkt position; //gemeinsame attribute
    protected String bezeichnung;
    protected Color farbe;
    
    public Figur() {}
    
    public Figur(Punkt position, String bezeichnung, Color farbe) //base constructor
    {
        this.position = position;
        this.bezeichnung = bezeichnung;
        this.farbe = farbe;
        // if(farbe == Color.white) 
        // {
        //     System.out.println("Error color is white");
        // } 
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
    abstract int minX();
    abstract int minY();
    abstract int maxX();
    abstract int maxY();
}
