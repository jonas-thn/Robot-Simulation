import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Leinwand zum Zeichnen von Figuren auf einer Zeichenflaeche.
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public class Leinwand {

    public static final int breite=1000;
    public static final int laenge=1000;
    
    public static Leinwand instanz = new Leinwand(breite, laenge); //singleton pattern (1x instanz im gesamten projekt)
    
    public static Leinwand getInstanz()
    {
        return instanz;
    }

    private JFrame fenster;
    public Zeichenflaeche zeichenflaeche; //damit wir von Spielfeld zugreifen können

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