package ClassiServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                   Comparator<Coordinate> x = new SortX();
                   Collections.sort(coordinate, x);
                   return true;
            }
            if( inizioy == finey)
            {
                for( int k = 1; k < finex - iniziox; k++)
                {
                    coordinate.set(k, new Coordinate( iniziox + j, inizioy));
                    j++;
                }
                Comparator<Coordinate> y = new SortY();
                Collections.sort(coordinate, y);
                return true;
            }
            return false;
        }
        public void setAffondata(boolean aff) {
        this.affondata = aff;
    }
}