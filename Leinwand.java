import java.awt.Dimension;
import java.util.*;
import javax.swing.JFrame;

/**
 * Leinwand zum Zeichnen von Figuren auf einer Zeichenflaeche.
 */

public class Leinwand {
    
    static Leinwand instanz = new Leinwand(1000, 1000); //singleton pattern (1x instanz im gesamten projekt)
    
    static Leinwand getInstanz()
    {
        return instanz;
    }

    private JFrame fenster;
    public Zeichenflaeche zeichenflaeche; 

    /**
     * Leinwand erzeugen indem laenge und breite gesetzt und eine zeichenfläche zugeordnet wird.
     */
    private Leinwand(int laenge, int breite) {
        fenster = new JFrame();
        zeichenflaeche = new Zeichenflaeche();
        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle("Robotersimulation");
        zeichenflaeche.setPreferredSize(new Dimension(laenge, breite));
        fenster.pack();
        fenster.setVisible(true);
    }
    
    /**
     * Das uebergebene Figur-Objekt auf die Leinwand zeichnen.
     */
    public void zeichnen(ArrayList<Rechteck> hindernisse, ArrayList<Punkt> punkte) {
        zeichenflaeche.repaintFiguren(hindernisse, punkte);
    }

}