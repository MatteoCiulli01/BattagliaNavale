package ClassiServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author informatica
 */
public class BattagliaNavaleServer {

    public static void main(String[] args) throws Exception 
    {
        Input a = new Input();
        System.out.println("Inserire Coordinate x");
        int x = a.x();
        System.out.println("Inserire Coordinate y");
        int y = a.y();
        System.out.println("Coordinate scelte: "+x+","+y);
    }
    
}
