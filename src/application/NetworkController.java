package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class NetworkController {
	//서버와 RESTFUL API를 이용하여 json형식으로 통신하기 위한 클래스
	
	//url과 jsonobject를 받아 서버와 통신을 진행하는 method 반환값으로 string형식으로 저장한 다음
	//controller 클래스로 반환하고, controller 클래스에서 처리하도록 설계하였다.
	public String sendREST(String sendURL, JSONObject jsonValue) {
		String inputLine = null; //반환된 값을 저장할 string 변수
		StringBuffer outResult = new StringBuffer();
		//서버와의 통신 값을 저장할 버퍼
		try {
			//url 객체를 이용하여 서버와 통신한다.
			URL url = new URL(sendURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//header 설정
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			
			//버퍼에 json값을 넣어 통신을 진행함. 
			OutputStream os = conn.getOutputStream();
			os.write(jsonValue.toJSONString().getBytes("UTF-8"));
			os.flush();
			
			//통신 반환값을 퍼버로 받음.
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			//반환값을 읽음.
			while((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//서버로 통신후 받은 값을 string 형식으로 반환해줌
		return outResult.toString();
	}
}
