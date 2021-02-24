package DAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import Common.Constants;

/**
 * Clase para gestionar la conexion con la base de datos.
 * @author andresprog
 *
 */
public abstract class AbstractDAO {
	protected Connection con;
	protected Statement stm;
	protected ResultSet rs;

	public AbstractDAO() {
		conexionBBDD();
	}

	/**
	 * Método que realiza la conexión con la base de datos obteniendo
	 * los recursos necesarios a partir del archivo conection.properties
	 */
	private void conexionBBDD() {
		try {
			Properties properties = new Properties();
			try {
				properties.load(new FileReader(Constants.CONFIG_FILE));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Class.forName(properties.getProperty("controller"));
			try {
				this.con = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
						properties.getProperty("password"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para ejecutar el RollBack y poner
	 * el AutoCommit a true.
	 */
	protected void conectionRollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que cierra la conexión, el ResultSet y el Statement.
	 */
	protected void closeConexion() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stm != null) {
				stm.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}