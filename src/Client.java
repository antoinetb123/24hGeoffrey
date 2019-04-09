
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	BufferedReader buf;
	Socket socket;
	PrintWriter fluxSortant;

	public void createConnection(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			fluxSortant = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void envoieMessage(String message) {
		fluxSortant.print(message);
		fluxSortant.flush();
	}

	public String ecouteMessage() throws IOException {
		char val;
		String reponse = "";
		do {

			try {
				val = (char) buf.read();

				if (val != -1)
					reponse += val;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				val = (char) -1;
				e.printStackTrace();
				System.exit(0);
			}
		} while (buf.ready());
		return reponse;
	}

	public Client() throws IOException {
		Scanner sc = new Scanner(System.in);
		Partie partie = new Partie();
//		String ip=sc.nextLine();
		createConnection("127.0.0.1", 8000);
		envoieMessage("celestin");
		String reponse = ecouteMessage();
		System.out.println("numero : " + reponse);
		reponse = ecouteMessage();
		partie.recupere1emessage(reponse);
		System.out.println("taille et emplacement : " + reponse);
		boolean pasfinit = true;
		do {
			reponse = ecouteMessage();
			partie.traiteMessage(reponse);
			System.out.println("emplacement : " + reponse);
			envoieMessage(partie.choix());

		} while (true);

	}
}
