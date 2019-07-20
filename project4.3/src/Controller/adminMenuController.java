package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class adminMenuController implements Initializable {
	
	@FXML private Button btnMemberEnrol;
	@FXML private Button btnRegister;

	private function fun = new function();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {

		btnMemberEnrol.setOnAction(event -> memberEnrolBtnAction(event));
		btnRegister.setOnAction(event -> registerBtnAction(event));

	}

	
	public void memberEnrolBtnAction(ActionEvent event) {

		try {
			Stage dialog = new Stage(StageStyle.UNIFIED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("사용자 등록");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/memberEnrol.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
			fun.alertDisplay(2, "학번/사번 삭제방법", "학번/사번 삭제", "삭제할 사용자를 데이터를 클릭하거나, 삭제할 학번/사번만 입력하세요.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void registerBtnAction(ActionEvent event) {

		try {
			Stage dialog = new Stage(StageStyle.UNIFIED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("수강등록");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/updateClasses.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
			fun.alertDisplay(2, "강의조회", "강의검색", "교과목명으로 강의를 조회할 수 있습니다.");
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
