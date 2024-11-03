package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Order;
import domain.User;

public class UserData {

	public static void updateUser(User us) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spUpdateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	        CallableStatement stmt = cn.prepareCall(query);

	        // Establece cada parámetro en el orden correcto
	        stmt.setInt(1, us.getId_tbuser()); // ID del registro que se está actualizando
	        stmt.setInt(2, us.getId());
	        stmt.setString(3, us.getPassword());
	        stmt.setString(4, us.getTipe());
	        stmt.setString(5, us.getPhotoRoute());
	        stmt.setString(6, us.getName());
	        stmt.setString(7, us.getEmail());
	        stmt.setInt(8, us.getPhone());
	        stmt.setBoolean(9, us.getIsActive());
	        stmt.setDate(10, Date.valueOf(us.getDateEntry())); 
	        stmt.setBoolean(11, us.getGender());
	        stmt.setDouble(12, us.getMoneyAvailable());

	        // Imprimir parámetros para debug
	        System.out.println("Ejecutando update con ID: " + us.getId_tbuser() + ", Nombre: " + us.getName());

	        stmt.execute();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	public static void saveUser(User us) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spSaveUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Definir los parámetros
	        CallableStatement stmt = cn.prepareCall(query);
	    
	        // Establece cada parámetro en el orden correcto
	        stmt.setInt(1, us.getId());
	        stmt.setString(2, us.getPassword());
	        stmt.setString(3, us.getTipe());
	        stmt.setString(4, us.getPhotoRoute());
	        stmt.setString(5, us.getName());
	        stmt.setString(6, us.getEmail());
	        stmt.setInt(7, us.getPhone());
	        stmt.setBoolean(8, us.getIsActive());
	        stmt.setDate(9, Date.valueOf(us.getDateEntry())); 
	        stmt.setBoolean(10, us.getGender());
	        stmt.setDouble(11, us.getMoneyAvailable());

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
	
	public static LinkedList<User> getUsers() {
	    LinkedList<User> list = new LinkedList<>();
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spListUser}";
	        CallableStatement stmt = cn.prepareCall(query);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            User user = new User();
	            user.setId_tbuser(rs.getInt(1));
	            user.setId(rs.getInt(2));
	            user.setPassword(rs.getString(3));
	            user.setTipe(rs.getString(4));
	            user.setPhotoRoute(rs.getString(5));
	            user.setName(rs.getString(6));
	            user.setEmail(rs.getString(7));
	            user.setPhone(rs.getInt(8));
	            user.setActive(rs.getInt(9) == 1); // Convierte 1 o 0 a booleano

	            // Verifica si el resultado de getDate() no es null antes de convertirlo
	            java.sql.Date sqlDate = rs.getDate(10);
	            if (sqlDate != null) {
	                user.setDateEntry(sqlDate.toLocalDate()); // Conversión a LocalDate
	            } else {
	                user.setDateEntry(null); // O maneja el caso según tu lógica
	            }

	            user.setGender(rs.getBoolean(11));
	            user.setMoneyAvailable(rs.getDouble(12));
	            list.add(user);
	        }

	    } catch (SQLException e) {
	        System.out.println("UserData.getUsers: " + e.getMessage());
	    }
	    
	    return list;
	}



	public static String getPhotoLinkByCedula(int cedula) {
	    String photoLink = null;
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spGetPhotoLinkByCedula(?)}";
	        CallableStatement stmt = cn.prepareCall(query);	        
	        // Establece el parámetro de cédula
	        stmt.setInt(2, cedula);	        
	        // Ejecuta el SP y obtiene el resultado
	        ResultSet rs = stmt.executeQuery();	        
	        // Si hay un resultado, obtener el enlace de la foto
	        if (rs.next()) {
	            photoLink = rs.getString("photoRoute");
	        } 
	    } catch (SQLException e) {
	        System.out.println("UserData.getPhotoLinkByCedula: " + e.getMessage());
	    }
	    return photoLink;
	}
	
	public static User getStudentByCarnet(int cedula) {
	    User photoLink = null;
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spGetPhotoLinkByCedula(?)}";
	        CallableStatement stmt = cn.prepareCall(query);	        
	        // Establece el parámetro de cédula
	        stmt.setInt(2, cedula);	        
	        // Ejecuta el SP y obtiene el resultado
	        ResultSet rs = stmt.executeQuery();	        
	        // Si hay un resultado, obtener el enlace de la foto
	        if (rs.next()) {
//	            photoLink = rs.getString("photoRoute");
	        } 
	    } catch (SQLException e) {
	        System.out.println("UserData.getPhotoLinkByCedula: " + e.getMessage());
	    }
	    return photoLink;
	   
	}

}
