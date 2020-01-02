package application;

import model.ProductModel;
import model.UserModel;

public class AppManager {
	private UserModel user;
	private ProductModel product;
	private static AppManager instance = new AppManager();
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
	
}
