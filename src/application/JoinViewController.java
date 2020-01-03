package application;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
	private Button btn_register;
	@FXML
	private Button btn_cancel;

	public void register(ActionEvent event) throws Exception {
		String id = tf_id.getText();
		String pw = tf_password.getText();
		String rePw = tf_rePassword.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		
		if (pw.equals(rePw)) {
			NetworkController networkController = new NetworkController();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			jsonObject.put("pw", pw);
			
			String result = networkController.sendREST("http://15011066.iptime.org:8080/user/register", jsonObject);
			
			JSONParser parser = new JSONParser();
			JSONObject result1 = (JSONObject) parser.parse(result);
			String code = String.valueOf(result1.get("code"));
			
			if(code.equals("200")) {
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("회원가입 성공!");
				alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent arg0) {
						// TODO Auto-generated method stub
						Stage primaryStage = (Stage) btn_register.getScene().getWindow();
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
							Scene scene = new Scene(root);
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				alert.showAndWait();
			}else {
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("이미 아이디가 존재합니다.");
				alert.showAndWait();
			}
		}
		else {
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 다시 확인해주시기 바랍니다.");
			alert.showAndWait();
		}
	}

	public void cancel(ActionEvent event) throws Exception {
		Stage primaryStage = (Stage) btn_cancel.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
