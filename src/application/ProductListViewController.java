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

//ProductListView(상품리스트화면)을 컨트롤하는 컨트롤러 클래스
public class ProductListViewController implements Initializable {
	private ArrayList<ProductModel> showProdList; //그리드페인에 보여줄 상품들을 저장하는 어레이리스트 객체
	private int prodindex; //인덱스 설정을 위한 변수
	private int maxnumber; //총 상품의 갯수를 담을 변수

	//뷰 컨트롤러에서는 뷰에 담긴 컴포넌트들을 가지고 있어야됨
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
		prodindex = 0; //페이지 인덱스를 0으로 설정하고
		showProdList = new ArrayList<ProductModel>(); //상품리스트를 초기화함
	}

	//뷰 설정
	public void initView() {
		item_listview.setHgap(20); //여백설정
		item_listview.setVgap(10);
	}

	//다음 버튼 클리시 호출되는 method로 grid pane entry 변경
	public void nextlist(ActionEvent event) throws Exception {
		System.out.println("다음버튼 눌림");
		if (prodindex >= Math.ceil((double) maxnumber / 6.0) - 1) //최대 인덱스에 도달하면 return
			return;
		item_listview.getChildren().clear(); //최대 인덱스 전이면 리스뷰를 초기화 한다.
		prodindex++; //페이지 인덱스를 증가 시키고

		showList(); //해당 인덱스에 맞는 상품들을 출력하기 위한 메소드 호출
	}

	//이전 버튼 클리시 호출되는 method로 grid pane entry 변경
	public void prevlist(ActionEvent event) throws Exception {
		System.out.println("이전버튼 눌림");
		if (prodindex <= 0) //0번 인덱스에 도달하면 return
			return;
		item_listview.getChildren().clear(); //0번 인덱스 전이면 리스뷰를 초기화 한다.
		prodindex--;//페이지 인덱스를 감소 시키고

		showList();//해당 인덱스에 맞는 상품들을 출력하기 위한 메소드 호출
	}

	//gridpane 안에 component 클릭시 다이얼로그 창을 화면에 보여주는 method.
	public void clickEntry(Event arg0) throws Exception {
		Image image = new Image("/image/AppIcon.png"); //app icon 설정
		
		Stage detailDialog = new Stage(StageStyle.DECORATED); //다이얼로그를 위한 스테이지 생성
		detailDialog.getIcons().add(image);//app icon 설정
		detailDialog.initModality(Modality.WINDOW_MODAL); //modal형식으로 새 스테이지를 생한다.
		detailDialog.setTitle("상품 상세보기");
		detailDialog.initOwner(btn_prev.getScene().getWindow()); //해당 스테이지의 소유자를 설정함
		Parent parent = FXMLLoader.load(getClass().getResource("/view/ProdDetailView.fxml")); // 생성할 뷰가 그려진 fxml파일을 load함
		Scene scene = new Scene(parent); //씬을 생성하고
		detailDialog.setScene(scene);
		detailDialog.setResizable(false);
		detailDialog.show(); //씬을 보여준다.
	}

	//검색버튼 클릭시 textfield에 입력된 값으로  list에서 검색하여 리스트를 showprodlist를 변경하고 showlist를 호출하여 gridpane entry를 변경함
	public void searchEntry(ActionEvent event) throws Exception {
		ArrayList<ProductModel> prodList = AppManager.getInstance().getProductList(); //모든 제품 리스트를 앱매니저를 통해 참조함
		item_listview.getChildren().clear(); //현재 리스트뷰를 초기화하고
		showProdList.clear(); //보여줄 상품리스트를 초기한다.
		maxnumber = 0; // 최대 상품번호를 다시 0 으로 초기화하고
		prodindex = 0;// 페이지 인덱스도 다시 0으로 초기화 한다.
		for (int i = 0; i < prodList.size(); i++) { //모든 상품 리스트의 갯수만큼 반복하고
			ProductModel model = prodList.get(i); //하나씩 상품정보를 가져온다
			if (model.getProdName().contains(tf_search.getText())) { //검색어와 상품의 이름일 비교하해서 일치하는 부분이 있으면
				showProdList.add(model); //보여질 상품리스트에 담는다
				System.out.println(model.getProdName());
				maxnumber++;//상품 갯수를 증가 시키고
			}
		}
		showList(); //생성된 리스트를 통해 해당 상품리스트를 보여준다.
	}

	//서버와 통신을 통해 서버 DB에 저장된 상품정보를 가져오는 메소드
	public void getList() {
		//서버와의 통신을위해 네트워크 컨트롤러 객체를 생성한다.
		NetworkController networkController = new NetworkController();

		//통신을 위한 json 오브젝트 생성
		JSONObject jsonObject1 = new JSONObject();
		//http 통신에 사용될 body생성
		jsonObject1.put("id", "123");
		
		//서버와의 통신을 통해 서버로부터의 응답을 가져온다.
		String result = networkController.sendREST("http://15011066.iptime.org:8080/prod/all", jsonObject1);
		
		//모델의 모델링 객체를 통해 서버로부터의 응답을 모델링함
		Modelling modelling = new Modelling();
		showProdList = modelling.prodModelling(result);
		
		//상품의 최대갯수를 설정한다.
		maxnumber = showProdList.size();
	}

	//grid pane에 entry를 바인딩해주는 method 
	public void showList() {
		for (int i = 0; i < 6; i++) {
			if ((prodindex * 6) + i < 0 || (prodindex * 6) + i >= maxnumber)
				break;//해당 페이지에 보여줄 상품의 최소, 최대 번호를 넘어가면 메소드 return
			
			//인덱스 라벨을 갱신함
			label_index.setText((prodindex + 1) + " / " + (int) Math.ceil((double) maxnumber / 6.0));
			
			//그리드페인에 보여줄 상품을 상품리스트에서 가져온다.
			ProductModel model = showProdList.get((prodindex * 6) + i);
			
			//상품 정보를 보여줄 VBOX를 생성하고 스타일을 지정한다
			VBox newItem = new VBox(); 
			String style = "-fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 30;";
			newItem.setStyle(style);
			newItem.setPadding(new Insets(10));
			newItem.setAlignment(Pos.CENTER);
			
			//상품이미지 설정
			String path = model.getProdImage(); //경로를 상품정보에서 가져오고
			Image itemImage = new Image(path);
			ImageView imageView = new ImageView(itemImage); //이미지 뷰를 생성한다.
			imageView.setFitHeight(100);
			imageView.setFitWidth(100);
			newItem.getChildren().add(imageView); //이미지의 경로를 넣어준다.
			
			//상품 이름 설정
			Label label_name = new Label(model.getProdName()); //이름을 상품 정보에서 가져오고 스타일을 지정한다.
			label_name.setFont(new Font(15));
			newItem.getChildren().add(label_name);
			
			//상품 판매처 설정
			Label label_company = new Label(model.getProdCompany()); //판매처를 상품에서 가져오고 스타일을 지정한다.
			label_company.setFont(new Font(15));
			newItem.getChildren().add(label_company);
			
			//상품 가격 설정
			Label label_price = new Label(model.getProdPrice());//가격정보를 상품에서 가져오고 스타일을 지정한다.
			label_price.setFont(new Font(15));
			newItem.getChildren().add(label_price);
			
			//상품 클릭시 새로운 창을 생성하기 위한 클릭 메소드를 생성한다.
			newItem.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					System.out.print("" + label_name.getText());// 클릭된 상품의 이름을 콘솔창에 출력
					try {
						AppManager.getInstance().setProduct(model);
						clickEntry(arg0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//그리드페인의 x,y좌표 설정
			int x = i % 3;
			int y = (i < 3) ? 0 : 1;
			//상품 정보를 보여주기 위한 리스트뷰에 위에서 설정한 아이템을 추가한다.
			item_listview.add(newItem, x, y);
		}
	}

	//화면 왼쪽 상단에 내 리뷰보기 버튼 클릭시, 화면을 전환해주는 method
	public void changeToMyReviewListView() {
		Stage primaryStage = (Stage) vbox_myReviewList.getScene().getWindow(); //현재스테이지 정보를 가져옴
		Parent root=null;; //해당 상품의 부모를 null로 초기화 한고
		String URL="/view/MyReviewListVIew.fxml"; //새로 보여줄 뷰의 fxml파일을 load하기 위한 url
		try {
			root = FXMLLoader.load(getClass().getResource(URL)); //새로 보여줄 뷰의 fxml파일을 load한다.
			Scene scene = new Scene(root); //load한 fxml을 새로운 씬으로 생성하고
			primaryStage.setScene(scene);
			primaryStage.show(); //씬을 보여준다.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
