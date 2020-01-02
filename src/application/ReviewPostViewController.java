package application;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReviewPostViewController {

	
	public void postReview() throws Exception{
		String id = AppManager.getInstance().getUser().getUserID();
		int prodNumber = AppManager.getInstance().getProduct().getProdNumber();
		String title = null;
		String content = null;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("prodNumber", prodNumber);
		jsonObject.put("reviewTitle", title);
		jsonObject.put("reviewContent", content);
		
		NetworkController networkController = new NetworkController();
		
		String result = networkController.sendREST("", jsonObject);
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObjectResult = (JSONObject) parser.parse(result);
		String code = String.valueOf(jsonObjectResult.get("code"));
		
		if(code.equals("200")) {
			
		}
	}
}
