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
	
	public void testBuildData() {
		DataFile df = new DataFile();
		
		df.fileIN = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\DataIN.csv";
		
		df.descriptionFileName = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\Desc.json";

		df.verificationFileName = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\Verif.json";
		df.fileOUTverif = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\DataOutVerif.csv";
		
		df.anonymisationFileName = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\Anonym.json";
		df.fileOUTanonym = "C:\\Users\\mimim\\Documents\\Dauphine\\M1 MIAGE Alternance\\Java Avancé\\0. Projet Outline\\Outline\\src\\test\\java\\Projet\\Outline\\DataOutAnonym.csv";
		
		df.buildDataFile();
		
		assertTrue(df.nbCol==5);
		assertTrue(df.colNames[0].equals("NOM"));
		assertTrue(df.colNames[1].equals("AGE"));
		assertTrue(df.colNames[2].equals("DATE_DE_NAISSANCE"));
		assertTrue(df.colNames[3].equals("EMAIL_PRO"));
		assertTrue(df.colNames[4].equals("EMAIL_PERSO"));
		assertTrue(df.nbChanges==3);
		assertTrue(df.nbRules==3);
	}
}
