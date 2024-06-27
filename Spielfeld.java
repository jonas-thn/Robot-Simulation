import java.awt.Color;
import java.util.*;

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
    
    private ArrayList<Punkt> punkteListe = new ArrayList<Punkt>(); //punkte Liste
    private ArrayList<Rechteck> hindernisse; //hindernisse Liste
    
    private Roboter bot = Roboter.getInstanz(); //roboter singleton instanz
    Leinwand leinwand = Leinwand.getInstanz(); //leinwand singleton instanz
    
    
    public Spielfeld()
    {
        
    }

    /*---------------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) //main methode mit aktueller aktion
    {   
        Spielfeld spielfeld = new Spielfeld();
        spielfeld.hindernisseZeichnen();
        spielfeld.hindernisseUmfahren();

        // spielfeld.kreiseZeichnen();
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    
    /* Hindernisse Umfahren:
     * 
     * -unser roboter hat die option zwischen 4 verschiedenen bewegungsmustern und einem rückwärts-failsafe gang zu unterschieden
     * 
     * -diese methode / der alogorithmus ist aus eigener interesse als experiment entstanden 
     *  und gehört (in diesem umfang) nicht zu den offiziellen aufgaben des projekts
     * 
     * -uns ist bewusst, dass der code dieser methode nicht besonders gut aufgebaut ist
     * --> es gibt viele wiederhohlugen, die in seperate methoden ausgelagert werden sollten
     * --> die boolean flags könnte man mit hilfe eines enums gruppieren
     * --> usw...
     * 
     * -wir haben uns primär aus zeigründen dazu entschieden die methode so zu lassen
     * -so müssen wir keine weitere variablen global definieren oder neue methoden erstellen
     * -unser informatik-projekt bleibt dadurch getrennt von diesem (aus eigener initiative entstanden) algorithmus
     * 
     * -wenn dieser text nach der abgabe des projektes noch da steht, dann haben wir keine zeit mehr gefunden, die methode aufzuräumen
     */
    public void hindernisseUmfahren() //hindernisse diagonal umfahren 
    {
        boolean normal = true; //unterscheidet zwischen vier verschiedenen bewegungsmustern
        boolean extra = false; //boolean kombination aus extra und nornal representiert gänge

        boolean rechts = true; //testet ob rechts frei ist
        boolean unten = true; //testet ob links frei wird
        boolean links = false; //testet ob rechts frei ist
        boolean oben = false; //testet ob links frei ist

        boolean ende = false; //roboter ist komplett am ende

        int failsafeCounter = 0;
        int maxFailsafe = 12; //bestimmen, wie oft der failsafe modus maximal ausgeführt wird (12 empfohlen)
        boolean failsafeModus = false; //rückwärts gang
        boolean rückwärtsCollision = false; //testet rückwärts-collision im failsafe-modus
        
        while(!ende)
        {   
            //BREAK CONDITION
            if(failsafeCounter >= maxFailsafe)
            {
                ende = true;
            }

            //FAILSAFE MODUS - SEKUNDÄERE BEWEGUNG
            if(failsafeModus) //rückwärs gang, wenn vorwärts nicht geht
            {
                if(!rückwärtsCollision)
                {
                    bot.bewegeLinks(); //fahre einen pixel nach links
                    links = true;
                    if(bot.roboterUeberlappt(hindernisse) || bot.minX() == -1) { //teste ob überlappt oder am linken rand
                        bot.bewegeRechts(); //wenn ja, dann wieder zurück
                        links = false;
                        rückwärtsCollision = true;
                    }

                    bot.bewegeOben(); //fahre einen pixel nach oben
                    oben = true;
                    if(bot.roboterUeberlappt(hindernisse) || bot.minY() == -1) { //teste ob überlappt oder am oberem rand
                        bot.bewegeUnten(); //wenn ja, wieder zurück
                        oben = false;
                        rückwärtsCollision = true;
                    } 
                }
                else if(rückwärtsCollision)
                {
                    if((!links || !oben) && (failsafeCounter % 4) == 0)
                    {
                        System.err.println("0");

                        bot.bewegeRechts(); //fahre einen pixel nach rechts
                        rechts = true;
                        if(bot.roboterUeberlappt(hindernisse) || bot.maxX() == 1000) { //teste ob überlappt oder am rechten rand
                            bot.bewegeLinks(); //wenn ja, dann wieder zurück
                            rechts = false;

                            failsafeModus = false;
                            failsafeModus = false;
                            normal = true;
                            extra = false;

                            rückwärtsCollision = false;
                            failsafeCounter++;
                        }
                    }
                    else if((!links || !oben) && (failsafeCounter % 4) == 1)
                    {
                        System.err.println("1");

                        bot.bewegeOben(); //fahre einen pixel nach oben
                        oben = true;
                        if(bot.roboterUeberlappt(hindernisse) || bot.minY() == -1) { //teste ob überlappt oder am oberem rand
                            bot.bewegeUnten(); //wenn ja, wieder zurück
                            oben = false;

                            failsafeModus = false;
                            failsafeModus = false;
                            normal = true;
                            extra = false;

                            rückwärtsCollision = false;
                            failsafeCounter++;
                        }
                    }
                    else if((!links || !oben) && (failsafeCounter % 4) == 2)
                    {
                        System.err.println("2");

                        bot.bewegeUnten(); //fahre einen pixel nach unten
                        unten = true;
                        if(bot.roboterUeberlappt(hindernisse) || bot.maxY() == 1000) { //teste ob überlappt oder am unteren rand
                            bot.bewegeOben(); //wenn ja, wieder zurück
                            unten = false;

                            failsafeModus = false;
                            failsafeModus = false;
                            normal = true;
                            extra = false;

                            rückwärtsCollision = false;
                            failsafeCounter++;
                        }
                    }
                    else if((!links || !oben) && (failsafeCounter % 4) == 3)
                    {
                        System.err.println("3");

                        bot.bewegeLinks(); //fahre einen pixel nach links
                        links = true;
                        if(bot.roboterUeberlappt(hindernisse) || bot.minX() == -1) { //teste ob überlappt oder am linken rand
                            bot.bewegeRechts(); //wenn ja, dann wieder zurück
                            links = false;

                            failsafeModus = false;
                            failsafeModus = false;
                            normal = true;
                            extra = false;

                            rückwärtsCollision = false;
                            failsafeCounter++;
                        }
                    }
                }
                  
            }

            //PRIMÄRERE BEWEGUNG
            else if(normal && !extra)
            {
                bot.bewegeRechts(); //fahre einen pixel nach rechts
                rechts = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxX() == 1000) { //teste ob überlappt oder am rechten rand
                    bot.bewegeLinks(); //wenn ja, dann wieder zurück
                    rechts = false;
                }
                
                bot.bewegeUnten(); //fahre einen pixel nach unten
                unten = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxY() == 1000) { //teste ob überlappt oder am unteren rand
                    bot.bewegeOben(); //wenn ja, wieder zurück
                    unten = false;
                } 
            
                if(!rechts && !unten){
                    normal = false; //wenn unten und rechts überlappt, dann wechsel gang

                    if((failsafeCounter >= 5) && (failsafeCounter <= 8))
                    {
                        failsafeModus = true;
                    }
                }

                if(bot.maxX() >= 999 && bot.maxY() >= 999) {
                    ende = true; //wenn ende des spielfeldes erreicht, dann ende
                }
            }
            else if(!normal && !extra)
            {
                bot.bewegeLinks(); //fahre einen pixel nach links
                links = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.minX() == -1) { //teste ob überlappt oder am linken rand
                    bot.bewegeRechts(); //wenn ja, dann wieder zurück
                    links = false;
                }

                bot.bewegeUnten(); //fahre einen pixel nach unten
                unten = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxY() == 1000) { //teste ob überlappt oder am unteren rand
                    bot.bewegeOben(); //wenn ja, wieder zurück
                    unten = false;
                } 
                else
                {
                    normal = true;
                }

                if(!links && !unten){
                    extra = true; //wenn unten und links überlappt, dann wechsel gang zu extra

                    if((failsafeCounter >= 9) && (failsafeCounter <= 12))
                    {
                        failsafeModus = true;
                    }
                }
            }
            else if(extra && !normal)
            {
                bot.bewegeRechts(); //fahre einen pixel nach rechts
                rechts = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxX() == 1000) { //teste ob überlappt oder am rechten rand
                    bot.bewegeLinks(); //wenn ja, dann wieder zurück
                    rechts = false;
                }
                
                bot.bewegeOben(); //fahre einen pixel nach oben
                oben = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.minY() == -1) { //teste ob überlappt oder am oberem rand
                    bot.bewegeUnten(); //wenn ja, wieder zurück
                    oben = false;

                    extra = true;
                    normal = true;
                } 

                if(bot.maxX() >= 999 && bot.maxY() >= 999) {
                    ende = true; //wenn ende des spielfeldes erreicht, dann ende
                }
            }
            else if(extra && normal)
            {
                bot.bewegeRechts(); //fahre einen pixel nach rechts
                rechts = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxX() == 1000) { //teste ob überlappt oder am rechten rand
                    bot.bewegeLinks(); //wenn ja, dann wieder zurück
                    rechts = false;
                }
                
                bot.bewegeUnten(); //fahre einen pixel nach unten
                unten = true;
                if(bot.roboterUeberlappt(hindernisse) || bot.maxY() == 1000) { //teste ob überlappt oder am unteren rand
                    bot.bewegeOben(); //wenn ja, wieder zurück
                    unten = false;
                } 

                if(!rechts && !unten){
                    failsafeModus = true; //failsafe modus aktivieren

                    normal = false;
                    extra = false;
                }

                if(bot.maxX() >= 999 && bot.maxY() >= 999) {
                    ende = true; //wenn ende des spielfeldes erreicht, dann ende
                }
            }
            
            leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

            Helfer.warten(bot.verlangsamung); //roboter geschwindigkeit wird verlangsamt durch kurzes warten jeden frame
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
            System.out.println("input mismatch exception - " + e); //falls input kein int -> error
        }
        finally
        {
            scanner.close(); //immer scanner schließen
        }
        
        return punkte; //eingebene punkte liste zurückgeben
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
        ArrayList<Rechteck> rechteckListe = new ArrayList<Rechteck>(); //leere Liste von Rechtecken
        Scanner scanner = new Scanner(System.in); //scanner init
        System.out.println("Anzahl der Rechtecke?: ");
        int anzahl = scanner.nextInt(); //anzahl der hindenrisse 
        int überlappCounter = 0; //mitzählen, wie oft es zu überlappungen kommt
        Rechteck kandidat; //representiert potenzielles rechteck für jeden durchlauf der loop
                
        for(int i = 0; i < anzahl; i++)
        {
            //rechteck mit zufälliger position und farbe erstellen
            kandidat = new Rechteck(Helfer.zufallsPunkt(), Helfer.zufallszahl(20,100),Helfer.zufallszahl(20,100), "Rechteck " + i, Helfer.zufallsfarbe());
        
            boolean überlapp = false;
            
            for(Rechteck r : rechteckListe) //überlappung mit allen bisherigen rechtecken der rechteck-liste testen
            {
                if(kandidat.ueberlappt(r)) 
                {
                    überlapp = true;
                    break;
                }
                
            }
            
            int freiRaum = 100;
            //start und ende bleibt im 50x50 bereich frei
            boolean imStart = (kandidat.getPosition().getX() < freiRaum) && (kandidat.getPosition().getY() < freiRaum);
            boolean imEnde = (kandidat.maxX() > (1000 - freiRaum)) && (kandidat.maxY() > (1000 - freiRaum));

            if(!überlapp && !(imStart || imEnde)) //wenn keine überlappung, dann kandidat zur rechteck liste hinzufügen
            {
                rechteckListe.add(kandidat);
            }
            else //wenn überlappung, dann counter inkrementieren & rechteck nicht hinzufügen
            {
                überlappCounter++;
            }
            
            if(überlappCounter >= 50) //wenn mehr als 50 überlappungen bevor loop zuende, dann abbruch
            {
                break;
            }
            überlappCounter = 0;
        }
        
        return rechteckListe; //liste mit rechtecken zurückgeben
    }
    
    public void hindernisseZeichnen() //zeichne hindenrisse & keine kreise
    {
        hindernisse = hindernisListeErzeugen();
        
        zeichnen(hindernisse, punkteListe);
    }
    
    public void kreiseZeichnen() //zeichne kreise & keine hindernisse
    {
        ArrayList<Punkt> punkte = punkteEingeben();
        
        zeichnen(hindernisse, punkte);
    }
    
    public void zeichnen(ArrayList<Rechteck> hindernisse, ArrayList<Punkt> punkte) //zeichne hindernisse oder rechtecke in leinwand
    {
        leinwand.zeichnen(hindernisse, punkte);
    }
    
}
