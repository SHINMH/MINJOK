package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
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
	@FXML
	private TableColumn<ReviewModel,String> user;
	@FXML
	private TableColumn<ReviewModel,String> title;
	@FXML
	private TableColumn<ReviewModel,String> content;
	
	private ObservableList<ReviewModel> data=null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String path = AppManager.getInstance().getProduct().getProdImage();
		Image itemImage = new Image(path);
		imageView_prod.setImage(itemImage);
		
		label_name.setText("제품명 : "+AppManager.getInstance().getProduct().getProdName());
		label_price.setText("가격 : "+AppManager.getInstance().getProduct().getProdPrice());
		label_company.setText("판매처 : "+AppManager.getInstance().getProduct().getProdCompany());
		
		data=FXCollections.observableArrayList();
		user.setCellValueFactory(cellData->cellData.getValue().getName());
		title.setCellValueFactory(cellData->cellData.getValue().getTitle());
		content.setCellValueFactory(cellData->cellData.getValue().getContent());

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
		
		data.clear();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject tempObject = (JSONObject) jsonArray.get(i);
			data.add(new ReviewModel(tempObject.get("reviewUser").toString(),tempObject.get("reviewTitle").toString(),tempObject.get("reviewContent").toString(),
					Integer.parseInt(tempObject.get("reviewNumber").toString()), Integer.parseInt(tempObject.get("prodNumber").toString())));
			
		}
		
		tableView_review.setItems(data);
	}
	
	public void writeReview(ActionEvent event) throws Exception {
		AppManager.getInstance().setScene(btn_writeReview.getScene());
		AppManager.getInstance().setStage((Stage) btn_writeReview.getScene().getWindow());
		
		Stage postDialog = new Stage(StageStyle.DECORATED);
		
		postDialog.initModality(Modality.WINDOW_MODAL);
		postDialog.setTitle("리뷰 작성");
		postDialog.initOwner(btn_writeReview.getScene().getWindow());

		Parent parent = FXMLLoader.load(getClass().getResource("../view/ReviewPostView.fxml"));

		Scene scene = new Scene(parent);

		postDialog.setScene(scene);

		postDialog.setResizable(false);

		postDialog.show();
		
		postDialog.setOnHiding(new EventHandler<WindowEvent>() {
	         @Override
	         public void handle(WindowEvent event) {
	            getReviewList();
	         }
	     });
		
	}
}
