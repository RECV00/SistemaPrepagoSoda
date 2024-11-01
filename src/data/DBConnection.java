package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private final static String database = "dbregistrosoda";//nombre de la base
	private final static String user = "root";
	private final static String pass = "";
	private final static int port = 3306;
	private final static String host = "localhost";
	private final static String url = "jdbc:mysql://"+host+":"+port+"/"+database;
	
	private static Connection con;// hacer la variable para la coneccion java.sql
	
	
	
	public static Connection getConecction() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");//lib connecion
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("Se Estable Coneccion");
		}catch(SQLException e) {
			System.out.println("Error Coneccion"+ e.getMessage());
		}
		
		return con;
	}
	
}
