package domain;

import javafx.scene.control.CheckBox;

public class Dishe {
	
		private boolean serviceHours;
		private String serviceDay;
		private String serviceName;
		private double servicePrice;
		
		public Dishe() {}

		public Dishe(boolean serviceHours, String serviceDay, String serviceName, double servicePrice) {
			super();
			this.serviceHours = serviceHours;
			this.serviceDay = serviceDay;
			this.serviceName = serviceName;
			this.servicePrice = servicePrice;
		}
//---------------------------------------------------
		


	public boolean isServiceHours() {
		return serviceHours;
	}

	public void setServiceHours(boolean serviceHours) {
		this.serviceHours = serviceHours;
	}

	public String getServiceDay() {
		return serviceDay;
	}

	public void setServiceDay(String serviceDay) {
		this.serviceDay = serviceDay;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	@Override
	public String toString() {
		return  ((serviceHours)?1:0) + "-" +serviceDay + "-" + serviceName+ "-" +servicePrice;
	}

	
}
