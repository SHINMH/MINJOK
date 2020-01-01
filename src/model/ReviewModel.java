package model;

public class ReviewModel {
	private int reviewNumber;
	private int prodNumber;
	private String reviewUser;
	private String reviewTitle;
	private String reviewContent;
	
	public int getReviewNumber() {
		return reviewNumber;
	}
	public void setReviewNumber(int reviewNumber) {
		this.reviewNumber = reviewNumber;
	}
	public int getProdNumber() {
		return prodNumber;
	}
	public void setProdNumber(int prodNumber) {
		this.prodNumber = prodNumber;
	}
	public String getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
}
