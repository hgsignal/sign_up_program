package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.AdminInformation;
import Model.GeneralInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class memberIDCheckViewController implements Initializable{

	@FXML private TextField memberNameText;
	@FXML private TextField memberBirthText;
	
	@FXML private Button btnIDCheck;
	
	@FXML private TableColumn colStatus;
	@FXML private TableColumn colCollege;
	@FXML private TableColumn colDepartment;
	@FXML private TableColumn colMemberID;
	
	@FXML private TableView<GeneralInformation> memberListView;
	
	private GeneralInformation generalInformation;
	private memberIDCheckViewDAO mDAO = new memberIDCheckViewDAO();
	
	ObservableList<GeneralInformation> memberData = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		memberListViewColData();
		btnIDCheck.setOnAction(event -> IDCheckBtnAction(event));
	}
	
	
	
	private void IDCheckBtnAction(ActionEvent event) {
		
		String memberName = memberNameText.getText();
		String memberBirth = memberBirthText.getText();
	
		ArrayList<GeneralInformation> listMember = mDAO.memberInfromationLoad(memberName, memberBirth);
		
		if (listMember == null) {

		} else {
			memberData.removeAll(memberData);
			for (GeneralInformation generalInformation : listMember) {
				memberData.add(generalInformation);
			}
		}	
	}
	
	
	
	private void memberListViewColData() {
		memberListView.setEditable(true);
			
		colStatus.setCellValueFactory(new PropertyValueFactory<>("Stu_state"));
		colCollege.setCellValueFactory(new PropertyValueFactory<>("Dept_college"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("Dept_name"));
		colMemberID.setCellValueFactory(new PropertyValueFactory<>("Stu_no"));
	
		memberListView.setItems(memberData);
		
		//--------------------- 빈 테이블에 표시될 내용
		memberListView.setPlaceholder(new Label(""));
	}
}
