import java.util.*;
import java.awt.Color;
import java.io.PushbackInputStream;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 * 
 * @author (Thorben Thaun) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spielfeld
{
    private final int laenge=1000;
    private final int breite=1000;
    
    private ArrayList<Punkt> punkteListe = new ArrayList<Punkt>();
    
    private Roboter bot = Roboter.getInstanz();
    Leinwand leinwand = Leinwand.getInstanz();
    
    private ArrayList<Rechteck> hindernisse;
    
    public Spielfeld(){
        
    }

    public static void main(String[] args)
    {   
        Spielfeld spielfeld = new Spielfeld();
        // spielfeld.hindernisseZeichnen();
        // spielfeld.hindernisseUmfahren();

        spielfeld.kreiseZeichnen();
    }
    
    public void hindernisseUmfahren()
    {
        boolean rechts = true;
        boolean unten = false;
        boolean ende = false;
        
        while(!ende)
        {   
            bot.bewegeRechts();
            rechts = true;
            if(bot.roboterUeberlappt(hindernisse)) {
                bot.bewegeLinks();
                rechts = false;
            }
            
            bot.bewegeUnten();
            unten = true;
            if(bot.roboterUeberlappt(hindernisse)) {
                bot.bewegeOben();
                unten = false;
            } 
        
            if(!rechts && !unten){
                ende = true;
            }
            
            if(bot.maxX() == 1000 || bot.maxY() == 1000) {
                ende = true;
            }
            
            leinwand.zeichenflaeche.repaint();
            Helfer.warten(5);
        }
    }
    
    
    public ArrayList<Punkt> punkteEingeben()
    {   
        Scanner scanner = new Scanner(System.in);
        ArrayList<Punkt> punkte = new ArrayList<Punkt>(); //ein Array aus Punkten mit anzahlpunkte als laenge
        //erster Punkt ist Startpunkt 0,0, siehe oben
        try
        {
            System.out.println("Wie viele Punkte?: ");
            int anzahlPunkte = scanner.nextInt() + 1; //+1 da wir den Startpunkt mit 0,0 nach nachträglich hinzufügen
            
            for(int i = 1; i < anzahlPunkte; i++) //einzelne Punkte Indexweise angeben
            {        
                punkte.add(new Punkt(0, 0));

                System.out.println("x für Punkt " + i);
                int x = scanner.nextInt();
                
                System.out.println("y für Punkt " + i);
                int y = scanner.nextInt();
                
                if((x > 1000 || x < 0) || (y > 1000 || y < 0)) //prüfe ob Punkt im Spielfeld
                {
                    System.out.println("Error: Punkt außerhalb des Spielfeldes - wird zufällig gesetzt!");
                    x = Helfer.zufallszahl(0, 1000); //zufällige Koordinaten festlegen
                    y = Helfer.zufallszahl(0, 1000);
                }
                
                //der eben definierte Punkt wird in das Array eingefügt
                punkte.set(i - 1, new Punkt(x,y));
            } 
        }
        catch(InputMismatchException e)
        {
            System.out.println("input mismatch exception - " + e);
        }
        finally
        {
            scanner.close();
        }
        
        return punkte;
    }
    
    /**
     * Helfer Methode von punktAbfahren, die die aktuelle Liste von Punkten iteriert, um den Index des nächsten Punktes zurückzugeben.
     * Dabei ist der Startpunkt des Element 0
     */
    public int punktFinden(List<Punkt> punkt) 
    {   
        double minAbstand = Double.MAX_VALUE; //damit der nächst kleinste Abstand diesen ersetzt
        int minPunkt = 0; //erster Punkt
        
        for(int i = 1; i < punkt.size(); i++) //iterieren über unser Punkte restlichen Punkte
        {
             double abstand = punkt.get(i).gibAbstand(punkt.get(0)); //vergleiche jeweiligen Punkt der Loop mit Index I mit dem Startpunkt
             
             if(abstand < minAbstand) //vergleiche bisherigen kürzesten abstand mit aktuellem abstand
             {
                 minAbstand = abstand; // neuer kleinster abstand setzen mit aktuellen abstand
                 minPunkt = i; //neuer index vom nähchsten Punkt
             }
        }
        
        return minPunkt; //gebe index von nächsten punkt zurück
    }
    
    public void punktAbfahren()
    {
        List<Punkt> übrigePunkte = new ArrayList<Punkt>();
        List<Punkt> sortiertePunkte = new ArrayList<Punkt>();
        
        for(Punkt punkt : punkteListe) //überschreiben von punkte Liste in übrige punkte
        {
            übrigePunkte.add(punkt);
        }
        
        while(übrigePunkte.size() > 1) //solange mehr als der Startpunkt in übrige Punkte ist
        {   
            int i = punktFinden(übrigePunkte); //Index nächstgelegener Punkt
            übrigePunkte.set(0, übrigePunkte.get(i)); //neuen Startpunkt am Anfang der Liste überschreiben, mit dem nächstgelegenen Punkt
            sortiertePunkte.add(übrigePunkte.get(i)); //schreiben des nächsten Punktes in der Liste der sotierten Punkte
            übrigePunkte.remove(i); //entfernen des nächsten Punktes aus der Liste
        }
        
        for(Punkt p : sortiertePunkte) //sortierte Punkte mit Koordinaten ausgeben
        {
            System.out.println(p.getX() + " " + p.getY());
        }
    }
    
    public ArrayList<Rechteck> hindernisListeErzeugen()
    {
        ArrayList<Rechteck> rechteckListe = new ArrayList<Rechteck>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Anzahl der Rechtecke?: ");
        int anzahl = scanner.nextInt();
        int überlappCounter = 0;
        Rechteck kandidat;
                
        for(int i = 0; i < anzahl; i++)
        {
            kandidat = new Rechteck(Helfer.zufallsPunkt(), Helfer.zufallszahl(20,100),Helfer.zufallszahl(20,100), "Rechteck " + i, Helfer.zufallsfarbe());
        
            boolean überlapp = false;
            
            for(Rechteck r : rechteckListe)
            {
                if(kandidat.ueberlappt(r)) 
                {
                    überlapp = true;
                    break;
                }
                
            }
            
            if(!überlapp)
            {
                rechteckListe.add(kandidat);
            }
            else
            {
    
                überlappCounter++;
                //i--;
            }
            
            if(überlappCounter >= 50) 
            {
                break;
            }
            überlappCounter = 0;
        }
        
        return rechteckListe;
    }
    
    public void hindernisseZeichnen() 
    {
        hindernisse = hindernisListeErzeugen();
        
        zeichnen(hindernisse, punkteListe);
    }
    
    public void kreiseZeichnen()
    {
        ArrayList<Punkt> punkte = punkteEingeben();
        
        zeichnen(hindernisse, punkte);
    }
    
    public void zeichnen(ArrayList<Rechteck> hindernisse, ArrayList<Punkt> punkte)
    {
        leinwand.zeichnen(hindernisse, punkte);
    }
    
}
