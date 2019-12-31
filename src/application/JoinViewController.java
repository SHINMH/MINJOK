package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
			/*
			 * 통신 파트 들어가야 함
			 */
			Stage primaryStage = (Stage) btn_resister.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
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
