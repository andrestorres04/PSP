package ClienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase para gestionar el Cliente del WalkieTalkie
 * @author andresprog
 *
 */
public class Cliente {
	private DataInputStream mensajeRecibido;
	private DataOutputStream mensajeEnviado;
	private Socket Client;

	/**
	 * Constructor de un ojeto Cliente
	 * @param frecuencia
	 */
	public Cliente(int frecuencia) {
		Client = null;
		mensajeRecibido = null;
		mensajeEnviado = null;
		iniciarCliente(frecuencia);
	}
	
	/**
	 * Método que inicia el Cliente.
	 * Se inicia el Socket y el Cliente espera a que se conecte
	 * el servidor.
	 * @param frecuencia
	 * @return
	 */
	public Socket iniciarCliente(int frecuencia) {
		try {
			System.out.println("Iniciando cliente");
			Client = new Socket("localhost", frecuencia);
			System.out.println("Cliente Iniciado. Esperando mensaje del servidor.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Client;
	}
	
	/**
	 * Método que gestiona el envío de mensajes
	 * del cliente al servidor. 
	 * @throws IOException 
	 */
	public void flujoSalidaCliente(String miMensaje) throws IOException {
		//Se crea el flujo de salida al servidor para enviar los mensajes
		mensajeEnviado = new DataOutputStream(Client.getOutputStream());
		//Se envía el mensaje al servidor
		mensajeEnviado.writeUTF(miMensaje);
	}
	
	/**
	 * Método para gestionar la recepción de los mensajes.
	 * @throws IOException 
	 */
	public String flujoEntradaCliente() throws IOException {
		//Se crea el flujo de entrada del servidor para recibir el mensaje
		mensajeRecibido = new DataInputStream(Client.getInputStream());
		//Se imprime el mensaje del servidor.
		System.out.println("Mensaje recibido: " + mensajeRecibido.readUTF());
		return mensajeRecibido.readUTF();
	}
	
	/**
	 * Método para cerrar la conexión del Cliente con el
	 * servidor.
	 * @throws IOException 
	 */
	public void cerrarConexionCliente() throws IOException {
		mensajeEnviado.close();
		mensajeRecibido.close();
		Client.close();
	}

}
