import java.awt.Robot;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//breadth first search
public class BFS 
{
    Punkt startKoordinate = new Punkt(0, 0);
    Punkt zielKoordinate = new Punkt(974, 974);
    

    HashMap<Punkt, Knoten> felder = new HashMap<Punkt, Knoten>();
    Roboter bot = Roboter.getInstanz();
    Leinwand leinwand = Leinwand.getInstanz();

    Knoten startKnoten = new Knoten(startKoordinate, true);
    Knoten zielKnoten = new Knoten(zielKoordinate, true);
    Knoten aktuellerSuchKnoten;

    HashMap<Punkt, Knoten> erreichteFelder = new HashMap<Punkt, Knoten>();
    Queue<Knoten> queue = new LinkedList<Knoten>(); //FIFO order

    private Punkt[] richtungen = 
    { 
        new Punkt(1, 0), //rechts
        new Punkt(-1, 0), //links
        new Punkt(0, -1), //hoch
        new Punkt(0, 1) //runter
    };

    public Knoten getKnoten(Punkt koordinaten)
    {
        if(felder.containsKey(koordinaten))
        {
            return felder.get(koordinaten);
        }

        return null;
    }

    public HashMap<Punkt, Knoten> getFelder()
    {
        return felder;
    }

    // O(n^2) = ca. 1 millionen
    public void FelderFinden(ArrayList<Rechteck> hindernisse)
    {
        for(int x = 0; x < 975; x++)
        {
            for(int y = 0; y < 975; y++)
            {
                Punkt koordinaten = new Punkt(x, y);
                bot.setPosition(koordinaten);
                leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen
                
                felder.put(koordinaten, new Knoten(koordinaten, !bot.roboterUeberlappt(hindernisse)));
            }
        }
    }

    private void NachbarnFinden()
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
                queue.add(nachbar); //nachbar als nächstes element in queue
            }
        }
    }

    private void BreadthFirstSearch() 
    {
        boolean isRunning = true;

        queue.add(startKnoten);
        erreichteFelder.put(startKoordinate, startKnoten);

        while(queue.size() > 0 && isRunning) //suche bis ziel gefunden oder queue leer (feld voll)
        {
            aktuellerSuchKnoten = queue.poll(); //aktueller knoten erstem element in queue setzen
            aktuellerSuchKnoten.erkundet = true; 
            NachbarnFinden(); //alle nachbarn zu aktuellem knoten finden

            if(aktuellerSuchKnoten.koordinaten.equals(zielKoordinate)) //wenn ziel -> fertig
            {
                zielKnoten = aktuellerSuchKnoten; // Zielknoten setzen (?????)
                isRunning = false;
            }
        }
    }

    public  ArrayList<Knoten> WegErstellen() //weg rückwärts finden
    {
        BreadthFirstSearch();

        ArrayList<Knoten> weg = new ArrayList<Knoten>();
        Knoten aktuellerKnoten = zielKnoten; //am Zeil starten

        if (aktuellerKnoten == null) {
            return weg; // falls kein Weg gefunden wurde (???????)
        }

        weg.add(aktuellerKnoten);
        aktuellerKnoten.istWeg = true; //aktueller knoten zu weg setzen

        while(aktuellerKnoten.verbundenMit != null) //solange bis alle verbindungen von aktuellem 0 sind
        {
            aktuellerKnoten = aktuellerKnoten.verbundenMit; //verbindung wird zu neuem aktuellen knoten
            weg.add(aktuellerKnoten);
            aktuellerKnoten.istWeg = true; //aktueller knoten zu weg setzen
        }

        Collections.reverse(weg);

        return weg;
    }
}
