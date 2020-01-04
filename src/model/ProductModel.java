package model;

public class ProductModel {
	//제품 정보를 담을 DTO 클래스
	
	private int prodNumber;//제품 고유 번호
	private String prodName;//제품 이름
	private String prodPrice;//제품 가격
	private String prodImage;//제품 제품 이미지 string 주소
	private String prodCompany;//판매처
	
	//get, set
	public int getProdNumber() {
		return prodNumber;
	}
	public void setProdNumber(int prodNumber) {
		this.prodNumber = prodNumber;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(String prodPrice) {
		this.prodPrice = prodPrice;
	}
	public String getProdImage() {
		return prodImage;
	}
	public void setProdImage(String prodImage) {
		this.prodImage = prodImage;
	}
	public String getProdCompany() {
		return prodCompany;
	}
	public void setProdCompany(String prodCompany) {
		this.prodCompany = prodCompany;
	}
}
