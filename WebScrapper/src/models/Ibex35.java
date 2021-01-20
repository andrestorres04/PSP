package models;

/**
 * Clase para gestionar la salida que se escribirá
 * en el fichero de texto
 * @author andresprog
 *
 */
public class Ibex35 {

	//Estado
	String nombre;
	String ant;
	String ult;
	String dif;
	String max;
	String min;
	String fecha;
	String hora;
	String difAno;
	
	/**
	 * Constructor
	 * @param fecha
	 * @param hora
	 * @param ibex
	 * @param maximo
	 */
	public Ibex35(String nombre, String ant, String ult, String dif, String max, String min, String fecha, String hora, String difAno) {
		this.nombre = nombre;
		this.ant = ant;
		this.ult = ult;
		this.dif = dif;
		this.max = max;
		this.min = min;
		this.fecha = fecha;
		this.hora = hora;
		this.difAno = difAno;
	}
	/**
	 * Getters y Setters.
	 */
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnt() {
		return ant;
	}

	public void setAnt(String ant) {
		this.ant = ant;
	}

	public String getUlt() {
		return ult;
	}

	public void setUlt(String ult) {
		this.ult = ult;
	}

	public String getDif() {
		return dif;
	}

	public void setDif(String dif) {
		this.dif = dif;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getDifAno() {
		return difAno;
	}

	public void setDifAno(String difAno) {
		this.difAno = difAno;
	}

	/**
	 * Método que devuelve la cadena de texto para
	 * escribirla en el fichero de salida.
	 */
	public String toString() {
		return nombre + "," + ant + ";" + ult + ";" + dif + ";" + max + ";" + min + ";" + fecha + ";" + hora + ";" + difAno;
	}
	
}
