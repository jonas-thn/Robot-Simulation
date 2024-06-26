import java.awt.Color;
import java.util.*;

public class Roboter extends Kreis
{        
    static int roboterRadius = 20;
    static Roboter instance = new Roboter(new Punkt(0, 0), roboterRadius, "Test", Color.red);
    public Rechteck vRoboter = new Rechteck(position, 2*radius, 2*radius, "Roboter", Color.white); //virtueller Roboter als Rechteck für überlappt
    private enum Stichwort
    {
        NAME,
        HERSTELLER,
        ALTER,
        GESCHLECHT
    };
    
    private Stichwort stichwort;
    Scanner scanner = new Scanner(System.in);
    
    private Roboter()
    {
        
    }
    
    private Roboter(Punkt position, int radius, String bezeichnung, Color farbe) 
    {
        super(position, radius, bezeichnung, farbe);
    } 
    
    public static Roboter getInstanz()
    {
        return instance;
    }
    
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
    
    
    public void spracherkennung()
    {
        String frage = "";
        
        
        while(!frage.contains("END"))
        {
            frage = scanner.nextLine().toUpperCase();
            
            if(frage.contains("NAME")) { System.out.println("Mein Name ist Müller."); }
            if(frage.contains("HERSTELLER")) { System.out.println("Mein Hersteller ist Thorben Thaun."); }
            if(frage.contains("ALT")) { System.out.println("Sowas fragt man nicht."); }
            if(frage.contains("GESCHLECHT")) { System.out.println("Ich bin binär."); }
        }
        
        System.out.println("Tschüss!");    
        
       
    }
}
