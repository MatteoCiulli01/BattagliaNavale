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
 *     MOSSA VALIDA
 *     IL TUO AVVERSARIO HA COLPITO <n>
 *     L'AVVERSARIO HA LASCIATO LA PARTITA...
 *     VITTORIA, BEN FATTO SOLDATO MA HAI VINTO LA BATTAGLIA NON LA GUERRA!
 *     SCONFITTA, DAVVERO UN PECCATO SOLDATO, ALLA BASE PRENDERANNO SERI PROVVEDIMENTI.
 *     MESSAGGIO <text>
 */

public class BattagliaNavaleServer {
    public static void main(String[] args) throws IOException {
        
        // Inizializzo Connessione
        try (ServerSocket listener = new ServerSocket(56789)) {
            System.out.println("INIZIALIZZO LA BATTAGLIA...");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            while (true) {
                Gioco game = new Gioco();
                pool.execute(game.new Giocatore(listener.accept(), "Giocatore 1"));
                pool.execute(game.new Giocatore(listener.accept(), "Giocatore 2"));
            }
        }
    }
}

class Gioco {
    
    // Processi relativi al campo di gioco con relative condizioni
    
    private Giocatore[] campo = new Giocatore[9];
    Giocatore giocatoreCorrente;
    
    // Condizioni per la vincita di un giocatore
    public boolean Vincita() {                  /* Da gestire */
        return (campo[0] != null && campo[0] == campo[1] && campo[0] == campo[2])
            || (campo[3] != null && campo[3] == campo[4] && campo[3] == campo[5])
            || (campo[6] != null && campo[6] == campo[7] && campo[6] == campo[8])
            || (campo[0] != null && campo[0] == campo[3] && campo[0] == campo[6])
            || (campo[1] != null && campo[1] == campo[4] && campo[1] == campo[7])
            || (campo[2] != null && campo[2] == campo[5] && campo[2] == campo[8])
            || (campo[0] != null && campo[0] == campo[4] && campo[0] == campo[8])
            || (campo[2] != null && campo[2] == campo[4] && campo[2] == campo[6]
        );
    }

    // Riempimento caselle campo
    public boolean riempimentoCampo() {                 /*Da gestire*/
        return Arrays.stream(campo).allMatch(p -> p != null);
    }

    // Gestione scelta mossa di un client
    public synchronized void mossa(int casella, Giocatore player) {                 /*Da gestire*/
        if (player != giocatoreCorrente) {
            throw new IllegalStateException("Non è il tuo turno ancora...");
        } else if (player.avversario == null) {
            throw new IllegalStateException("Non hai ancora un avversario");
        } else if (campo[casella] != null) {
            throw new IllegalStateException("Cella colpita in precedenza, riprova.");
        }
        campo[casella] = giocatoreCorrente;
        giocatoreCorrente = giocatoreCorrente.avversario;
    }

    /**
     * Un giocatore è identificato da un identificativo ovvero "Giocatore 1" o "Giocatore 2"
     * Per la comunicazione col client il giocatore ha attributi associati ovvero:
     * - La sua Socket - Scanner(input) - PrintWriter(output)
     */
    class Giocatore implements Runnable {
        String id;
        Giocatore avversario;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Giocatore(Socket socket, String id) {
            this.socket = socket;
            this.id = id;
        }

        // classe run per gestire la connessione tra le socket dei due client
        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (avversario != null && avversario.output != null) {
                    avversario.output.println("L'AVVERSARIO HA LASCIATO LA PARTITA...");
                }
                try {socket.close();} catch (IOException e) {}
            }
        }

        // Inizializzazione connessione con relativi messaggi
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("BENVENUTO " + id + "!");
            if (id == "Giocatore 1") {
                giocatoreCorrente = this;
                output.println("MESSAGGIO Aspetto l'avversario...");
            } else {
                avversario = giocatoreCorrente;
                avversario.avversario = this;
                avversario.output.println("MESSAGGIO Dammi le coordinate della casella che vuoi colpire");
            }
        }

        // Comandi per output duranti i processi
        private void processCommands() {                    /*Da gestire*/
            while (input.hasNextLine()) {
                String command = input.nextLine();
                if (command.startsWith("USCITA")) {
                    return;
                } else if (command.startsWith("MOSSA")) {
                    gestioneProcessiMossa(Integer.parseInt(command.substring(5)));
                }
            }
        }

        // Gestione processi durante la mossa relativa e scelta dal giocatore
        private void gestioneProcessiMossa(int casella) {                      /*Da gestire*/
            try {
                mossa(casella, this);
                output.println("MOSSA VALIDA");
                avversario.output.println("OPPONENT_MOVED " + casella);
                if (Vincita()) {
                    output.println("VITTORIA, BEN FATTO SOLDATO MA HAI VINTO LA BATTAGLIA NON LA GUERRA!");
                    avversario.output.println("SCONFITTA, DAVVERO UN PECCATO SOLDATO, ALLA BASE PRENDERANNO SERI PROVVEDIMENTI.");
                }
            } catch (IllegalStateException e) {
                output.println("MESSAGGIO " + e.getMessage());
            }
        }
    }
}