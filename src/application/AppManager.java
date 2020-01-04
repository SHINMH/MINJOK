package application;

import java.util.ArrayList;

import model.ProductModel;
import model.ReviewModel;
import model.UserModel;

public class AppManager {
	private UserModel user;
	private ProductModel product;
	private ReviewModel review;
	private static AppManager instance = new AppManager();
	private int checkPoint;
	public final static int REVIEWEDIT = 1;
	public final static int REVIEWWRITE = 2;
	
	private ArrayList<ProductModel> productList;
	
	private AppManager() {
		productList = new ArrayList<ProductModel>();
	}
	
	public void setUser(String id, String password) {
		user = new UserModel();
		user.setUserID(id);
		user.setUserPW(password);
	}
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
