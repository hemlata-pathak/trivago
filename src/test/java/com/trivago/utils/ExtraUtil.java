package com.trivago.utils;

import java.security.SecureRandom;
import java.util.Random;

public class ExtraUtil {

	public static String generateRandomEmail() {
		return "testRoom5" + String.valueOf(generateNdigitRandomNumber(4)) + "@gmail.com";
	}
	
	public static String generateRandomPassword() {
		return "Room5@" + String.valueOf(generateNdigitRandomNumber(3));
	}

	// Generate N Digit Random Number
	public static int generateNdigitRandomNumber(int n) {
		Random randGen = new Random();
		Double startNum = Math.pow(10, n - 1);
		int num1 = startNum.intValue();
		Double endNum = Math.pow(10, n);
		int num2 = endNum.intValue();
		int range = num2 - num1 + 1;
		int randomNum = randGen.nextInt(range) + num1;
		return randomNum;
	}
	
	public static String getRandomString(){
		char[] CHARSET_AZ_09 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		String randomstr = randomString(CHARSET_AZ_09, 20);
		return randomstr;
	}
	
	public static String randomString(char[] characterSet, int length) {
	    Random random = new SecureRandom();
	    char[] result = new char[length];
	    for (int i = 0; i < result.length; i++) {
	        // picks a random index out of character set > random character
	        int randomCharIndex = random.nextInt(characterSet.length);
	        result[i] = characterSet[randomCharIndex];
	    }
	    return new String(result);
	}
	
}
