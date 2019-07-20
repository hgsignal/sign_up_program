package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.GeneralInformation;
import Model.RecordInformation;
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
import javafx.stage.Stage;

public class stuRecordController implements Initializable{
	
	@FXML private TextField studentClassYearText;
	@FXML private Button btnRecordCheck;
	
	@FXML private TableColumn colClassYear;
	@FXML private TableColumn colFinishDiv;
	@FXML private TableColumn colYear;
	@FXML private TableColumn colClassName;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colScore;
	@FXML private TableColumn colProf;
	@FXML private TableColumn colMidterm;
	@FXML private TableColumn colFinals;
	
	@FXML private TableView<RecordInformation> recordListView;
	
	private stuRecordDAO rDAO = new stuRecordDAO();
	private function fun = new function();
	private Stage recordStage;
	private String nowLoginID;
	

	public void setRecordController(Stage recordStage) {
		this.recordStage = recordStage;
	}

	public String getNowLoginID() {
		return nowLoginID;
	}

	public void setNowLoginID(String nowLoginID) {
		this.nowLoginID = nowLoginID;
	}

	ObservableList<RecordInformation> recordData = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
	
		recordViewColData();
		
		btnRecordCheck.setOnAction(event -> recordCheckBtnAction(event));
	}
	
	
	private void recordViewColData() {
		recordListView.setEditable(true); 
		
		colClassYear.setCellValueFactory(new PropertyValueFactory<>("Record_openYear"));
		colFinishDiv.setCellValueFactory(new PropertyValueFactory<>("Record_div"));
		colYear.setCellValueFactory(new PropertyValueFactory<>("Record_year"));
		colClassName.setCellValueFactory(new PropertyValueFactory<>("Record_name"));
		colNumber.setCellValueFactory(new PropertyValueFactory<>("Record_no"));;
		colScore.setCellValueFactory(new PropertyValueFactory<>("Record_score"));
		colProf.setCellValueFactory(new PropertyValueFactory<>("Record_prof"));
		colMidterm.setCellValueFactory(new PropertyValueFactory<>("Record_midterm"));
		colFinals.setCellValueFactory(new PropertyValueFactory<>("Record_finals"));	

		recordListView.setItems(recordData);
		
		//--------------------- 빈 테이블에 표시될 내용
		recordListView.setPlaceholder(new Label(""));
		
	}
	
	private void recordCheckBtnAction(ActionEvent event) {
		
		String classYear = studentClassYearText.getText();
		
		ArrayList<RecordInformation> recordListView = rDAO.recordInformationLoad(nowLoginID, classYear+"%");
		if (recordListView == null) {} 
		else {
			recordData.removeAll(recordData);
			for (RecordInformation recordDataClassLoad : recordListView) {
				recordData.add(recordDataClassLoad);
			}
		}
	}
}
