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
	        String query = "{call spUpdateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Agregamos un nuevo parámetro para `salt`
	        CallableStatement stmt = cn.prepareCall(query);

	        // Establece cada parámetro en el orden correcto
	        stmt.setInt(1, us.getId_tbuser()); // ID del registro que se está actualizando
	        stmt.setInt(2, us.getId());
	        stmt.setString(3, us.getPassword());
	        stmt.setString(4, us.getSalt());
	        stmt.setString(5, us.getTipe());
	        stmt.setString(6, us.getPhotoRoute());
	        stmt.setString(7, us.getName());
	        stmt.setString(8, us.getEmail());
	        stmt.setInt(9, us.getPhone());
	        stmt.setBoolean(10, us.getIsActive());
	        stmt.setDate(11, Date.valueOf(us.getDateEntry())); 
	        stmt.setBoolean(12, us.getGender());
	        stmt.setDouble(13, us.getMoneyAvailable());

	        stmt.execute();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveUser(User us) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spSaveUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Agregar parámetro para `salt`
	        CallableStatement stmt = cn.prepareCall(query);

	        // Establece cada parámetro en el orden correcto
	        stmt.setInt(1, us.getId());
	        stmt.setString(2, us.getPassword());
	        stmt.setString(3, us.getSalt());
	        stmt.setString(4, us.getTipe());
	        stmt.setString(5, us.getPhotoRoute());
	        stmt.setString(6, us.getName());
	        stmt.setString(7, us.getEmail());
	        stmt.setInt(8, us.getPhone());
	        stmt.setBoolean(9, us.getIsActive());
	        stmt.setDate(10, Date.valueOf(us.getDateEntry())); 
	        stmt.setBoolean(11, us.getGender());
	        stmt.setDouble(12, us.getMoneyAvailable());

	        stmt.execute();
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
	            user.setSalt(rs.getString(4));
	            user.setTipe(rs.getString(5));
	            user.setPhotoRoute(rs.getString(6));
	            user.setName(rs.getString(7));
	            user.setEmail(rs.getString(8));
	            user.setPhone(rs.getInt(9));
	            user.setActive(rs.getInt(10) == 1);
	            java.sql.Date sqlDate = rs.getDate(11);
	            if (sqlDate != null) {
	                user.setDateEntry(sqlDate.toLocalDate());
	            } else {
	                user.setDateEntry(null);
	            }
	            user.setGender(rs.getBoolean(12));
	            user.setMoneyAvailable(rs.getDouble(13));
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
	        stmt.setInt(1, cedula);	        
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
	
	public static boolean updateRecharge(User u) {
	    boolean updateSuccess = false;
	    
	    try {
	        Connection cn = DBConnection.getConecction();
	        
	        // Consulta SQL para el procedimiento almacenado que actualiza el saldo
	        String query = "{call spUpdateRecharge(?, ?)}";  // Asume que tienes un stored procedure 'spUpdateRecharge'
	        
	        // Preparar la llamada al procedimiento almacenado
	        CallableStatement stmt = cn.prepareCall(query);
	        
	        // Establecer los parámetros del procedimiento almacenado
	        stmt.setInt(1, u.getId()); // ID del estudiante
	        stmt.setDouble(2, u.getMoneyAvailable()); // Nuevo saldo disponible
	        
	        // Ejecutar la actualización
	        int rowsAffected = stmt.executeUpdate();
	        
	        // Si al menos una fila fue actualizada, la operación fue exitosa
	        updateSuccess = (rowsAffected > 0);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return updateSuccess;
	}

	public static User getStudentByCedula(int cedula) {
	    User student = null;

	    try {
	        Connection cn = DBConnection.getConecction();
	        
	        // Llamada al procedimiento almacenado para obtener datos del estudiante
	        String query = "{call spGetStudentByCedula(?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        
	        // Establecer el parámetro de cédula
	        stmt.setInt(2, cedula);
	        
	        // Ejecuta el procedimiento almacenado y obtiene el resultado
	        ResultSet rs = stmt.executeQuery();
	        
	        // Si hay un resultado, crea y llena el objeto User con los datos obtenidos
	        if (rs.next()) {
	            student = new User();
	            student.setId_tbuser(rs.getInt("id_tbuser"));
	            student.setId(rs.getInt("id"));
	            student.setPassword(rs.getString("password"));
	            student.setTipe(rs.getString("tipe"));
	            student.setPhotoRoute(rs.getString("photoRoute"));
	            student.setName(rs.getString("name"));
	            student.setEmail(rs.getString("email"));
	            student.setPhone(rs.getInt("phone"));
	            student.setActive(rs.getString("isActive").equals("1"));  // Convertir a boolean
	            student.setDateEntry(rs.getDate("dateEntry").toLocalDate());
	            student.setGender(rs.getBoolean("gender"));
	            student.setMoneyAvailable(rs.getDouble("moneyAvailable"));
	        }

	    } catch (SQLException e) {
	        System.out.println("UserData.getStudentByCedula: " + e.getMessage());
	    }

	    return student;  // Retorna el objeto User con los datos del estudiante
	}

	public static String getStudentNameByCedula(int cedula) {
	    String studentName = null;

	    try {
	        Connection cn = DBConnection.getConecction();
	        
	        // Llamada al procedimiento almacenado para obtener el nombre del estudiante
	        String query = "{call spGetStudentNameByCedula(?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        
	        // Establecer el parámetro de cédula
	        stmt.setInt(1, cedula);
	        
	        // Ejecuta el procedimiento almacenado y obtiene el resultado
	        ResultSet rs = stmt.executeQuery();
	        
	        // Si hay un resultado, obtener el nombre
	        if (rs.next()) {
	            studentName = rs.getString("name");
	        }

	    } catch (SQLException e) {
	        System.out.println("UserData.getStudentNameByCedula: " + e.getMessage());
	    }

	    return studentName;  // Retorna el nombre del estudiante
	}
	
	
	public static boolean hasSufficientFunds(int cedula, double orderTotal) {
	    boolean hasFunds = false;

	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spCheckStudentFunds(?, ?, ?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        stmt.setInt(1, cedula);
	        stmt.setDouble(2, orderTotal);
	        
	        // Registro de salida
	        stmt.registerOutParameter(3, java.sql.Types.BOOLEAN);
	        stmt.execute();
	        
	        // Obtener el valor de retorno
	        hasFunds = stmt.getBoolean(3);
	        
	    } catch (SQLException e) {
	        System.out.println("UserData.hasSufficientFunds: " + e.getMessage());
	    }

	    return hasFunds;
	}

	
	public static void updateStudentFunds(int cedula, double orderTotal) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        
	        // Llamada al procedimiento almacenado para actualizar los fondos
	        String query = "{call spUpdateStudentFunds(?, ?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        
	        // Establecer los parámetros
	        stmt.setInt(1, cedula);
	        stmt.setDouble(2, orderTotal);
	        
	        // Ejecutar el procedimiento almacenado
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("UserData.updateStudentFunds: " + e.getMessage());
	    }
	}


}
