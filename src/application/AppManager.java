package application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ProductModel;
import model.ReviewModel;
import model.UserModel;

public class AppManager {
	private UserModel user;
	private ProductModel product;
	private ReviewModel review;
	private int checkPoint;
	private static AppManager instance = new AppManager();
	private Scene scene;
	private Stage stage;
	
	public int getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(int checkPoint) {
		this.checkPoint = checkPoint;
	}
	private AppManager() {
	}
	public static AppManager getInstance() {
		return instance;
	}
	
	public void setUser(String id, String password) {
		user = new UserModel();
		user.setUserID(id);
		user.setUserPW(password);
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
	
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
