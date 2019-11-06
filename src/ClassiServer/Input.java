package ClassiServer;
import java.util.*;

public class Input 
{
    Coordinate c = new Coordinate();
    
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
    
}
