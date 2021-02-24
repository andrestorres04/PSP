package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import Common.Constants;
import Models.Employee;

/**
 * Clase EmployeeDAO
 * @author andresprog
 *
 */
public class EmployeeDAO extends AbstractDAO {

	ArrayList<Employee> listaEmpleados;
	Employee empleado;
	
	/**
	 * Constructor
	 */
	public EmployeeDAO() {
		super();
		empleado = null;
		listaEmpleados = new ArrayList<Employee>();
	}
	
	/**
	 * Método que devuelve todos los empleados
	 * en un ArrayList
	 * @return
	 */
	public ArrayList<Employee> getAllEmployees(){
		
        try {
        	stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_ALL_EMPLOYEES);
			while(rs.next()) {
	        	listaEmpleados.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return listaEmpleados;
        
	}
	
	/**
	 * Método que devuelve un empleado según el id introducido.
	 * Si existe devuelve el empleado, si no, devuleve un empleado null.
	 * @param id
	 * @return
	 */
	public Employee getEmployeeById(int id) {
		
		Calendar c = Calendar.getInstance();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
		String anyo = Integer.toString(c.get(Calendar.YEAR));
		
		String ultimaSesion = anyo + "-" + mes + "-" + dia;
		
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_EMPLOYEES_BY_ID + id + "';");
			while(rs.next()) {
	        	empleado = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
	        	stm = con.createStatement();
	        	stm.executeUpdate("UPDATE supermercado.empleado SET ultima_sesion = '" + ultimaSesion + "' WHERE id = " + id + ";");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empleado;
	}
	
	
}
