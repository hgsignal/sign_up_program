package Model;

public class GeneralInformation {

	private String Stu_admission;
	private String Stu_no;
	private String General_password;
	private String Stu_name;
	private String Stu_birth;
	private String Stu_contact;
	private String Stu_email;
	private String Stu_address;
	private String Stu_year;
	private String Stu_count;
	private String Dept_college;
	private String Dept_name;
	private String Dept_no;
	private String Stu_state;
	
	
	public GeneralInformation() {
		super();
	}
	
	public GeneralInformation(String Stu_admission, String Stu_no, String General_password, String Stu_name, String Stu_birth, String Stu_contact, 
			String Stu_email, String Stu_address, String Stu_year, String Dept_college, String Dept_name, String Dept_no, String Stu_state) {
		super();
		this.Stu_admission = Stu_admission;
		this.Stu_no = Stu_no;
		this.General_password = General_password;
		this.Stu_name = Stu_name;
		this.Stu_birth = Stu_birth;
		this.Stu_contact = Stu_contact;
		this.Stu_email = Stu_email;
		this.Stu_address = Stu_address;
		this.Stu_year = Stu_year;
		this.Dept_college = Dept_college;
		this.Dept_name = Dept_name;
		this.Dept_no = Dept_no;
		this.Stu_state = Stu_state;
	}

	
	public GeneralInformation(String Stu_name, String Stu_no, String Stu_birth, String Stu_contact, String Stu_email,
			String Stu_address, String General_password) {
		super();
		this.Stu_name = Stu_name;
		this.Stu_no = Stu_no;
		this.Stu_birth = Stu_birth;
		this.Stu_contact = Stu_contact;
		this.Stu_email = Stu_email;
		this.Stu_address = Stu_address;
		this.General_password = General_password;

	}

	public GeneralInformation(String Stu_contact, String Stu_email, String Stu_address, String General_password) {
		super();
		this.Stu_contact = Stu_contact;
		this.Stu_email = Stu_email;
		this.Stu_address = Stu_address;
		this.General_password = General_password;

	}

	public GeneralInformation(String Stu_no, String Stu_name, String Dept_name, String Stu_year, String Dept_no, String Dept_college, String Stu_state, String Stu_email) {
		super();
		this.Stu_no = Stu_no;
		this.Stu_name = Stu_name;
		this.Dept_name = Dept_name;
		this.Stu_year = Stu_year;
		this.Dept_no = Dept_no;
		this.Dept_college = Dept_college;
		this.Stu_state = Stu_state;
		this.Stu_email = Stu_email;
	}
	
	public GeneralInformation(String Stu_no, String General_password) {
		super();
		this.Stu_no = Stu_no;
		this.General_password = General_password;

	}
		
	public GeneralInformation(String Stu_count) {
		super();
		this.Stu_count = Stu_count;
	}
	
	

	public String getStu_admission() {
		return Stu_admission;
	}

	public void setStu_admission(String stu_admission) {
		Stu_admission = stu_admission;
	}

	public String getStu_no() {
		return Stu_no;
	}

	public void setStu_no(String stu_no) {
		Stu_no = stu_no;
	}

	public String getGeneral_password() {
		return General_password;
	}

	public void setGeneral_password(String general_password) {
		General_password = general_password;
	}

	public String getStu_name() {
		return Stu_name;
	}

	public void setStu_name(String stu_name) {
		Stu_name = stu_name;
	}

	public String getStu_birth() {
		return Stu_birth;
	}

	public void setStu_birth(String stu_birth) {
		Stu_birth = stu_birth;
	}

	public String getStu_contact() {
		return Stu_contact;
	}

	public void setStu_contact(String stu_contact) {
		Stu_contact = stu_contact;
	}

	public String getStu_email() {
		return Stu_email;
	}

	public void setStu_email(String stu_email) {
		Stu_email = stu_email;
	}

	public String getStu_address() {
		return Stu_address;
	}

	public void setStu_address(String stu_address) {
		Stu_address = stu_address;
	}

	public String getStu_year() {
		return Stu_year;
	}

	public void setStu_year(String stu_year) {
		Stu_year = stu_year;
	}

	public String getStu_count() {
		return Stu_count;
	}

	public void setStu_count(String stu_count) {
		Stu_count = stu_count;
	}

	public String getDept_college() {
		return Dept_college;
	}

	public void setDept_college(String dept_college) {
		Dept_college = dept_college;
	}

	public String getDept_name() {
		return Dept_name;
	}

	public void setDept_name(String dept_name) {
		Dept_name = dept_name;
	}

	public String getDept_no() {
		return Dept_no;
	}

	public void setDept_no(String dept_no) {
		Dept_no = dept_no;
	}

	public String getStu_state() {
		return Stu_state;
	}

	public void setStu_state(String stu_state) {
		Stu_state = stu_state;
	}
	
	
	
}
