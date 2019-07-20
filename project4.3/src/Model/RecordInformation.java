package Model;

public class RecordInformation {

	private String Record_openYear;
	private String Record_div;
	private String Record_year;
	private String Record_name;
	private String Record_no;
	private String Record_score;
	private String Record_prof;
	private String Record_midterm;
	private String Record_finals;
	
	public RecordInformation(String Record_openYear, String Record_div, String Record_year, String Record_name, String Record_no, String Record_score, String Record_prof, String Record_midterm, String Record_finals) {
		super();
		this.Record_openYear = Record_openYear;
		this.Record_div = Record_div;
		this.Record_year = Record_year;
		this.Record_name = Record_name;
		this.Record_no = Record_no;
		this.Record_score = Record_score;
		this.Record_prof = Record_prof;
		this.Record_midterm = Record_midterm;
		this.Record_finals = Record_finals;
		
	}

	public String getRecord_openYear() {
		return Record_openYear;
	}

	public void setRecord_openYear(String record_openYear) {
		Record_openYear = record_openYear;
	}

	public String getRecord_div() {
		return Record_div;
	}

	public void setRecord_div(String record_div) {
		Record_div = record_div;
	}

	public String getRecord_year() {
		return Record_year;
	}

	public void setRecord_year(String record_year) {
		Record_year = record_year;
	}

	public String getRecord_name() {
		return Record_name;
	}

	public void setRecord_name(String record_name) {
		Record_name = record_name;
	}

	public String getRecord_no() {
		return Record_no;
	}

	public void setRecord_no(String record_no) {
		Record_no = record_no;
	}

	public String getRecord_score() {
		return Record_score;
	}

	public void setRecord_score(String record_score) {
		Record_score = record_score;
	}

	public String getRecord_prof() {
		return Record_prof;
	}

	public void setRecord_prof(String record_prof) {
		Record_prof = record_prof;
	}

	public String getRecord_midterm() {
		return Record_midterm;
	}

	public void setRecord_midterm(String record_midterm) {
		Record_midterm = record_midterm;
	}

	public String getRecord_finals() {
		return Record_finals;
	}

	public void setRecord_finals(String record_finals) {
		Record_finals = record_finals;
	}

	
}
