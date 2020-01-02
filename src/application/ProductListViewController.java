package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ProductModel;

public class ProductListViewController implements Initializable {
	private int prodindex;
	private int maxnumber;
	private ArrayList<ProductModel> prodList;
	private ArrayList<ProductModel> showProdList;

	@FXML
	private GridPane item_listview;
	@FXML
	private TextField tf_search;
	@FXML
	private Button btn_search;
	@FXML
	private Button btn_next;
	@FXML
	private Button btn_prev;
	@FXML
	private Label label_index;

	@FXML
	private VBox vbox_reviewList;
	@FXML
	private VBox vbox_reviewPost;
	@FXML
	private VBox vbox_myReivew;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initView();
		initData();
		getList();
		showList();
	}

	public void initData() {
		prodindex = 0;

		prodList = new ArrayList<ProductModel>();
		showProdList = new ArrayList<ProductModel>();
	}

	public void initView() {
		item_listview.setHgap(20);
		item_listview.setVgap(10);
	}

	public void nextlist(ActionEvent event) throws Exception {
		System.out.println("다음버튼 눌림");
		if (prodindex >= Math.ceil((double) maxnumber / 6.0) - 1)
			return;
		item_listview.getChildren().clear();
		prodindex++;
		showList();
	}

	public void prevlist(ActionEvent event) throws Exception {
		System.out.println("이전버튼 눌림");
		if (prodindex <= 0)
			return;

		item_listview.getChildren().clear();
		prodindex--;
		showList();
	}
	
	public void clickEntry(Event arg0) throws Exception {
			Stage joinDialog = new Stage(StageStyle.DECORATED);

	      joinDialog.initModality(Modality.WINDOW_MODAL);
	      joinDialog.setTitle("상품 상세보기");
	      joinDialog.initOwner(btn_prev.getScene().getWindow());

	      Parent parent = FXMLLoader.load(getClass().getResource("../view/ProdDetailView.fxml"));
	      
	      Scene scene = new Scene(parent);

	      joinDialog.setScene(scene);

	      joinDialog.setResizable(false);

	      joinDialog.show();

	}

	public void searchEntry(ActionEvent event) throws Exception {
		item_listview.getChildren().clear();
		showProdList.clear();
		maxnumber = 0;
		prodindex = 0;
		for (int i = 0; i < prodList.size(); i++) {
			ProductModel model = prodList.get(i);

			if (model.getProdName().contains(tf_search.getText())) {
				showProdList.add(model);
				System.out.println(model.getProdName());
				maxnumber++;
			}
		}
		showList();
	}

	public void getList() {
		NetworkController networkController = new NetworkController();

		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("id", "123");

		String result = networkController.sendREST("http://15011066.iptime.org:8080/prod/all", jsonObject1);
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxnumber = jsonArray.size();

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			System.out.println(jsonObject.get("prodName"));
			ProductModel newModel = new ProductModel();
			newModel.setProdName(jsonObject.get("prodName").toString());
			newModel.setProdNumber(Integer.parseInt(jsonObject.get("prodNumber").toString()));
			newModel.setProdPrice(jsonObject.get("prodPrice").toString());
			newModel.setProdCompany(jsonObject.get("prodCompany").toString());
			newModel.setProdImage(jsonObject.get("prodImage").toString());
			prodList.add(newModel);
			showProdList.add(newModel);
		}
	}

	public void showList() {
		for (int i = 0; i < 6; i++) {
			if ((prodindex * 6) + i < 0 || (prodindex * 6) + i >= maxnumber)
				break;
			ProductModel model = showProdList.get((prodindex * 6) + i);

			VBox newItem = new VBox();
			String style = "-fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 30;";
			newItem.setStyle(style);
			newItem.setPadding(new Insets(10));
			newItem.setAlignment(Pos.CENTER);

			String path = model.getProdImage();
			Image itemImage = new Image(path);
			ImageView imageView = new ImageView(itemImage);
			imageView.setFitHeight(100);
			imageView.setFitWidth(100);
			newItem.getChildren().add(imageView);

			Label label_name = new Label(model.getProdName());
			label_name.setFont(new Font(15));
			newItem.getChildren().add(label_name);

			Label label_company = new Label(model.getProdCompany());
			label_company.setFont(new Font(15));
			newItem.getChildren().add(label_company);

			Label label_price = new Label(model.getProdPrice());
			label_price.setFont(new Font(15));
			newItem.getChildren().add(label_price);
			newItem.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					System.out.print("" + label_name.getText());
					try {
						clickEntry(arg0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			int x = i % 3;
			int y = (i < 3) ? 0 : 1;
			item_listview.add(newItem, x, y);
		}
	}

	public void changeToReviewListView() {
		Stage primaryStage = (Stage)vbox_reviewList.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/ReviewListView.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeToReviewPostView() {
		Stage primaryStage = (Stage)vbox_reviewPost.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/ReviewPostView.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeToMyReviewView() {
		Stage primaryStage = (Stage)vbox_myReivew.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/ReviewListView.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
