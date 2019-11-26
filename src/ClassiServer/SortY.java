package ClassiServer;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
class SortY implements Comparator<Coordinate> 
{
    public int compare(Coordinate v, Coordinate w)
    {
        try 
        {
            return v.comparey(w);
        }
        catch (Exception ex) 
        {
            Logger.getLogger(SortY.class.getName()).log(Level.SEVERE, null, ex);
        }
       return 0;
    }
}