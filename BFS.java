import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Klasse Breath First Search als Pathfinding-Algorithmus, um den schnellsten Weg durch das Spielfeld zu finden
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public class BFS 
{
    private HashMap<Punkt, Knoten> felder = new HashMap<Punkt, Knoten>();
    private Roboter bot = Roboter.getInstanz();
    private Leinwand leinwand = Leinwand.getInstanz();

    private Punkt startKoordinate = new Punkt(0, 0);
    private Punkt zielKoordinate = new Punkt(Leinwand.breite - 2*bot.getRadius(), Leinwand.laenge - 2*bot.getRadius());

    private Knoten startKnoten = new Knoten(startKoordinate, true);
    private Knoten zielKnoten = new Knoten(zielKoordinate, true);
    private Knoten aktuellerSuchKnoten;

    private HashMap<Punkt, Knoten> erreichteFelder = new HashMap<Punkt, Knoten>();
    private Queue<Knoten> nächsterQueue = new LinkedList<Knoten>(); //FIFO order

    private Punkt[] richtungen = 
    { 
        new Punkt(1, 0), //rechts
        new Punkt(-1, 0), //links
        new Punkt(0, -1), //hoch
        new Punkt(0, 1) //runter
    };

    //gib Knoten an bestimmten Punkt
    public Knoten getKnoten(Punkt koordinaten)
    {
        if(felder.containsKey(koordinaten))
        {
            return felder.get(koordinaten);
        }

        return null;
    }

    //gib alle felder
    public HashMap<Punkt, Knoten> getFelder()
    {
        return felder;
    }

    // O(n^2) = ca. 1 millionen
    // Scanne alle Punkte auf dem Spielfeld und erstelle entsprechende Knoten
    public void felderFinden(ArrayList<Rechteck> hindernisse)
    {
        for(int x = 0; x < (Leinwand.breite - 2*bot.getRadius()); x++)
        {
            for(int y = 0; y < (Leinwand.laenge - 2*bot.getRadius()); y++)
            {
                Punkt koordinaten = new Punkt(x, y);
                bot.setPosition(koordinaten); //setze roboter zu jedem punkt um überlappungen zu testen
                leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen
                
                felder.put(koordinaten, new Knoten(koordinaten, !bot.roboterUeberlappt(hindernisse))); //knoten hinzufügen
            }
        }
    }

    private void nachbarnFinden()
    {
        ArrayList<Knoten> nachbarn = new ArrayList<Knoten>();

        for(Punkt richtung : richtungen) //in alle vier richtungen suchen (von aktuellem such knoten aus)
        {
            Punkt nachbarKoordinate = new Punkt(aktuellerSuchKnoten.koordinaten.x + richtung.x, aktuellerSuchKnoten.koordinaten.y + richtung.y);

            if(felder.containsKey(nachbarKoordinate))
            {
                nachbarn.add(felder.get(nachbarKoordinate)); //wenn nachbarkoordinate im feld, dann zu nachbar liste hinzufügen
            }
        }

        for(Knoten nachbar : nachbarn) //für jeden nachbar in nachbar liste
        {
            if(!erreichteFelder.containsKey(nachbar.koordinaten) && nachbar.istFrei) //wenn nachbar nochnicht erreicht & frei 
            {
                nachbar.verbundenMit = aktuellerSuchKnoten; //verbindung herstellen
                erreichteFelder.put(nachbar.koordinaten, nachbar); //nachbar in erreichte felder packen
                nächsterQueue.add(nachbar); //nachbar als nächstes element in queue
            }
        }
    }

    private void breadthFirstSearch() 
    {
        boolean aktiv = true;

        nächsterQueue.add(startKnoten);
        erreichteFelder.put(startKoordinate, startKnoten);

        while(nächsterQueue.size() > 0 && aktiv) //suche bis ziel gefunden oder queue leer (feld voll)
        {
            aktuellerSuchKnoten = nächsterQueue.poll(); //aktueller knoten erstem element in queue setzen
            aktuellerSuchKnoten.erkundet = true; 
            nachbarnFinden(); //alle nachbarn zu aktuellem knoten finden

            if(aktuellerSuchKnoten.koordinaten.equals(zielKoordinate)) //wenn ziel -> fertig
            {
                zielKnoten = aktuellerSuchKnoten; // Zielknoten setzen
                aktiv = false;
            }
        }
    }

    public  ArrayList<Knoten> wegErstellen() //weg rückwärts finden
    {
        breadthFirstSearch();

        ArrayList<Knoten> weg = new ArrayList<Knoten>();
        Knoten aktuellerKnoten = zielKnoten; //am Zeil starten

        if (aktuellerKnoten == null) {
            return weg; // falls kein Weg gefunden wurde
        }

        weg.add(aktuellerKnoten);
        aktuellerKnoten.istWeg = true; //aktueller knoten zu weg setzen

        while(aktuellerKnoten.verbundenMit != null) //solange bis alle verbindungen von aktuellem 0 sind
        {
            aktuellerKnoten = aktuellerKnoten.verbundenMit; //verbindung wird zu neuem aktuellen knoten
            weg.add(aktuellerKnoten);
            aktuellerKnoten.istWeg = true; //aktueller knoten zu weg setzen
        }

        Collections.reverse(weg); //liste umdrehen 

        return weg;
    }
}
