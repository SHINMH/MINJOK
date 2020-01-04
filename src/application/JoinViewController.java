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
	//회원가입을 진행하는 joinview에 앱핑되있는 컨트롤러
	
	//뷰의 component 
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

	//회원가입 번호 클릭시 서버와 통신하여 회원가입 결과를 반환하고 그 결과에 따라 화면에 보여줌
	public void register(ActionEvent event) throws Exception {
		String id = tf_id.getText();
		String pw = tf_password.getText();
		String rePw = tf_rePassword.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		
		//1차 비밀번호와 2차 비밀번호가 동일한지 검사함
		if (pw.equals(rePw)) {
			NetworkController networkController = new NetworkController();
			//서버와 통신하기 위해 jsonobject를 생성하고, id와 pw를 넣음.
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			jsonObject.put("pw", pw);
			
			//networkcontroller의 sendREST method를 이용하여 서버와 통신함, 이때 url 과 jsonobject를 넘겨줌
			String result = networkController.sendREST("http://15011066.iptime.org:8080/user/register", jsonObject);
			//반환값으로 string을 받음
			
			//서버와 통신후 받은 string 값을 jsonparser를 이용하여 jsonobject로 파싱함.
			//jsonobject에서 code값을 뽑아냄
			JSONParser parser = new JSONParser();
			JSONObject result1 = (JSONObject) parser.parse(result);
			String code = String.valueOf(result1.get("code"));
			
			//code값이 200일때 회원가입 성공, 200이 아닌 다른값이 오면 회원가입 실패
			if(code.equals("200")) {
				//회원가입에 대해 dialog로 알려주고, 화원을 로그인화면으로 전환함.
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
			}else {//회원가입 실패시 화면에 dialog를 보여주며, 회원가입이 실패했음을 알림.
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("이미 아이디가 존재합니다.");
				alert.showAndWait();
			}
		}//1차 2차 비밀번호가 다를시 화면에 다이얼로그를 보여줌
		else {
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 다시 확인해주시기 바랍니다.");
			alert.showAndWait();
		}
	}
	
	//취소버튼 클릭시 화면을 로그인 화면으로 전환함.
	public void cancel(ActionEvent event) throws Exception {
		Stage primaryStage = (Stage) btn_cancel.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
