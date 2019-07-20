package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.AdminInformation;
import Model.GeneralInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
	
	@FXML private ComboBox<String> userType;
	@FXML private TextField userId;
	@FXML private PasswordField userPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnCancle;
	@FXML private Button btnMemberIDCheck;

	private Stage primaryStage = null;
	private generalLoginDAO gDAO = new generalLoginDAO();
	private adminLoginDAO aDAO = new adminLoginDAO();
	private function fun = new function();
	
	  
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		btnLogin.setOnAction(event -> loginBtnAction(event));
		btnMemberIDCheck.setOnAction(event -> memberIDCheckBtnAction(event));
	}

	//--------------------------------------------- 로그인 시작 함수
	private void loginBtnAction(ActionEvent event) {
		int resultNumber = 0;
		int resultNumber1 = 0;
		
		GeneralInformation generalInformation1 = new GeneralInformation(userId.getText(), userPassword.getText());
		AdminInformation adminLogin = new AdminInformation(userId.getText(), userPassword.getText());

		resultNumber = gDAO.generalLogin(generalInformation1);
		resultNumber1 = aDAO.adminLogin(adminLogin);
		
		try {
			if (userType.getValue().equals("학생 (학번)")) {
				if (resultNumber == 1) {
					Stage stage = new Stage();
					stage.initOwner(primaryStage);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.setTitle("학생 로그인");
					Parent root = null;

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/stuMenu.fxml"));

					try {
						root = fxmlLoader.load();
					} catch (Exception e1) {
					}
					
					
					//--------------------------------- 로그인 정보 넘기기
					
					stuMenuController smc = fxmlLoader.getController();
					smc.setStuMenuController(primaryStage);
					smc.setNowLoginID(userId.getText());

					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					stage.setResizable(false);

					closeLogin();
					
					fun.alertDisplay(2, "로그인", "로그인 성공", "일반 사용자 입니다");
				} else {
					fun.alertDisplay(1, "로그인", "로그인 실패", "아이디 및 패스워드를 확인해주세요");
				}

			} else if (userType.getValue().equals("직원 (사번)")) {
				
				if (resultNumber1 == 1) {
					Stage stage = new Stage();
					stage.initOwner(primaryStage);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.setTitle("직원 로그인");
					Parent root = null;
					
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/adminMenu.fxml"));

					try {
						root = fxmlLoader.load();
					} catch (Exception e1) {

					}

					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					stage.setResizable(false);

					closeLogin();
					fun.alertDisplay(2, "로그인", "로그인 성공", "관리자 입니다");
				} else {
					fun.alertDisplay(1, "로그인", "로그인 실패", "아이디 및 패스워드를 확인해주세요");
				}
			} else {
				fun.alertDisplay(1, "사용자 구분", "로그인 실패", "사용자 구분을 확인해주세요");
			}
		} catch (NullPointerException e) {
			fun.alertDisplay(1, "사용자 구분", "로그인 실패", "사용자 구분을 확인해주세요");
		}

	}

	//--------------------------------------------- 화면창 닫기 (화면 전환시 필요)
	private void closeLogin() {
		primaryStage = (Stage) btnLogin.getScene().getWindow();
		primaryStage.close();
	}

	
	//--------------------------------------------- 개인번호조회
	private void memberIDCheckBtnAction(ActionEvent event) {
		
		try {
			Stage memberIDCheckView = new Stage(StageStyle.UNIFIED);
			memberIDCheckView.initModality(Modality.WINDOW_MODAL);
			memberIDCheckView.setTitle("개인번호 조회하기");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/memberIDCheckView.fxml"));
			Scene scene = new Scene(parent);
			memberIDCheckView.setScene(scene);
			memberIDCheckView.setResizable(false);
			memberIDCheckView.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
