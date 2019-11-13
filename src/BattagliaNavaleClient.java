import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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


public class BattagliaNavaleClient {

    private JFrame frame = new JFrame("Battaglia Navale");
    private JLabel messageLabel = new JLabel("...");

    private Tavola[] campo = new Tavola[9];                 /*Da gestire*/
    private Tavola casellaCorrente;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public BattagliaNavaleClient(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        JPanel campoPanel = new JPanel();
        campoPanel.setBackground(Color.black);
        campoPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int  i = 0; i < campo.length; i++) {                   /*Da gestire*/
            final int j = i;
            campo[i] = new Tavola();
            campo[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    casellaCorrente = campo[j];
                    out.println("MOVE " + j);
                }
            });
            campoPanel.add(campo[i]);
        }
        frame.getContentPane().add(campoPanel, BorderLayout.CENTER);
    }

    public void Gioco() throws Exception {                  /*Da gestire*/
        try {
            String response = in.nextLine();
            char id = response.charAt(8);
            char avversario = id == 'X' ? 'O' : 'X';
            frame.setTitle("Tic Tac Toe: Player " + id);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    casellaCorrente.setText(id);
                    casellaCorrente.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    campo[loc].setText(avversario);
                    campo[loc].repaint();
                    messageLabel.setText("Il nemico ha fatto la sua mossa, ora è il tuo turno");
                } else if (response.startsWith("MESSSAGGIO")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("VITTORIA, BEN FATTO SOLDATO MA HAI VINTO LA BATTAGLIA NON LA GUERRA!")) {
                    JOptionPane.showMessageDialog(frame, "Vincitore");
                    break;
                } else if (response.startsWith("SCONFITTA, DAVVERO UN PECCATO SOLDATO, ALLA BASE PRENDERANNO SERI PROVVEDIMENTI.")) {
                    JOptionPane.showMessageDialog(frame, "Hai perso");
                    break;
                } else if (response.startsWith("L'AVVERSARIO HA LASCIATO LA PARTITA")) {
                    JOptionPane.showMessageDialog(frame, "Connessione persa");
                    break;
                }
            }
            out.println("USCITA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
            frame.dispose();
        }
    }

    static class Tavola extends JPanel {
        JLabel label = new JLabel();

        public Tavola() {
            setBackground(Color.white);
            setLayout(new GridBagLayout());
            label.setFont(new Font("Arial", Font.BOLD, 40));
            add(label);
        }

        public void setText(char text) {                    /*Da gestire*/
            label.setForeground(text == 'X' ? Color.BLUE : Color.RED);
            label.setText(text + "");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        BattagliaNavaleClient client = new BattagliaNavaleClient(args[0]);                  
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(320, 320);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.Gioco();
    }
}
