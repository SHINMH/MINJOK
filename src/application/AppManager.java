package application;

import model.UserModel;

public class AppManager {
	private UserModel user;
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
}
