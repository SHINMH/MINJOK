import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebCrawler {
	ArrayList<Prod> cu; //cu페이지에서 크롤링한 데이터를 담는 변수
	ArrayList<Prod> gs; //gs페이지에서 크롤링한 데이터를 담는 변수
	ArrayList<Prod> ministop; //ministop페이지에서 크롤링한 데이터를 담는 변수

	DBA db; //크롤링 결과를 담기위한 메소드 호출을 위한 DBA형 변수
	
	WebCrawler(){
		cu = new ArrayList<Prod>();
		gs = new ArrayList<Prod>();
		ministop = new ArrayList<Prod>();
		db=new DBA();
	} //생성자를 통해 해당 클래스에서 사용될 변수들을 생성한다.
	
	void ministopCrawler() {
		//ministop을 크롤링하는 메소드
		String name = null; 
		String price = null;
		String image=null;
		String baseURL="https://www.ministop.co.kr/MiniStopHomePage/page"; //크롤링한 이미지path의 baseURL, baseURL+imagePath로 db에 저장
		Document doc = null; //크롤링한 데이터가 담길 변수
		String URL ="https://www.ministop.co.kr/MiniStopHomePage/page/guide/list.do"; //크롤링할 사이트의 URL
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe"); //동적 실행을 위한 브라우저 드라이버 load
		WebDriver driver = new ChromeDriver(); 
		driver.get(URL); //드라이버를 통해 url주소를 호출하며 실행한다.
		WebElement webElement;
		
			webElement = driver.findElement(By.className("pr_more")); //실행된 페이지의 html문서중 pr_more클래스 네임을 가지고 있는 요소를 가져온다.
	        webElement.click(); // 해당 요소를 클릭하고
	        try {
				Thread.sleep(1000); //스크립트등의 실행을 위한 대기 시간
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        
        
		doc = Jsoup.parse(driver.getPageSource()); //jsoup파서를 통해 해당 html문서를 저장한다.
		Elements elem = doc.select("div[class=\"section_tab_content\"]"); //html문서중 가져올 영역 선택
		System.out.println(elem);
		int count=0;
		for(Element e: elem.select("li")) {
			System.out.println(count);
			image=baseURL+e.select("img").attr("src").substring(2); //이미지 정보가 담긴 id
			name=e.select("img").attr("alt"); //이름정보가 담긴 id
			price=e.select("strong").text()+"원"; //가격정보가 담긴 id 
			System.out.println("img : "+image);
			System.out.println("name : "+name);
			System.out.println("price : "+price);
			ministop.add(new Prod("ministop")); //상품의 정보를 어레이리스트에 추가한다.
			ministop.get(count).setProdName(name);
			ministop.get(count).setProdImage(image);
			ministop.get(count).setProdPrice(price);
			count++;
		}
		System.out.print(ministop.size());
		//크롤링 확인을 위한 콘솔 출력
		for(int i=0;i<ministop.size();i++) {
			System.out.println(ministop.get(i).getProdCompany());
			System.out.println(ministop.get(i).getProdName());
			System.out.println(ministop.get(i).getProdPrice());
			System.out.println(ministop.get(i).getProdImage());
		}
		//데이터베이스 저장을 위한 메소드 호출 상품정보가 담긴 ArrayList를 전달한다. 
		db.insertProdInfo(ministop);
	}
	void gsCrawler() {
		//gs 페이지를 크롤링하는 메소드
		Document doc = null;
		String URL ="http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood"; //크롤링할 사이트의 URL
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe");//동적 실행을 위한 브라우저 드라이버 load
		WebDriver driver = new ChromeDriver();
		driver.get(URL); //드라이버를 통해 url주소를 호출하며 실행한다.
		doc = Jsoup.parse(driver.getPageSource()); //jsoup파서를 통해 해당 html문서를 저장한다.
		Elements elem = doc.select("div[class=\"tab_cont on\"]");  //html문서중 가져올 영역 선택
		System.out.println(elem);
		int count = 0;

		for(Element e: elem.select("p")) {
			
			if(e.className().equals("img")) { //이미지 정보가 담긴 id
				gs.add(new Prod("gs"));
				System.out.println("img : "+e.select("img").attr("src"));
				gs.get(count).setProdImage(e.select("img").attr("src"));
			}
			
			if(e.className().equals("tit")) { //이름정보가 담긴 id
				System.out.println("name : "+e.text());
				gs.get(count).setProdName(e.text());
			}
			
			if(e.className().equals("price")) { //가격정보가 담긴 id 
				System.out.println("price : "+e.text());
				gs.get(count).setProdPrice(e.text());
				count++;
			}
		}
				
		//크롤링 확인을 위한 콘솔 출력
		for(int i=0;i<count;i++) {
			System.out.println(gs.get(i).getProdCompany());
			System.out.println(gs.get(i).getProdName());
			System.out.println(gs.get(i).getProdPrice());
			System.out.println(gs.get(i).getProdImage());
		}
		
		//데이터베이스 저장을 위한 메소드 호출 상품정보가 담긴 ArrayList를 전달한다. 
		db.insertProdInfo(gs);

	}
	void cuCrawler() {
		//cu 페이지를 크롤링하는 메소드
		Document doc = null;
		String URL ="http://cu.bgfretail.com/product/product.do?category=event&depth2=4";//크롤링할 사이트의 URL
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe");//동적 실행을 위한 브라우저 드라이버 load
		WebDriver driver = new ChromeDriver();
		driver.get(URL); //드라이버를 통해 url주소를 호출하며 실행한다.
		doc = Jsoup.parse(driver.getPageSource()); //jsoup파서를 통해 해당 html문서를 저장한다.
		Elements elem = doc.select("div[class=\"prodListWrap\"]");  //html문서중 가져올 영역 선택
		System.out.println(elem);
		int count = 0;

		for(Element e: elem.select("p")) {
			
			if(e.className().equals("prodName")) { //이름정보가 담긴 id
				cu.add(new Prod("cu"));
				System.out.println("name : "+e.text());
				cu.get(count).setProdName(e.text());
			}
			
			if(e.className().equals("prodPrice")) { //가격정보가 담긴 id 
				System.out.println("price : "+e.text());
				cu.get(count).setProdPrice(e.text());
				count++;
			}
		}
		count=0;
		for(Element e : elem.select("div")) { //이미지 정보가 담긴 id
			if(e.className().equals("photo")) {
				System.out.println("img : "+e.select("img").attr("src"));
				cu.get(count).setProdImage(e.select("img").attr("src"));
				count++;
			}
		}
		
		//크롤링 확인을 위한 콘솔 출력
		for(int i=0;i<count;i++) {
			System.out.println(cu.get(i).getProdCompany());
			System.out.println(cu.get(i).getProdName());
			System.out.println(cu.get(i).getProdPrice());
			System.out.println(cu.get(i).getProdImage());
		}
		//데이터베이스 저장을 위한 메소드 호출 상품정보가 담긴 ArrayList를 전달한다. 
		db.insertProdInfo(cu);
	}
}
