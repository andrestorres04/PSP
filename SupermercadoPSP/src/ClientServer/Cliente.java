package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase para gestionar el Cliente (las cajas)
 * @author andresprog
 *
 */
public class Cliente {
	//Estado
	private static Socket Client;
	
	private static int puerto = 6000;
	
	private static DataOutputStream salida;
	private static ObjectInputStream entrada;
	private static DataInputStream entradaTexto;
	
	private static Scanner miScanner = new Scanner(System.in);
	
	private static Boolean continuar;
	
	private static int idEmpleadoLogueado;
	
	/**
	 * Main para iniciar el Cliente.
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		continuar = false;
		System.out.println("Iniciando cliente");
		try {
			Client = new Socket("localhost", puerto);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Cliente Iniciado.");
		
		while (true) {
			//Al iniciar el cliente se pide que inicie sesión
			iniciarSesion();
			//Si se inicia sesión correctamente se salta este bucle, si no, vuelve a pedir
			//que se inicie sesión hasta que encuentre un empleado con el id introducido.
			while(!continuar) {
				System.out.println("");
				iniciarSesion();
			}
			
			while(continuar) {
				//Se imprime el menú principal.
				System.out.println("");
				System.out.println("Elija la opción que desea ejecutar: ");
				System.out.println("1. Cobrar compra.");
				System.out.println("2. Obtener la caja del día.");
				System.out.println("3. Salir");
				
				int opcion = Integer.parseInt(miScanner.next());
				switch(opcion) {
				case 1:
					cobrarCompra();
					break;
				case 2:
					obtenerCaja();
					break;
				case 3:
					try {
						cerrarConexionCliente();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
					break;
				}
			}
		}
	}

	/**
	 * Método para gestionar el inicio de sesión de los empleados.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void iniciarSesion() throws IOException, ClassNotFoundException {
		System.out.println("Bienvenido. Para iniciar sesión introduzca su id:");
		int idEmpleado = Integer.parseInt(miScanner.next());
		idEmpleadoLogueado = idEmpleado;
		salida = new DataOutputStream(Client.getOutputStream());
		salida.writeUTF("Login;" + idEmpleado);
			
		entrada = new ObjectInputStream(Client.getInputStream());
		Object empleado = entrada.readObject();
		if(empleado == null) {
			System.out.println("ERROR: El id " + idEmpleado + " no corresponde a ningún empleado.");
			continuar = false;
		} else {
			System.out.println("Bienvenido empleado número: " + idEmpleado);
			continuar = true;
		}
		
	}
	
	/**
	 * Método que gestiona el cobro de una compra. Se imprimen los productos,
	 * se pide que seleccione el producto y la cantidad y si todo va bien,
	 * se realiza la compra haciendo los cambios en la base de datos.
	 * @throws ClassNotFoundException
	 */
	public static void cobrarCompra() throws ClassNotFoundException {
		System.out.println("Elija el producto que desea cobrar: ");
		System.out.println("1. Disco duro");
		System.out.println("2. USB");
		System.out.println("3. Monitor");
		System.out.println("4. Ratón");
		System.out.println("5. Cargador");
		System.out.println("6. Agua");
		System.out.println("7. CocaCola");
		System.out.println("8. Pizza");
		System.out.println("9. Café");
		System.out.println("10. Ambientador");
		System.out.println("11. Hamburguesa");
		System.out.println("12. Boligrafo azul");
		System.out.println("13. Patatas fritas");
		
		int idProducto = Integer.parseInt(miScanner.next());
		System.out.println("¿Cuántas unidades quiere?");
		int cantidad = Integer.parseInt(miScanner.next());
		try {
			salida = new DataOutputStream(Client.getOutputStream());
			salida.writeUTF("Cobro;" + cantidad + ";" + idProducto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que devuelve las ganacias del empleado conectado en el día.
	 * @throws ClassNotFoundException
	 */
	public static void obtenerCaja() throws ClassNotFoundException {
		Float total = 0.0f;
		try {
			salida = new DataOutputStream(Client.getOutputStream());
			salida.writeUTF("Caja");
			entradaTexto = new DataInputStream(Client.getInputStream());
			total = entradaTexto.readFloat();
			//El total recibido es diferente al enviado y además es negativo.
			if(total > 0) {
				System.out.println("Caja realizada por el empleado con id: " + idEmpleadoLogueado +"\nTotal ganancias: " + total + "€");
			} else if(total < 0) {
				System.out.println("La cantidad recibida es negativa.");
			} else {
				System.out.println("El empleado con id: " + idEmpleadoLogueado + " aún no tiene ganacias en el día de hoy.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para cerrar la conexión del Cliente con el
	 * servidor.
	 * @throws IOException 
	 */
	public static void cerrarConexionCliente() throws IOException {
		salida.writeUTF("Salir");
		entrada.close();
		salida.close();
		Client.close();
		miScanner.close();
	}
	
	

}
