package com.trivago.utils;

public class DataProviderUtil {
	
	public static Object[][] getSearchDataProvider(String[] searchTerms){
		Object[][] arrayObject =  new Object[searchTerms.length][1];
		for (int i= 0 ; i < searchTerms.length; i++) {
			arrayObject[i][0] = searchTerms[i];
		}
		return arrayObject;
	}
	
	public static Object[][] getCountryDataProvider(String[] countrydata){
		Object[][] arrayObject =  new Object[countrydata.length][2];
		for (int i= 0 ; i < countrydata.length; i++) {
			arrayObject[i][0] = countrydata[i];
		}
		return arrayObject;
	}
}
