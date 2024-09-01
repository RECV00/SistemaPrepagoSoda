package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Recharge;
import domain.Student;

public class RechargeData {

	public static ArrayList<Recharge> RechargetList = new ArrayList();
	private static final String fileName = "student_recharges.json";
	private static JSONUtils<Recharge> jsonUtils = new JSONUtils<>(fileName);
	
	
	public static List<Recharge> getRechargeList(){
		try {
				return jsonUtils.getElements(Recharge.class);
		}catch(IOException e) {
			
		}
		return null;
		
	}
	
	public static String getRechargeStringFormat(Recharge r) {
		return "\n Carnet: "+ r.getCarnetStudent().toUpperCase()+"\n Monto: "+ r.getAmount()+"\n Fecha Ingreso: " + r.getDateEntry();	
	}
	
	public static boolean saveRecharge(Recharge r) {
		System.out.print(r);
		try {
			jsonUtils.saveElement(r);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteRecharge(Recharge r) {
		System.out.println("deleteRecharge"+r);
	    try {
	    	List<Recharge> list = getRechargeList();
	    	int index = 0;
	    	for(Recharge recharge: list) {
	    		
	    		if(r.getCarnetStudent().equals(recharge.getCarnetStudent())) {
	    			jsonUtils.deleteElement(r,index);
	    			return true;
	    		}
	    		index++;
	    	}
	        
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public static boolean updateRecharge(Recharge r,String carnet) {
		System.out.print(r);
		try {
			jsonUtils.updateElementRecharges(r,carnet);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}