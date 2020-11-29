package threads;

import java.util.Random;

import tray.Tray;

/**
 * Clase que gestiona el hilo Cocinero.
 * @author andresprog
 *
 */
public class Cocinero extends Thread {

	/**
	 * Estado.
	 */
	int idCocinero;
	Tray tray;
	
	/**
	 * Constructor.
	 * @param idCocinero
	 * @param tray
	 */
	public Cocinero(int idCocinero, Tray tray) {
		super();
		this.idCocinero = idCocinero;
		this.tray = tray;
	}
	
	/**
	 * Método que gestiona el tiempo de descanso del hilo Cocinero
	 */
	public void cocinar() {
		
		//Tiempo de descanso entre 1 y 3 segundos
		try {
			Random random = new Random();
			int low = 1000;
			int high = 3000;
			int result = random.nextInt(high-low) + low;
			Thread.sleep(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Llamada al método dejarEnBandeja de la clase Tray.
		this.tray.dejarEnBandeja(idCocinero);
	}
	
	/**
	 * El cocinero hace cangreburguers constantemente
	 */
	public void run() {
		while (true) {
			cocinar();
		}
	}
}
