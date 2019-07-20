package Model;

public class IndispensableClassInformation {

	private String Clas_no;
	private String Indi_class;
	private String Clas_score;
	private String Clas_prof;

	public IndispensableClassInformation(String Clas_no, String Indi_class, String Clas_score, String Clas_prof) {
		super();
		this.Clas_no = Clas_no;
		this.Indi_class = Indi_class;
		this.Clas_score = Clas_score;
		this.Clas_prof = Clas_prof;
	}

	public String getClas_score() {
		return Clas_score;
	}

	public void setClas_score(String clas_score) {
		Clas_score = clas_score;
	}

	public String getClas_no() {
		return Clas_no;
	}

	public void setClas_no(String clas_no) {
		Clas_no = clas_no;
	}

	public String getIndi_class() {
		return Indi_class;
	}

	public void setIndi_class(String indi_class) {
		Indi_class = indi_class;
	}

	public String getClas_prof() {
		return Clas_prof;
	}

	public void setClas_prof(String clas_prof) {
		Clas_prof = clas_prof;
	}

}