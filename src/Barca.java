import java.util.ArrayList;
public class Barca 
{
    private ArrayList<Coordinate> Coordinate;
    private int puntivita;
    private boolean affondata;
    public Barca(int PV,ArrayList<Coordinate> Coordinate1)
    {
        this.puntivita = PV;
        this.Coordinate = Coordinate1;
        this.affondata = false;
    }
    private boolean Colpo(Coordinate colpo)
    {
        if (Coordinate.contains(colpo))
        {
           int pos = Coordinate.indexOf(colpo);
           Coordinate.get(pos).change_state();
           puntivita--;
           if(puntivita == 0)
           {
               affondata = true;
           }
           return true;
        }
        else
        {
            return false;
        }
        
    }
}