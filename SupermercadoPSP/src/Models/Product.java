package Models;

import java.io.Serializable;

/**
 * Clase para crear los productos y definir sus atributos.
 * @author andresprog
 *
 */
public class Product implements Serializable{
	//Estado
	int id;
	String nombreProducto;
	Float precioVenta;
	Float precioProveedor;
	int cantidadStock;

	/**
	 * Constructor que crea un objeto Product
	 * @param id
	 * @param nombreProducto
	 * @param precioVenta
	 * @param precioProveedor
	 * @param cantidadStock
	 */
	public Product (int id, String nombreProducto, Float precioVenta, Float precioProveedor, int cantidadStock) {
		this.id = id;
		this.nombreProducto = nombreProducto;
		this.precioVenta = precioVenta;
		this.precioProveedor = precioProveedor;
		this.cantidadStock = cantidadStock;
	}
	
	public Product(Float precioVenta, Float precioProveedor) {
		this.precioVenta = precioVenta;
		this.precioProveedor = precioProveedor;
	}

	//Getters y Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Float getPrecioProveedor() {
		return precioProveedor;
	}

	public void setPrecioProveedor(Float precioProveedor) {
		this.precioProveedor = precioProveedor;
	}

	public int getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(int cantidadStock) {
		this.cantidadStock = cantidadStock;
	}

	/**
	 * Método para imprimir los productos
	 */
	@Override
	public String toString() {
		return id + ". " + nombreProducto + " --- Precio: " + precioVenta;
	}
	
	
}
