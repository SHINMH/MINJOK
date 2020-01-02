package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.ProductModel;
import model.ReviewModel;

public class MyReviewListViewController implements Initializable {

	ArrayList<ReviewModel> reviewList;
	
	@FXML
	private Label label_userInfo;
	@FXML
	private TableView<ReviewModel> tableView_myReviewList;
	@FXML
	private Label label_prodList;
	@FXML
	private TableColumn<ReviewModel,String> title;
	@FXML
	private TableColumn<ReviewModel,String> content;
	
	private ObservableList<ReviewModel> data=null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reviewList=new ArrayList<ReviewModel>();
		
		label_userInfo.setText(AppManager.getInstance().getUser().getUserID()+" 님이 작성하신 리뷰 입니다.");
		data=FXCollections.observableArrayList();
		title.setCellValueFactory(cellData->cellData.getValue().getTitle());
		content.setCellValueFactory(cellData->cellData.getValue().getContent());

		getList();
	}
	
	public void getList() {
		NetworkController networkController = new NetworkController();

		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("id", AppManager.getInstance().getUser().getUserID());
		
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/my", jsonObject1);
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			System.out.println(jsonObject.get("reviewTitle"));

			//	public ReviewModel(String name, String title, String content,int reviewNumber, int prodNumber) 
			data.add(new ReviewModel(jsonObject.get("reviewUser").toString(),jsonObject.get("reviewTitle").toString(),jsonObject.get("reviewContent").toString(),
					Integer.parseInt(jsonObject.get("reviewNumber").toString()), Integer.parseInt(jsonObject.get("prodNumber").toString())));
			
		}
		tableView_myReviewList.setItems(data);

	}
	
	public void changeToMyProdListView() {
		Stage primaryStage = (Stage) label_prodList.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/ProductListView.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
