package ClassiServer;
import java.util.*;

public class Input 
{
    Coordinate c = new Coordinate();
    Barca b = new Barca();
    
    public int x(int v) throws Exception
    {
    int x;
    x = v;
    c.setX(x);
    return x;
    }
    public int y(int v) throws Exception
    {
    int y;
    y= v;
    c.setY(y);
    return y;
    }
    public int PV() throws Exception
    {
    int pv;
    Scanner myObj = new Scanner(System.in);  
    pv = myObj.nextInt();
    b.setPuntivita(pv);
    return pv;
    }
    
    public void change_state()
    {
       b.getPuntivita();
    }
}
