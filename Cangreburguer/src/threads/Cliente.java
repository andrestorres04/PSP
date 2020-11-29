package threads;

import java.util.Random;

import tray.Tray;

/**
 * Clase que gestiona el hilo Cliente.
 * @author andresprog
 *
 */
public class Cliente extends Thread {

	/**
	 * Estado.
	 */
	int idCliente;
	Tray tray;
	
	/**
	 * Constructor.
	 * @param idCliente
	 * @param tray
	 */
	public Cliente(int idCliente, Tray tray) {
		this.idCliente = idCliente;
		this.tray = tray;
	}
	
	/**
	 * Método que gestiona el tiempo de descanso del hilo Cliente.
	 */
	public void consumir() {
		
		//Tiempo de descanso de los hilos entre 1 y 3 segundos.
		try {
			Random random = new Random();
			int low = 1000;
			int high = 3000;
			int result = random.nextInt(high-low) + low;
			Thread.sleep(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Llamada al método comerHamburguesa de la clase Tray.
		this.tray.comerHamburguesa(idCliente);
	}
	
	/**
	 * Los clientes piden constantemente cangreburguers
	 */
	public void run() {
		while (true) {
			consumir();
		}
	}
}
