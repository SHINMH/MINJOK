package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ReviewModel;

public class ProdDetailViewController implements Initializable{
	@FXML
	private ImageView imageView_prod;
	@FXML
	private Label label_name;
	@FXML
	private Label label_price;
	@FXML
	private Label label_company;
	@FXML
	private Button btn_writeReview;
	@FXML
	private TableView<ReviewModel> tableView_review;
	
	private ObservableList<ReviewModel> data;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String path = AppManager.getInstance().getProduct().getProdImage();
		Image itemImage = new Image(path);
		imageView_prod.setImage(itemImage);
		
		label_name.setText("제품명 : "+AppManager.getInstance().getProduct().getProdName());
		label_price.setText("가격 : "+AppManager.getInstance().getProduct().getProdPrice());
		label_company.setText("판매처 : "+AppManager.getInstance().getProduct().getProdCompany());
		
		data = FXCollections.observableArrayList();
		
		getReviewList();
	}

	public void getReviewList() {
		NetworkController networkController = new NetworkController();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("prodNumber", AppManager.getInstance().getProduct().getProdNumber());

		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/prod/", jsonObject);
		
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject tempObject = (JSONObject) jsonArray.get(i);
			
			
			
			
			tableView_review.getItems().add(new ReviewModel(""+i,"",""));
		}
		
	}
}
