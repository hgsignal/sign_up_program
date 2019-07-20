package Model;

public class AdminInformation {

	private String Admin_no;
	private String Admin_password;
	private String Admin_name;
	private String Admin_joincompany;
	private String Admin_department;
	private String Admin_Birth;
	private String Admin_contact;
	
	
	
	public AdminInformation(String Admin_joincompany, String Admin_department, String Admin_name,
			String Admin_Birth, String Admin_no, String Admin_contact ) {
		super();
		this.Admin_joincompany = Admin_joincompany;
		this.Admin_department = Admin_department;
		this.Admin_name = Admin_name;
		this.Admin_Birth = Admin_Birth;
		this.Admin_no = Admin_no;
		this.Admin_contact = Admin_contact;
		
	}
	
	
	public AdminInformation(String Admin_no, String Admin_password) {
		super();
		this.Admin_no = Admin_no;
		this.Admin_password = Admin_password;
		
	}

	
	
	public String getAdmin_no() {
		return Admin_no;
	}

	public void setAdmin_no(String admin_no) {
		Admin_no = admin_no;
	}

	public String getAdmin_password() {
		return Admin_password;
	}

	public void setAdmin_password(String admin_password) {
		Admin_password = admin_password;
	}

	public String getAdmin_name() {
		return Admin_name;
	}

	public void setAdmin_name(String admin_name) {
		Admin_name = admin_name;
	}

	public String getAdmin_joincompany() {
		return Admin_joincompany;
	}

	public void setAdmin_joincompany(String admin_joincompany) {
		Admin_joincompany = admin_joincompany;
	}

	public String getAdmin_department() {
		return Admin_department;
	}

	public void setAdmin_department(String admin_department) {
		Admin_department = admin_department;
	}

	public String getAdmin_Birth() {
		return Admin_Birth;
	}

	public void setAdmin_Birth(String Admin_Birth) {
		Admin_Birth = Admin_Birth;
	}

	public String getAdmin_contact() {
		return Admin_contact;
	}

	public void setAdmin_contact(String admin_contact) {
		Admin_contact = admin_contact;
	}
	
	
}
