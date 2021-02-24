package Models;

import java.io.Serializable;

/**
 * Clase para crear los empleados y definir sus estados.
 * @author andresprog
 *
 */
public class Employee implements Serializable{
	//Estado
	int id;
	String ultimaSesion;
	String fechaContratacion;
	
	/**
	 * Constructor que crea un objeto Employee
	 * @param id
	 * @param ultimaSesion
	 * @param fechaContratacion
	 */
	public Employee(int id, String ultimaSesion, String fechaContratacion) {
		this.id = id;
		this.ultimaSesion = ultimaSesion;
		this.fechaContratacion = fechaContratacion;
	}

	//Getters y Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUltimaSesion() {
		return ultimaSesion;
	}

	public void setUltimaSesion(String ultimaSesion) {
		this.ultimaSesion = ultimaSesion;
	}

	public String getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(String fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

	
}
