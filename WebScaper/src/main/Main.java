package main;

import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import models.Empresas;
import models.Ibex35;

/**
 * Main del proyecto para lanzar los métodos
 * @author andresprog
 *
 */
public class Main {
	
	private static int time;

	public static void main(String[] args) throws IOException {

		time = 10000;
		while (true) {
			Controller miController = new Controller("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000");
			ArrayList<String> lineas = miController.getDatos();
			ArrayList<Ibex35> ibex = miController.getIbex(lineas);
			ArrayList<Empresas> misEmpresas = miController.getEmpresas(lineas);
			miController.escribir(ibex, misEmpresas);
		
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}