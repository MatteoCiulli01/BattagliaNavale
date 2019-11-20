package ClassiServer;

import java.util.ArrayList;
import java.util.Collections;
public class Barca 
{
    public ArrayList<Coordinate> coordinate;
    private int puntivita;
    private boolean affondata;
    
    public Barca()
    {}
    public Barca(int PV,ArrayList<Coordinate> Coordinate1)throws Exception
    {
        if(PV >= 0 && Coordinate1.size() == 2)
        {
            throw new Exception("input non vaido");
        }
        this.puntivita = PV;
        this.coordinate = Coordinate1;
        this.affondata = false;
        this.riempi();
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
        if (coordinate.contains(colpo))
        {
           int pos = coordinate.indexOf(colpo);
           coordinate.get(pos).change_state();
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

    public ArrayList<Coordinate> getCoordinate() {
        return coordinate;
    }

    public int getPuntivita() {
        return puntivita;
    }

    public boolean isAffondata() {
        return affondata;
    }

    public void setCoordinate(ArrayList<Coordinate> coordinate) {
        this.coordinate = coordinate;
    }

    public void setPuntivita(int puntivita) {
        this.puntivita = puntivita;
    }
	public boolean riempi () throws Exception
	{
            int iniziox = coordinate.get(0).getx();
            int inizioy = coordinate.get(0).gety(); 
            int finex = coordinate.get(1).getx();
            int finey = coordinate.get(1).gety();
            int j = 0;
            if( iniziox == finex)
            {
                    for( int k = 1; k < finey - inizioy; k++)
                    {
                        coordinate.set(k, new Coordinate( iniziox, inizioy + j));
                        j++;
                    }
                    //insert sortx here
            }
            if( inizioy == finey)
            {
                for( int k = 1; k < finex - iniziox; k++)
                {
                    coordinate.set(k, new Coordinate( iniziox + j, inizioy));
                    j++;
                }
                Collections.sort(this.coordinate, new SortY());
            }
}         
}
