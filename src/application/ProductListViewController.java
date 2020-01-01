package application;

import java.net.URL;
import java.util.ResourceBundle;

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

public class ProductListViewController implements Initializable {
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
		item_listview.setHgap(10);
		item_listview.setVgap(10);
		
		getList();
	}

	public void nextlist(ActionEvent event) throws Exception {
		System.out.print("다음버튼 눌림");
		item_listview.getChildren().clear();
	}

	public void prevlist(ActionEvent event) throws Exception {

	}
	
	public void clickEntry(ActionEvent event) throws Exception {
		
	}

	public void getList() {
		for(int i=0;i<2;i++) {
			for(int j=0;j<3;j++) {
				VBox newItem = new VBox();
				String path = "http://bgf-cu.xcache.kinxcdn.com/product/8809692950042.jpg";
				String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
				newItem.setStyle(style);

				newItem.setPadding(new Insets(10));
				newItem.setAlignment(Pos.CENTER);

				Image itemImage = new Image(path);
				ImageView imageView = new ImageView(itemImage);
				imageView.setFitHeight(100);
				imageView.setFitWidth(100);
				newItem.getChildren().add(imageView);

				Label label_name = new Label("이름"+i+j);
				label_name.setFont(new Font(15));
				newItem.getChildren().add(label_name);

				Label label_company = new Label("판매처");
				label_company.setFont(new Font(15));
				newItem.getChildren().add(label_company);

				Label label_price = new Label("가격");
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
