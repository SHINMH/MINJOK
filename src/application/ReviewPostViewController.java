package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReviewPostViewController implements Initializable{

	@FXML
	private Label label_name;
	@FXML
	private TextField tf_title;
	@FXML
	private TextField tf_content;
	
	@FXML
	private Button btn_submit;
	@FXML
	private Button btn_cancel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		label_name.setText(""+AppManager.getInstance().getProduct().getProdName());
	}
	
	public void postReview() throws Exception{
		String id = AppManager.getInstance().getUser().getUserID();
		int prodNumber = AppManager.getInstance().getProduct().getProdNumber();
		String title = tf_title.getText();
		String content = tf_content.getText();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("prodNumber", prodNumber);
		jsonObject.put("reviewTitle", title);
		jsonObject.put("reviewContent", content);
		
		NetworkController networkController = new NetworkController();
		
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/upload/", jsonObject);
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();
		stage.close();
	}
	
	public void cancel() throws Exception{
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();
		stage.close();
	}
}
