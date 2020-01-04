package model;

public class UserModel {
	//유저 정보 DTO를 모델링한 클래스
	private String userID;//유저의 아이디
	private String userPW;//유저의 비밀번호
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
}
