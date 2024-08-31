package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import domain.Dishe;

public class DisheData {

	public static ArrayList<Dishe> disheList = new ArrayList<>();
	
	private static String fileMondayB = "monday_breakfast.json";
	private static String fileMondayL = "monday_lunch.json";
	private static String fileTuesdayB = "tuesday_breakfast.json";
	private static String fileTuesdayL = "tuesday_lunch.json";
	private static String fileWednesdayB = "wednesday_breakfast.json";
	private static String fileWednesdayL = "wednesday_lunch.json";
	private static String fileThursdayB = "thursday_breakfast.json";
	private static String fileThursdayL = "thursday_lunch.json";
	private static String fileFridayB = "friday_breakfast.json";
	private static String fileFridayL = "friday_lunch.json";
	
	private static JSONUtils<Dishe> jsonUtilsMondayB = new JSONUtils<>(fileMondayB);
	private static JSONUtils<Dishe> jsonUtilsMondayL = new JSONUtils<>(fileMondayL);
	private static JSONUtils<Dishe> jsonUtilsTuesdayB = new JSONUtils<>(fileTuesdayB);
	private static JSONUtils<Dishe> jsonUtilsTuesdayL = new JSONUtils<>(fileTuesdayL);
	private static JSONUtils<Dishe> jsonUtilsWednesdayB = new JSONUtils<>(fileWednesdayB);
	private static JSONUtils<Dishe> jsonUtilsWednesdayL = new JSONUtils<>(fileWednesdayL);
	private static JSONUtils<Dishe> jsonUtilsThursdayB = new JSONUtils<>(fileThursdayB);
	private static JSONUtils<Dishe> jsonUtilsThursdayL = new JSONUtils<>(fileThursdayL);
	private static JSONUtils<Dishe> jsonUtilsFridayB = new JSONUtils<>(fileFridayB );
	private static JSONUtils<Dishe> jsonUtilsFridayL = new JSONUtils<>(fileFridayL );
	
//-------------------------------------------------------------------------------------------------------------------
	//GETS 
	public static List<Dishe> getMonday_BreakfastList(){
		try {
			return jsonUtilsMondayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getMonday_LunchList(){
		try {
			return jsonUtilsMondayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getTuesday_BreakfastList(){
		try {
			return jsonUtilsTuesdayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getTuesday_LunchList(){
		try {
			return jsonUtilsTuesdayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getWednesday_BreakfastList(){
		try {
			return jsonUtilsWednesdayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getWednesday_LunchList(){
		try {
			return jsonUtilsWednesdayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getThursday_BreakfastList(){
		try {
			return jsonUtilsThursdayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getThursday_LunchList(){
		try {
			return jsonUtilsThursdayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dishe> getFriday_BreakfastList(){
		try {
			return jsonUtilsFridayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Dishe> getFriday_LunchList(){
		try {
			return jsonUtilsFridayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//-------------------------------------------------------------------------------------------
	public static boolean saveDishe(Dishe d,boolean serviceHours, String serviceDay) {
		try {
			if(serviceHours == true && serviceDay.equals("Lunes")) {
				jsonUtilsMondayL.saveElement(d);
			}else if(serviceHours == false && serviceDay.equals("Lunes")) {
				jsonUtilsMondayB.saveElement(d);
			}else if(serviceHours == true && serviceDay.equals("Martes")) {
				jsonUtilsTuesdayL.saveElement(d);
			}else if(serviceHours == false && serviceDay.equals("Martes")) {
				jsonUtilsTuesdayB.saveElement(d);
			}else if(serviceHours == true && serviceDay.equals("Miercoles")) {
				jsonUtilsWednesdayL.saveElement(d);
			}else if(serviceHours == false && serviceDay.equals("Miercoles")) {
				jsonUtilsWednesdayB.saveElement(d);
			}else if(serviceHours == true && serviceDay.equals("Jueves")) {
				jsonUtilsThursdayL.saveElement(d);
			}else if(serviceHours == false && serviceDay.equals("Jueves")) {
				jsonUtilsThursdayB.saveElement(d);
			}else if(serviceHours == true && serviceDay.equals("Viernes")) {
				jsonUtilsFridayL.saveElement(d);
			}else if(serviceHours == false && serviceDay.equals("Viernes")) {
				jsonUtilsFridayB.saveElement(d);
			}
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateDishesByServiceName(Dishe updatedDishe, String name,boolean serviceHours,String serviceDay) {
	    try {
	    	
	    	if(serviceHours == true && serviceDay.equals("Lunes")) {
	    		jsonUtilsMondayL.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == false && serviceDay.equals("Lunes")) {
				jsonUtilsMondayB.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == true && serviceDay.equals("Martes")) {
				jsonUtilsTuesdayL.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == false && serviceDay.equals("Martes")) {
				jsonUtilsTuesdayB.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == true && serviceDay.equals("Miercoles")) {
				jsonUtilsWednesdayL.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == false && serviceDay.equals("Miercoles")) {
				jsonUtilsWednesdayB.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == true && serviceDay.equals("Jueves")) {
				jsonUtilsThursdayL.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == false && serviceDay.equals("Jueves")) {
				jsonUtilsThursdayB.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == true && serviceDay.equals("Viernes")) {
				jsonUtilsFridayL.updateElementDishes(updatedDishe, name);
			}else if(serviceHours == false && serviceDay.equals("Viernes")) {
				jsonUtilsFridayB.updateElementDishes(updatedDishe, name);
			}else {
				
			}
	    	
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
//-----------------------------------------------------------------------------------------------------
	//ELIMINAR PLATILLO
	public static boolean deleteMonday_Breakfast(Dishe d) {
		List<Dishe> listMonday_Breakfast = getMonday_BreakfastList();
		int index =0;
		try {
			for( Dishe dishe: listMonday_Breakfast) {
				if(d.getServiceName().equals(dishe.getServiceName())) {
					jsonUtilsMondayB.deleteElement(d,index);
					return true;
				}
				index++;
			}
	        return true;
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
//-----------------------------------------------------------------------------------------------------
	//Monday lunch
	
	public static boolean deleteMonday_Lunch(Dishe d) {
		List<Dishe> listMonday_Lunch = getMonday_LunchList();
		int index =0;
		try {
			for( Dishe dishe: listMonday_Lunch) {
				if(d.getServiceName().equals(dishe.getServiceName())) {
					jsonUtilsMondayL.deleteElement(d,index);
					return true;
				}
				index++;
			}
	        return true;
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

//------------------------------------------------------------------------------------------------------------------------
	//TUESDAY_BREAKFAST
	
	public static boolean deleteTuesday_Breakfast(Dishe d) {
		List<Dishe> listTuesday_Breakfast = getTuesday_BreakfastList();
		int index =0;
		try {
			for( Dishe dishe: listTuesday_Breakfast) {
				if(d.getServiceName().equals(dishe.getServiceName())) {
					jsonUtilsTuesdayB.deleteElement(d,index);
					return true;
				}
				index++;
			}
	        return true;
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

//-----------------------------------------------------------------------------------------------------------------
	//TUESDAY lunch
		
		public static boolean deleteTuesday_Lunch(Dishe d) {
			List<Dishe> listTuesday_Lunch = getTuesday_LunchList();
			int index =0;
			try {
				for( Dishe dishe: listTuesday_Lunch) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsTuesdayL.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

//---------------------------------------------------------------------------------------------------------
//WEDNESDAY BREAKFAST
		

		public static boolean deleteWebnesday_Breakfast(Dishe d) {
			List<Dishe> listWednesday_Breakfast = getWednesday_BreakfastList();
			int index =0;
			try {
				for( Dishe dishe: listWednesday_Breakfast) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsWednesdayB.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
	
//-----------------------------------------------------------------------------------------------------------------------
//		WEDNESDAY_LUNCH
		
	

		public static boolean deleteWednesday_Lunch(Dishe d) {
			List<Dishe> listWednesday_Lunch = getWednesday_LunchList();
			int index =0;
			try {
				for( Dishe dishe: listWednesday_Lunch) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsWednesdayL.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

//---------------------------------------------------------------------------------------------------------------
	//THURSDAY_BREAKFAST
		
		public static boolean deleteThursday_Breakfast(Dishe d) {
			List<Dishe> listThursday_Breakfast = getThursday_BreakfastList();
			int index =0;
			try {
				for( Dishe dishe: listThursday_Breakfast) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsWednesdayB.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

//-----------------------------------------------------------------------------------------------------
	//THURSDAY_LUNCH
		
		
		public static boolean deleteThursday_Lunch(Dishe d) {
			List<Dishe> listThursday_Lunch = getThursday_LunchList();
			int index =0;
			try {
				for( Dishe dishe: listThursday_Lunch) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsThursdayL.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

//-------------------------------------------------------------------------------------------------------------
	//FRIDAY_BREAKFAST
	
		
		public static boolean deleteFriday_Breakfast(Dishe d) {
			List<Dishe> listFriday_Breakfast = getFriday_BreakfastList();
			int index =0;
			try {
				for( Dishe dishe: listFriday_Breakfast) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsFridayB.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

//------------------------------------------------------------------------------------------------------------
	//FRIDAY_LUNCH
		
		public static boolean deleteFriday_Lunch(Dishe d) {
			List<Dishe> listFriday_Lunch = getFriday_LunchList();
			int index =0;
			try {
				for( Dishe dishe: listFriday_Lunch) {
					if(d.getServiceName().equals(dishe.getServiceName())) {
						jsonUtilsFridayL.deleteElement(d,index);
						return true;
					}
					index++;
				}
		        return true;
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

}
