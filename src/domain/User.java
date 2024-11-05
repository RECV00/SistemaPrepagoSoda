package domain;

import java.time.LocalDate;

public class User {
	private int id_tbuser;
	private int id;
	private String password;
	private String tipe;
	private String photoRoute;
	private String name; 
	private String email;
	private int phone;
	private boolean isActive;
	private LocalDate dateEntry;
	private boolean gender;
	private double moneyAvailable;
	private String salt;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(int id_tbuser, int id, String password,String salt, String tipe, String photoRoute, String name, String email,
            int phone, boolean isActive, LocalDate dateEntry, boolean gender, double moneyAvailable) {
    super();
    this.id_tbuser = id_tbuser;
    this.id = id;
    this.password = password;
    this.salt = salt; 
    this.tipe = tipe;
    this.photoRoute = photoRoute;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.isActive = isActive;
    this.dateEntry = dateEntry;
    this.gender = gender;
    this.moneyAvailable = moneyAvailable;
    
}
	public User(int id, String password,String salt, String tipe, String photoRoute, String name, String email, int phone,
			boolean isActive, LocalDate dateEntry, boolean gender, double moneyAvailable) {
		super();
		this.id = id;
		this.password = password;
		this.salt = salt; 
		this.tipe = tipe;
		this.photoRoute = photoRoute;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.isActive = isActive;
		this.dateEntry = dateEntry;
		this.gender = gender;
		this.moneyAvailable = moneyAvailable;
	}

	public int getId_tbuser() {
		return id_tbuser;
	}

	public void setId_tbuser(int id_tbuser) {
		this.id_tbuser = id_tbuser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipe() {
		return tipe;
	}

	public void setTipe(String tipe) {
		this.tipe = tipe;
	}

	public String getPhotoRoute() {
		return photoRoute;
	}

	public void setPhotoRoute(String photoRoute) {
		this.photoRoute = photoRoute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(LocalDate dateEntry) {
		this.dateEntry = dateEntry;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public double getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(double moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}

	// Método getter para salt
	public String getSalt() {
	    return salt;
	}

	// Método setter para salt
	public void setSalt(String salt) {
	    this.salt = salt;
	}
	@Override
	public String toString() {
		return "User [id_tbuser=" + id_tbuser + ", id=" + id + ", password=" + password + ", tipe=" + tipe
				+ ", photoRoute=" + photoRoute + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", isActive=" + isActive + ", dateEntry=" + dateEntry + ", gender=" + gender + ", moneyAvailable="
				+ moneyAvailable + "]";
	}
}
