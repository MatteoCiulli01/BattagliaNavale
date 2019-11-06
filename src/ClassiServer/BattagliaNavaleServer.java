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
    // Board cells numbered 0-8, top to bottom, left to right; null if empty
    private Giocatore[] campo = new Giocatore[9];

    Giocatore giocatoreCorrente;

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

    public boolean riempimentoCampo() {                 /*Da gestire*/
        return Arrays.stream(campo).allMatch(p -> p != null);
    }

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
     * A Player is identified by a character mark which is either 'X' or 'O'.
     * For communication with the client the player has a socket and associated
     * Scanner and PrintWriter.
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

        private void gestioneProcessiMossa(int casella) {                      /*Da gestire*/
            try {
                mossa(casella, this);
                output.println("VALID_MOVE");
                avversario.output.println("OPPONENT_MOVED " + casella);
                if (Vincita()) {
                    output.println("VICTORY");
                    avversario.output.println("DEFEAT");
                }
            } catch (IllegalStateException e) {
                output.println("MESSAGGIO " + e.getMessage());
            }
        }
    }
}
