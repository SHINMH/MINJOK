package application;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinViewController {

	@FXML
	private TextField tf_id;
	@FXML
	private PasswordField tf_password;
	@FXML
	private PasswordField tf_rePassword;

	@FXML
	private Button btn_resister;
	@FXML
	private Button btn_cancel;

	public void resister(ActionEvent event) throws Exception {
		String pw = tf_password.getText();
		String rePw = tf_password.getText();

		if (pw.equals(rePw)) {
			NetworkController networkController = new NetworkController();
			HashMap<String,Object> resultMap = new HashMap<>();
			resultMap.put("", "");
			String json = 
			
			networkController.sendREST("http://15011066.iptime.org:8080/user/register", "");
			
			/*
			 * 통신 파트 들어가야 함
			 */
			
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("I have a great message for you!");
			alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
				
				@Override
				public void handle(DialogEvent arg0) {
					// TODO Auto-generated method stub
					Stage primaryStage = (Stage) btn_resister.getScene().getWindow();
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});

			alert.showAndWait();
		}
	}

	public void cancel(ActionEvent event) throws Exception {
		Stage primaryStage = (Stage) btn_cancel.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
