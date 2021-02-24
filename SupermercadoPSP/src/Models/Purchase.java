package Models;

import java.io.Serializable;

/**
 * Clase que crea los productos y define su estado.
 * @author andresprog
 *
 */
public class Purchase implements Serializable{
	//Estado
	int id;
	int idEmpleado;
	int idProducto;
	int cantidad;
	String fecha;
	
	/**
	 * Constructor que crea un objeto purchase
	 * @param id
	 * @param idEmpleado
	 * @param idProducto
	 * @param cantidad
	 * @param fecha
	 */
	public Purchase (int id, int idEmpleado, int idProducto, int cantidad, String fecha) {
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}
	
	//Getters y Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	
	
	
	
	

}
