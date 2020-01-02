package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReviewModel {
	
	private int reviewNumber;
	private int prodNumber;
	private String reviewUser;
	private String reviewTitle;
	private String reviewContent;
	
	private StringProperty name;
	private StringProperty title;
	private StringProperty content;
	
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
	
	public ReviewModel(String name, String title, String content,int reviewNumber, int prodNumber) {
		this.name=new SimpleStringProperty(name);
		this.title=new SimpleStringProperty(title);
		this.content=new SimpleStringProperty(content);
		this.reviewNumber=reviewNumber;
		this.prodNumber=prodNumber;
		this.reviewUser=name;
		this.reviewTitle=title;
		this.reviewContent=content;

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
