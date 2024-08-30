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
	private static String fileTuesdayL = "tuesday_lunchTuesday.json";
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
	//Monday breakfast
	public static List<Dishe> getMonday_BreakfastList(){
		try {
			return jsonUtilsMondayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean saveMonday_Breakfast(Dishe d) {
		try {
			jsonUtilsMondayB.saveElement(d);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
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
//	public static boolean updateMonday_BreakfastByName(Dishe updatedDishe, String name) {
//	    try {
//	    	jsonUtilsMondayB.updateElementByCarnet(updatedDishe, name);
//	        return true;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return false;
//	}
//-----------------------------------------------------------------------------------------------------
	//Monday lunch
	public static List<Dishe> getMonday_LunchList(){
		try {
			return jsonUtilsMondayL.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean saveMonday_Lunch(Dishe d) {
		try {
			jsonUtilsMondayL.saveElement(d);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
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
//	public static boolean updateMonday_BreakfastByName(Dishe updatedDishe, String name) {
//	    try {
//	    	jsonUtilsMondayL.updateElementByCarnet(updatedDishe, name);
//	        return true;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return false;
//	}
//------------------------------------------------------------------------------------------------------------------------
	//TUESDAY_BREAKFAST
	public static List<Dishe> getTuesday_BreakfastList(){
		try {
			return jsonUtilsTuesdayB.getElements(Dishe.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean saveTuesday_Breakfast(Dishe d) {
		try {
			jsonUtilsTuesdayB.saveElement(d);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
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
//	public static boolean updateTuesday_BreakfastByName(Dishe updatedDishe, String name) {
//	    try {
//	    	jsonUtilsTuesdayB.updateElementByCarnet(updatedDishe, name);
//	        return true;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return false;
//	}
//-----------------------------------------------------------------------------------------------------------------
	//TUESDAY lunch
		public static List<Dishe> getTuesday_LunchList(){
			try {
				return jsonUtilsTuesdayL.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveTuesday_Lunch(Dishe d) {
			try {
				jsonUtilsTuesdayL.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateTuesday_LunchByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsTuesdayL.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
//---------------------------------------------------------------------------------------------------------
//WEDNESDAY BREAKFAST
		public static List<Dishe> getWednesday_BreakfastList(){
			try {
				return jsonUtilsWednesdayB.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveWednesday_Breakfast(Dishe d) {
			try {
				jsonUtilsWednesdayB.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateWebnesday_BreakfastByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsWednesdayB.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}	
//-----------------------------------------------------------------------------------------------------------------------
//		WEDNESDAY_LUNCH
		
		public static List<Dishe> getWednesday_LunchList(){
			try {
				return jsonUtilsWednesdayL.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveWednesday_Lunch(Dishe d) {
			try {
				jsonUtilsWednesdayL.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateWednesday_LunchByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsWednesdayL.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
//---------------------------------------------------------------------------------------------------------------
	//THURSDAY_BREAKFAST
		public static List<Dishe> getThursday_BreakfastList(){
			try {
				return jsonUtilsThursdayB.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveThursday_Breakfast(Dishe d) {
			try {
				jsonUtilsThursdayB.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateThursday_BreakfastByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsThursdayB.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
//-----------------------------------------------------------------------------------------------------
	//THURSDAY_LUNCH
		public static List<Dishe> getThursday_LunchList(){
			try {
				return jsonUtilsThursdayL.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveThursday_Lunch(Dishe d) {
			try {
				jsonUtilsThursdayL.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateThursday_LunchByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsThursdayL.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
//-------------------------------------------------------------------------------------------------------------
	//FRIDAY_BREAKFAST
		public static List<Dishe> getFriday_BreakfastList(){
			try {
				return jsonUtilsFridayB.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveFriday_Breakfast(Dishe d) {
			try {
				jsonUtilsFridayB.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateFriday_BreakfastByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsFridayB.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
//------------------------------------------------------------------------------------------------------------
	//FRIDAY_LUNCH
		public static List<Dishe> getFriday_LunchList(){
			try {
				return jsonUtilsFridayL.getElements(Dishe.class);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static boolean saveFriday_Lunch(Dishe d) {
			try {
				jsonUtilsFridayL.saveElement(d);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
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
//		public static boolean updateFriday_LunchByName(Dishe updatedDishe, String name) {
//		    try {
//		    	jsonUtilsFridaydayL.updateElementByCarnet(updatedDishe, name);
//		        return true;
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return false;
//		}
}
