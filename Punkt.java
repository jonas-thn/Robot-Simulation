
//representiert einen punkt auf dem spielfeld

import java.util.Objects;

public class Punkt
{
    public int x,y; //koordinaten
    
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
    
    public void bewegeUm(int dx, int dy) //bewege punkt um x/y verschiebung
    {
        x += dx;
        y += dy;
    }
    
    public double gibAbstand(Punkt andererPunkt) //gibt abstand zwischen diesem punkt und anderem punkt 
    {
        int dx = andererPunkt.x - this.x;
        int dy = andererPunkt.y - this.y;
        return Math.hypot(dx, dy);
    }

    public Punkt gibDifferenz(Punkt andererPunkt)
    {
        int dx = andererPunkt.x - this.x;
        int dy = andererPunkt.y - this.y;
        return new Punkt(dx, dy);
    }

    @Override
    public boolean equals(Object o) //equals überschreiben um reference basierte vergleiche zu vermiden (wird zur optimierung der route benötigt --> damit .conatins() und .indexOf() funktionieren)
    {
        Punkt p = (Punkt)o;
        if(this.x == p.x && this.y == p.y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() { //hash code auch überschreiben um sicher zu gehen
        return Objects.hash(x, y);
    }

}
