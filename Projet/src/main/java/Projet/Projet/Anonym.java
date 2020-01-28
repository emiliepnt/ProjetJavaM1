package Projet.Projet;

import java.util.Random;

public class Anonym {

	static String randomLetter(String str) {
		int n = str.length();
		Random rand = new Random();
		String newStr = "";
		for (int i = 0; i < n; i++) {
			newStr += (char) (rand.nextInt(26) + 'a');
		}
		return newStr;
	}

	static String localRandomLetter(String str) {
		String newStr = "";
		if (str.contains("@")) {
			String firstPart = str.split("@")[0];
			String lastPart = str.split("@")[1];
			newStr += randomLetter(firstPart) + "@" + lastPart;
		} else
			newStr = randomLetter(str);

		return newStr;
	}

}
