package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import Common.Constants;
import Models.Employee;
import Models.Product;
import Models.Purchase;

/**
 * Clase PurchaseDAO
 * @author andresprog
 *
 */
public class PurchaseDAO extends AbstractDAO{
	
	Calendar c = Calendar.getInstance();
	String dia = Integer.toString(c.get(Calendar.DATE));
	String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
	String anyo = Integer.toString(c.get(Calendar.YEAR));
	
	String fecha = anyo + "-" + mes + "-" + dia;
	
	ArrayList<Purchase> listaCompras;
	ArrayList<Purchase> listaComprasById;
	
	/**
	 * Constructor
	 */
	public PurchaseDAO() {
		super();
		listaCompras = new ArrayList<Purchase>();
		listaComprasById = new ArrayList<Purchase>();
	}
	
	/**
	 * Método que devuleve todas las compras en un ArrayList
	 * @return
	 */
	public ArrayList<Purchase> getAllPurchases() {
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_ALL_PURCHASES);
			while(rs.next()) {
	        	listaCompras.add(new Purchase(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaCompras;
	}
	
	/**
	 * Método que añade las compras a la base de datos.
	 * @param fechaCompra
	 * @param idProducto
	 * @param idEmployee
	 * @param cantidad
	 */
	public void addPurchase(String fechaCompra, int idProducto, int idEmployee, int cantidad) {
		fechaCompra = this.fecha;
		
		try {
			stm = con.createStatement();
			stm.executeUpdate("INSERT INTO supermercado.compra (id_empleado, fecha_compra) "
					+ "VALUES ('" + idEmployee + "', '" + fechaCompra + "');");
			stm.executeUpdate("INSERT INTO supermercado.cantidadproducto (id_producto, cantidad) "
					+ "VALUES ('" + idProducto + "', '" + cantidad + "');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que devuelve todas las compras gestionadas por un empleado
	 * en concreto.
	 * @param id
	 * @return
	 */
	public ArrayList<Purchase> getPurchasesByIdEmployee(int id) {
		
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT id, id_empleado, id_producto, cantidad, fecha_compra FROM supermercado.compra "
					+ "INNER JOIN supermercado.cantidadproducto ON id = id_compra "
					+ "WHERE id_empleado = " + id + " AND fecha_compra = '" + fecha + "';");
			while(rs.next()) {
	        	listaComprasById.add(new Purchase(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaComprasById;
	}
	
}
