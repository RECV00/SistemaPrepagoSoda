package data;

import domain.Dishe;

public class LogicData {

	public LogicData() {
		// TODO Auto-generated constructor stub
	}
	
	public void deleteDishes(Dishe d,boolean serviceHours, String serviceDay) {
		
		if(serviceHours == true && serviceDay.equals("Lunes")) {
			DisheData.deleteMonday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Lunes")) {// desayuno
			DisheData.deleteMonday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Martes")) {
			DisheData.deleteTuesday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Martes")) {// desayuno
			DisheData.deleteTuesday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Miercoles")) {
			DisheData.deleteWednesday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Miercoles")) {// desayuno
			DisheData.deleteWebnesday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Jueves")) {
			DisheData.deleteThursday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Jueves")) {// desayuno
			DisheData.deleteThursday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Viernes")) {
			DisheData.deleteFriday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Viernes")) {// desayuno
			DisheData.deleteFriday_Breakfast(d);
		}else {
			
		}
		
	}
}
