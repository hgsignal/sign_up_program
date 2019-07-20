package Controller;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class function {

	 public void alertDisplay(int number, String title, String header, String content) {
	      Alert alert = null;               
	      switch(number) {
	      case 1: alert = new Alert(AlertType.ERROR); break;
	      case 2: alert = new Alert(AlertType.WARNING); break;       
	      default : break;
	      }
	      alert.setTitle(title);
	      alert.setHeaderText(header);
	      alert.setContentText(content);
	      alert.setResizable(false);
	      alert.showAndWait();
	      return;
	      
	   }
}
