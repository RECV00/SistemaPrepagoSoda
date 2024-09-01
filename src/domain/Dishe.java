package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Dishe {
	
		private boolean serviceHours;
		private String serviceDay;
		private String serviceName;
		private double servicePrice;
		
	    private BooleanProperty selected = new SimpleBooleanProperty(false); // Nuevo atributo
	    private boolean requested;// saber si hay una solicitud 
	    
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

	 public boolean isSelected() {
	        return selected.get();
	    }

	    public void setSelected(boolean selected) {
	        this.selected.set(selected);
	    }

	    public BooleanProperty selectedProperty() {
	        return selected;
	    }
	    // Getter y Setter para requested
	    public boolean isRequested() {
	        return requested;
	    }

	    public void setRequested(boolean requested) {
	        this.requested = requested;
	    }
	@Override
	public String toString() {
		return  ((serviceHours)?"Almuerzo":"Desayuno") + "-" +serviceDay + "-" + serviceName+ "-" +servicePrice;
	}

	
}
