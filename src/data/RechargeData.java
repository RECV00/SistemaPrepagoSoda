package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Recharge;
import domain.Student;

public class RechargeData {

	public static ArrayList<Recharge> RechargetList = new ArrayList<Recharge>();
	private static final String fileName = "student_recharges.json";
	private static JSONUtils<Recharge> jsonUtils = new JSONUtils<>(fileName);
	
	
	public static List<Recharge> getRechargeList(){
		try {
				return jsonUtils.getElements(Recharge.class);
		}catch(IOException e) {
			
		}
		return null;
		
	}
	
	public static LinkedList<Recharge> getRechargesByCarnet1(String carnet) {
        try {
            // Obtener la lista completa de recargas desde el archivo JSON
            List<Recharge> allRecharges = jsonUtils.getElements(Recharge.class);
            
            // Filtrar las recargas por el carnet proporcionado
            return allRecharges.stream()
                .filter(recharge -> recharge.getCarnetStudent().equals(carnet))
                .collect(Collectors.toCollection(LinkedList::new));
                
        } catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<>(); // Retorna una lista vacía en caso de error
        }
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
			e.printStackTrace();
		}
		return false;
	}
	
	  public static Recharge getRechargeByCarnet(String carnet) {
	        List<Recharge> recharges = getRechargeList();
	        for (Recharge recharge : recharges) {
	            if (recharge.getCarnetStudent().equals(carnet)) {
	                return recharge;
	            }
	        }
	        return null;
	    }
		
}