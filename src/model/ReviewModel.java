package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReviewModel {
	// 리뷰 정보를 담을 DTO class

	private int reviewNumber;// 리뷰의 고유 번호
	private int prodNumber;// 리뷰가 어느 제품에 관한것인지를 나타내는 제품고유번호
	private String reviewUser;// 리뷰 작성자 아이디
	private String reviewTitle;// 리뷰 제목
	private String reviewContent;// 리뷰 내용

	// table List에 리뷰를 넣기위해 선언하고 사용.
	private StringProperty name;
	private StringProperty title;
	private StringProperty content;

	// 생성자
	public ReviewModel(String name, String title, String content, int reviewNumber, int prodNumber) {
		this.name = new SimpleStringProperty(name);
		this.title = new SimpleStringProperty(title);
		this.content = new SimpleStringProperty(content);
		this.reviewNumber = reviewNumber;
		this.prodNumber = prodNumber;
		this.reviewUser = name;
		this.reviewTitle = title;
		this.reviewContent = content;
	}

	// get, set
	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}

	public StringProperty getTitle() {
		return title;
	}

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public StringProperty getContent() {
		return content;
	}

	public void setContent(StringProperty content) {
		this.content = content;
	}

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
