import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Consola {
	
	public static void main(String args[]) {
		
		//Imprimir el men� de las opciones disponibles
		System.out.println("1. Crear una carpeta dada una ruta y el nombre.");
		System.out.println("2. Crear un fichero dada la ruta y el nombre.");
		System.out.println("3. Listar todas las interfaces de red de nuestro ordenador.");
		System.out.println("4. Mostrar la IP del ordenador dado el nombre de la interfaz de red.");
		System.out.println("5. Mostrar la direcci�n MAC dado el nombre de la interfaz de red.");
		System.out.println("6. Comprobar conectividad con internet.");
		System.out.println("7. Salir.");
		System.out.println(" ");
		String numero = leerNumero();
		
		//Seg�n el n�mero introducido ejecutamos el m�todo correspondiente.
			if(numero.equalsIgnoreCase("1")) {
				System.out.println(" ");
				crearCarpeta();
				//Al terminar siempre pregunto que hacer, si otra cosa o salir.
				queHacer();
			}else if(numero.equalsIgnoreCase("2")){
				System.out.println(" ");
				crearFichero();
				queHacer();
			}else if(numero.equalsIgnoreCase("3")){
				System.out.println(" ");
				listarInterfaces();
				queHacer();
			}else if(numero.equalsIgnoreCase("4")){
				System.out.println(" ");
				mostrarIP();
				queHacer();
			}else if(numero.equalsIgnoreCase("5")){
				System.out.println(" ");
				mostrarMAC();
				queHacer();
			}else if(numero.equalsIgnoreCase("6")){
				System.out.println(" ");
				comprobarInternet();
				queHacer();
			}else if(numero.equalsIgnoreCase("7")){
				System.out.println(" ");
				System.out.println("Programa terminado.");
			}else {
				//Si se introduce un n�mero que no corresponda a los de la lista se pide que se introduzca
				//uno que corresponda a una opcion del men�
				System.out.println("El n�mero " + numero + " no est� en la lista. Introduzca uno que corresponda a una de las siguientes opciones.");
				System.out.println(" ");
				main(args);
			}
	}
	
	//***********************************	
	//Crear carpeta	dada una ruta y un nombre
	//***********************************	
	private static void crearCarpeta() {
		System.out.println("****Crear una carpeta dada una ruta y un nombre.****");
		ProcessBuilder processBuilder = new ProcessBuilder();
		System.out.println("Introduce la ruta:");
		String ruta = leerRuta();
		System.out.println("Introduce el nombre");
		String nombreCarpeta = leerRuta();
		
		processBuilder.command("cmd.exe", "/c", "mkdir" + ruta + nombreCarpeta);
				
		try {
			if (!checkRuta(ruta + nombreCarpeta)) {
				Process process = processBuilder.start();
				
				if (process.waitFor() == 0) {
					System.out.println("Carpeta creada.");
				} else {
					System.out.println("No ha podido crearse la carpeta.");
					System.out.println("Compruebe que ha introducido la ruta correctamente");
					System.out.println("Pruebe a introducirla de la siguiente forma: \\Users\\usuario\\Desktop\\nombrecarpeta\\"+ "\n");
					crearCarpeta();
				}
			} else {
				System.out.println("Ya existe una carpeta con ese nombre.");
			}

			} catch (IOException e) {
					e.printStackTrace();
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
	}
	
	//***********************************	
	//Crear fichero dada la ruta y el nombre
	//***********************************	
	private static void crearFichero() {
		System.out.println("****Crear un fichero dada la ruta y el nombre.****");
		ProcessBuilder processBuilder = new ProcessBuilder();
		System.out.println("Introduce la ruta:");
		String ruta = leerRuta();
		System.out.println("Introduce el nombre junto con la extensi�n del archivo:");
		String nombreFichero = leerRuta();
		
		processBuilder.command("cmd.exe", "/c", "/b >" + ruta + nombreFichero);
		
		try {
			//Si la ruta no existe, se crea
			if (!checkRuta(ruta + nombreFichero)) {
				Process process = processBuilder.start();

				if (process.waitFor() == 1) {
					System.out.println("Fichero creado.");
				} else {
					System.out.println("No ha podido crearse el fichero.");
				}
			//Si existe, se devuelve el siguiente mensaje:
			} else {
				System.out.println("Ya existe un fichero con ese nombre.");
			}

			} catch (IOException e) {
					e.printStackTrace();
			} catch (InterruptedException e) {
					e.printStackTrace();
			}

	}
	
	
	
	//***********************************	
	//Listar todas las interfaces de red de nuestro ordenador
	//***********************************	
	private static void listarInterfaces() {
		System.out.println("****Listar interfaces de red.****");
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ipconfig", "-all");
		
		try {
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			// Guardamos en un buffer la salida del proceso
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append((line + "\n").toString());
			}	
			if (process.waitFor() == 0) {
				System.out.println(buffer);
			} else {
				System.out.println("Algo ha ido mal...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//***********************************	
	//Mostrar IP dado el nombre de la interfaz
	//***********************************	
	private static void mostrarIP() {
		System.out.println("****Mostrar direcci�n IP seg�n el nombre de la interfaz.****");
		Integer interfaz = leerInterfaz();
		String line;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ipconfig", "-all");
		//ArrayList para a�adir las direcciones IP, los nombres de las interfaces y las MAC.
		ArrayList<String> direccionesIP = new ArrayList<String>();
		ArrayList<String> interfaces = new ArrayList<String>();
		
		try {
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
			while ((line = reader.readLine()) != null) {
				//Si la linea contiene Direccion IP la a�ado al ArrayList
				if(line.contains("Direcci�n IP")) {
					direccionesIP.add(line);
				//Aqui igual pero para las interfaces.	
				}else if (line.contains("Adaptador de LAN inal�mbrica Wi-Fi")){
					interfaces.add(line);
				}else {
					
				}
			}
			
			int i = 0;
			while(interfaz != null) {
				//Comprobar que el numero introducido corresponde a una de las opciones, si no, pedir que
				//vuelva a introducirlo.
				if(interfaz > interfaces.size()) {
					System.out.println("Introduzca un n�mero correcto.");
					interfaz = leerInterfaz();
				//Imprimir por pantalla la interfaz elegida con su correspondiente direccion IP	
				}else if(interfaz == i){
						interfaz = i--;
						System.out.println(interfaces.get(i));
						System.out.println(direccionesIP.get(i));
						break;
				}else {
					i++;
				}
				}
		} catch(IOException e) {
		}
	}
	
	
	//***********************************	
	//Mostrar MAC dado el nombre de la interfaz
	//***********************************	
	private static void mostrarMAC() {
		System.out.println("****Mostrar direcci�n MAC seg�n el nombre de la interfaz.****");
		Integer interfaz = leerInterfazMAC();
		String line;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ipconfig", "-all");
		ArrayList<String> interfaces = new ArrayList<String>();
		ArrayList<String> MAC = new ArrayList<String>();
		
		try {
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
			while ((line = reader.readLine()) != null) {
				//Almacenar la direccion MAC en un ArrayList
				if(line.contains("Direcci�n f�sica")) {
					MAC.add(line);
				}else if (line.contains("Adaptador")){
					interfaces.add(line);
				}else {
					
				}
			}
			
			int i = 0;
			while(interfaz != null) {
				if(interfaz > interfaces.size()) {
					System.out.println("Introduzca un n�mero correcto.");
					interfaz= leerInterfazMAC();
				}else if(interfaz == i){
						interfaz = i--;
						System.out.println(interfaces.get(i));
						System.out.println(MAC.get(i));
						break;
				}else {
					i++;
				}
				}
		
		} catch(IOException e) {
			
		}
	}
	
	//***********************************	
	//Comprobar conexi�n a internet
	//***********************************
	private static void comprobarInternet() {
		System.out.println("****Comprobar conexi�n a internet.****" + "\n");
		String IP  = leerIP();
		String line;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ping", IP);
		
		try {
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			//A�adir en un buffer la salida del comando.
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				System.out.println(line);
			}
			
			System.out.println(" ");
			//Si este m�todo devuelve true habr� conexion con internet.
			if(comprobarConexion(buffer.toString())) {
				System.out.println("Hay conexi�n con internet");
			}else {
				System.out.println("No hay conexi�n a internet");
			}
		} catch(IOException e) {
		}
	}
	
	
	//***********************************	
	//Comprobar conexi�n a internet
	//***********************************	
	private static boolean comprobarConexion(String buffer) {
		//Devolver� true si el buffer contiene esa cadena de texto que significa que
		//hay conexi�n a internet ya que no se ha perdido ningun paquete
		return buffer.contains("0% perdidos");
	}
	
	
	//***********************************	
	//M�todo para leer los n�meros introducidos
	//***********************************	
	private static String leerNumero() {
		Scanner miScanner = new Scanner(System.in);
		System.out.println("Introduce el n�mero correspondiente a la opci�n que elija:");
		return miScanner.nextLine();
	}
	
	//***********************************	
	//M�todo para leer la ruta
	//***********************************	
	private static String leerRuta() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
				
	}
	
	
	//***********************************	
	//M�todo para comprobar si la ruta existe
	//***********************************	
	private static boolean checkRuta(String ruta) {
		return new File(ruta).exists();
	}
	
	
	//***********************************	
	//M�todo para leer la Interfaz introducida para ver la IP que tiene
	//***********************************	
	private static Integer leerInterfaz() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ipconfig", "-all");
		ArrayList<String> interfaces = new ArrayList<String>();
		String line;
		
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			while ((line = reader.readLine()) != null) {
				//Del unico del que me aparecia dreccion IP era de este adaptador
				if(line.contains("Adaptador de LAN inal�mbrica Wi-Fi")) {
					interfaces.add(line);
				}else {
				}
			}
			
			int i = 0;
			Integer indice = 1;
			while( i<interfaces.size()) {
				System.out.println(indice + ". "+ interfaces.get(i));
				indice++;
				i++;
			}
			System.out.println(" ");
			
		} catch(IOException e) {
			
		}
		System.out.println("Introduzca el n�mero de la interfaz que desea conocer la direcci�n IP:" + "\n");
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}
	
	
	//***********************************	
	//M�todo para conocer la MAC de una interfaz en concreto
	//***********************************	
	private static Integer leerInterfazMAC() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("ipconfig", "-all");
		ArrayList<String> interfaces = new ArrayList<String>();
		
		try {
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Adaptador")) {
					interfaces.add(line);
				}else {
				}
			}
			int i = 0;
			Integer indice = 1;
			while( i<interfaces.size()) {
				System.out.println(indice + ". "+ interfaces.get(i));
				indice++;
				i++;
			}
			System.out.println(" ");
			
		} catch(IOException e) {
			
		}
		System.out.println("Introduzca el n�mero de la interfaz que desea conocer la direcci�n MAC:" + "\n");
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}
	
	//***********************************	
	//M�todo a ejecutar cuando terminemos una opci�n
	//para saber si ejecutar otra opci�n o terminar.
	//***********************************	
	private static String getOpcion() {
		System.out.println("\n" + "Si desea realizar otra acci�n introduzca SI, de lo contrario introduzca NO" + "\n");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
		
	}
	
	//***********************************	
	//M�todo para leer la IP introducida
	//***********************************	
	private static String leerIP() {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("ipconfig", "-all");
			ArrayList<String> direccionesIP = new ArrayList<String>();
			
			Process process = processBuilder.start();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Direcci�n IP")) {
					direccionesIP.add(line);
					
				}else {
				}
			}
			
			if(direccionesIP.size() == 0) {
				System.out.println("No hay direcciones IP disponibles para mostrar.");
				System.out.println("Esto puede deberse a que no hay conexi�n con internet. Pruebe a introducir una p�gina web.");
			}
			int i = 0;
			Integer indice = 1;
			while( i<direccionesIP.size()) {
				System.out.println(indice + ". "+ direccionesIP.get(i));
				indice++;
				i++;
			}
			System.out.println(" ");
			
		} catch(IOException e) {
			
		}
		System.out.println("Introduzca la direcci�n IP de la que quiere conocer si existe conexi�n a internet o una p�gina web:" + "\n");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
		
	}
	
	
	//***********************************	
	//M�todo para gestionar la opci�n introducida en getOpcion()
	//***********************************	
	private static void queHacer() {
		String opcion = getOpcion().toString();
		while(opcion!=null) {
			if(opcion.equals("SI")) {
				System.out.println(" ");
				main(null);
				break;
			}else if(opcion.equals("NO")){
				System.out.println("Programa terminado");
				break;
			}else {
				System.out.println("No ha introducido una opci�n correcta.");
				opcion = getOpcion();
			}
		}
	}
		
}
