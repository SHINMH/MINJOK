package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

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
import model.Modelling;
import model.ProductModel;

public class ProductListViewController implements Initializable {
	private ArrayList<ProductModel> showProdList;
	private int prodindex;
	private int maxnumber;

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
	private VBox vbox_myReviewList;

	//initializable interface를 상속받아 overwriting한 method로 뷰 생성시 실행됩니다.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initView();
		initData();
		getList();
		showList();
	}

	//데이터 설정
	public void initData() {
		prodindex = 0;
		showProdList = new ArrayList<ProductModel>();
	}

	//뷰 설정
	public void initView() {
		item_listview.setHgap(20);
		item_listview.setVgap(10);
	}

	//다음 버튼 클리시 호출되는 method로 grid pane entry 변경
	public void nextlist(ActionEvent event) throws Exception {
		System.out.println("다음버튼 눌림");
		if (prodindex >= Math.ceil((double) maxnumber / 6.0) - 1)
			return;
		item_listview.getChildren().clear();
		prodindex++;

		showList();
	}

	//이전 버튼 클리시 호출되는 method로 grid pane entry 변경
	public void prevlist(ActionEvent event) throws Exception {
		System.out.println("이전버튼 눌림");
		if (prodindex <= 0)
			return;
		item_listview.getChildren().clear();
		prodindex--;

		showList();
	}

	//gridpane 안에 component 클릭시 다이얼로그 창을 화면에 보여주는 method.
	public void clickEntry(Event arg0) throws Exception {
		Image image = new Image("/image/AppIcon.png");
		Stage detailDialog = new Stage(StageStyle.DECORATED);
		detailDialog.getIcons().add(image);
		detailDialog.initModality(Modality.WINDOW_MODAL);
		detailDialog.setTitle("상품 상세보기");
		detailDialog.initOwner(btn_prev.getScene().getWindow());
		Parent parent = FXMLLoader.load(getClass().getResource("/view/ProdDetailView.fxml"));
		Scene scene = new Scene(parent);
		detailDialog.setScene(scene);
		detailDialog.setResizable(false);
		detailDialog.show();
	}

	//검색버튼 클릭시 textfield에 입력된 값으로  list에서 검색하여 리스트를 showprodlist를 변경하고 showlist를 호출하여 gridpane entry를 변경함
	public void searchEntry(ActionEvent event) throws Exception {
		ArrayList<ProductModel> prodList = AppManager.getInstance().getProductList();
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
		
		Modelling modelling = new Modelling();
		
		showProdList = modelling.prodModelling(result);
		
		maxnumber = showProdList.size();
	}

	//grid pane에 entry를 바인딩해주는 method 
	public void showList() {
		for (int i = 0; i < 6; i++) {
			if ((prodindex * 6) + i < 0 || (prodindex * 6) + i >= maxnumber)
				break;
			label_index.setText((prodindex + 1) + " / " + (int) Math.ceil((double) maxnumber / 6.0));

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
						AppManager.getInstance().setProduct(model);
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

	//화면 왼쪽 상단에 내 리뷰보기 버튼 클릭시, 화면을 전환해주는 method
	public void changeToMyReviewListView() {
		Stage primaryStage = (Stage) vbox_myReviewList.getScene().getWindow();
		Parent root=null;;
		String URL="/view/MyReviewListVIew.fxml";
		try {
			root = FXMLLoader.load(getClass().getResource(URL));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
