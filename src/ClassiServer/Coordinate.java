package ClassiServer;

public class Coordinate
{
    private int x;
    private int y;
    boolean inserita = false;
    
    public Coordinate()
    {
        
    }
    public Coordinate(int x,int y)throws Exception
    {   
        if(x > 21 ||  x < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        if(y > 21 || y < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
        this.x= x;
        this.y = y;
    }

    void change_state()
    {
        this.inserita = true;
    }
    public int getx()throws Exception
    {
        if(x < 21 || x < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        return this.x;
    }
    public int gety()throws Exception
    {
        if(y < 21 || y < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
        return this.y;
    }
    public void setX(int x) throws Exception 
    {
        if(x > 21 ||  x < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        this.x = x;
    }

public void setY(int y) throws Exception 
    {
        if(y > 21 || y < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
        this.y = y;
    }
}
