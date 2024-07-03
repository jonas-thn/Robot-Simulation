import java.awt.Color;
import java.util.*;


/**
 * Klasse Roboter welche die Überlappung zu Hindernissen testen kann und die Sprachbefehle verarbeitet.
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public class Roboter extends Kreis
{        
    //enum an frage stichwörtern
    enum Stichwort 
    {
        NAME,
        HERSTELLER, 
        ALT,
        GESCHLECHT
    }

    public int verlangsamung = 2; //höhere zahl = langsamere geschwindigkeit
    private static int roboterRadius = 13; //radius hier ändern
    
    static Roboter instance = new Roboter(new Punkt(0, 0), roboterRadius, "Test", Color.red); //roboter singelton pattern (nur 1x in gesamten projekt)

    private Rechteck vRoboter = new Rechteck(position, 2*getRadius(), 2*getRadius(), "Roboter", Color.white);  //virtueller Roboter als Rechteck darstellen, um überlappung zu testen

    private Scanner scanner = new Scanner(System.in); //input scanner
    
    private Roboter() {}
    
    private Roboter(Punkt position, int radius, String bezeichnung, Color farbe) 
    {
        super(position, radius, bezeichnung, farbe); //base constructor aufrufen
    } 
    
    public static Roboter getInstanz() //gibt singleton instanz von roboter
    {
        return instance;
    }
    //teste überlappung von roboter mit hindernissen (mit hilfe von virtuellem roboter als rechteck)
    public boolean roboterUeberlappt(ArrayList<Rechteck> hindernisse) 
    {
        vRoboter.position = this.position;
        for(Rechteck rechteck : hindernisse)
        {
            if(vRoboter.ueberlappt(rechteck))
            {
                return true;
            }
        }
        return false;
    }
    
    //roboter kann stichwörter erkennen und entsprechend auf fragen antworten
    public void spracherkennung()
    {
        String frage = ""; //leere frage init
        Stichwort stichwort = null; //leeres stichwort init
        
        while(!frage.contains("END")) //while loop um so lange zu fargen, bis ende
        {
            System.out.println("Stelle mir eine Frage: ");
            frage = scanner.nextLine().toUpperCase(); //frage input zu uppercase, zum vereinheitlichen

            stichwort = null; //stichwort null, falls kein stichwort gefunden wird

            for(Stichwort s : Stichwort.values()) //frage auf stichwörter untersuchen
            {
                if(frage.contains(s.toString()))
                {
                    stichwort = s; //stichwort zu übereinstimmung setzen
                    break;
                }
            
            } 
            if(stichwort != null) {
                switch(stichwort) //je nach stichwort -> output
                {
                    case NAME:
                        System.out.println("Mein Name ist Müller.\n");
                        break;
                    case HERSTELLER:
                        System.out.println("Mein Hersteller ist Thorben Thaun.\n");
                        break;
                    case ALT:
                        System.out.println("Sowas fragt man nicht.\n");
                        break;
                    case GESCHLECHT:
                        System.out.println("Ich bin binär.\n");
                        break;
                }
            } else {
                System.out.println("Die Frage verstehe ich nicht.\n");
            }
            /* -der unten beschriebene if-ansatz ist unserer meinung nach besser für dieses problem geeignet
             * -der code ist so deutlich übersichtlicher und kürzer & man muss nur jeweils eine stelle ändern um den code zu erweitern (anstatt jeweils enum und switch)
             * -das enum bewirkt im gegensatz zur if-veriante, dass nur ein zustand gleichzeitig wahr sein kann (ohne ein EnumSet<> zu verwenden und den code noch komplizierter zu machen)
             * -dadurch können nicht mehrere fragen gleichzeitig beantwortet werden
             * 
             * --> die unten kommentierte if-string-ansatz ist unserer meinung nach ein kompakterer ansatz als die enum-switch variante um diese funktion zu realisieren */

            // if(frage.contains("NAME")) { System.out.println("Mein Name ist Müller."); }
            // if(frage.contains("HERSTELLER")) { System.out.println("Mein Hersteller ist Thorben Thaun."); }
            // if(frage.contains("ALT")) { System.out.println("Sowas fragt man nicht."); }
            // if(frage.contains("GESCHLECHT")) { System.out.println("Ich bin binär."); }

            // if(!(frage.contains("NAME") || frage.contains("HERSTELLER") || frage.contains("ALT") || frage.contains("ALTER") || frage.contains("GESCHLECHT") || frage.contains("END")))
            // {
            //     System.out.println("Die Frage verstehe ich nicht.");
            // }
        }
        
        System.out.println("Tschüss!");    
    }
}
