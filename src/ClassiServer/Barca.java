package ClassiServer;

import java.util.ArrayList;
public class Barca 
{
    public ArrayList<Coordinate> Coordinate;
    private int puntivita;
    private boolean affondata;
    public Barca(int PV,ArrayList<Coordinate> Coordinate1)throws Exception
    {
        if(PV >= 0 && Coordinate1.size() > 0)
        {
            throw new Exception("input non vaido");
        }
        this.puntivita = PV;
        this.Coordinate = Coordinate1;
        this.affondata = false;
    }
    private boolean Colpo(Coordinate colpo)throws Exception
    {
        if(colpo.getx() > 21 ||  colpo.getx() < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        if(colpo.gety() > 21 ||  colpo.gety() < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
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
