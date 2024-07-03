import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Robot;
import java.util.*;

/**
 * Klasse Spielfeld, welches die Hindernisse erstellt und speichert, sowie den Roboter steuert 
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public class Spielfeld
{    
    private ArrayList<Punkt> punkteListe = new ArrayList<Punkt>(); //punkte Liste
    private ArrayList<Rechteck> hindernisse; //hindernisse Liste

    private ArrayList<Punkt> routeSpeichern = new ArrayList<>();
    
    private Roboter bot = Roboter.getInstanz(); //roboter singleton instanz
    private Leinwand leinwand = Leinwand.getInstanz(); //leinwand singleton instanz

    private final int breite = Leinwand.breite;
    private final int laenge = Leinwand.laenge;

    public Spielfeld() { }

    //Wähle eine Option aus, wenn das Programm gestartet wird
    private void OptionWählen(Spielfeld spielfeld)
    {
        Scanner scanner = new Scanner(System.in); //input scanner
        String antwort;

        do
        {
            System.out.println("Wähle eine Option (1-5): \n 1 - Hindernisse umfahren \n 2 - Punkte abfahren \n 3 - Fragen beantworten \n 4 - Breadth First Search \n 5 - Programm beenden ");
        
            antwort = scanner.nextLine().strip();

            switch (antwort)
            {
                case "1": //Hindernisse umfahren (Bewegunsmuster Algorithmus)
                    hindernisse = hindernisListeErzeugen();
                    zeichnen(hindernisse, punkteListe);
                    spielfeld.hindernisseUmfahren();
                    return;

                case "2": //Punkte abfahren
                    punkteListe = spielfeld.punkteEingeben();
                    spielfeld.punkteSortieren();
                    zeichnen(hindernisse, punkteListe);
                    spielfeld.punkteAbfahren();

                    return;
                
                case "3": //Fragen beantworten
                    Roboter bot = Roboter.getInstanz();
                    bot.spracherkennung();
                    return;
                
                case "4": //Hindernisse umfahren (Breadth First Search)
                    hindernisse = hindernisListeErzeugen();
                    zeichnen(hindernisse, punkteListe);
                    spielfeld.pathfindingAlgorithm(); 
                    return;

                case "5": //Programm beenden
                    System.exit(0);
                
            }
        }
        while((antwort != "1") || (antwort != "2") || (antwort != "3") || (antwort != "4") || (antwort != "5"));

    }

    /*--------------------------------------------------MAIN--------------------------------------------------------------*/

    public static void main(String[] args) //main methode mit aktueller aktion
    {   
        Spielfeld spielfeld = new Spielfeld();
        
        spielfeld.OptionWählen(spielfeld);
    }

    /*--------------------------------------------------MAIN--------------------------------------------------------------*/

    
    /* Hindernisse Umfahren:
     * 
     * -unser roboter hat die option zwischen 4 verschiedenen bewegungsmustern und einem rückwärts-failsafe gang zu unterschieden
     * -er schickt zuerst einen Test-Lauf (in schwaz) durch die Hindernisse, welcher anhand der Bewegungsmuster verschiedene Weg-Kombinationen probiert, um ans Ziel zu gelangen
     * -der Test-Lauf (in schwarz) ist absichtlich sichtbar und verlangsamt, sodass man seinen fortschritt beobachten kann
     * -anhand der gesammelten Daten vom Test-Lauf wird die schnellste Route berechnet
     * -der eigentliche Roboter (in rot) folgt anschließend der schnellsten berechneten Route, ohne hängen zu bleiben
     * 
     * -diese methode / der alogorithmus ist aus eigener interesse als experiment entstanden 
     *  und gehört (in diesem umfang) nicht zu den offiziellen aufgaben des projekts */
     
    private void hindernisseUmfahren() //hindernisse diagonal umfahren 
    {
        enum Bewegungsmuster
        {
            rechtsRunter, //bewegungsmuster 1: diagonal noch rechts unten
            linksGerade, //bewegunsmuster 2: gerade nach links
            rechtsHoch, //bewegungsmuster 3: diagonal nach rechts oben
            runterGerade, //bewegungsmuster 4: gerade nach unten
            failsafeRückwärts //wird im notfall ausgeführt --> rückwärts bis zum ersten zusammenstoß und dann gerade in eine der 4 richtungen (abhängig vom failsafe counter)
        }

        Bewegungsmuster aktuelleBewegung = Bewegungsmuster.rechtsRunter;

        boolean rechts = true; //testet ob rechts frei ist
        boolean unten = true; //testet ob links frei wird
        boolean links = false; //testet ob rechts frei ist
        boolean oben = false; //testet ob links frei ist

        boolean ende = false; //roboter ist komplett am ende
        int failsafeCounter = 0;
        int maxFailsafe = 24; //bestimmen, wie oft der failsafe modus maximal ausgeführt wird (12 empfohlen)
        boolean rückwärtsCollision = false; //testet rückwärts-collision im failsafe-modus
        
        bot.setFarbe(Color.black);

        while(!ende)
        {   
            //BREAK CONDITION
            if(failsafeCounter >= maxFailsafe)
            {
                ende = true;
            }

            switch(aktuelleBewegung) //zwischen verschiedenen bewegungsmustern wählen
            {
                case failsafeRückwärts:
                    if(!rückwärtsCollision) //teste erste rückwärst collision beim rückwärts gang
                    {
                        links = tryLinksBewegung();
                        rückwärtsCollision = !links;

                        oben = tryHochBewegung(); 
                        rückwärtsCollision = !oben;
                    }
                    else if(rückwärtsCollision) //wähle neue richtung nach erster rückwärts collision
                    {
                        if((!links || !oben) && (failsafeCounter % 4) == 0) //wähle richtung aus, je nach failsafeCounter
                        {
                            System.err.println("0");

                            rechts = tryRechtsBewegung();
                            if(!rechts)
                            {
                                rückwärtsCollision = false;
                                failsafeCounter++;

                                aktuelleBewegung = Bewegungsmuster.rechtsRunter; //ändere wider zu gang 1
                            }
                        }
                        else if((!links || !oben) && (failsafeCounter % 4) == 1) //wähle richtung aus, je nach failsafeCounter
                        {
                            System.err.println("1");

                            oben = tryHochBewegung();
                            if(!oben)
                            {
                                rückwärtsCollision = false;
                                failsafeCounter++;

                                aktuelleBewegung = Bewegungsmuster.rechtsRunter; //ändere wider zu gang 1
                            }
                        }
                        else if((!links || !oben) && (failsafeCounter % 4) == 2) //wähle richtung aus, je nach failsafeCounter
                        {
                            System.err.println("2");

                            unten = tryRunterBewegung();
                            if(!unten)
                            {
                                aktuelleBewegung = Bewegungsmuster.rechtsRunter; //ändere wieder zu gang 1

                                rückwärtsCollision = false;
                                failsafeCounter++;
                            }
                        }
                        else if((!links || !oben) && (failsafeCounter % 4) == 3) //wähle richtung aus, je nach failsafeCounter
                        {
                            System.err.println("3");

                            links = tryLinksBewegung();
                            if(!links)
                            {
                                rückwärtsCollision = false;
                                failsafeCounter++;

                                aktuelleBewegung = Bewegungsmuster.rechtsRunter; //ändere wider zu gang 1
                            }
                        }
                    }
                    break;

                case rechtsRunter:
                    rechts = tryRechtsBewegung();

                    unten = tryRunterBewegung();
                
                    if(!rechts && !unten){
                        aktuelleBewegung = Bewegungsmuster.linksGerade; //wenn unten und rechts überlappt, dann wechsel gang
                    }

                    ende = testeObImZiel();
                    break;

                case linksGerade:
                    links = tryLinksBewegung();

                    unten = tryRunterBewegung(); //bewege solnage links bis runter möglich ist
                    if(unten)
                    {
                        aktuelleBewegung = Bewegungsmuster.rechtsRunter; //zurück zu gang 1 (diagonal)
                    }

                    if(!links && !unten){
                        aktuelleBewegung = Bewegungsmuster.rechtsHoch; //wenn unten und links überlappt, dann wechsel gang

                        if((failsafeCounter >= 5) && (failsafeCounter <= 8))
                        {
                            aktuelleBewegung = Bewegungsmuster.failsafeRückwärts;
                            System.err.println("FAILSAFE 1");
                        }
                    }
                    break;

                case rechtsHoch:
                    rechts = tryRechtsBewegung();
                    
                    oben = tryHochBewegung();
                    if(!oben)
                    {
                        if(!((failsafeCounter >= 9) && (failsafeCounter <= 12)))
                        {
                            aktuelleBewegung = Bewegungsmuster.runterGerade;
                        }
                        else
                        {
                            if(!oben && !rechts)
                            {
                                aktuelleBewegung = Bewegungsmuster.failsafeRückwärts;
                                System.err.println("FAILSAFE 2");
                            }
                        }
                    }
                    ende = testeObImZiel();
                    break;

                case runterGerade:
                    rechts = tryRechtsBewegung(); //gehe solange runter bis rechts möglich
                    
                    unten = tryRunterBewegung();

                    if(!rechts && !unten){
                        aktuelleBewegung = Bewegungsmuster.failsafeRückwärts; //failsafe modus aktivieren
                        System.err.println("FAILSAFE 0");
                    }

                    ende = testeObImZiel();
                    break;

                default:
                    ende = testeObImZiel();
            }
            
            leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

            Helfer.warten(bot.verlangsamung / 5); //roboter geschwindigkeit wird verlangsamt durch kurzes warten jeden frame (schwarzer roboter)
        }

        bot.setFarbe(Color.red); //zurücksetzen der farbe

        routeAbfahren(routeSpeichern);

    }

    private boolean tryRechtsBewegung()
    {
        bot.bewegeRechts(); //fahre einen pixel nach rechts
        if(bot.roboterUeberlappt(hindernisse) || bot.maxX() == breite) { //teste ob überlappt oder am rechten rand
            bot.bewegeLinks(); //wenn ja, dann wieder zurück
            return  false;
        }
        else
        {
            routeOptimieren();

            return true;
        }
    }

    private boolean tryLinksBewegung()
    {
        bot.bewegeLinks(); //fahre einen pixel nach links    
            if(bot.roboterUeberlappt(hindernisse) || bot.minX() == -1) { //teste ob überlappt oder am linken rand
                bot.bewegeRechts(); //wenn ja, dann wieder zurück
                return false;
            }
            else
            {
                routeOptimieren();

                return true;
            }
            
    }

    private boolean tryRunterBewegung()
    {
        bot.bewegeUnten(); //fahre einen pixel nach unten
        if(bot.roboterUeberlappt(hindernisse) || bot.maxY() == laenge) { //teste ob überlappt oder am unteren rand
            bot.bewegeOben(); //wenn ja, wieder zurück
            return false;
        } 
        else
        {
            routeOptimieren();

            return true;
        }
        
    }

    private boolean tryHochBewegung()
    {
        bot.bewegeOben(); //fahre einen pixel nach oben
            if(bot.roboterUeberlappt(hindernisse) || bot.minY() == -1) { //teste ob überlappt oder am oberem rand
                bot.bewegeUnten(); //wenn ja, wieder zurück
                return false;
            }
            else
            {
                routeOptimieren();

                return true;
            }
            
    }

    private boolean testeObImZiel() //wenn ende des spielfeldes erreicht, dann true
    {
        return bot.maxX() >= (breite -1) && bot.maxY() >= (breite -1);
    }

    //alle positionen, die man abfährt, werden in der "routeSpeichern" liste gespeichert
    //wenn man sich aber auf einer position befindet, die bereits in der liste ist, dann werden alle elemente zwischen den beiden gleichen positionen gelöscht
    //so werden alle "sackgassen" oder unnötigen "kreisverkehre" aus der liste gelöscht --> übrig bleibt die optimierte route
    private void routeOptimieren() 
    {
        int i = routeSpeichern.indexOf(bot.position);
        if(i != -1)
        {
            routeSpeichern.subList(i, routeSpeichern.size()).clear();
        }
        else if(i == -1)
        {
            Punkt p = new Punkt(bot.position.x, bot.position.y);
            routeSpeichern.add(p);
        }
    }    

    //fahre die route ab, die in "routeListe" gegeben wird
    private void routeAbfahren(ArrayList<Punkt> routeListe)
    {
        bot.setPosition(new Punkt(0, 0));

        leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

        Helfer.warten(bot.verlangsamung);

        for(Punkt nächster : routeListe)
        {
            bot.setPosition(nächster);

            leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

            Helfer.warten(bot.verlangsamung);

        }
    }
    
    private void pathfindingAlgorithm()
    {
        BFS pathfinding = new BFS();
        pathfinding.felderFinden(hindernisse); //scannt alle Felder ob frei oder nicht
        bot.setPosition(new Punkt(0, 0));

        ArrayList<Knoten> weg = pathfinding.wegErstellen(); //findet kürzesten weg mit breadth first search pathfinding-algorithmus

        ArrayList<Punkt> route = new ArrayList<Punkt>();
        for(Knoten k : weg)
        {
            route.add(k.koordinaten); //übersetzt knoten in punkte
        }        

        routeAbfahren(route); //fährt route ab
    }
    
    private ArrayList<Punkt> punkteEingeben()
    {   
        Scanner scanner = new Scanner(System.in);
        ArrayList<Punkt> punkte = new ArrayList<Punkt>(); //ein Array aus Punkten mit anzahlpunkte als laenge
        //erster Punkt ist Startpunkt 0,0, siehe oben
        try
        {
            System.out.println("Wie viele Punkte?: ");
            int anzahlPunkte = scanner.nextInt(); //anzahl punkte eingeben

            punkte.add(new Punkt(0, 0));
            
            for(int i = 1; i <= anzahlPunkte; i++) //einzelne Punkte Indexweise angeben
            {        
                punkte.add(new Punkt(0, 0));

                System.out.println("x für Punkt " + i);
                int x = scanner.nextInt();
                
                System.out.println("y für Punkt " + i);
                int y = scanner.nextInt();
                
                if((x > breite || x < 0) || (y > laenge || y < 0)) //prüfe ob Punkt im Spielfeld
                {
                    System.out.println("Error: Punkt außerhalb des Spielfeldes - wird zufällig gesetzt!");
                    x = Helfer.zufallszahl(0, breite); //zufällige Koordinaten festlegen
                    y = Helfer.zufallszahl(0, laenge);
                }
                
                //der eben definierte Punkt wird in das Array eingefügt
                punkte.set(i, new Punkt(x,y));
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
     * Helfer Methode von punkteSortieren, die die aktuelle Liste von Punkten iteriert, um den Index des nächsten Punktes zurückzugeben.
     * Dabei ist der Startpunkt des Element 0
     */
    private int punktFinden(List<Punkt> punkt) 
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
    
    private void punkteSortieren()
    {
        ArrayList<Punkt> übrigePunkte = new ArrayList<Punkt>();
        ArrayList<Punkt> sortiertePunkte = new ArrayList<Punkt>();
        
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

        punkteListe = sortiertePunkte;
    }

    //fährt die punkte in richtiger reihenfolge ab
    private void punkteAbfahren()
    {
        bot.setPosition(new Punkt(0, 0));

        for(Punkt p : punkteListe)
        {
            Punkt differenz = bot.position.gibDifferenz(p); //je nach differenz wählt roboter eine richtung aus

            while(bot.position.x != p.x) //fahre solange bis x erreicht
            {
                if(differenz.x > 0)
                {
                    bot.bewegeRechts();
                    leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

                    Helfer.warten(bot.verlangsamung);
                        
                        
                }
                else if(differenz.x < 0) //fahre solange bis x erreicht
                {
                    bot.bewegeLinks();
                    leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

                    Helfer.warten(bot.verlangsamung);

                }
            }

            while(bot.position.y != p.y) //fahre solange bis y erreicht
            {
                if(differenz.y > 0)
                {
                    bot.bewegeUnten();
                    leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

                    Helfer.warten(bot.verlangsamung);

                }
                else if(differenz.y < 0) //fahre solange bis y erreicht
                {
                    bot.bewegeOben();
                    leinwand.zeichenflaeche.repaint(); //jeden frame neu zeichnen

                    Helfer.warten(bot.verlangsamung);
                }
            }
            
        }
    }
    
    private ArrayList<Rechteck> hindernisListeErzeugen() 
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
            //zufallsPunkt vereinfacht um code zu sparen daher 900, 900 und Rechtecke mit maximaler Größe 100, 100
            kandidat = new Rechteck(Helfer.zufallsPunkt(breite - 100, laenge - 100), Helfer.zufallszahl(20,100), Helfer.zufallszahl(20,100), "Rechteck " + i, Helfer.zufallsfarbe());
            
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
            boolean imEnde = (kandidat.maxX() > (breite - freiRaum)) && (kandidat.maxY() > (laenge - freiRaum));

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

    private void zeichnen(ArrayList<Rechteck> hindernisse, ArrayList<Punkt> punkte) //zeichne hindernisse oder rechtecke in leinwand
    {
        leinwand.zeichnen(hindernisse, punkte);
    }
    
}
