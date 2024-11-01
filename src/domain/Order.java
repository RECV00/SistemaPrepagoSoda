package domain;

public class Order {
	private int id_tborders;
	private String nameProduct;
	private int amount;
	private double total;
	private char isState;
	private String idStudent;
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int id_tborders, String nameProduct, int amount, double total, char isState, String idStudent) {
		super();
		this.id_tborders = id_tborders;
		this.nameProduct = nameProduct;
		this.amount = amount;
		this.total = total;
		this.isState = isState;
		this.idStudent = idStudent;
	}

	public Order(String nameProduct, int amount, double total, char isState, String idStudent) {
		super();
		this.nameProduct = nameProduct;
		this.amount = amount;
		this.total = total;
		this.isState = isState;
		this.idStudent = idStudent;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public char getIsState() {
		return isState;
	}

	public void setIsState(char isState) {
		this.isState = isState;
	}

	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}


	public int getId_tborders() {
		return id_tborders;
	}

	public void setId_tborders(int id_tborders) {
		this.id_tborders = id_tborders;
	}

	@Override
	public String toString() {
		return "Order [id_tborders=" + id_tborders + ", nameProduct=" + nameProduct + ", amount=" + amount + ", total="
				+ total + ", isState=" + isState + ", idStudent=" + idStudent + "]";
	}

	
}
