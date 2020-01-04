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
	private Label label_main;
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
	@FXML
	private Button btn_delete;
	@FXML
	private Button btn_revise;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if(AppManager.getInstance().getCheckPoint()== AppManager.REVIEWWRITE) {
			label_main.setText("< 리뷰 작성 >");
			label_name.setText(""+AppManager.getInstance().getProduct().getProdName());
			btn_delete.setVisible(false);
			btn_revise.setVisible(false);
		}
		else if(AppManager.getInstance().getCheckPoint()== AppManager.REVIEWEDIT) {
			String productName = AppManager.getInstance().getProductList().get(AppManager.getInstance().getReview().getProdNumber()-1).getProdName();
			label_name.setText(productName);
			label_main.setText("< 리뷰 편집 >");
			btn_submit.setVisible(false);
			tf_title.setText(AppManager.getInstance().getReview().getReviewTitle());
			tf_content.setText(AppManager.getInstance().getReview().getReviewContent());
		}
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
	
	public void reviseReview() throws Exception{
		String id = AppManager.getInstance().getUser().getUserID();
		String title = tf_title.getText();
		String content = tf_content.getText();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reviewNumber", AppManager.getInstance().getReview().getReviewNumber());
		jsonObject.put("reviewTitle", title);
		jsonObject.put("reviewContent", content);
		
		NetworkController networkController = new NetworkController();
		
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/modify/", jsonObject);
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();
		stage.close();
	}
	
	public void deleteReview() throws Exception{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reviewNumber", AppManager.getInstance().getReview().getReviewNumber());
		
		NetworkController networkController = new NetworkController();
		
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/delete/", jsonObject);
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();
		stage.close();
	}
}
