import java.awt.Color;
import java.util.*;

public class Roboter extends Kreis
{        
    public int verlangsamung = 3; //höhere zahl = langsamere geschwindigkeit
    static int roboterRadius = 13; //radius hier ändern
    
    static Roboter instance = new Roboter(new Punkt(0, 0), roboterRadius, "Test", Color.red); //roboter singelton pattern (nur 1x in gesamten projekt)

    //virtueller Roboter als Rechteck darstellen, um überlappung zu testen
    public Rechteck vRoboter = new Rechteck(position, 2*radius, 2*radius, "Roboter", Color.white); 

    Scanner scanner = new Scanner(System.in); //input scanner
    
    private Roboter()
    {
        
    }
    
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
        String frage = "";
        
        while(!frage.contains("END"))
        {
            System.out.println("Stelle mir eine Frage: ");
            frage = scanner.nextLine().toUpperCase();
            
            if(frage.contains("NAME")) { System.out.println("Mein Name ist Müller."); }
            if(frage.contains("HERSTELLER")) { System.out.println("Mein Hersteller ist Thorben Thaun."); }
            if(frage.contains("ALT") || frage.contains("ALTER")) { System.out.println("Sowas fragt man nicht."); }
            if(frage.contains("GESCHLECHT")) { System.out.println("Ich bin binär."); }

            if(!(frage.contains("NAME") || frage.contains("HERSTELLER") || frage.contains("ALT") || frage.contains("ALTER") || frage.contains("GESCHLECHT") || frage.contains("END")))
            {
                System.out.println("Die Frage verstehe ich nicht.");
            }
        }
        
        System.out.println("Tschüss!");    
    }
}
