package ClassiServer;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BattagliaNavaleClient {

    private JLabel messageLabel = new JLabel("...");
    
    ArrayList<Barca> Barche;
    private Campo board = new Campo(Barche); //campo di gioco
    private Campo currentSquare;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public BattagliaNavaleClient(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
    }

    public void play() throws Exception {
        try {
            String response = in.nextLine();
            char mark = response.charAt(8);
            char opponentMark = mark == '0' ? '1' : '2';
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    messageLabel.setText("Opponent moved, your turn");
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    out.println("Winner Winner");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    out.println("Hai perso!");
                    break;
                } else if (response.startsWith("TIE")) {
                    out.println("Pareggio");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    out.println("L'altro player è uscito");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        BattagliaNavaleClient client = new BattagliaNavaleClient(args[0]);
        client.play();
    }
}
