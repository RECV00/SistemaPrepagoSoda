package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Order;
import domain.User;

public class UserData {

	public static void updateUser(User us) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spUpdateUser(?, ?, ?, ?, ?)}"; // Definir los parámetros, incluyendo el ID
	        CallableStatement stmt = cn.prepareCall(query);

	        stmt.setInt(1, us.getId_tbuser());
	        stmt.setInt(2, us.getId());
	        stmt.setString(3, us.getPassword());
	        stmt.setDouble(4, us.getTipe());
	        stmt.setString(5, us.getPhotoRoute());

	        stmt.execute(); // Ejecutar sin ResultSet si no se espera un resultado

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void saveUser(User us) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spSaveUser(?, ?, ?, ?)}"; // Definir los parámetros
	        CallableStatement stmt = cn.prepareCall(query);
	    
	        stmt.setInt(1, us.getId());
	        stmt.setString(2, us.getPassword());
	        stmt.setDouble(3, us.getTipe());
	        stmt.setString(4, us.getPhotoRoute());

	        stmt.execute(); // Ejecutar sin ResultSet si no se espera un resultado
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public static void deleteUser(int vUs) {
		try {
			Connection cn = DBConnection.getConecction();
			String query = "{call spDeleteUser(?)}";
			CallableStatement stmt = cn.prepareCall(query);
			stmt.setInt(1, vUs);
			stmt.executeQuery();
			
		}catch(SQLException e) {

			e.printStackTrace();
		}
	}
	public static LinkedList<User > getUsers(){
		LinkedList<User> list = new LinkedList();
		try {
			Connection cn = DBConnection.getConecction();
			String query = "{call spListUser}";
//					PreparedStatement ps = cn.prepareStatement(query);
			CallableStatement stmt = cn.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId_tbuser(rs.getInt(1));
				user.setId(rs.getInt(2));
				user.setPassword(rs.getString(3));
				String tipeStr = rs.getString(4).trim(); // Elimina espacios adicionales
		        user.setTipe(tipeStr.isEmpty() ? '\0' : tipeStr.charAt(0));
				user.setPhotoRoute(rs.getString(5));
				list.add(user);
			}
			
			
		}catch(SQLException e) {
			System.out.println("UserData.getUser"+ e.getMessage());
		}
		
		return list;
	}

}
