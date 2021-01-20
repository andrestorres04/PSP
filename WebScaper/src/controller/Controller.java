package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import models.Empresas;
import models.Ibex35;

/**
 * Clase que controlará el html de la página web para
 * obtener los datos y gestionar la escritura en el
 * fichero de texto.
 * @author andresprog
 *
 */
public class Controller {

	private URLConnection conexion;
	private String direccion;
	
	//Variable booleana para controlar la escritura
	private Boolean escribir;
	
	public Controller(String direccion) {
		conexion = null;
		this.direccion = direccion;
		escribir = false;
	}
	
	/**
	 * Comportamiento que gestiona la conexión
	 * con la página web.
	 */
	public void conexion() {
		try {
			URL miUrl = new URL(direccion);
			conexion = miUrl.openConnection();
			conexion.setDoOutput(true);
		} catch (IOException e) { }
	}
	
	/**
	 * Comportamiento que devuelve los datos que queremos obtener
	 * a un ArrayList.
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> getDatos() throws IOException {
		conexion();
		ArrayList<String> lineas = new ArrayList<String>();
		BufferedReader miBuffer = null;
		
		try {
			miBuffer = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String linea="";
			//Bucle para controlar el contenido de cada línea del html.
			while (miBuffer.readLine() != null) {
				linea = miBuffer.readLine();
				if (linea.contains("<ctl00_Contenido_tblÍndice")) {
					escribir = true;
				} else if (linea.contains("ctl00_Contenido_tblAcciones")) {
					escribir = true;
				} else if (linea.contains("</table>")) {
					escribir = false;
				}
				
				if (escribir = true) {
					lineas.add(linea);
				}
			}
		} catch (Exception e) {
			
		} finally {
			miBuffer.close();
		}
		
		return lineas;
	}
	
	/**
	 * Método para escribir los primeros datos
	 * en el fichero de texto.
	 * @param lineas
	 * @return
	 */
	public ArrayList<Ibex35> getIbex(ArrayList<String> lineas) {
		ArrayList<Ibex35> ibex = new ArrayList<Ibex35>();
		
		String nombre = "";
		String ant = "";
		String ultIb = "";
		String difIb = "";
		String maxIb = "";
		String minIb = "";
		String fechaIb = "";
		String horaIb = "";
		String difAno = "";
		
		
		for (String linea : lineas) {
			if (linea.contains("<td class=\"DifFlSb\" align=\"left\">IBEX 35")) {
				//Escribir la primera línea en el fichero con los datos generales del ibex
				String nombre1[] = linea.split("<td");
				String nombre2[] = nombre1[1].split(">");
				String nombre3[] = nombre2[1].split("<");
				nombre = nombre3[0];
				
				String ant1[] = linea.split("</td>");
				String ant2[] = ant1[1].split(">");
				String ant3[] = ant2[1].split("<");
				ant = ant3[0];
				
				String ultIb1[] = ant1[2].split(">");
				ultIb = ultIb1[1];
				
				String difIb1[] = ant1[3].split(">");
				difIb = difIb1[1];
				
				String maxIb1[] = ant1[4].split(">");
				maxIb = maxIb1[1];
				
				String minIb1[] = ant1[5].split(">");
				minIb = minIb1[1];
				
				String fechaIb1[] = ant1[6].split(">");
				fechaIb = fechaIb1[1];
				
				String horaIb1[] = ant1[7].split(">");
				horaIb = horaIb1[1];
				
				String difAno1[] = ant1[8].split(">");
				difAno = difAno1[1];
				
				ibex.add(new Ibex35(nombre, ant, ultIb, difIb, maxIb, minIb, fechaIb, horaIb, difAno));
				
			} 
				
			
		}
		
		return ibex;
		
	}
	
	/**
	 * Método para obtener los datos de las empresas
	 * @param lineas
	 * @return
	 */
	public ArrayList<Empresas> getEmpresas(ArrayList<String> lineas) {
		ArrayList<Empresas> empresas = new ArrayList<Empresas>();
		
		String empresa = "";
		String ult = "";
		String dif = "";
		String max = "";
		String min = "";
		String vol = "";
		String efect = "";
		String fecha = "";
		String hora = "";
		for (String linea : lineas) {
			if (linea.contains("/esp/aspx/Empresas/FichaValor.aspx")) {
				//Escribir el resto de lineas de las empresas.
				String empresa1[] = linea.split("ISIN");
				String empresa2[] = empresa1[1].split(">");
				String empresa3[] = empresa2[1].split("<");
				empresa = empresa3[0];
			
				String ult1[] = empresa1[1].split("</td>");
				String ult2[] = ult1[1].split(">");
				ult = ult2[1];
			
				String dif1[] = ult1[2].split(">");
				dif = dif1[1];
			
				String max1[] = ult1[3].split(">");
				max = max1[1];
			
				String min1[] = ult1[4].split(">");
				min = min1[1];
			
				String vol1[] = ult1[5].split(">");
				vol = vol1[1];
			
				String efect1[] = ult1[6].split(">");
				efect = efect1[1];
			
				String fecha1[] = ult1[7].split(">");
				fecha = fecha1[1];
			
				String hora1[] = ult1[8].split(">");
				hora = hora1[1];
			
				empresas.add(new Empresas(empresa, ult, dif, max, min, vol, efect, fecha, hora));
			}
		
		}
		
		return empresas;
	}
	
	/**
	 * Método para escribir los datos en el fichero
	 * @param ibex
	 * @param empresas
	 * @throws IOException
	 */
	public void escribir(ArrayList<Ibex35> ibex, ArrayList<Empresas> empresas) throws IOException {
		FileWriter file = null;
		PrintWriter print = null;
		File miFile = new File("Registro.txt");
		
		try {
			file = new FileWriter(miFile.getAbsoluteFile(), true);
			print = new PrintWriter(file);
			
			for (Ibex35 ibex35 : ibex) {
				print.println();
				print.println(ibex35.toString());
				print.println();
			}
			
			for (Empresas empresa : empresas) {
				print.println(empresa.toString());
			}
			
		} catch (Exception e) {
			
		} finally {
			file.close();
		}
		
	}
	
	
}
