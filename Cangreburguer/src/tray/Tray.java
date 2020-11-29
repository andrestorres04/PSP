package tray;

/**
 * Clase que gestiona los comportamientos de los hilos
 * así como el control de hamburguesas cocinadas y consumidas.
 * @author andresprog
 *
 */
public class Tray {
	
	/**
	 * Estado.
	 */
	private int hamburguesasConsumidas;
	private int hamburguesasCocinadas;
	
	/**
	 * Constructor.
	 */
	public Tray() {
		this.hamburguesasCocinadas = 0;
		this.hamburguesasConsumidas = 0;
	}
	
	/**
	 * Método usado por la clase Cocinero para ejecutar los hilos.
	 * @param idCocinero
	 */
	public synchronized void dejarEnBandeja(int idCocinero) {
		System.out.println("El cocinero " + idCocinero + " ha preparado una Cangreburguer");
		hamburguesasCocinadas++;
		printHamburguesasCocinadas();
		notify();
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Método usado por la clase Cliente para ejecutar los hilos.
	 * Gestiona el número de hamburguesas cocinadas y consumidas.
	 * @param idCliente
	 */
	public synchronized void comerHamburguesa(int idCliente) {
		/**
		 * Si el número de hamburguesas es 0, se indica que no hay hamburguesas
		 * para consumir y se pone en espera al hilo.
		 */
		if(hamburguesasCocinadas==0) {
			System.out.println("El cliente " + idCliente + " está esperando su Cangreburguer.");
			notify();
		} else {
			/**
			 * Cuando hay hamburguesas, los clientes se las comen.
			 */
			System.out.println("El cliente " + idCliente + " se ha comido una Cangreburguer");
			hamburguesasConsumidas++;
			printHamburguesasConsumidas();
			hamburguesasCocinadas--;
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Método para imprimir el número de hamburguesas cocinadas.
	 */
	public void printHamburguesasCocinadas() {
		System.out.println("[ Hamburguesas cocinadas: " + hamburguesasCocinadas + " ]");;
	}
	
	/**
	 * Método para imprimir el número de hamburguesas consumidas.
	 */
	public void printHamburguesasConsumidas() {
		System.out.println("[Hamburguesas consumidas: " + hamburguesasConsumidas + " ]");
	}
	
}
