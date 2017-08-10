package com.trivago.utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDataParser {

	JSONParser parser;
	JSONObject jsonObject;

	public TestDataParser(String fileName) {
		parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(fileName));
			jsonObject = (JSONObject) obj;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getSearchTerms(){
		JSONArray searchDataArray = (JSONArray) jsonObject.get("searchData");
		String[] searchTerm = new String[searchDataArray.size()];
		for(int i=0; i<searchDataArray.size();i++){
			searchTerm[i] = searchDataArray.get(i).toString();
		}
        return searchTerm;
	}
	
	public JSONArray getLocaleData(){
		JSONArray localeDataArray = (JSONArray) jsonObject.get("localeData");
        return localeDataArray;
	}
}
