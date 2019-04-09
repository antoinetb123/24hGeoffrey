
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion {

	private Socket socket;
	private Writer out;
	private Reader in;
	private char[] tampon;

	public Connexion(String ip, int port) throws UnknownHostException, IOException {

		this.socket = new Socket(ip, port);
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.tampon = new char[1024];

	}

	public void envoi(String donnees) throws IOException {

		out.append(donnees);
		out.flush();

	}

	public String reception() throws IOException {

		in.read(this.tampon);

		return new String(this.tampon);

	}

	public int conversionIntReception() throws IOException {

		return Integer.parseInt(this.reception());

	}

	public void fermerSocket() throws IOException {

		this.socket.close();

	}

}
