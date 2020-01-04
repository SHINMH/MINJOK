//상품 모델을 정의한 클래스
public class Prod {
	
	//상품의 정보 : 이름, 가격, 이미지, 판매처
	private String prodName;
	private String prodPrice;
	private String prodImage;
	private String prodCompany;
	
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

	public Prod(String company) {
		this.prodCompany=company;

	}

	public Prod(String name, String price, String image, String company) {
		this.prodName=name;
		this.prodPrice=price;
		this.prodImage=image;
		this.prodCompany=company;
	}
}
