import java.util.ArrayList;
public class Campo 
{
    private char [][] Campo;
    ArrayList<Barca> Barche;
    Campo(ArrayList<Barca> Barche1)throws Exception
    {
        if (Barche1.size() != 7 )
        {
            throw new Exception("numero barche diverso da 7");
        }
        this.Barche = Barche1;
        for(int k = 0; k < 21; k++)
        {
            for(int j = 0; j < 21; j++)
            {
                Campo[k][j] = 0;
            }
        }
    }
    public char getCasella(int x,int y)throws Exception
    {
        if(x < 21 || x < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        if(y < 21 || y < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
        return Campo[x][y];
    }
   
    char change_state(int x,int y,char NewState)throws Exception
    {
        if(x < 21 || x < 0 )
        {
            throw new Exception("coordinate x non vaide");
        }
        if(y < 21 || y < 0 )
        {
            throw new Exception("coordinate y non vaide");
        }
        if (NewState != 'B' && NewState != 'X')
        {
            throw new Exception("stato non vaide");
        }
        Campo[x][y] = NewState;
        return NewState;
    }
    public boolean riempi()
    {
        for(int i = 0; i < Barche.size(); i++)
        {
            for(int j = 0; j < Barche.get(i).Coordinate.size(); j++)
            {
                
            }
        }
    }
}
