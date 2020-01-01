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
	ArrayList<Prod> cu;
	ArrayList<Prod> gs;
	ArrayList<Prod> ministop;

	DBA db;
	
	WebCrawler(){
		cu = new ArrayList<Prod>();
		gs = new ArrayList<Prod>();
		ministop = new ArrayList<Prod>();
		db=new DBA();
	}
	
	void ministopCrawler() {
		String name = null;
		String price = null;
		String image=null;
		String baseURL="https://www.ministop.co.kr/MiniStopHomePage/page";
		Document doc = null;
		String URL ="https://www.ministop.co.kr/MiniStopHomePage/page/guide/list.do";
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(URL);
		WebElement webElement;
		
			webElement = driver.findElement(By.className("pr_more"));
	        webElement.click();
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        
        
		doc = Jsoup.parse(driver.getPageSource());
		Elements elem = doc.select("div[class=\"section_tab_content\"]");
		System.out.println(elem);
		int count=0;
		for(Element e: elem.select("li")) {
			System.out.println(count);
			image=baseURL+e.select("img").attr("src").substring(2);
			name=e.select("img").attr("alt");
			price=e.select("strong").text()+"원";
			System.out.println("img : "+image);
			System.out.println("name : "+name);
			System.out.println("price : "+price);
			ministop.add(new Prod("ministop"));
			ministop.get(count).setProdName(name);
			ministop.get(count).setProdImage(image);
			ministop.get(count).setProdPrice(price);
			count++;
		}
		System.out.print(ministop.size());
		for(int i=0;i<ministop.size();i++) {
			System.out.println(ministop.get(i).getProdCompany());
			System.out.println(ministop.get(i).getProdName());
			System.out.println(ministop.get(i).getProdPrice());
			System.out.println(ministop.get(i).getProdImage());
		}
		db.insertProdInfo(ministop);
	}
	void gsCrawler() {
		Document doc = null;
		String URL ="http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood";
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(URL);
		doc = Jsoup.parse(driver.getPageSource());
		Elements elem = doc.select("div[class=\"tab_cont on\"]");
		System.out.println(elem);
		int count = 0;

		for(Element e: elem.select("p")) {
			
			if(e.className().equals("img")) {
				gs.add(new Prod("gs"));
				System.out.println("img : "+e.select("img").attr("src"));
				gs.get(count).setProdImage(e.select("img").attr("src"));
			}
			
			if(e.className().equals("tit")) {
				System.out.println("name : "+e.text());
				gs.get(count).setProdName(e.text());
			}
			
			if(e.className().equals("price")) {
				System.out.println("price : "+e.text());
				gs.get(count).setProdPrice(e.text());
				count++;
			}
		}
				
		for(int i=0;i<count;i++) {
			System.out.println(gs.get(i).getProdCompany());
			System.out.println(gs.get(i).getProdName());
			System.out.println(gs.get(i).getProdPrice());
			System.out.println(gs.get(i).getProdImage());
		}
		db.insertProdInfo(gs);

	}
	void cuCrawler() {
		Document doc = null;
		String URL ="http://cu.bgfretail.com/product/product.do?category=event&depth2=4";
		System.setProperty("webdriver.chrome.driver","src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(URL);
		doc = Jsoup.parse(driver.getPageSource());
		Elements elem = doc.select("div[class=\"prodListWrap\"]");
		System.out.println(elem);
		int count = 0;

		for(Element e: elem.select("p")) {
			
			if(e.className().equals("prodName")) {
				cu.add(new Prod("cu"));
				System.out.println("name : "+e.text());
				cu.get(count).setProdName(e.text());
			}
			
			if(e.className().equals("prodPrice")) {
				System.out.println("price : "+e.text());
				cu.get(count).setProdPrice(e.text());
				count++;
			}
		}
		count=0;
		for(Element e : elem.select("div")) {
			if(e.className().equals("photo")) {
				System.out.println("img : "+e.select("img").attr("src"));
				cu.get(count).setProdImage(e.select("img").attr("src"));
				count++;
			}
		}
		
		for(int i=0;i<count;i++) {
			System.out.println(cu.get(i).getProdCompany());
			System.out.println(cu.get(i).getProdName());
			System.out.println(cu.get(i).getProdPrice());
			System.out.println(cu.get(i).getProdImage());
		}
		db.insertProdInfo(cu);
	}
}
