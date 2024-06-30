public class Knoten {

    public Punkt koordinaten;
    public boolean istFrei;
    public boolean erkundet;
    public boolean istWeg;
    public Knoten verbundenMit;

    public Knoten(Punkt koordinaten, boolean istFrei)
    {
        this.koordinaten = koordinaten;
        this.istFrei = istFrei;
    }
}
