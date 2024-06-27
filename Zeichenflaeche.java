import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

/**
 * In der Klasse Zeichenflaeche werden die Objekte gezeichnet, die auf der Leinwand zu sehen sein sollen.
 * Zeichenflaeche erbt von der Klasse JPanel.
 */
public class Zeichenflaeche extends JPanel
{
    private Roboter roboter; //roboter singleton instanz
    private ArrayList<Rechteck> hindernisse; //zu zeichnende Hindernisse
    private ArrayList<Punkt> punkte; //zu zeichnende punkte
    
    public Zeichenflaeche() {}
 
    /**
     * Hier wird ein weisser Hintergrund gezeichnet, sowie die Hindernisse, Punkte und der Roboter.
     * Die Methode paintComponent der Klasse JPanel mit einer eigenen Methode überschreiben
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
        Roboter roboter = Roboter.getInstanz();
        graphic.setColor(roboter.getFarbe());
        graphic.drawOval(roboter.minX(), roboter.minY(), roboter.getRadius()*2, roboter.getRadius()*2);
        graphic.fillOval(roboter.minX(), roboter.minY(), roboter.getRadius()*2, roboter.getRadius()*2);

        // //linkes auge
        // graphic.setColor(Color.white);
        // graphic.drawOval(roboter.minX() + 5, roboter.minY() + 7, roboter.getRadius() - 5, roboter.getRadius() - 5);
        // graphic.fillOval(roboter.minX() + 5, roboter.minY() + 7, roboter.getRadius() - 5, roboter.getRadius() - 5);

        // //linke pupille
        // graphic.setColor(Color.black);
        // graphic.drawOval(roboter.minX() + 7, roboter.minY() + 10, roboter.getRadius() - 10, roboter.getRadius() - 10);
        // graphic.fillOval(roboter.minX() + 7, roboter.minY() + 10, roboter.getRadius() - 10, roboter.getRadius() - 10);

        // //rechtes auge
        // graphic.setColor(Color.white);
        // graphic.drawOval(roboter.maxX() - 19, roboter.minY() + 7, roboter.getRadius() - 5, roboter.getRadius() - 5);
        // graphic.fillOval(roboter.maxX() - 19, roboter.minY() + 7, roboter.getRadius() - 5, roboter.getRadius() - 5);

        // //rechte pupille
        // graphic.setColor(Color.black);
        // graphic.drawOval(roboter.maxX() - 15, roboter.minY() + 10, roboter.getRadius() - 10, roboter.getRadius() - 10);
        // graphic.fillOval(roboter.maxX() - 15, roboter.minY() + 10, roboter.getRadius() - 10, roboter.getRadius() - 10);

    }
    
    //wird genutzt um jeden frame neu zu zeichnen
    public void repaintFiguren(ArrayList<Rechteck> rechtecke, ArrayList<Punkt> punkte) {
        hindernisse = rechtecke;
        this.punkte = punkte;
        repaint();
    }
    
}
