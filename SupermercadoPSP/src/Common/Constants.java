package Common;

/**
 * Clase para gestionar las sentencias sql que se usarán para
 * obtener los datos necesarios de la base de datos.
 * @author andresprog
 *
 */
public class Constants {
	//Fichero de configuración de la BBDD
	public static final String CONFIG_FILE = "src\\conection.properties";
	
	//Consultas
	
	//EmployeeDAO
	public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM supermercado.empleado;";
	public static final String SELECT_EMPLOYEES_BY_ID = "SELECT * FROM supermercado.empleado WHERE id = '";
	
	//ProductDAO
	public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM supermercado.producto;";
	public static final String SELECT_PRICE_AND_AMOUNT_PRODUCTS = "SELECT precio_venta, precio_proveedor, cantidad FROM supermercado.producto "
			+ "INNER JOIN supermercado.cantidadproducto ON producto.id = cantidadproducto.id_producto "
			+ "INNER JOIN supermercado.compra ON cantidadproducto.id_compra = compra.id "
			+ "INNER JOIN supermercado.empleado ON compra.id_empleado = empleado.id WHERE producto.id = ";
	public static final String SELECT_DATA_PRODUCTS = "SELECT nombre_producto, precio_proveedor FROM supermercado.producto where id = ";
	public static final String SELECT_STOCK_PRODUCTS = "SELECT cantidad_stock FROM supermercado.producto where id = ";
	public static final String SELECT_NAME_AND_PRICE_PRODUCTS = "SELECT nombre_producto, precio_proveedor FROM supermercado.producto where id = ";
	
	//PurchaseDAO
	public static final String SELECT_ALL_PURCHASES = "SELECT id, id_empleado, id_producto, cantidad, fecha_compra FROM supermercado.compra INNER JOIN supermercado.cantidadproducto ON id = id_compra;";
	public static final String SELECT_PURCHASES_BY_ID_EMPLOYEE = "SELECT * FROM supermercado.compra WHERE id_empleado = ";
}
