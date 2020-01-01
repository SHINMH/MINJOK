package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class NetworkController {
	public String sendREST(String sendURL, String jsonValue) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "shin");
		jsonObject.put("pw", "1234");
		
		jsonValue = jsonObject.toJSONString();
		
		System.out.println(jsonValue);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			URL url = new URL(sendURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonValue.getBytes("UTF-8"));
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			while((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(outResult.toString());
		
		return outResult.toString();
	}
}
