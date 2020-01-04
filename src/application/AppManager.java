package application;

import java.util.ArrayList;

import model.ProductModel;
import model.ReviewModel;
import model.UserModel;

public class AppManager {
	//controller간의 데이터 전송을 위해 사용하는 클래스로 singletone pattern을 적용하였다.
	private UserModel user;//로그인한 회원 정보를 저장할 변수
	private ProductModel product;//제품 리스트 뷰에서 상세뷰로 전환시 선택한 제품에 대해 넘겨주기 위해 선언
	private ReviewModel review;//리뷰 리스트 뷰에서 상세뷰로 전환시 선택한 리뷰에 대해 넘겨주기 위해 선언
	private static AppManager instance = new AppManager();
	private int checkPoint;//리뷰 작성인지, 수정인지에 대한 flag 변수
	//checkpoint에 들어가는 값
	public final static int REVIEWEDIT = 1;
	public final static int REVIEWWRITE = 2;
	//서버와의 통신으로 받아온 모든 제품 리스트
	private ArrayList<ProductModel> productList;
	
	//생성자
	private AppManager() {
		productList = new ArrayList<ProductModel>();
	}
	//유저를 setting하는 method
	public void setUser(String id, String password) {
		user = new UserModel();
		user.setUserID(id);
		user.setUserPW(password);
	}
	
	//get set
	public void setProductList(ArrayList<ProductModel> productList) {
		this.productList = productList;
	}
	public ArrayList<ProductModel> getProductList(){
		return productList;
	}
	public int getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(int checkPoint) {
		this.checkPoint = checkPoint;
	}
	public static AppManager getInstance() {
		return instance;
	}	
	public UserModel getUser() {
		return user;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	public ProductModel getProduct() {
		return product;
	}
	public ReviewModel getReview() {
		return review;
	}
	public void setReview(ReviewModel review) {
		this.review = review;
	}
}
