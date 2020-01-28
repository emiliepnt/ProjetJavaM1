package Projet.Projet;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testRegles() {

		String str1 = "emilie.peynet@dauphine.eu";
		String str2 = "azerty@";
		String str3 = "rodolphe.com";
		String str4 = "jean-eudes.bernard@dauphine.psl.eu";
		String str5 = "thais piganeau@dauphine.eu";
		String str6 = "georges.cloney@lamsade.dauphine.fr";
		String str7 = "jean.dujardin@gmail.fr";

		// emailValid tests
		assertTrue(Regles.emailValid(str1));
		assertFalse(Regles.emailValid(str2));
		assertFalse(Regles.emailValid(str3));
		assertTrue(Regles.emailValid(str4));
		assertFalse(Regles.emailValid(str5));
		assertTrue(Regles.emailValid(str6));
		assertTrue(Regles.emailValid(str7));

		// dauphineEmail tests
		assertTrue(Regles.dauphineEmail(str1));
		assertFalse(Regles.dauphineEmail(str2));
		assertFalse(Regles.dauphineEmail(str3));
		assertTrue(Regles.dauphineEmail(str4));
		assertFalse(Regles.dauphineEmail(str5));
		assertTrue(Regles.dauphineEmail(str6));
		assertFalse(Regles.dauphineEmail(str7));

		// age test
		assertTrue(Regles.ageValid("1","INT"));
		assertFalse(Regles.ageValid("-1","INT"));
		assertFalse(Regles.ageValid("1234","INT"));
		assertTrue(Regles.ageValid("78.5", "DOUBLE"));
		assertFalse(Regles.ageValid("20","STRING"));

	}
	
	public void testAnonym() {
		String str1 = "emilie.peynet@dauphine.eu";
		String str2 = "Rodolphe";
		String str3 = "bonjour";
		String str4 = "jean-eudes.bernard@dauphine.psl.eu";
		String str7 = "jean.dujardin@gmail.fr";
		
		assertTrue(!str1.equals(Anonym.randomLetter(str1)));
		assertTrue(!str2.equals(Anonym.randomLetter(str2)));
		assertTrue(!str3.equals(Anonym.randomLetter(str3)));
		assertTrue(!str4.equals(Anonym.randomLetter(str4)));
		assertTrue(!str7.equals(Anonym.randomLetter(str7)));
		
		assertTrue(!str1.equals(Anonym.localRandomLetter(str1)));
		assertTrue(!str4.equals(Anonym.localRandomLetter(str4)));
		assertTrue(!str7.equals(Anonym.localRandomLetter(str7)));
		
	}
	
}
