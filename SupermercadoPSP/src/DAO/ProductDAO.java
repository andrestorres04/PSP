package DAO;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Common.Constants;
import Models.Product;

/**
 * Clase ProductDAO
 * @author andresprog
 *
 */
public class ProductDAO extends AbstractDAO{

	Product producto;
	ArrayList<Product> listaProductos;
	ArrayList<Product> listaPrecioProductos;
	
	/**
	 * Constructor
	 */
	public ProductDAO() {
		super();
		producto = null;
		listaProductos = new ArrayList<Product>();
		listaPrecioProductos = new ArrayList<Product>();
	}
	
	/**
	 * Método que devuelve todos los productos en un ArrayList
	 * @return
	 */
	public ArrayList<Product> getAllProducts() {
		
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_ALL_PRODUCTS);
			while(rs.next()) {
				listaProductos.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getInt(5)));
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaProductos;
	}
	
	/**
	 * Método que devuelve el total de las ganancias de un producto en concreto
	 * según la cantidad vendida en el día.
	 * @param idProducto
	 * @return
	 */
	public Float getProfitsById(int idProducto, int idEmpleado) {
		
		Float totalGanancias = 0.0f;
		Float ganancias = 0.0f;
		int cantidad = 0;
		
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_PRICE_AND_AMOUNT_PRODUCTS + idProducto + " AND id_empleado = " + idEmpleado + ";");
			while(rs.next()) {
				ganancias = rs.getFloat(1) - rs.getFloat(2);
				cantidad = rs.getInt(3);
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ganancias * cantidad;
		
	}
	
	/**
	 * Método que te devulve el número de productos que hay en 
	 * la base de datos.
	 * @return
	 */
	public int getCountAllProducts() {
		int i = 0;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT COUNT(*) FROM supermercado.producto;");
			while(rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	/**
	 * Método que actualiza el stock de los productos en la base de datos
	 * cuando se realiza una compra.
	 * @param id
	 * @param cantidad
	 */
	public void updateStockProduct(int id, int cantidad) {
		
		try {
			stm = con.createStatement();
			stm.executeUpdate("UPDATE supermercado.producto SET cantidad_stock = cantidad_stock - " + cantidad + " WHERE id = " + id + ";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que devuelve el stock disponible de un producto en concreto.
	 * @param idProducto
	 * @return
	 */
	public int getStockProductsById(int idProducto) {
		int stock = 0;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_STOCK_PRODUCTS + idProducto + ";");
			while(rs.next()) {
				stock = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stock;
	}
	
	/**
	 * Método que devuelve el mensaje que se enviará por email en caso de que el stock sea cero.
	 * @param idProducto
	 * @return
	 */
	public String getDatesProductsById(int idProducto) {
		 DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		 Date date = new Date();
		 String hora = dateFormat.format(date);
		
		String nombre = "";
		float precioProveedor = 0.0f;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_NAME_AND_PRICE_PRODUCTS + idProducto + ";");
			while(rs.next()) {
				nombre = rs.getString(1);
				precioProveedor = rs.getFloat(2);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "El stock del producto " + nombre + " se ha terminado a las " + hora + ". \nEl precio del proveedor es: " + precioProveedor;
		
	}
	
	
}
