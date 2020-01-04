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

//ReviewPost(리뷰등록화면)를 컨트롤하는 클래스
public class ReviewPostViewController implements Initializable{
	
	//뷰 컨트롤러에서는 뷰에 담긴 컴포넌트들을 가지고 있어야됨
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
		//체크포인트에 따라 리뷰 작성과 리뷰편집을 나누어서 생성함
		if(AppManager.getInstance().getCheckPoint()== AppManager.REVIEWWRITE) {
			label_main.setText("< 리뷰 작성 >");
			label_name.setText(""+AppManager.getInstance().getProduct().getProdName());
			btn_delete.setVisible(false); //삭제 버튼 비활성화
			btn_revise.setVisible(false); //편집 버튼 비활성화
		}//리뷰 작성일때
		else if(AppManager.getInstance().getCheckPoint()== AppManager.REVIEWEDIT) {
			String productName = AppManager.getInstance().getProductList().get(AppManager.getInstance().getReview().getProdNumber()-1).getProdName();
			label_name.setText(productName);
			label_main.setText("< 리뷰 편집 >");
			btn_submit.setVisible(false);//리뷰 등록버튼 비활성화
			tf_title.setText(AppManager.getInstance().getReview().getReviewTitle());
			tf_content.setText(AppManager.getInstance().getReview().getReviewContent());
		}//리뷰 편집일때
	}
	
	//리뷰 등록 메소드
	public void postReview() throws Exception{
		String id = AppManager.getInstance().getUser().getUserID();//앱매니저를 통해 현재 유저의 id를 참조함
		int prodNumber = AppManager.getInstance().getProduct().getProdNumber(); //앱매니저를 통해 현재 등록하고자 하는 상품의 고유번호 를 참조함
		String title = tf_title.getText(); //텍스트박스에 입력된 텍스트를 가져옴
		String content = tf_content.getText();
		
		//서버와의 통신을 위한 json 오브젝트 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id); 
		jsonObject.put("prodNumber", prodNumber);
		jsonObject.put("reviewTitle", title);
		jsonObject.put("reviewContent", content);  //body 설정
		
		//통신을위한 네트워크 컨트롤러 객체 생성
		NetworkController networkController = new NetworkController();
		
		//sendRest메소드를 통해 요청을 전송하고 결과를 result변수에 담음
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/upload/", jsonObject);
		
		//파서를 통해 서버로부터의 응답을 설정함
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		//현재 스테이지를 닫음
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow(); //현재 설정된 스테이지의 정보를 가져옴
		stage.close();//현재 스테이지를 닫음
	}
	
	//취소버튼 클릭시 연결된 메소드
	public void cancel() throws Exception{
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow(); //현재 스테이지 객체를 가져옴
		stage.close(); //현재 스테이지를 닫는다.
	}
	
	//리뷰 수정버튼과 연결된 메소드
	public void reviseReview() throws Exception{
		String id = AppManager.getInstance().getUser().getUserID(); //앱매니저를 통해 현재 유저의 id를 참조함
		String title = tf_title.getText();//텍스트박스에 입력된 텍스트를 가져옴
		String content = tf_content.getText();
		
		//서버와의 통신을 위한 json 오브젝트 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reviewNumber", AppManager.getInstance().getReview().getReviewNumber());
		jsonObject.put("reviewTitle", title);
		jsonObject.put("reviewContent", content);
		
		//통신을위한 네트워크 컨트롤러 객체 생성
		NetworkController networkController = new NetworkController();
		
		//sendRest메소드를 통해 요청을 전송하고 결과를 result변수에 담음
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/modify/", jsonObject);
		
		//파서를 통해 서버응답을 각각의 변수에 담음
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
	
		//현재 스테이지를 닫음
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();//현재 설정된 스테이지의 정보를 가져옴
		stage.close();//현재 스테이지를 닫는다.
	}
	
	//리뷰 삭제 버튼과 연결된 메소드
	public void deleteReview() throws Exception{
		//서버와의 통신을 위한 json 오브젝트 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reviewNumber", AppManager.getInstance().getReview().getReviewNumber()); //앱매니저를 통해 삭제하고자하는 리뷰의 번호를 참조함
		
		//통신을 위한 네트워크 컨트롤러 객체 생성
		NetworkController networkController = new NetworkController();
	
		//sendRest메소드를 통해 요청을 전송하고 결과를 result변수에 담음
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/delete/", jsonObject);

		//파서를 통해 서버응답을 각각의 변수에 담음
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		Stage stage;
		stage = (Stage)btn_cancel.getScene().getWindow();//현재 설정된 스테이지의 정보를 가져옴
		stage.close();//현재 스테이지를 닫는다.
	}
}
