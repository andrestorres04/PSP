package ClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Multihilo.HiloServidor;

/**
 * Clase que gestiona el servidor del programa.
 * Siempre se está ejecutando.
 * @author andresprog
 *
 */
public class Servidor {

	//Estado
	private static int puerto = 6000;
	private static ServerSocket Server;
	
	/**
	 * Main para arrancar el servidor.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Iniciando servidor");
		try {
			Server = new ServerSocket(puerto);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Servidor Iniciado.");
		
		while(true) {
			Socket cliente = new Socket();
            cliente = Server.accept();//esperando cliente
            HiloServidor hilo = new HiloServidor(cliente);
            hilo.start(); //Se atiende al cliente
		}
	}

}
