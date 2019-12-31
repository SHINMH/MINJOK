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

public class LoginViewController {

	@FXML
	private TextField tf_id;
	@FXML
	private PasswordField tf_password;

	@FXML
	private Button btn_login;
	@FXML
	private Button btn_signUp;

	public void login(ActionEvent event) throws Exception {
		if (tf_id.getText().equals("test") && tf_password.getText().equals("test")) {
			Stage primaryStage = (Stage)btn_login.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("../view/ProdDetailView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}

	public void signIn(ActionEvent event) throws Exception {
		System.out.print("로그인 버튼 눌림");

		Stage primaryStage = (Stage)btn_signUp.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../view/JoinView.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
