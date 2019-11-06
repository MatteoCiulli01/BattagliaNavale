public class Coordinate 
{
    private int x;
    private int y;
    boolean inserita = false;
    public Coordinate(int x,int y)
    {
        this.x= x;
        this.y = y;
    }
    void change_state()
    {
        this.inserita = true;
    }
}