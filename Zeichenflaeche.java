import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

/**
 * In der Klasse Zeichenflaeche werden die Objekte gezeichnet, die auf der Leinwand zu sehen sein sollen.
 * Zeichenflaeche erbt von der Klasse JPanel.
 * @author Thorben Paap
 * @author Jonas Thaun
 */
public class Zeichenflaeche extends JPanel
{
    private Roboter roboter = Roboter.getInstanz(); //roboter singleton instanz
    private ArrayList<Rechteck> hindernisse; //zu zeichnende Hindernisse
    private ArrayList<Punkt> punkte; //zu zeichnende punkte
    
    public Zeichenflaeche() {} //leerer Konstruktor
 
    /**
     * Hier wird ein weisser Hintergrund gezeichnet, sowie die Hindernisse, Punkte und der Roboter.
     */
    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        
        // einen weissen Hintergrund zeichnen
        Dimension size = getSize();
        graphic.setColor(Color.white);
        graphic.drawRect(0, 0, size.width, size.height);
        graphic.fillRect(0, 0, size.width, size.height);
        
        //alle Rechtecke zeichnen (wenn nicht null)
        if(hindernisse != null)
        {
            for(Rechteck rechteck : hindernisse) {
                graphic.setColor(rechteck.getFarbe()); 
                graphic.drawRect(rechteck.getPosition().getX(), rechteck.getPosition().getY(), rechteck.getBreite(), rechteck.getLaenge());
                graphic.fillRect(rechteck.getPosition().getX(), rechteck.getPosition().getY(), rechteck.getBreite(), rechteck.getLaenge());  
            }
        }
        
        //alle punkte zeichnen (wenn nicht null)
        if(punkte != null)
        {
            for(Punkt punkt : punkte) {
                graphic.setColor(Color.blue); 
                graphic.drawOval(punkt.getX(), punkt.getY(), 20, 20);
                graphic.fillOval(punkt.getX(), punkt.getY(), 20, 20);  
            }
        }
        
        //roboter zeichnen
        graphic.setColor(roboter.getFarbe());
        graphic.drawOval(roboter.minX(), roboter.minY(), roboter.getRadius()*2, roboter.getRadius()*2);
        graphic.fillOval(roboter.minX(), roboter.minY(), roboter.getRadius()*2, roboter.getRadius()*2);
    }
    
    //wird genutzt um jeden frame neu zu zeichnen. Dabei werden immer Rechtecke und Punkte gezeichnet jedoch ist jeweils eine Ansammlung leer
    public void repaintFiguren(ArrayList<Rechteck> rechtecke, ArrayList<Punkt> punkte) {
        hindernisse = rechtecke;
        this.punkte = punkte;
        repaint();
    }
    
}
