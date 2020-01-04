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
	//로그인 view의 작업을 처리할 controller 클래스
	
	//view component mapping
	@FXML
	private TextField tf_id;
	@FXML
	private PasswordField tf_password;
	@FXML
	private Button btn_login;
	@FXML
	private Button btn_register;

	//login버튼 클릭시 실행되는 method
	//아이디, 비밀번호를 서버와 통신하여, 코드값 200이 반환되면 로그인 성공, 다른 값이 넘어오면 로그인 실패
	public void login(ActionEvent event) throws Exception {
		String id;
		String pw;
		
		//textfield 값을 가져와 id와 pw값에 저장함
		id = tf_id.getText();
		pw = tf_password.getText();
		
		//json object를 만들고, jsonobject에 id와 pw를 넣음.
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("pw", pw);
		
		//networkcontroller를 이용하여 서버와 통신.
		NetworkController networkController = new NetworkController();
		//sendREST함수를 호출하고, url과 jsonobject를 넘겨줘 restful api를 호출하여 서버와 통신함
		String result = networkController.sendREST("http://15011066.iptime.org:8080/user/login", jsonObject);
		System.out.println(result);
		
		//jsonparser를 이용하여, 반환된 값에서 code값을 뽑아냄.
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		//코드값을 비교하여, 값이 200일 경우 로그인이 성공하여, productListView로 화면이 전환됨.
		if (code.equals("200")) {
			AppManager.getInstance().setUser(id, pw);
			
			Stage primaryStage = (Stage)btn_login.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/view/ProductListView.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}else {//코드값이 200이 아닐 경우, 로그인이 실패했다는 메세지를 dialog로 화면에 보여줌.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("아이디와 비밀번호를 확인해주시기 바랍니다!");
			alert.showAndWait();
		}
	}

	//회원가입 버튼 클릭시 실행되는 method, 화면이 회원가입 화면으로 전환된다.
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
