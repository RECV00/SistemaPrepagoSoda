package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Order;

public class OrderData {

	public static void updateOrder(Order or) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spUpdateOrder(?, ?, ?, ?, ?, ?)}"; 
	        CallableStatement stmt = cn.prepareCall(query);

	        stmt.setInt(1, or.getId_tborders());
	        stmt.setString(2, or.getNameProduct());
	        stmt.setInt(3, or.getAmount());
	        stmt.setDouble(4, or.getTotal());
	        stmt.setString(5, String.valueOf(or.getIsState()));
	        stmt.setString(6, or.getIdStudent());

	        stmt.execute(); 

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void saveOrder(Order or) {
	    try {
	        Connection cn = DBConnection.getConecction();
	        String query = "{call spSaveOrder(?, ?, ?, ?, ?)}"; 
	        CallableStatement stmt = cn.prepareCall(query);

	        stmt.setString(1, or.getNameProduct());
	        stmt.setInt(2, or.getAmount());
	        stmt.setDouble(3, or.getTotal());
	        stmt.setString(4, String.valueOf(or.getIsState()));
	        stmt.setString(5, or.getIdStudent());

	        stmt.execute(); 

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



public static void deleteOrder(int vOr) {
	try {
		Connection cn = DBConnection.getConecction();
		String query = "{call spDeleteOrder(?)}";
		CallableStatement stmt = cn.prepareCall(query);
		stmt.setInt(1, vOr);
		stmt.executeQuery();
		
	}catch(SQLException e) {

		e.printStackTrace();
	}
}
public static LinkedList<Order > getOrders(){
	LinkedList<Order> list = new LinkedList();
	try {
		Connection cn = DBConnection.getConecction();
		String query = "{call spListOrder}";
		CallableStatement stmt = cn.prepareCall(query);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Order order = new Order();
			order.setId_tborders(rs.getInt(1));
			order.setNameProduct(rs.getString(2));
			order.setAmount(rs.getInt(3));
			order.setTotal(rs.getDouble(4));
			String isStateStr = rs.getString(5).trim(); 
			order.setIsState(isStateStr.isEmpty() ? '\0' : isStateStr.charAt(0));
			order.setIdStudent(rs.getString(6));
			list.add(order);
		}
		
		
	}catch(SQLException e) {
		System.out.println("OrderData.getOrder"+ e.getMessage());
	}
	
	return list;
}
		
public static void updateOrderState(String nameProduct, String idStudent, char newState) {
    try {
        Connection cn = DBConnection.getConecction();
        String query = "{call spUpdateOrderState(?, ?, ?)}";
        CallableStatement stmt = cn.prepareCall(query);
        stmt.setString(1, nameProduct);
        stmt.setString(2, idStudent);
        stmt.setString(3, String.valueOf(newState));
        stmt.executeUpdate();
        
    } catch (SQLException e) {
        System.out.println("UserData.updateOrderState: " + e.getMessage());
    }
}

}


