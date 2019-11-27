package ClassiServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client -> Server
 *     MOSSA <n>
 *     USCITA
 *
 * Server -> Client
 *     BENVENUTO <char>!
 *     VALID_MOVE MOSSA VALIDA
 *     OTHER_PLAYER_MOVED IL TUO AVVERSARIO HA COLPITO <n>
 *     OTHER_PLAYER_LEFT L'AVVERSARIO HA LASCIATO LA PARTITA
 *     VICTORY VITTORIA, BEN FATTO <char> MA HAI VINTO LA BATTAGLIA NON LA GUERRA!
 *     SCONFITTA, DAVVERO UN PECCATO <char> ALLA BASE PRENDERANNO SERI PROVVEDIMENTI.
 *     MESSAGGIO <text>
 */

public class BattagliaNavaleServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(58901)) {
            System.out.println("INIZIALIZZO LA BATTAGLIA...");
            ExecutorService pool = Executors.newFixedThreadPool(2);
                Gioco game = new Gioco();
                pool.execute(game.new Giocatore(listener.accept(), "Giocatore 1"));
                pool.execute(game.new Giocatore(listener.accept(), "Giocatore 2"));
            }
        }
    }


class Gioco {

    Giocatore giocatoreCorrente;


    public synchronized void mossa(int x,int y, Giocatore player,Campo[][] campo) {                
        if (player != giocatoreCorrente) {
            throw new IllegalStateException("Non è il tuo turno ancora...");
        } else if (player.avversario == null) {
            throw new IllegalStateException("Non hai ancora un avversario");
        } else if (campo[x][y] != null) {
            throw new IllegalStateException("Cella colpita in precedenza, riprova.");
        }
        giocatoreCorrente = giocatoreCorrente.avversario;
    }

    class Giocatore implements Runnable {
        String id;
        Giocatore avversario;
        private Campo[][] campo = new Campo[21][21];

        Socket socket;
        Scanner input;
        PrintWriter output;

        public Giocatore(Socket socket, String id) {
            this.socket = socket;
            this.id = id;
        }
        
        @Override
        public void run() {
            try {
                setup();
                while(true)gestioneProcessiMossa();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (avversario != null && avversario.output != null) {
                    avversario.output.println("L'AVVERSARIO HA LASCIATO LA PARTITA...");
                }
                try {socket.close();} catch (IOException e) {}
            }
        }
        
        
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("BENVENUTO " + id + "!");
            if (id == "Giocatore 1") {
                giocatoreCorrente = this;
                output.println("Aspetto l'avversario...");
            } else {
                avversario = giocatoreCorrente;
                avversario.avversario = this;
                avversario.output.println("Dammi le coordinate della casella che vuoi colpire");
            }
        }

        private int processCommandsX() {                    
               String command = input.nextLine();
                int x = 0;
                Input i = new Input();
                
                try{
                i.x(Integer.parseInt(command.substring(2)));
                }
                catch(Exception e){
                }
                if (command.startsWith("USCITA")) {
                    return -1;
                } else if (command.startsWith("X") ) {
                   x = Integer.parseInt(command.substring(2));
                }
                return x;
         }
        private int processCommandsY(){
                String command = input.nextLine();
                int y = 0;  
                Input i = new Input();
                try{
                i.y(Integer.parseInt(command.substring(2)));
                }
                catch(Exception e){
                }
                if (command.startsWith("USCITA")) {
                    return -1;
                } else if (command.startsWith("Y") ) {
                   y = Integer.parseInt(command.substring(2));
                  }
                return y;
        }
        

             public void campo() {
               String command = input.nextLine();
                if(command.startsWith("campo"))
                {
                    output.println("Giocatore{" + "campo=" + campo + '}');
                }
        }
            private void gestioneProcessiMossa() {                     
            try {
 
                int x = processCommandsX();
                int y = processCommandsY();
                mossa(x,y, this,campo);
                output.println("VALID_MOVE");
                avversario.output.println("OPPONENT_MOVED " + x+","+y);
            } catch (IllegalStateException e) {
                output.println("MESSAGGIO " + e.getMessage());
            }
        }
    }
}
