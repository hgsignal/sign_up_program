package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.ClassInformation;
import Model.GeneralInformation;
import Model.IndispensableClassInformation;
import Model.PersonalClassInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class signupClassController implements Initializable {

	@FXML private Button btnclassCheck;
	@FXML private Button btnClassCheck2;
	@FXML private Button btnIndispensableClass;
	@FXML private Label Score;
	@FXML private Label Count;
	
	@FXML private TextField classNameText;
	@FXML private TextField profNameText;
	
	@FXML private TableColumn colYearAll;
	@FXML private TableColumn colNumberAll;
	@FXML private TableColumn colClassNameAll;
	@FXML private TableColumn colFinishDivAll;
	@FXML private TableColumn colProfAll;
	@FXML private TableColumn colPeopleAll;
	@FXML private TableColumn colTime1All;
	@FXML private TableColumn colTime2All;
	@FXML private TableColumn colLocationAll;
	@FXML private TableColumn colScoreAll;
	
	@FXML private TableColumn colYear;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colClassName;
	@FXML private TableColumn colFinishDiv;
	@FXML private TableColumn colProf;
	@FXML private TableColumn colPeople;
	@FXML private TableColumn colTime1;
	@FXML private TableColumn colTime2;
	@FXML private TableColumn colLocation;
	@FXML private TableColumn colScore;
	
	@FXML private TableColumn colNumberIndi;
	@FXML private TableColumn colClassNameIndi;
	@FXML private TableColumn colScoreIndi;
	@FXML private TableColumn colProfIndi;

	@FXML private TableView<ClassInformation> classListView;
	@FXML private TableView<PersonalClassInformation> personalList;
	@FXML private TableView<IndispensableClassInformation> indispensableClassList;
	
	@FXML private ComboBox<String> comboYearValue;
	@FXML private ComboBox<String> comboClassDivValue;
	ObservableList<String> comboItemYear = FXCollections.observableArrayList("전체","1","2","3","4");
	ObservableList<String> comboItemDiv = FXCollections.observableArrayList("전체","교양필수","전공필수");

	private signupClassDAO sDAO = new signupClassDAO();
	private generalLoginDAO gDAO = new generalLoginDAO();
	private function fun = new function();

	private Stage signupClassStage;
	private String nowLoginID;

	private ClassInformation classInformation;
	private PersonalClassInformation personalClassInformation;
	private IndispensableClassInformation indispensableClass;
	private int classSelectIndex, classSelectIndex2, classSelectIndex3;
	
	
	ObservableList<ClassInformation> classData = FXCollections.observableArrayList();
	ObservableList<PersonalClassInformation> classData2 = FXCollections.observableArrayList();
	ObservableList<IndispensableClassInformation> indispensableData = FXCollections.observableArrayList();
	
	public void setSignupClassController(Stage signupClassStage) {
		this.signupClassStage = signupClassStage;
	}

	public String getNowLoginID() {
		return nowLoginID;
	}

	public void setNowLoginID(String nowLoginID) {
		this.nowLoginID = nowLoginID;
	}
	
	public signupClassController() {
		
		// ------------------------------- 탭1) 수강편람 첫화면
		ArrayList<ClassInformation> listAll = sDAO.classAllLoad();
		if (listAll == null) {
		} else {
			classData.removeAll(classData);
			for (ClassInformation classInformation : listAll) {
				classData.add(classInformation);
			}
		}
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resource) {

		classListViewSetColData();
		classListCheckColData();
		indispensableClassListView();
		
		
		//------------------------------- 탭1)테이블, 탭2)테이블, 필수과목 테이블 버튼 이벤트
		btnclassCheck.setOnAction(event -> classCheckBtnAction(event));
		btnClassCheck2.setOnAction(event -> classCheckBtnAction2(event));
		btnIndispensableClass.setOnAction(event -> indispensableClassBtnAction(event));
		
		
		// ------------------------------ [수강편람], [필수과목] 셀 마우스 더블클릭 이벤트 -> "수강신청"
		classListView.setOnMouseClicked(event -> handleTableViewMouseClicked(event));
		indispensableClassList.setOnMouseClicked(event -> handleIndispensableListMouseClicked(event));
		
		
		// ------------------------------ [수강신청 목록] 셀 마우스 더블클릭 이벤트 -> "수강삭제"
		personalList.setOnMouseClicked(event ->handleDeleteListMouseClicked(event));
		
		
		comboYearValue.setItems(comboItemYear);
		comboClassDivValue.setItems(comboItemDiv);
	}
	
	

	// ------------------------------ 탭1) [수강편람] 테이블

	private void classListViewSetColData() {
		classListView.setEditable(true);
			
		colYearAll.setCellValueFactory(new PropertyValueFactory<>("Clas_year"));
		colNumberAll.setCellValueFactory(new PropertyValueFactory<>("Clas_no"));
		colClassNameAll.setCellValueFactory(new PropertyValueFactory<>("Clas_name"));
		colFinishDivAll.setCellValueFactory(new PropertyValueFactory<>("Clas_div"));
		colProfAll.setCellValueFactory(new PropertyValueFactory<>("Clas_prof"));;
		colPeopleAll.setCellValueFactory(new PropertyValueFactory<>("Clas_people"));
		colTime1All.setCellValueFactory(new PropertyValueFactory<>("Clas_time1"));
		colTime2All.setCellValueFactory(new PropertyValueFactory<>("Clas_time2"));
		colLocationAll.setCellValueFactory(new PropertyValueFactory<>("Clas_location"));
		colScoreAll.setCellValueFactory(new PropertyValueFactory<>("Clas_Score"));

		classListView.setItems(classData);
		
		//--------------------- 빈 테이블에 표시될 내용
		classListView.setPlaceholder(new Label("표시할 내용이 없습니다. 다시 검색해주세요."));
	}

	
	// ------------------------------ 탭2) [수강신청 목록] 테이블

	private void classListCheckColData() {
		personalList.setEditable(true);

		colYear.setCellValueFactory(new PropertyValueFactory<>("Clas_year"));
		colNumber.setCellValueFactory(new PropertyValueFactory<>("Clas_no"));
		colClassName.setCellValueFactory(new PropertyValueFactory<>("Clas_name"));
		colFinishDiv.setCellValueFactory(new PropertyValueFactory<>("Clas_div"));
		colProf.setCellValueFactory(new PropertyValueFactory<>("Clas_prof"));
		colPeople.setCellValueFactory(new PropertyValueFactory<>("Clas_people"));   
		colTime1.setCellValueFactory(new PropertyValueFactory<>("Clas_time1"));
		colTime2.setCellValueFactory(new PropertyValueFactory<>("Clas_time2"));
		colLocation.setCellValueFactory(new PropertyValueFactory<>("Clas_location"));
		colScore.setCellValueFactory(new PropertyValueFactory<>("Clas_score"));   		
		
		personalList.setItems(classData2);
		
		//--------------------- 빈 테이블에 표시될 내용
		personalList.setPlaceholder(new Label("한 학기 학점은 최소 12학점 ~ 최대 21학점 입니다."));
	}
	
	
	// ------------------------------필수과목 테이블

	private void indispensableClassListView() {
		indispensableClassList.setEditable(true);
		
		colProfIndi.setCellValueFactory(new PropertyValueFactory<>("Clas_prof")); 
		colNumberIndi.setCellValueFactory(new PropertyValueFactory<>("Clas_no")); 
		colClassNameIndi.setCellValueFactory(new PropertyValueFactory<>("Indi_class"));  
		colScoreIndi.setCellValueFactory(new PropertyValueFactory<>("Clas_score"));
		
		indispensableClassList.setItems(indispensableData);
		
		//--------------------- 빈 테이블에 표시될 내용
		indispensableClassList.setPlaceholder(new Label("학과별로 반드시 들어야하는 과목입니다."));
	}
	
	
	
	// ------------------------------ 탭1) 수강편람 조회하기 "버튼" 이벤트

	private void classCheckBtnAction(ActionEvent event) {
		
			String classYear = comboYearValue.getValue();     //--------------콤보박스 "학년" 값 받아오기
			String classDiv = comboClassDivValue.getValue();  //--------------콤보박스 "이수구분" 값 받아오기		
	        String className = classNameText.getText();       //--------------텍스트필드 "수강번호" 값 받아오기
			String profName = profNameText.getText();         //--------------텍스트필드 "교수" 값 받아오기
			
			if(classYear==null){
				classYear = "전체";
			}
			if(classDiv==null){
				classDiv = "전체";
			}

		ArrayList<ClassInformation> list = sDAO.classAllLoad(classYear,classDiv,className,profName);
		if (list == null) {

		} else {
			classData.removeAll(classData);
			for (ClassInformation classInformation : list) {
				classData.add(classInformation);
			}
		}
	}
	
	
	// ------------------------------ 탭2) 수강신청 목록 조회하기 "버튼" 이벤트
	
	private void classCheckBtnAction2(ActionEvent event) {
		ArrayList<PersonalClassInformation> list = sDAO.personalClassLoad(nowLoginID);
		if (list == null) {

		} else {
			classData2.removeAll(classData2);
			for (PersonalClassInformation personalClassInformation : list) {
				classData2.add(personalClassInformation);
			}
		}
	}
	
	
	//------------------------------ 필수과목 조회하기 "버튼" 이벤트

	private void indispensableClassBtnAction(ActionEvent event) {

		ArrayList<IndispensableClassInformation> list = sDAO.indispensableClassLoad(nowLoginID);
		if (list == null) {

		} else {
			indispensableData.removeAll(indispensableData);
			for (IndispensableClassInformation indispensableClass : list) {
				indispensableData.add(indispensableClass);
			}
		}
	}
	

	//------------------------------- [수강편람] 테이블의 더블클릭 "수강추가" 이벤트

	private void handleTableViewMouseClicked(MouseEvent event) {
		
		//------------------------------- 신청한 총 학점 구하기
		
		signupClassDAO siDAO = new signupClassDAO();
		ClassInformation classSumSCore = siDAO.loadSignupScore(getNowLoginID());	
		int sumScore = Integer.parseInt(classSumSCore.getClas_Score());
		
		if (event.getClickCount() != 2)
			return;	
		
		
		//------------------------------- 선택한 row 값 받아오기
		
		try {
		classInformation = classListView.getSelectionModel().getSelectedItem();
		classSelectIndex = classListView.getSelectionModel().getSelectedIndex();
		
		try {
			Stage dialog = new Stage();
			dialog.initOwner(signupClassStage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("수강신청하기");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/signupWindow.fxml"));

			TextField subName = (TextField) parent.lookup("#SubName");
			TextField subNo = (TextField) parent.lookup("#SubNo");
			
			subName.setText(classInformation.getClas_name());
			subNo.setText(classInformation.getClas_no());
			
			String strSubName = classInformation.getClas_name();
			String subScore = classInformation.getClas_Score();
			
			//-------------------------- 신청할 학점(subScore)의 속성을 String에서 int로 변환
			int subScoreint = Integer.parseInt(subScore);

				
			//-------------------------- 취소버튼
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			btnCancel.setOnAction(e -> dialog.close());

			
			//-------------------------- 수강신청버튼
			Button btnSignup = (Button) parent.lookup("#btnSignup");

			btnSignup.setOnAction((e2) -> {
				
				
				//-------------------------- 신청 학점이 최대 22학점 미만일 때만 수강신청 가능
				if(sumScore+subScoreint<22) {
					
					
				int resultNumber = sDAO.siunupClass(nowLoginID, subNo.getText(), strSubName, subScore);
								

				//-------------------- Label "신청학점", "신청과목수" 새로고침
				GeneralInformation generalInformation2 = gDAO.loadSubCount(getNowLoginID());
				ClassInformation classInformation = sDAO.loadSignupScore(getNowLoginID());
				Score.setText(classInformation.getClas_Score());
				Count.setText(generalInformation2.getStu_count());
				
				classCheckBtnAction2(e2);		//-------------------- 탭2)수강신청 목록 새로고침
				
				dialog.close();	
			}else {				
			fun.alertDisplay(1, "수강신청 실패", "수강신청할 수 없음", "한 학기에 신청할 수 있는 21 학점을 초과합니다.");
			dialog.close();
			}
				
			});

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();

		} catch (NullPointerException e) {
			classInformation = null;
			classSelectIndex = 0;
			}
		} catch (IOException e) {}	
	}

	
	// ------------------------------ [필수과목] 테이블의 더블클릭 "수강신청" 이벤트

	private void handleIndispensableListMouseClicked(MouseEvent event) {
		
			
		//------------------------------- 신청한 총 학점 구하기
		
		signupClassDAO siDAO = new signupClassDAO();
		ClassInformation classSumSCore = siDAO.loadSignupScore(getNowLoginID());	
		int sumScore = Integer.parseInt(classSumSCore.getClas_Score());
		
		
		if (event.getClickCount() != 2)
			return;

		
		//------------------------------- 선택한 row 값 받아오기

		try {			
		indispensableClass = indispensableClassList.getSelectionModel().getSelectedItem();
		classSelectIndex2 = indispensableClassList.getSelectionModel().getSelectedIndex();
		
		try {

			Stage dialog = new Stage();
			dialog.initOwner(signupClassStage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("수강신청하기");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/signupWindow.fxml"));

			TextField subName = (TextField) parent.lookup("#SubName");
			TextField subNo = (TextField) parent.lookup("#SubNo");

			subName.setText(indispensableClass.getIndi_class());
			subNo.setText(indispensableClass.getClas_no());
			String strSubName =  indispensableClass.getIndi_class();
			String subScore =  indispensableClass.getClas_score();

			//-------------------------- 신청할 학점(subScore)의 속성을 String에서 int로 변환
			int subScoreint = Integer.parseInt(subScore);

			
			// 수강취소버튼
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			btnCancel.setOnAction(e -> dialog.close());
			

			// 수강신청버튼
			Button btnSignup = (Button) parent.lookup("#btnSignup");

			btnSignup.setOnAction((e2) -> {
				
				
				//-------------------------- 신청 학점이 최대 22학점 미만일 때만 수강신청 가능
				if(sumScore+subScoreint<22) {
					
					
				int resultNumber = sDAO.siunupClass(nowLoginID, subNo.getText(), strSubName, subScore);
				
				
							
				//-------------------- Label "신청학점", "신청과목수" 새로고침
				GeneralInformation generalInformation2 = gDAO.loadSubCount(getNowLoginID());
				ClassInformation classInformation = sDAO.loadSignupScore(getNowLoginID());
				Score.setText(classInformation.getClas_Score());
				Count.setText(generalInformation2.getStu_count());
				
				
				classCheckBtnAction2(e2);		//--------------------탭2)수강신청 목록 새로고침
				
				dialog.close();
			}else {				
				fun.alertDisplay(1, "수강신청 실패", "수강신청할 수 없음", "한 학기에 신청할 수 있는 21 학점을 초과합니다.");
				dialog.close();
				}
				
				});

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();

		} catch (NullPointerException e) {}
		} catch (IOException e) {}	
	}

	
	// ------------------------------ [수강신청 목록] 테이블의 더블클릭 "수강신청 취소/삭제" 이벤트
	
	private void handleDeleteListMouseClicked(MouseEvent event) {
		
		
		//------------------------------- 신청한 총 학점 구하기
		
		signupClassDAO siDAO = new signupClassDAO();
		ClassInformation classSumSCore = siDAO.loadSignupScore(getNowLoginID());	
		int sumScore = Integer.parseInt(classSumSCore.getClas_Score());
		
		
		if (event.getClickCount() != 2)
			return;

		
		//------------------------------- 선택한 row 값 받아오기
		try {
		personalClassInformation = personalList.getSelectionModel().getSelectedItem();
		classSelectIndex3 = personalList.getSelectionModel().getSelectedIndex();
		
		
		try {

			Stage dialog = new Stage();
			dialog.initOwner(signupClassStage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("수강삭제하기");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/deleteWindow.fxml"));

			TextField SubName = (TextField) parent.lookup("#SubName");
			TextField SubNo = (TextField) parent.lookup("#SubNo");
			String subScore =  personalClassInformation.getClas_score();
			
			int subScoreint = Integer.parseInt(subScore);

			SubName.setText(personalClassInformation.getClas_name());
			SubNo.setText(personalClassInformation.getClas_no());

			// 수강포기 취소버튼
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			btnCancel.setOnAction(e -> dialog.close());

			
			// 수강삭제버튼
			Button btnSignupDelete = (Button) parent.lookup("#btnSignup");

			btnSignupDelete.setOnAction((e2) -> {
				
				
				//-------------------------- 신청 학점 총합이 최소 13학점 이상일 때만 수강삭제 가능 (최소학점:12)
				if(sumScore-subScoreint>=12) {
					
					
				int resultNumber = sDAO.deleteClass(nowLoginID, SubNo.getText());
				fun.alertDisplay(2, "수강신청 취소", "수강삭제", "수강포기를 완료했습니다.");
				
				
				
				//-------------------- Label "신청학점", "신청과목수" 새로고침
				GeneralInformation generalInformation2 = gDAO.loadSubCount(getNowLoginID());
				ClassInformation classInformation = sDAO.loadSignupScore(getNowLoginID());
				Score.setText(classInformation.getClas_Score());
				Count.setText(generalInformation2.getStu_count());
				
				
				classCheckBtnAction2(e2);		//--------------------탭2)수강신청 목록 새로고침
				
				dialog.close();
			}else {				
				fun.alertDisplay(1, "수강삭제 실패", "수강삭제할 수 없음", "한 학기 최소학점은 12 학점입니다.");
				dialog.close();
				}
			});

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (NullPointerException e) {}
		} catch (IOException e) {}	
	}
}
