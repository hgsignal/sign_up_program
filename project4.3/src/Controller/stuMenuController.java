package Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Model.ClassInformation;
import Model.GeneralInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class stuMenuController implements Initializable {
	
	@FXML private Button btnMemberInfor;
	@FXML private Button btnSignupClasses;
	@FXML private Button btnRecord;

	private function fun = new function(); // 확인창, 경고창 함수
	private Stage stuMenuStage; 
	private String nowLoginID; // 현재 로그인중인 유저의 정보 저장

	private generalLoginDAO gDAO = new generalLoginDAO();
	private signupClassDAO siDAO = new signupClassDAO();

	public void setStuMenuController(Stage stuMenuStage) {
		this.stuMenuStage = stuMenuStage;
	}

	public String getNowLoginID() {
		return nowLoginID;
	}

	public void setNowLoginID(String nowLoginID) {
		this.nowLoginID = nowLoginID;
	}
   
	
	
	//----------------------------------------------- 학생 로그인 창의 버튼 이벤트
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {

		
		btnMemberInfor.setOnAction(event -> memberInforBtnAction(event)); // 내 정보 버튼
		btnSignupClasses.setOnAction(event -> singUpClassesBtnAction(event)); // 수강신청 버튼
		btnRecord.setOnAction(event -> RecordBtnAction(event)); // 성적조회 버튼
		
	}
	
	
	
	// --------------------------------------------- 사용자가 정보를 수정하는 함수
	
	private void memberInforBtnAction(ActionEvent event) {
		
		Stage stage = new Stage();
		stage.initOwner(stuMenuStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("개인정보 수정");
		Parent root = null;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/memberInfor.fxml"));
		try {
			root = fxmlLoader.load();
		} catch (Exception e2) {
			System.out.println(e2);
		}

		Label memberName = (Label) root.lookup("#memberName");
		Label memberID = (Label) root.lookup("#memberID");
		Label memberBirth = (Label) root.lookup("#memberBirth");
		TextField editPhone = (TextField) root.lookup("#editPhone");
		TextField editEmail = (TextField) root.lookup("#editEmail");
		TextField editAddress = (TextField) root.lookup("#editAddress");
		PasswordField editPassword = (PasswordField) root.lookup("#editPassword");
		PasswordField editPassword2 = (PasswordField) root.lookup("#editPassword2");
		Button editMember = (Button) root.lookup("#editMember");
		Button editCencel = (Button) root.lookup("#editCencel");

		GeneralInformation generalInformation = gDAO.loadMemberEdit(getNowLoginID());

		memberName.setText(generalInformation.getStu_name());
		memberID.setText(generalInformation.getStu_no());
		memberBirth.setText(generalInformation.getStu_birth()); 
		editPhone.setText(generalInformation.getStu_contact());
		editEmail.setText(generalInformation.getStu_email());
		editAddress.setText(generalInformation.getStu_address());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);

		editPassword.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]*$";
			
			//-------------------------------------------- 영어,숫자,특수문자만 받습니다

			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 11) {
				
				//---------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));

		editPassword2.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]*$";
			// 영어 , 숫자, 특수문자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 11) {
				
				//---------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));

		editPhone.setTextFormatter(new TextFormatter<>(e -> {
			if (e.getControlNewText().isEmpty()) {
				return e;
			}
			String pattern = "^[ㅏ-ㅣㄱ-ㅎ가-힣0-9]*$"; // 한글, 숫자만 받습니다
			boolean i = Pattern.matches(pattern, e.getControlNewText());
			if (!i || e.getControlNewText().length() == 12) {
				
				//---------------------------------------- 조건문엔 받아올 텍스트의 판별 결과(Boolean)와 텍스트길이를 설정해줍니다.
				return null;
			} else {
				return e;
			}
		}));

		editMember.setOnAction((e2) -> {
			GeneralInformation editMemberInformation = new GeneralInformation(editPhone.getText(), editEmail.getText(),
					editAddress.getText(), editPassword.getText());
			if (editPassword.getText().equals(editPassword2.getText())) {
				int resultNumber = gDAO.editInformation(editMemberInformation, nowLoginID);
				fun.alertDisplay(2, "개인정보 수정", "수정 성공", "개인정보 수정을 완료했습니다.");
				
				stage.close(); //-------------------------------- fun.alertDisplay 창 누르면 "내 정보"창 닫기
			} else{
				fun.alertDisplay(1, "비밀번호 수정", "수정 실패", "비밀번호가 일치하지 않습니다. 확인해주세요.");
			}

		});

		editCencel.setOnAction((e2) -> {
			stage.close();
		});
	}
	
	
	
	//------------------------------------------- 수강신청 창 열기 버튼

	private void singUpClassesBtnAction(ActionEvent event) {	
	
		try {
			
			Stage dialog = new Stage(StageStyle.UNIFIED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("수강편람/신청/정정");
	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/signupClasses.fxml"));
			Parent parent = null;
		
			try {parent = fxmlLoader.load();} 
			catch (Exception e) {e.printStackTrace();}
	
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
			fun.alertDisplay(2, "수강신청하기", "수강신청 방법", "표 안의 과목을 더블클릭하면 수강신청/삭제가 가능합니다. 신청할 수 있는 학점은 12~21학점 입니다.");
			
			Label StuNo = (Label) parent.lookup("#StuNo");
			Label StuName = (Label) parent.lookup("#StuName");
			Label StuDept = (Label) parent.lookup("#StuDept");
			Label StuYear = (Label) parent.lookup("#StuYear");
			Label Score = (Label) parent.lookup("#Score");
			Label Count = (Label) parent.lookup("#Count");
				
			GeneralInformation generalInformation = gDAO.loadMemberInformation(getNowLoginID());
			GeneralInformation generalInformation2 = gDAO.loadSubCount(getNowLoginID());
			ClassInformation classInformation = siDAO.loadSignupScore(getNowLoginID());
								
			StuNo.setText(generalInformation.getStu_no());
			StuName.setText(generalInformation.getStu_name());
			StuDept.setText(generalInformation.getDept_name());
			StuYear.setText(generalInformation.getStu_year());
			Score.setText(classInformation.getClas_Score());
			Count.setText(generalInformation2.getStu_count());
			
							
			//------------------------------------- 로그인 정보 넘기기
			
			signupClassController scc = fxmlLoader.getController();
			scc.setSignupClassController(stuMenuStage);
			scc.setNowLoginID(getNowLoginID());


		} catch (Exception e) {System.out.println(e);}
	}
	
	
	
	
	//------------------------------------------- 수강성적 확인하기 창 열기 버튼

	private void RecordBtnAction(ActionEvent event) {
		
		try {
			
			Stage recordStage = new Stage();
			recordStage.initModality(Modality.WINDOW_MODAL);
			recordStage.initOwner(stuMenuStage);
			recordStage.setTitle("성적조회");

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/stuRecord.fxml"));		
			Parent parent = null;
	
			try {parent = fxmlLoader.load();} 
			catch (Exception e) {e.printStackTrace();}
			
			
			//------------------------------------- 로그인 정보 넘기기
			
			stuRecordController rc = fxmlLoader.getController();
			rc.setRecordController(stuMenuStage);
			rc.setNowLoginID(getNowLoginID());
				
			
			Scene scene = new Scene(parent);	
			recordStage.setScene(scene);
			recordStage.setResizable(false);
			recordStage.show();		
			
			fun.alertDisplay(2, "수강성적 확인하기", "수강성적 검색 방법", "수강년도를 입력해 수강했던 년도의 강의를 검색할 수 있습니다.");

			TextField studentName = (TextField) parent.lookup("#studentName");
			TextField studentCollege = (TextField) parent.lookup("#studentCollege");
			TextField studentDepartment = (TextField) parent.lookup("#studentDepartment");	
			TextField studentState = (TextField) parent.lookup("#studentState");
			TextField studentYear = (TextField) parent.lookup("#studentYear");
			
			GeneralInformation generalInformation = gDAO.loadMemberInformation(getNowLoginID());

			studentName.setText(generalInformation.getStu_name());			
			studentCollege.setText(generalInformation.getDept_college());	
			studentDepartment.setText(generalInformation.getDept_name());
			studentState.setText(generalInformation.getStu_state());
			studentYear.setText(generalInformation.getStu_year());
			
		} catch (Exception e) {System.out.println("1"+e);}
	}
}
