package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Model.ClassInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class updateClassesController implements Initializable {

	@FXML private ComboBox<String> comboClassYear;
	@FXML private TextField txtClassName;
	@FXML private TextField txtProfName;
	@FXML private ComboBox<String> comboClassDiv;
	@FXML private TextField txtClassPeople;
	@FXML private TextField txtTime1;
	@FXML private TextField txtTime2;
	@FXML private TextField txtClassLocation;
	@FXML private ComboBox<String> comboClassScore;
	@FXML private TextField txtClassID;
	@FXML private Button btnClassEnrol;
	@FXML private Button btnClassDelete;
	@FXML private Button btnClassNameSearch;
	@FXML private Button btnClassSearch;

	@FXML private TableColumn colClassYear;
	@FXML private TableColumn colClassID;
	@FXML private TableColumn colClassName;
	@FXML private TableColumn colProfName;
	@FXML private TableColumn colClassPeople;
	@FXML private TableColumn colTime1;
	@FXML private TableColumn colTime2;
	@FXML private TableColumn colClassLocation;
	@FXML private TableColumn colClassScore;
	@FXML private TableColumn colClassDiv;

	@FXML private TableView<ClassInformation> classDataListView;

	private updateClassesDAO upDAO = new updateClassesDAO();
	private int classSelectIndex;

	ObservableList<String> comboItemClassYear = FXCollections.observableArrayList("1", "2", "3", "4");
	ObservableList<String> comboItemClassDiv = FXCollections.observableArrayList("교양필수", "전공필수");
	ObservableList<String> comboItemClassScore = FXCollections.observableArrayList("1", "2", "3");

	ObservableList<ClassInformation> updateData = FXCollections.observableArrayList();

	
	public updateClassesController() {
		
		// ------------------------------------------ 수강등록 테이블 첫 화면
		ArrayList<ClassInformation> classInformationList = upDAO.classIDLoad();
		if (classInformationList == null) {
		} else {
			updateData.removeAll(updateData);
			for (ClassInformation classInformation : classInformationList) {
				updateData.add(classInformation);
			}
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textForm();
		updateClassListViewColData();
		
		// ------------------------------------------ 수강등록/삭제/조회/검색
		btnClassEnrol.setOnAction(event -> classEnrolBtnAction(event));
		btnClassDelete.setOnAction(event -> classDeleteBtnAction(event));
		btnClassSearch.setOnAction(event -> classSearchBtnAction(event));
		btnClassNameSearch.setOnAction(event -> classNameSearchBtnAction(event));
		
		classDataListView.setOnMouseClicked(event ->handleUpdateListMouseClicked(event));
		
		comboClassYear.setItems(comboItemClassYear);
		comboClassDiv.setItems(comboItemClassDiv);
		comboClassScore.setItems(comboItemClassScore);
	}
	

	private void updateClassListViewColData() {
		classDataListView.setEditable(true);

		colClassYear.setCellValueFactory(new PropertyValueFactory<>("Clas_year"));
		colClassID.setCellValueFactory(new PropertyValueFactory<>("Clas_no"));
		colClassName.setCellValueFactory(new PropertyValueFactory<>("Clas_name"));
		colProfName.setCellValueFactory(new PropertyValueFactory<>("Clas_prof"));
		colClassPeople.setCellValueFactory(new PropertyValueFactory<>("Clas_people"));
		colTime1.setCellValueFactory(new PropertyValueFactory<>("Clas_time1"));
		colTime2.setCellValueFactory(new PropertyValueFactory<>("Clas_time2"));
		colClassLocation.setCellValueFactory(new PropertyValueFactory<>("Clas_location"));
		colClassScore.setCellValueFactory(new PropertyValueFactory<>("Clas_Score"));
		colClassDiv.setCellValueFactory(new PropertyValueFactory<>("Clas_div"));

		classDataListView.setItems(updateData);
		
		//--------------------- 빈 테이블에 표시될 내용
		classDataListView.setPlaceholder(new Label("표시할 내용이 없습니다. 다시 검색해주세요."));
	}
	

	// ------------------------------------------ 직원
	// ------------------------------------------ 수강등록
	

	private void classEnrolBtnAction(ActionEvent event) {

		String classYear = comboClassYear.getValue();
		String classID = txtClassID.getText();
		String className = txtClassName.getText();
		String profName = txtProfName.getText();
		String classDiv = comboClassDiv.getValue();
		String classPeople = txtClassPeople.getText();
		String classtime1 = txtTime1.getText();
		String classtime2 = txtTime2.getText();
		String classLocation = txtClassLocation.getText();
		String classScore = comboClassScore.getValue();

		int resultNumber = upDAO.classUpdateLoad(classYear, classID, className, profName, classDiv, classPeople,
				classtime1, classtime2, classLocation, classScore);

		classNameSearchBtnAction(event);    // -------------- 수강등록한 강의 테이블에 띄우기
		inputReset();                       // -------------- 수강등록 입력란 모두 리셋
	}
	

	// ------------------------------------------ 직원
	// ------------------------------------------ 수강 삭제

	private void classDeleteBtnAction(ActionEvent event) {

		String classID = txtClassID.getText(); // --------------텍스트필드 "학번" 값
											
		int resultNumber = upDAO.classDeleteLoad(classID);

		
		// ------------------------------------------ 수강 테이블 리셋
		ArrayList<ClassInformation> classInformationList = upDAO.classIDLoad();
		if (classInformationList == null) {
		} else {
			updateData.removeAll(updateData);
			for (ClassInformation classInformation : classInformationList) {
				updateData.add(classInformation);
			}
		}
	}
	

	// ------------------------------------------ 직원
	// ------------------------------------------ 수강 조회

	private void classSearchBtnAction(ActionEvent event) {

		ArrayList<ClassInformation> classInformationList = upDAO.classIDLoad();

		if (classInformationList == null) {
		} else {
			updateData.removeAll(updateData);
			for (ClassInformation classInformation : classInformationList) {
				updateData.add(classInformation);
			}
		}
		inputReset();                       // -------------- 수강등록 입력란 모두 리셋
	}
	
	
	// ------------------------------------------ 직원
	// ------------------------------------------ 교과목명 검색

	private void classNameSearchBtnAction(ActionEvent event) {
		
		String className = txtClassName.getText();

		ArrayList<ClassInformation> classInformationList = upDAO.classNameLoad(className);

		if (classInformationList == null) {
		} else {
			updateData.removeAll(updateData);
			for (ClassInformation classInformation : classInformationList) {
				updateData.add(classInformation);

			}
		}
	}
	
	
	// ------------------------------------------ 수강등록 테이블 마우스클릭시 정보 get
	
	private void handleUpdateListMouseClicked(MouseEvent event) {

		if (event.getClickCount() != 1)
			return;
		ClassInformation classInformation;
		try {
			classInformation = classDataListView.getSelectionModel().getSelectedItem();
			classSelectIndex = classDataListView.getSelectionModel().getSelectedIndex();

			comboClassYear.setValue(classInformation.getClas_year());
			txtClassName.setText(classInformation.getClas_name());
			txtProfName.setText(classInformation.getClas_prof());
			comboClassDiv.setValue(classInformation.getClas_div());
			txtClassPeople.setText(classInformation.getClas_people());
			txtTime1.setText(classInformation.getClas_time1());
			txtTime2.setText(classInformation.getClas_time2());
			txtClassLocation.setText(classInformation.getClas_location());
			comboClassScore.setValue(classInformation.getClas_Score());
			txtClassID.setText(classInformation.getClas_no());

		} catch (NullPointerException e) {
			classInformation = null;
			classSelectIndex = 0;
		}
	}
	
	
	
	public void inputReset() {
		// ----------------------------------------------- "입력란" 초기화
		
		comboClassYear.setValue("");
		txtClassName.setText("");
		txtProfName.setText("");
		comboClassDiv.setValue("");
		txtClassPeople.setText("");
		txtTime1.setText("");
		txtTime2.setText("");
		txtClassLocation.setText("");
		comboClassScore.setValue("");
		txtClassID.setText("");
	}
	
	
	public void textForm() {
		// ----------------------------------------------- "강의이름" 문자 길이

		txtClassName.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣0-9]*$";
			//-------------------------------------------- 영어, 한글, 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 16) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- 교수 "이름" 문자유형, 길이
		
		txtProfName.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣]*$";
			//-------------------------------------------- 영어, 한글만 받습니다.
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 8) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- "수강인원" 문자유형, 길이
		
		txtClassPeople.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 4) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- "시간1" 문자유형, 길이
		
		txtTime1.setTextFormatter(new TextFormatter<>(e -> {
		if (e.getControlNewText().isEmpty()) {
			return e;
		}
		String pattern = "^[ㅏ-ㅣㄱ-ㅎ가-힣0-9]*$";
		//-------------------------------------------- 한글, 숫자만 받습니다
		boolean i = Pattern.matches(pattern, e.getControlNewText());
		if (!i || e.getControlNewText().length() == 3) {			
			//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
			return null;
		} else {
			return e;
		}
	}));
		
	// ----------------------------------------------- "시간2" 문자유형, 길이
		
		txtTime2.setTextFormatter(new TextFormatter<>(e -> {
		if (e.getControlNewText().isEmpty()) {
			return e;
		}
		String pattern = "^[ㅏ-ㅣㄱ-ㅎ가-힣0-9]*$";
		//-------------------------------------------- 한글, 숫자만 받습니다
		boolean i = Pattern.matches(pattern, e.getControlNewText());
		if (!i || e.getControlNewText().length() == 3) {			
			//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
			return null;
		} else {
			return e;
		}
	}));
		
		// ----------------------------------------------- "강의실" 문자유형, 길이
		
		txtClassLocation.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣0-9]*$";
			//-------------------------------------------- 영어, 한글, 숫자만 받습니다.
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 8) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
		
		// ----------------------------------------------- "강의번호" 문자유형, 길이
		
		txtClassID.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[0-9]*$";
			//-------------------------------------------- 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 12) {			
				//-------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));
	}
}
