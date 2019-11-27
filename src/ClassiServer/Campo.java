package ClassiServer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.riempi();
    }

    Campo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if (NewState != 'B' && NewState != 'X' && NewState != 'A') 
        {
            throw new Exception("stato non vaide");
        }
        Campo[x][y] = NewState;
        return NewState;
    }
    public void riempi()throws Exception
    {
        for(int i = 0; i < Barche.size(); i++)
        {
            if(this.controllo(Barche.get(i)) == true)
            {
                for(int j = 0; j < Barche.get(i).coordinate.size(); j++)
                {
                    Campo[Barche.get(i).coordinate.get(j).getx()][Barche.get(i).coordinate.get(j).gety()]='B';
                }
            }
            else
            {
                Barche.remove(i);
            }
        }
    }
	public boolean controllo (Barca Barchetta) throws Exception
	{
		boolean first = false;
		for(int i = 0; i < Barche.size() ; i++)
		{
			for ( int j = 0 ; j < Barche.get(i).coordinate.size(); j++)
			{
				for( int k = 0; k < Barchetta.coordinate.size(); k++)
				{
					if( Barchetta.coordinate.get(0).getx() == Barchetta.coordinate.get(Barchetta.coordinate.size()).getx())
					{
						if ( first == false)
						{
							if( Barchetta.coordinate.get(0).getx()-1 == Barche.get(j).coordinate.get(k).getx() && // x-1,y+1
								Barchetta.coordinate.get(0).gety()+1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(0).getx()-1 == Barche.get(j).coordinate.get(k).getx() && // x-1,y
								Barchetta.coordinate.get(0).gety() == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(0).getx()-1 == Barche.get(j).coordinate.get(k).getx() && // x-1,y-1
								Barchetta.coordinate.get(0).gety()-1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx()+1 == Barche.get(j).coordinate.get(k).getx() && // x+1,y+1
								Barchetta.coordinate.get(0).gety()+1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx()+1 == Barche.get(j).coordinate.get(k).getx() && // x+1,y
								Barchetta.coordinate.get(0).gety() == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx()+1 == Barche.get(j).coordinate.get(k).getx() && // x+1,y-1
								Barchetta.coordinate.get(0).gety()-1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
								first = true;
						}
						if( Barchetta.coordinate.get(k).getx() == Barche.get(j).coordinate.get(k).getx() && // x,y+1
							Barchetta.coordinate.get(k).gety()+1 == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
						if( Barchetta.coordinate.get(k).getx() == Barche.get(j).coordinate.get(k).getx() && // x,y
							Barchetta.coordinate.get(k).gety() == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
						if( Barchetta.coordinate.get(k).getx() == Barche.get(j).coordinate.get(k).getx() && // x,y-1
							Barchetta.coordinate.get(k).gety()-1 == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
					}
					if( Barchetta.coordinate.get(0).gety() == Barchetta.coordinate.get(Barchetta.coordinate.size()).gety())
					{						
                                            if ( first == false)
						{
							if( Barchetta.coordinate.get(0).getx()+1 == Barche.get(j).coordinate.get(k).getx() && // x+1,y+1
								Barchetta.coordinate.get(0).gety()+1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(0).getx() == Barche.get(j).coordinate.get(k).getx() && // x,y+1
								Barchetta.coordinate.get(0).gety()+1 == Barche.get(j).coordinate.get(k).gety())
								{ return false; }
							if( Barchetta.coordinate.get(0).getx()-1 == Barche.get(j).coordinate.get(k).getx() && // x-1,y+1
								Barchetta.coordinate.get(0).gety()+1 == Barche.get(j).coordinate.get(k).gety())
								{ return false ;}
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx()+1 == Barche.get(j).coordinate.get(k).getx() && 
								Barchetta.coordinate.get(Barchetta.coordinate.size()).gety()-1 == Barche.get(j).coordinate.get(k).gety())// x+1,y-1
								{ return false;}
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx() == Barche.get(j).coordinate.get(k).getx() && 
								Barchetta.coordinate.get(Barchetta.coordinate.size()).gety()-1 == Barche.get(j).coordinate.get(k).gety())// x,y-1
								{ return false; }
							if( Barchetta.coordinate.get(Barchetta.coordinate.size()).getx()-1 == Barche.get(j).coordinate.get(k).getx() &&
								Barchetta.coordinate.get(Barchetta.coordinate.size()).gety()-1 == Barche.get(j).coordinate.get(k).gety()) // x-1,y-1
								{ return false; }
								first = true;
						}
						if( Barchetta.coordinate.get(k).getx()+1 == Barche.get(j).coordinate.get(k).getx() && // x+1,y
							Barchetta.coordinate.get(k).gety() == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
						if( Barchetta.coordinate.get(k).getx() == Barche.get(j).coordinate.get(k).getx() && // x,y
							Barchetta.coordinate.get(k).gety() == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
						if( Barchetta.coordinate.get(k).getx()-1 == Barche.get(j).coordinate.get(k).getx() && // x-1,y
							Barchetta.coordinate.get(k).gety() == Barche.get(j).coordinate.get(k).gety())
							{ return false; }
					}
				}
			}
		}
		return true;
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
        for (int i = 0; i < Barche.size();i++)
        {
        if (Barche.get(i).coordinate.contains(colpo))
        {
           int pos = Barche.get(i).coordinate.indexOf(colpo);
           Barche.get(i).coordinate.get(pos).change_state();
           Barche.get(i).setPuntivita(Barche.get(i).getPuntivita()-1);
           if(Barche.get(i).getPuntivita() == 0)
           {
               Barche.get(i).setAffondata(true);
           }
           this.change_state(Barche.get(i).coordinate.get(pos).getx(),Barche.get(i).coordinate.get(pos).gety(),'X');
           return true;
        }
      }
        return false;
    }
    public boolean vinto()
    {
        int vittoria = 0;
        for(int i = 0; i < Barche.size();i++)
        {
           if(Barche.get(i).isAffondata() == true)
           {
               vittoria++;
           }
        }
        if (vittoria == 7)
        {
            return true;
        }
        return false;
    }
    public boolean aggiungibarca(Barca b)
    {
        try 
        {
            if(this.controllo(b)== true)
            {
                this.Barche.add(b);
                this.riempi();
                return true;
            }
        }
        catch (Exception ex) 
        {
            Logger.getLogger(Campo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

