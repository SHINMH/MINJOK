package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

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
import model.Modelling;
import model.ReviewModel;
//ProdDetailView(상품 상세화면)를 컨트롤하는 클래스
public class ProdDetailViewController implements Initializable{
	//뷰 컨트롤러에서는 뷰에 담긴 컴포넌트들을 가지고 있어야됨
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
	
	private ObservableList<ReviewModel> data=null;//테이블뷰에 보여줄 리스트를 설정하기위한 ObservableList<ReviwModel>형 변수
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//상품 상세정보를 앱매니저를 통해 참조한다.
		String path = AppManager.getInstance().getProduct().getProdImage();
		Image itemImage = new Image(path);
		imageView_prod.setImage(itemImage);
		
		label_name.setText("제품명 : "+AppManager.getInstance().getProduct().getProdName());
		label_price.setText("가격 : "+AppManager.getInstance().getProduct().getProdPrice());
		label_company.setText("판매처 : "+AppManager.getInstance().getProduct().getProdCompany());
		
		//javaFx의 TableView설정을위한 데이터 연결
		data=FXCollections.observableArrayList();
		user.setCellValueFactory(cellData->cellData.getValue().getName());
		title.setCellValueFactory(cellData->cellData.getValue().getTitle());
		content.setCellValueFactory(cellData->cellData.getValue().getContent());

		getReviewList();
	}
	
	//상품에 달린 리뷰리스트를 불러오는 메소드
	public void getReviewList() {
		//서버와의 통신을 위한 네트워크 컨트롤러 객체 생성
		NetworkController networkController = new NetworkController();
		//서보와의 통신을 위한 json오브젝트 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("prodNumber", AppManager.getInstance().getProduct().getProdNumber());
		
		//네트워크 컨트롤러 객체를 통해 요청을 전송하고 응답을 담음
		String result = networkController.sendREST("http://15011066.iptime.org:8080/review/prod/", jsonObject);
		
		Modelling modelling = new Modelling();
		data.clear(); //테이블뷰의 내용을 비우고
		data.addAll(modelling.reviewModelling(result));//모델링을 통해 전달받은 리뷰를 넣는다.
		tableView_review.setItems(data);//테이블뷰에 data를 바인딩함
	}
	
	//리뷰작성창을 생성하는 메소드, 리뷰작성 버튼의 클릭이벤트를 처리하는 메소드
	public void writeReview(ActionEvent event) throws Exception {
		Image image = new Image("/image/AppIcon.png"); //app icon이미지
		
		AppManager.getInstance().setCheckPoint(2); //체크포인트를 설정하여 리뷰작성임을 알림 2=>리뷰작성, 1=>리뷰편집
		
		//새로 보여줄 리뷰등록화면을 위한 스테이지를 생성함
		Stage postDialog = new Stage(StageStyle.DECORATED);
		postDialog.initModality(Modality.WINDOW_MODAL); //modal형식으로 만듬
		postDialog.setTitle("리뷰 작성");//title 설정
		postDialog.getIcons().add(image); //app icon 설정
		postDialog.initOwner(btn_writeReview.getScene().getWindow()); //생성될 스테이지의 소유자를 현재 스테이지로 설정한다.

		Parent parent = FXMLLoader.load(getClass().getResource("/view/ReviewPostView.fxml")); //생성할 뷰의 fxml파일을 load한다.

		//씬을 생성한다.
		Scene scene = new Scene(parent);
		postDialog.setScene(scene); //스테이지위에 위에서 생성한 씬을 생성한다.
		postDialog.setResizable(false);
		postDialog.show();
		
		//새로 보여진 씬이 닫혔을경우를 감지하는 이벤트 핸들러를 등록한다.
		postDialog.setOnHiding(new EventHandler<WindowEvent>() {
	         @Override
	         public void handle(WindowEvent event) {
	            getReviewList();
	         }
	     });
		
	}
}
