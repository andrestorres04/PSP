package Main;

import java.io.IOException;
import java.util.Scanner;

import ClienteServidor.Cliente;
import ClienteServidor.Servidor;

/**
 * Main para gestionar el funcionamiento
 * del WalkieTalkie
 * @author andresprog
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Cliente Client = null;
		Servidor Server = null;
		int frecuencia = 6000;
		String opcionElegida = "";
		String mensaje = "";
		boolean escribirServer = false;
		boolean escribirClient = false;
		boolean conexion = false;
		
		
		while(true) {
			Scanner serverScanner = new Scanner(System.in);
			Scanner clientScanner = new Scanner(System.in);
			//Se imprime el menú princiapl
			System.out.println("--------------------------------");
			System.out.println("----------WalkieTalkie----------");
			System.out.println("--------------------------------");
			System.out.println("");
			System.out.println("Bienvenido, elija la opción a la que quiere acceder:");
			System.out.println("");
			System.out.println("1. Enviar un mensaje.");
			System.out.println("2. Esperar un mensaje.");
			System.out.println("3. Salir.");
			System.out.println("");
			opcionElegida = serverScanner.nextLine();
			
			//Gestionamos la opción elegida con switch
			switch(opcionElegida) {
				case "1":
					//Si se elije enviar un mensaje, se crea el servidor con la frecuencia 
					//indicada anteriormente.
					Server = new Servidor(frecuencia);
					//Gestiono las variables booleanas para evitar que los dos escriban a la vez o esperen
					//a recibir un mensaje
					conexion = true;
					escribirServer = true;
					escribirClient = false;
					//Siempre que la conexion sea true gestiono los mensajes
					//ya sean enviados o recibidos.
					while(conexion) {
						//El servidor puede escribir siempre y cuando no envíe "cambio" o "cambio y corto"
						while(escribirServer && conexion) {
							System.out.println("Introduzca el mensaje:");
							mensaje = serverScanner.nextLine();
							Server.flujoSalidaServidor(mensaje);
							//Si el mensaje es "cambio", pasa a escribir el cliente y el servidor recibe mensajes.
							if(mensaje.equalsIgnoreCase("cambio") || mensaje.equalsIgnoreCase("Cambio")) {
								escribirServer = false;
								escribirClient = true;
								System.out.println("Cambio. Esperando mensaje.");
							}
							//Si el mensaje es "cambio y corto", se cierra la conexión.
							if(mensaje.equalsIgnoreCase("cambio y corto") || mensaje.equalsIgnoreCase("Cambio y corto")) {
								conexion = false;
								escribirServer = false;
								escribirClient = false;
								System.out.println("Conexión cerrada.");
							}
						}
						//Si el servidor no puede escribir, solo recibe mensajes.
						while(!escribirServer && conexion) {
							mensaje = Server.flujoEntradaServidor();
							System.out.println("Mensaje recibido: " + mensaje);
							//Si el mensaje recibido es cambio, es ahora el servidor quien envía los mensajes.
							if(mensaje.equalsIgnoreCase("Cambio") || mensaje.equalsIgnoreCase("cambio")) {
								System.out.println("Cambio");
								escribirServer = true;
							}
							//Si el mensaje es cambio y corto, se cierra la conexión.
							if(mensaje.equalsIgnoreCase("Cambio y corto") || mensaje.equalsIgnoreCase("cambio y corto")) {
								conexion = false;
								escribirServer = false;
								escribirClient = false;
								System.out.println("Conexión cerrada.");
							}
						}
						
					}
					Server.cerrarConexionServidor();
					serverScanner.close();
					break;
				case "2":
					//Se crea el Cliente
					Client = new Cliente(frecuencia);
					conexion = true;
					//Al inicio, el cliente no podrá escribir solo recibir mensajes
					//hasta que el servidor envíe "cambio"
					escribirClient = false;
					escribirServer = true;
					
					while(conexion) {
						//Si el cliente puede escribir y la conexion es true, se envían los mensajes.
						while(escribirClient && conexion) {
							System.out.println("Introduzca el mensaje:");
							mensaje = clientScanner.nextLine();
							Client.flujoSalidaCliente(mensaje);
							//Si el mensaje es cambio, pasa a recibir mensajes.
							if(mensaje.equalsIgnoreCase("cambio") || mensaje.equalsIgnoreCase("Cambio")) {
								escribirClient = false;
								escribirServer = true;
								System.out.println("Cambio. Esperando mensaje.");
								mensaje = "";
							}
							//Si el mensaje es cambio y corto, se cierra la conexión.
							if(mensaje.equalsIgnoreCase("cambio y corto") || mensaje.equalsIgnoreCase("Cambio y corto")) {
								conexion = false;
								escribirServer = false;
								escribirClient = false;
								System.out.println("Conexión cerrada.");
							}
						}
						//Si recibe mensajes, se imprimen por pantalla hasta que sea cambio o cambio y corto.
						while(!escribirClient && conexion) {
							mensaje = Client.flujoEntradaCliente();
							System.out.println("Mensaje recibido: " + mensaje);
							//Si es cambio pasa a escribir el cliente
							if(mensaje.equalsIgnoreCase("Cambio") || mensaje.equalsIgnoreCase("cambio")) {
								System.out.println("Cambio.");
								escribirClient = true;
								escribirServer = false;
							} 
							//Si es cambio y corto se cierra la conexión
							if(mensaje.equalsIgnoreCase("Cambio y corto") || mensaje.equalsIgnoreCase("cambio y corto")) {
								conexion = false;
								escribirServer = false;
								escribirClient = false;
								System.out.println("Conexión cerrada.");
								
							}
						}
						
					}
					Client.cerrarConexionCliente();
					clientScanner.close();
					break;
				case "3":
					//Caso 3 se termina el programa.
					System.out.println("El programa ha sido finalizado.");	
					serverScanner.close();
					clientScanner.close();
					System.exit(0);
			}
		}
	}
	

}
