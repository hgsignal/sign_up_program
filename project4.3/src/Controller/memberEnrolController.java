package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Model.AdminInformation;
import Model.ClassInformation;
import Model.GeneralInformation;
import Model.PersonalClassInformation;
import Model.RecordInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class memberEnrolController implements Initializable {

	// ------------------------------------------------------------------ 학생등록 탭

	@FXML private TextField admissionText;
	@FXML private ComboBox<String> collegeCombo;
	@FXML private ComboBox<String> stuDepartmentCombo;
	@FXML private TextField stuNameText;
	@FXML private TextField stuDateOfBirthText;
	@FXML private TextField studentIDText;
	@FXML private Button btnStuEnrol;
	@FXML private Button btnStuDelete;
	@FXML private Button btnStudentListView;
	@FXML private Button btnStudentSearch;

	@FXML private TableColumn colAdmission;
	@FXML private TableColumn colCollege;
	@FXML private TableColumn colDepartment;
	@FXML private TableColumn colStuName;
	@FXML private TableColumn colDateOfBirth;
	@FXML private TableColumn colStudentID;

	@FXML private TableView<GeneralInformation> studentListView;

	// ------------------------------------------------------------------ 직원등록 탭

	@FXML private TextField joinCompanyText;
	@FXML private ComboBox<String> staffDepartmentCombo;
	@FXML private TextField staffNameText;
	@FXML private TextField staffDateOfBirthText;
	@FXML private TextField staffIDText;
	@FXML private TextField staffPhoneText;
	@FXML private Button btnStaffEnrol;
	@FXML private Button btnStaffDelete;
	@FXML private Button btnStaffListView;
	@FXML private Button btnStaffSearch;

	@FXML private TableColumn colJoinCompany;
	@FXML private TableColumn colStaffDepartment;
	@FXML private TableColumn colStaffNameText;
	@FXML private TableColumn colStaffDateOfBirth;
	@FXML private TableColumn colStaffID;
	@FXML private TableColumn colStaffPhone;

	@FXML private TableView<AdminInformation> staffListView;

	private memberEnrolDAO enrolDAO = new memberEnrolDAO();
	private int classSelectIndex, classSelectIndex2;
	private function fun = new function();
	
	ObservableList<String> comboItemCollege = FXCollections.observableArrayList("법학", "소프트웨어융합");
	ObservableList<String> comboItemStuDepartment = FXCollections.observableArrayList("일반법학", "컴퓨터공학");
	ObservableList<String> comboItemstaffDepartment = FXCollections.observableArrayList("학생교육지원부서", "학생지원팀");

	ObservableList<GeneralInformation> generalData = FXCollections.observableArrayList();
	ObservableList<AdminInformation> adminData = FXCollections.observableArrayList();


	
	public memberEnrolController() {
		
		// ------------------------------- 탭1) 학번 테이블 첫화면
		ArrayList<GeneralInformation> studentMemberList = enrolDAO.studentMemberLoad();
		if (studentMemberList == null) {
		} else {
			generalData.removeAll(generalData);
			for (GeneralInformation generalInformation : studentMemberList) {
				generalData.add(generalInformation);
			}
		}

		// ------------------------------- 탭2) 직원 테이블 첫화면
		ArrayList<AdminInformation> staffMemberList = enrolDAO.staffMemberLoad();
		if (staffMemberList == null) {
		} else {
			adminData.removeAll(adminData);
			for (AdminInformation adminInformation : staffMemberList) {
				adminData.add(adminInformation);
			}
		}
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		textForm();
		studentListViewColData();
		staffListViewColData();

		// ---------------------------------------------- 학번 등록/삭제/조회하기
		btnStuEnrol.setOnAction(event -> stuEnrolBtnAction(event));
		btnStuDelete.setOnAction(event -> stuDeleteBtnAction(event));
		btnStudentListView.setOnAction(event -> studentListViewBtnAction(event));
		btnStudentSearch.setOnAction(event -> studentSearchBtnAction(event));

		// ---------------------------------------------- 직원 등록/삭제/조회하기
		btnStaffEnrol.setOnAction(event -> staffEnrolBtnAction(event));
		btnStaffDelete.setOnAction(event -> staffDeleteBtnAction(event));
		btnStaffListView.setOnAction(event -> staffListViewBtnAction(event));
		btnStaffSearch.setOnAction(event -> staffSearchBtnAction(event));

		studentListView.setOnMouseClicked(event -> handleStudentListMouseClicked(event));
		staffListView.setOnMouseClicked(event -> handleStaffListMouseClicked(event));

		collegeCombo.setItems(comboItemCollege);
		stuDepartmentCombo.setItems(comboItemStuDepartment);
		staffDepartmentCombo.setItems(comboItemstaffDepartment);
	}

	private void studentListViewColData() {
		studentListView.setEditable(true);

		colAdmission.setCellValueFactory(new PropertyValueFactory<>("Stu_admission"));
		colCollege.setCellValueFactory(new PropertyValueFactory<>("Dept_college"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("Dept_name"));
		colStuName.setCellValueFactory(new PropertyValueFactory<>("Stu_name"));
		colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("Stu_birth"));
		colStudentID.setCellValueFactory(new PropertyValueFactory<>("Stu_no"));

		studentListView.setItems(generalData);
	}

	private void staffListViewColData() {
		staffListView.setEditable(true);

		colJoinCompany.setCellValueFactory(new PropertyValueFactory<>("Admin_joincompany"));
		colStaffDepartment.setCellValueFactory(new PropertyValueFactory<>("Admin_department"));
		colStaffNameText.setCellValueFactory(new PropertyValueFactory<>("Admin_name"));
		colStaffDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("Admin_Birth"));
		colStaffID.setCellValueFactory(new PropertyValueFactory<>("Admin_no"));
		;
		colStaffPhone.setCellValueFactory(new PropertyValueFactory<>("Admin_contact"));

		staffListView.setItems(adminData);
	}
	

	// ------------------------------------------ 학생 등록하기

	private void stuEnrolBtnAction(ActionEvent event) {

		String admission = admissionText.getText();             // -------------- 텍스트필드 "입학년도" 
		String college = collegeCombo.getValue();               // -------------- 콤보박스 "대학"
		String stuDepartment = stuDepartmentCombo.getValue();   // -------------- 콤보박스 "학과"
		String stuName = stuNameText.getText();                 // -------------- 텍스트필드 "이름" 값
		String stuDateOfBirth = stuDateOfBirthText.getText();   // -------------- 텍스트필드 "생년월일"
		String studentID = studentIDText.getText();             // -------------- 텍스트필드 "학번"

		int resultNumber = enrolDAO.stuEnrolClassLoad(admission, college, stuDepartment, 
				stuName, stuDateOfBirth, studentID);

		
		// -------------- 새로 등록한 학생 정보를 테이블에 띄워 확인하기
		ArrayList<GeneralInformation> generalInformationList = enrolDAO.studentNameLoad(stuName);
		if (generalInformationList == null) {
		} else {
			generalData.removeAll(generalData);
			for (GeneralInformation generalInformation : generalInformationList) {
				generalData.add(generalInformation);
			}
		}
		studentInputReset();             // ---------------------- 학생등록 입력란 모두 리셋
	}
	

	// ------------------------------------------ 학번 삭제하기

	private void stuDeleteBtnAction(ActionEvent event) {

		String StudentID = studentIDText.getText(); // -------------- 텍스트필드 "학번"
													
		int resultNumber = enrolDAO.stuDeletelClassLoad(StudentID);

		
		// ------------------------------------------ 학생 테이블 리셋
		ArrayList<GeneralInformation> studentMemberList = enrolDAO.studentMemberLoad();
		if (studentMemberList == null) {
		} else {
			generalData.removeAll(generalData);
			for (GeneralInformation generalInformation : studentMemberList) {
				generalData.add(generalInformation);
			}
		}
	}
	

	// ------------------------------------------ 학번 조회

	private void studentListViewBtnAction(ActionEvent event) {

		ArrayList<GeneralInformation> studentMemberList = enrolDAO.studentMemberLoad();

		if (studentMemberList == null) {
		} else {
			generalData.removeAll(generalData);
			for (GeneralInformation generalInformation : studentMemberList) {
				generalData.add(generalInformation);
			}
		} 
		studentInputReset();             // ---------------------- 학생등록 입력란 모두 리셋
	}
	
	
	// ----------------------------------------- 학생검색
	
	private void studentSearchBtnAction(ActionEvent event) {
		
		String stuName = stuNameText.getText();                 
		ArrayList<GeneralInformation> generalInformationList = enrolDAO.studentNameLoad(stuName);

		if (generalInformationList == null) {
		} else {
			generalData.removeAll(generalData);
			for (GeneralInformation generalInformation : generalInformationList) {
				generalData.add(generalInformation);
			}
		}
	}
	

	// ------------------------------------------ 직원등록/직원 부여하기

	private void staffEnrolBtnAction(ActionEvent event) {

		String staffJoin = joinCompanyText.getText(); 				// --------------텍스트필드 "입사년도"
		String staffDepartment = staffDepartmentCombo.getValue();   // --------------콤보박스  "부서"
		String staffName = staffNameText.getText(); 				// --------------텍스트필드 "이름"
		String staffDateOfBirth = staffDateOfBirthText.getText();   // --------------텍스트필드 "생년월일"
		String staffID = staffIDText.getText(); 					// --------------텍스트필드 "직원"
		String staffphone = staffPhoneText.getText(); 				// --------------텍스트필드 "연락처"

		int resultNumber = enrolDAO.staffEnrolClassLoad(staffJoin, staffDepartment, staffName, staffDateOfBirth,
				staffID, staffphone);

		
		// -------------- 새로 등록한 직원 정보를 테이블에 띄워 확인하기
		ArrayList<AdminInformation> adminInformationList = enrolDAO.staffNameLoad(staffName);
		if (adminInformationList == null) {
		} else {
			adminData.removeAll(adminData);
			for (AdminInformation adminInformation : adminInformationList) {
				adminData.add(adminInformation);
			}
		}
		adminInputReset();          // ---------------------- 직원등록 입력란 모두 리셋
	}
	

	// ------------------------------------------ 직원 삭제하기

	private void staffDeleteBtnAction(ActionEvent event) {
		String staffID = staffIDText.getText(); // --------------텍스트필드 "직원"

		int resultNumber = enrolDAO.staffDeletelClassLoad(staffID);
		
		// ------------------------------------------ 직원 테이블 리셋
		ArrayList<AdminInformation> staffMemberList = enrolDAO.staffMemberLoad();
		if (staffMemberList == null) {
		} else {
			adminData.removeAll(adminData);
			for (AdminInformation adminInformation : staffMemberList) {
				adminData.add(adminInformation);
			}
		}
	}
	

	// ------------------------------------------ 직원 조회

	private void staffListViewBtnAction(ActionEvent event) {
		ArrayList<AdminInformation> staffMemberList = enrolDAO.staffMemberLoad();
		if (staffMemberList == null) {
		} else {
			adminData.removeAll(adminData);
			for (AdminInformation adminInformation : staffMemberList) {
				adminData.add(adminInformation);
			}
		}
		adminInputReset();          // ---------------------- 직원등록 입력란 모두 리셋
	}
	
	
	// ----------------------------------------- 직원이름 검색
	
	private void staffSearchBtnAction(ActionEvent event) {
		
		String staffName = staffNameText.getText();                 
		ArrayList<AdminInformation> adminInformationList = enrolDAO.staffNameLoad(staffName);

		if (adminInformationList == null) {
		} else {
			adminData.removeAll(adminData);
			for (AdminInformation adminInformation : adminInformationList) {
				adminData.add(adminInformation);
			}
		}
	}
	

	// ----------------------------------------------- 학번 테이블 마우스클릭시 정보 get
	private void handleStudentListMouseClicked(MouseEvent event) {

		if (event.getClickCount() != 1)
			return;
		GeneralInformation generalInformation;

		try {
			generalInformation = studentListView.getSelectionModel().getSelectedItem();
			classSelectIndex = studentListView.getSelectionModel().getSelectedIndex();

			admissionText.setText(generalInformation.getStu_admission());
			collegeCombo.setValue(generalInformation.getDept_college());
			stuDepartmentCombo.setValue(generalInformation.getDept_name());
			stuNameText.setText(generalInformation.getStu_name());
			stuDateOfBirthText.setText(generalInformation.getStu_birth());
			studentIDText.setText(generalInformation.getStu_no());
		} catch (NullPointerException e) {
			generalInformation = null;
			classSelectIndex = 0;
		}
	}
	

	// ----------------------------------------------- 직원 테이블 마우스클릭시 정보 get
	private void handleStaffListMouseClicked(MouseEvent event) {

		if (event.getClickCount() != 1)
			return;
		AdminInformation adminInformation;
		try {
			adminInformation = staffListView.getSelectionModel().getSelectedItem();
			classSelectIndex2 = staffListView.getSelectionModel().getSelectedIndex();

			joinCompanyText.setText(adminInformation.getAdmin_joincompany());
			staffDepartmentCombo.setValue(adminInformation.getAdmin_department());
			staffNameText.setText(adminInformation.getAdmin_name());
			staffDateOfBirthText.setText(adminInformation.getAdmin_Birth());
			staffIDText.setText(adminInformation.getAdmin_no());
			staffPhoneText.setText(adminInformation.getAdmin_contact());

		} catch (NullPointerException e) {
			adminInformation = null;
			classSelectIndex2 = 0;
		}
	}
	
	
	
	public void studentInputReset() {
		// ----------------------------------------------- 학생 "입력란" 초기화
		
		admissionText.setText("");
		collegeCombo.setValue("");
		stuDepartmentCombo.setValue("");
		stuNameText.setText("");
		stuDateOfBirthText.setText("");
		studentIDText.setText("");
	}
	
	
	public void adminInputReset() {
		// ----------------------------------------------- 직원 "입력란" 초기화
		
		joinCompanyText.setText("");
		staffDepartmentCombo.setValue("");
		staffNameText.setText("");
		stuNameText.setText("");
		staffDateOfBirthText.setText("");
		staffIDText.setText("");
		staffPhoneText.setText("");
	}
	
	
	public void textForm() {
		// ----------------------------------------------- 학생 "입학년도" 문자유형, 길이
		
		admissionText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 5) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 학생 "이름" 문자유형, 길이
		
		stuNameText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣]*$";
			//-------------------------------------------- 영어, 한글만 받습니다.
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 16) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 학생 "생년월일" 문자유형, 길이
		
		stuDateOfBirthText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 7) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 학생 "학번" 문자유형, 길이
		
		studentIDText.setTextFormatter(new TextFormatter<>(e -> {
		if (e.getControlNewText().isEmpty()) {
			return e;
		}
		String pattern = "^[0-9]*$";
		//-------------------------------------------- 숫자만 받습니다
		boolean i = Pattern.matches(pattern, e.getControlNewText());
		if (!i || e.getControlNewText().length() == 11) {			
			//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
			return null;
		} else {
			return e;
		}
	}));
		
		// ----------------------------------------------- 직원 "이름" 문자유형, 길이
		
		staffNameText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣]*$";
			//-------------------------------------------- 영어, 한글만 받습니다.
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 16) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 직원 "생년월일" 문자유형, 길이
		
		staffDateOfBirthText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 7) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 직원 "연락처" 문자유형, 길이
		
		staffPhoneText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[ㅏ-ㅣㄱ-ㅎ가-힣0-9]*$";
			// -------------------------------------------- 숫자,한글만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 12) {
				// -------------------------------------- 조건문엔 받아올 텍스트의 판별
				// 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 직원 "사번" 문자유형, 길이
		
		staffIDText.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 11) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		}
}
