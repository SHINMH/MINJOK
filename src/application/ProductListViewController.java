package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ProductListViewController implements Initializable {
	@FXML
	private ListView<BorderPane> item_listview;
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
		getList();
		item_listview.edit(1);
	}

	public void nextlist(ActionEvent event) throws Exception {
		System.out.print("다음버튼 눌림");
		item_listview.getItems().clear();
	}

	public void prevlist(ActionEvent event) throws Exception {

	}

	public void getList() {
		BorderPane newItem = new BorderPane();
		String path = "http://bgf-cu.xcache.kinxcdn.com/product/8809692950042.jpg";
		Image itemImage = new Image(path);
		ImageView imageView = new ImageView(itemImage);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		newItem.setLeft(imageView);
		
		 HBox hbox = new HBox();
         hbox.setPadding(new Insets(10));
         hbox.setAlignment(Pos.CENTER_LEFT);
         Label label = new Label("테스트");
         label.setFont(new Font(15));
         hbox.getChildren().add(label);
         newItem.setCenter(hbox);
         newItem.addEventHandler(arg0, arg1);
         
         item_listview.getItems().add(newItem);

	}
}
