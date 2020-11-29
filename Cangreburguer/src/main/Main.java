package main;

import threads.Cliente;
import threads.Cocinero;
import tray.Tray;

public class Main {

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tray tray = new Tray();
		/**
		 * Asignamos el número de hilos a ejecutar
		 */
		Cliente[] clientes = new Cliente[100];
		Cocinero[] cocineros = new Cocinero[100];
		
		/**
		 * Lanzamos los hilos
		 */
		for (int i = 0; i < cocineros.length; i++) {
			cocineros[i] = new Cocinero(i,tray);
			cocineros[i].start();
		}
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(i,tray);
			clientes[i].start();
		}
		
	}

}
