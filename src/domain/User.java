package domain;

public class User {
	private int id_tbuser;
	private int id;
	private String password;
	private char tipe;
	private String photoRoute;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id_tbuser, int id, String password, char tipe, String photoRoute) {
		super();
		this.id_tbuser = id_tbuser;
		this.id = id;
		this.password = password;
		this.tipe = tipe;
		this.photoRoute = photoRoute;
	}

	public User(int id, String password, char tipe, String photoRoute) {
		super();
		this.id = id;
		this.password = password;
		this.tipe = tipe;
		this.photoRoute = photoRoute;
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

	public char getTipe() {
		return tipe;
	}

	public void setTipe(char tipe) {
		this.tipe = tipe;
	}

	public String getPhotoRoute() {
		return photoRoute;
	}

	public void setPhotoRoute(String photoRoute) {
		this.photoRoute = photoRoute;
	}

	public int getId_tbuser() {
		return id_tbuser;
	}

	public void setId_tbuser(int id_tbuser) {
		this.id_tbuser = id_tbuser;
	}

	@Override
	public String toString() {
		return "User [id_tbuser=" + id_tbuser + ", id=" + id + ", password=" + password + ", tipe=" + tipe
				+ ", photoRoute=" + photoRoute + "]";
	}


	
}
