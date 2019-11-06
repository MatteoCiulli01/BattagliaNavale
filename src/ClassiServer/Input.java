package ClassiServer;
import java.util.*;

public class Input 
{
    Coordinate c = new Coordinate();
    Barca b = new Barca();
    
    public int x() throws Exception
    {
    int x;
    Scanner myObj = new Scanner(System.in);  
    x = myObj.nextInt();
    c.setX(x);
    return x;
    }
    public int y() throws Exception
    {
    int y;
    Scanner myObj = new Scanner(System.in);  
    y = myObj.nextInt();
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
       b.getPuntivita()
    }
}
