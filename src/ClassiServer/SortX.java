package ClassiServer;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
class SortX implements Comparator<Coordinate> 
{
    public int compare(Coordinate v, Coordinate w)
    {
        try 
        {
            return v.comparex(w);
        }
        catch (Exception ex) 
        {
            Logger.getLogger(SortX.class.getName()).log(Level.SEVERE, null, ex);
        }
       return 0;
    }
}