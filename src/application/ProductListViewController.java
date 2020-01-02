package application;

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
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.ProductModel;

public class ProductListViewController implements Initializable {
	private int prodindex;
	private int maxnumber;
	private ArrayList<ProductModel> prodList;
	
	@FXML
	private GridPane item_listview;
	@FXML
	private TextField tf_id;
	@FXML
	private Button btn_search;
	@FXML
	private Button btn_next;
	@FXML
	private Button btn_prev;

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
		
		prodList= new ArrayList<ProductModel>();
	}
	
	public void initView() {
		item_listview.setHgap(10);
		item_listview.setVgap(10);	
	}

	public void nextlist(ActionEvent event) throws Exception {
		System.out.print("다음버튼 눌림");
		item_listview.getChildren().clear();

		showList();
	}

	public void prevlist(ActionEvent event) throws Exception {
		System.out.print("이전버튼 눌림");
		item_listview.getChildren().clear();
	
		showList();
	}
	
	public void clickEntry(ActionEvent event) throws Exception {
		
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
		
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			System.out.println(jsonObject.get("prodName"));
			ProductModel newModel = new ProductModel();
			newModel.setProdName(jsonObject.get("prodName").toString());
			newModel.setProdNumber(Integer.parseInt(jsonObject.get("prodNumber").toString()));
			newModel.setProdPrice(jsonObject.get("prodPrice").toString());
			newModel.setProdCompany(jsonObject.get("prodCompany").toString());
			newModel.setProdImage(jsonObject.get("prodImage").toString());
			prodList.add(newModel);
		}
	}
	
	public void showList() {
		for(int i=0;i<2;i++) {
			for(int j=0;j<3;j++) {
				//if(prodindex*6+i+j >= maxnumber)break;
				ProductModel model = prodList.get(prodindex);
				prodindex++;
				
				VBox newItem = new VBox();
				String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
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
						System.out.print(""+label_name.getText());
					}
				});
				item_listview.add(newItem, j,i);
			}
		}
	}
}
