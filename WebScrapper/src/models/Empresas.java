package models;

/**
 * Clase para gestionar las empresas y sus datos
 * @author andresprog
 *
 */
public class Empresas {
	
	String empresa;
	String ult;
	String dif;
	String max;
	String min;
	String vol;
	String efect;
	String fecha;
	String hora;
	
	public Empresas(String empresa, String ult, String dif, String max, String min, String vol, String efect, String fecha, String hora) {
		this.empresa = empresa;
		this.ult = ult;
		this.dif = dif;
		this.max = max;
		this.min = min;
		this.vol = vol;
		this.efect = efect;
		this.fecha = fecha;
		this.hora = hora;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getEfect() {
		return efect;
	}

	public void setEfect(String efect) {
		this.efect = efect;
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

	/**
	 * Método para escribir en el fichero los datos
	 * de las distintas empresas.
	 */
	public String toString() {
		return empresa + ";" + ult + ";" + dif + ";" + max + ";" + min + ";" + vol + ";" + efect + ";" + fecha + ";" + hora;
	}
}
