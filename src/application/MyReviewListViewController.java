package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Modelling;
import model.ReviewModel;

public class MyReviewListViewController implements Initializable {
	//내가 작성한 리뷰를 보여주는 MyReviewListView에 맵핑한 controller클래스
	
	//내가 작성한 리뷰 ArrayList
	ArrayList<ReviewModel> reviewList;
	
	//view component
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
	//table List에 바인딩하기 위해 사용한 변수
	private ObservableList<ReviewModel> data=null;
	
	//Initializable interface를 상속받아 overwriting한 method
	//뷰 생성시 호출된다.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reviewList=new ArrayList<ReviewModel>();//reviewList 생성
		//label 설정
		label_userInfo.setText(AppManager.getInstance().getUser().getUserID()+" 님이 작성하신 리뷰 입니다.");
		data=FXCollections.observableArrayList();//data 생성
		//tableview column 설정
		title.setCellValueFactory(cellData->cellData.getValue().getTitle());
		content.setCellValueFactory(cellData->cellData.getValue().getContent());
		//테이블 뷰 클릭시 발생할 method 설정
		tableView_myReviewList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.getClickCount() == 2) {
		            showReviewEditView();
		        }
		    }
		});
		//서버로 부터 통신하여 리뷰를 받아오는 method 실행
		getMyReviewList();
	}
	
	//tableview의 entry 클릭시 실행되는 method로 리뷰 수정 dialog을 화면에 보여줌
	public void showReviewEditView() {
		Image image = new Image("/image/AppIcon.png");//이미지 아이콘
		if(tableView_myReviewList.getSelectionModel().getSelectedItem()==null) {
			System.out.println("빈 공간");
			return;
		}
		AppManager.getInstance().setCheckPoint(AppManager.REVIEWEDIT);
        System.out.println("user : "+tableView_myReviewList.getSelectionModel().getSelectedItem().getReviewUser());
        AppManager.getInstance().setReview(tableView_myReviewList.getSelectionModel().getSelectedItem());
        System.out.println("title : " +AppManager.getInstance().getReview().getReviewTitle());
		//dialog 생성
        Stage editDialog = new Stage(StageStyle.DECORATED);
		editDialog.getIcons().add(image);
        editDialog.initModality(Modality.WINDOW_MODAL);
        editDialog.setTitle("리뷰 수정 / 삭제");
        editDialog.initOwner(tableView_myReviewList.getScene().getWindow());
        
		Parent parent=null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/ReviewPostView.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(parent);
		//다이얼로그 설정
		editDialog.setScene(scene);
		editDialog.setResizable(false);
		editDialog.show();
		//다이얼로그가 종료되었을때 리뷰가 수정됬을수 있기때문에 getMyReviewList함수를 호출하여 tableview를 갱신해줌
		editDialog.setOnHiding(new EventHandler<WindowEvent>() {
	         @Override
	         public void handle(WindowEvent event) {
	            getMyReviewList();
	         }
	     });
	}
	
	//서버와의 통신을 통해서 내가 작성한 리뷰를 서버로 부터 받아 tableview에 바인딩함
	public void getMyReviewList() {
		tableView_myReviewList.getItems().clear();//테이블뷰에 데이터를 바인딩하기 전에 클리어해줌.
		NetworkController networkController = new NetworkController();
		
		//서버 통신을 위해 jsonobject를 생성하고, id 값을 담음
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("id", AppManager.getInstance().getUser().getUserID());
		
		//networkController의 sendREST method를 호출하고, url과 jsonobejct를 넘겨 서버와의 통신을 진행함. 
		//반환값으로 string으로 받아서 modelling class의 reviewModelling mothed를 호출하여 모델링을 하여 tableview에 넣음
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/my", jsonObject1);
		Modelling modelling = new Modelling();
		
		data.clear();
		data.addAll(modelling.reviewModelling(result));
		tableView_myReviewList.setItems(data);
	}
	
	//화면 상단의 버튼 클릭 시 productListview로 화면을 전환해주는 method
	public void changeToMyProdListView() {
		Stage primaryStage = (Stage) label_prodList.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/ProductListView.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
