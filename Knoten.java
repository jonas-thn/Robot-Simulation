/**
 *  Klasse Knoten wird genutzt, um in Breadth First Search das Spielfeld in Knoten-Punkte zu zerlegen
 * @author Thorben Paap
 * @author Jonas Thaun
 */

 //Datenstruktur, die alle Eigenschaften von einem Knoten-Punkt speichert
public class Knoten {

    public Punkt koordinaten; 
    public boolean istFrei;
    public boolean erkundet;
    public boolean istWeg;
    public Knoten verbundenMit;

    public Knoten(Punkt koordinaten, boolean istFrei)
    {
        this.koordinaten = koordinaten;
        this.istFrei = istFrei;
    }
}
