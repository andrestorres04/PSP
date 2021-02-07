package ClienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase para gestionar el servidor del WalkieTalkie
 * @author andresprog
 *
 */
public class Servidor {
	private ServerSocket Server;
	private Socket Client;
	private DataInputStream mensajeRecibido;
	private DataOutputStream mensajeEnviado;
	private InputStream entrada;
	private OutputStream salida;
	
	/**
	 * Constructor de un objeto Servidor
	 * @param frecuencia
	 * @throws IOException 
	 */
	public Servidor(int frecuencia) throws IOException {
		mensajeRecibido = null;
		mensajeEnviado = null;
		Client = null;
		entrada = null;
		salida = null;
		iniciarServidor(frecuencia);
	}
	
	/**
	 * Método que inicia el Servidor.
	 * Se inicia el ServerSocket y se espera que el 
	 * cliente se conecte.
	 * @param frecuencia
	 * @return
	 */
	public ServerSocket iniciarServidor(int frecuencia) {
		try {
			System.out.println("Iniciando servidor");
			Server = new ServerSocket(frecuencia);
			System.out.println("Servidor iniciado. Esperando al cliente");
			Client = Server.accept();
			System.out.println("Cliente conectado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Server;
	}
	
	/**
	 * Método que gestiona el envío de mensajes 
	 * del servidor al cliente.
	 * @param miMensaje
	 * @throws IOException
	 */
	public void flujoSalidaServidor(String miMensaje) throws IOException {
		//Se crea el flujo de salida al cliente para enviar los mensajes.
		salida = Client.getOutputStream();
		mensajeEnviado = new DataOutputStream(salida);
		//Se envía el mensaje
		mensajeEnviado.writeUTF(miMensaje);
		
	}
	
	/**
	 * Método para gestionar la recepción de mensajes.
	 * @throws IOException
	 */
	public String flujoEntradaServidor() throws IOException {
		//Se crea el flujo de entrada del Cliente.
		entrada = Client.getInputStream();
		mensajeRecibido = new DataInputStream(entrada);
		//Se imprime el mensaje recibido.
		System.out.println("Mensaje recibido: " + mensajeRecibido.readUTF());
		return mensajeRecibido.readUTF();
	}
	
	/**
	 * Método para cerrar la conexión del Servidor
	 * con el cliente.
	 * @throws IOException 
	 */
	public void cerrarConexionServidor() throws IOException {
		Server.close();
		entrada.close();
		mensajeRecibido.close();
		salida.close();
		mensajeEnviado.close();
		Client.close();
	}
}
