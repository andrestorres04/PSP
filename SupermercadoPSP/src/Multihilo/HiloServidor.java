package Multihilo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import DAO.EmployeeDAO;
import DAO.ProductDAO;
import DAO.PurchaseDAO;
import Models.Employee;
import Models.Product;
import Models.Purchase;

/**
 * Clase HiloServidor que gestionará e interpretará los mensajes enviados
 * entre el Servidor y el Cliente.
 * @author andresprog
 *
 */
public class HiloServidor extends Thread{

	//Estado
	private Socket Client;
	private ObjectOutputStream salida;
	private DataInputStream entrada;
	private DataOutputStream salidaTexto;
	
	private String[] trozo;
	private String email;
	private int idEmpleadoLogeado;
	
	private EmployeeDAO employeeDAO;
	private ProductDAO productDAO;
	private PurchaseDAO purchaseDAO;
	
	/**
	 * Constructor
	 * @param Client
	 */
	public HiloServidor(Socket Client) {
		this.Client = Client;
	}
	
	/**
	 * Método run para iniciar el HiloServidor.
	 */
	public void run() {
		employeeDAO = new EmployeeDAO();
		productDAO = new ProductDAO();
		purchaseDAO = new PurchaseDAO();
		
		email = this.getEmail();
		idEmpleadoLogeado = 0;
		while(Client.isConnected()) {
			try {
				entrada = new DataInputStream(Client.getInputStream());
				trozo = entrada.readUTF().split(";");
				salida = new ObjectOutputStream(Client.getOutputStream());
				//Según el mensaje recibido se ejecuta un método u otro.
				if(trozo[0].equals("Login")) {
					if(!login(Integer.parseInt(trozo[1]))) {
						salida.writeObject(null);
					} else {
						idEmpleadoLogeado = Integer.parseInt(trozo[1]);
						salida.writeObject(employeeDAO.getEmployeeById(idEmpleadoLogeado));
					}
				} else if (trozo[0].equals("Cobro")) {
					updateStockProducts(Integer.parseInt(trozo[1]), Integer.parseInt(trozo[2]));
				} else if (trozo[0].equals("Caja")) {
					getPurchasesById(idEmpleadoLogeado);
				} else if (trozo[0].equals("Salir")) {
					Client.close();
					salida.close();
					entrada.close();
					System.exit(0);
					System.out.println("Servidor cerrado");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Método que gestiona el inicio de sesión.
	 * Recibe un id y comprueba que exista en la base de datos
	 * devolviendo true o false.
	 * @param id
	 * @return
	 */
	private Boolean login(int id) {
		Employee empleado = employeeDAO.getEmployeeById(id);
		if(empleado == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * Método que ordena actualizar el stock de los productos en la base de datos
	 * cuando se realiza una compra.
	 * @param cantidad
	 * @param idProducto
	 */
	private void updateStockProducts(int cantidad, int idProducto) {
		Calendar c = Calendar.getInstance();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH));
		String anyo = Integer.toString(c.get(Calendar.YEAR));
		
		String fecha = anyo + "-" + mes + "-" + dia;
		
		Employee e = employeeDAO.getEmployeeById(idEmpleadoLogeado);
		
		if(cantidad > 0 && cantidad <= productDAO.getStockProductsById(idProducto)) {
			productDAO.updateStockProduct(idProducto, cantidad);
			purchaseDAO.addPurchase(fecha, idProducto, e.getId(), cantidad);
			System.out.println("El cobro se ha realizado correctamente.");
		} else if (cantidad <= 0) {
			System.out.println("La cantidad introducida no puede ser menor o igual a 0.");
		} else {
			System.out.println("No hay stock suficiente");
		}
		if(productDAO.getStockProductsById(idProducto) == 0) {
			this.sendEmailWithJavaMail(this.email, "Aviso de producto sin stock", productDAO.getDatesProductsById(idProducto));
		}
	}
	
	/**
	 * Método que obtiene las compras de un empleado en concreto
	 * para calcular las ganancias y enviarlas al cliente.
	 * @param id
	 */
	private void getPurchasesById(int id) {
		Float ganancias = 0.0f;
		Float total = 0.0f;
		
		for (Purchase compras : purchaseDAO.getPurchasesByIdEmployee(id)) {
			ganancias = productDAO.getProfitsById(compras.getIdProducto(), id);
			total = total + ganancias;
		}
		try {
			salidaTexto = new DataOutputStream(Client.getOutputStream());
			salidaTexto.writeFloat(total);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para obtener el email a partir del archivo xml App.config.
	 * @return
	 */
	private String getEmail() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String correoXML = "";
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document miDocumento = builder.parse(new File("src\\App.config"));
			NodeList listaCorreos = miDocumento.getElementsByTagName("AppConfig");
			for(int i = 0; i < listaCorreos.getLength(); i++) {
				Node node = listaCorreos.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					NodeList listaCorreosChild = e.getChildNodes();
					for(int j = 0; j < listaCorreosChild.getLength(); j++) {
						Node node2 = listaCorreosChild.item(j);
						if(node2.getNodeType() == Node.ELEMENT_NODE) {
							correoXML = node2.getTextContent();
						}
					}
				}
			}
			
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return correoXML;
	}
	
	/**
	 * Método para gestionar el envio de emails cuando el stock sea cero.
	 * @param destinatario
	 * @param asunto
	 * @param mensaje
	 */
	private void sendEmailWithJavaMail(String destinatario, String asunto, String mensaje) {
		
		String correoEnvio = "supermercadopsp@gmail.com";
		String password = "P4ssW0Rd";
				
		Properties props = new Properties();
		// Nombre del host de correo, es smtp.gmail.com
		props.put("mail.smtp.host", "smtp.gmail.com");
		// TLS si está disponible
		props.put("mail.smtp.starttls.enable", "true");
		// Puerto de gmail para envio de correos
		props.put("mail.smtp.port","587");
		// La contraseña del correo
		props.put("mail.smtp.clave", password);
		// Nombre del usuario
		props.put("mail.smtp.user", correoEnvio);
		// Si requiere o no usuario y password para conectarse.
		props.put("mail.smtp.auth", "true");
				
		Session session = Session.getInstance(props);
		Message m = new MimeMessage(session);
		try {
			
			// Quien envia el correo
			m.setFrom(new InternetAddress(correoEnvio));
			// A quien va dirigido
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
			m.setSubject(asunto);
			m.setText(mensaje);
			Transport t = session.getTransport("smtp");
			t.connect(correoEnvio, password);
			t.sendMessage(m,m.getAllRecipients());
			t.close();
		} catch(MessagingException me) {
			me.printStackTrace();
		}
	}
}
