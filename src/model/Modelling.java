package model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.AppManager;

public class Modelling {
	public ArrayList<ProductModel> prodModelling(String rawData){
		ArrayList<ProductModel> resultArray = new ArrayList<ProductModel>();
		
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(rawData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		
		return resultArray;
	}
	
	public ArrayList<ReviewModel> reviewModelling(String rawData){
		ArrayList<ReviewModel> resultArray = new ArrayList<ReviewModel>();
		
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(rawData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject tempObject = (JSONObject) jsonArray.get(i);
			resultArray.add(new ReviewModel(tempObject.get("reviewUser").toString(),tempObject.get("reviewTitle").toString(),tempObject.get("reviewContent").toString(),
					Integer.parseInt(tempObject.get("reviewNumber").toString()), Integer.parseInt(tempObject.get("prodNumber").toString())));
			
		}
		
		return resultArray;

	}
		
}
