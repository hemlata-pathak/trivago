package com.trivago.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataProviderUtil {

	public static Object[][] getSearchDataProvider(String[] searchTerms) {
		Object[][] arrayObject = new Object[searchTerms.length][1];
		for (int i = 0; i < searchTerms.length; i++) {
			arrayObject[i][0] = searchTerms[i];
		}
		return arrayObject;
	}

	public static Object[][] getLocaleDataProvider(JSONArray localeData) {
		Object[][] arrayObject = new Object[localeData.size()][3];
		for (int i = 0; i < localeData.size(); i++) {
			JSONObject userData = (JSONObject) localeData.get(i);
			arrayObject[i][0] = userData.get("country");
			arrayObject[i][1] = userData.get("localeDomain");
			arrayObject[i][2] = userData.get("language");
		}
		return arrayObject;
	}

}
