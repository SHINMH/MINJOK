package application;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {

	//view component mapping
	@FXML
	private TextField tf_id;
	@FXML
	private PasswordField tf_password;
	@FXML
	private Button btn_login;
	@FXML
	private Button btn_register;

	public void login(ActionEvent event) throws Exception {
		String id;
		String pw;
		
		id = tf_id.getText();
		pw = tf_password.getText();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("pw", pw);
		
		NetworkController networkController = new NetworkController();
		String result = networkController.sendREST("http://15011066.iptime.org:8080/user/login", jsonObject);
		System.out.println(result);
		
		JSONParser parser = new JSONParser();
		
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		if (code.equals("200")) {
			AppManager.getInstance().setUser(id, pw);
			
			Stage primaryStage = (Stage)btn_login.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/view/ProductListView.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("아이디와 비밀번호를 확인해주시기 바랍니다!");
			alert.showAndWait();
		}
	}

	public void register(ActionEvent event) throws Exception {
		System.out.print("로그인 버튼 눌림");

		Stage primaryStage = (Stage)btn_register.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/JoinView.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
