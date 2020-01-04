package model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.AppManager;

public class Modelling {
	// network controller로 서버와 통신 후 결과를 모델링하는 클래스

	// network controller로 서버와 통신 후 반환받은 string 값을 ProductModel로 모델링한 후 Arraylist에
	// add하여 반환해주는 method
	public ArrayList<ProductModel> prodModelling(String rawData) {
		ArrayList<ProductModel> resultArray = new ArrayList<ProductModel>();

		JSONParser parser = new JSONParser();// string 형식의 값을 jsonArray의 형식으로 parsing하기 위해 선언한 파서
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(rawData);// 파서를 이용하여 string을 jsonarray로 변환
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonarray의 하나의 object에서 값을 뽑아 ProductModel을 생성한 후 리스트에 add해준다.
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			System.out.println(jsonObject.get("prodName"));
			ProductModel newModel = new ProductModel();
			newModel.setProdName(jsonObject.get("prodName").toString());
			newModel.setProdNumber(Integer.parseInt(jsonObject.get("prodNumber").toString()));
			newModel.setProdPrice(jsonObject.get("prodPrice").toString());
			newModel.setProdCompany(jsonObject.get("prodCompany").toString());
			newModel.setProdImage(jsonObject.get("prodImage").toString());
			resultArray.add(newModel);
			AppManager.getInstance().getProductList().add(newModel);
		}

		return resultArray;//결과 productModel arraylist를 반환함
	}

	// network controller로 서버와 통신 후 반환받은 string 값을 ReviewModel로 모델링한 후 Arraylist에
	// add하여 반환해주는 method
	public ArrayList<ReviewModel> reviewModelling(String rawData) {
		ArrayList<ReviewModel> resultArray = new ArrayList<ReviewModel>();

		JSONParser parser = new JSONParser();// string 형식의 값을 jsonArray의 형식으로 parsing하기 위해 선언한 파서
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(rawData);// 파서를 이용하여 string을 jsonarray로 변환
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// jsonarray의 하나의 object에서 값을 뽑아 ReviewModel을 생성한 후 리스트에 add해준다.
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject tempObject = (JSONObject) jsonArray.get(i);
			resultArray.add(new ReviewModel(tempObject.get("reviewUser").toString(),
					tempObject.get("reviewTitle").toString(), tempObject.get("reviewContent").toString(),
					Integer.parseInt(tempObject.get("reviewNumber").toString()),
					Integer.parseInt(tempObject.get("prodNumber").toString())));

		}

		return resultArray;//결과 reviewmodel arraylist를 반환함
	}
}
