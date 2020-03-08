package Projet.Projet;

import java.util.Scanner;

public class App 
{
	public static void main(String[] args) throws Exception {

		DataFile df = new DataFile();
		Scanner sc = new Scanner(System.in);

		System.out.println("Data_IN Path: ");
		df.fileIN = sc.nextLine();
		System.out.println("Description_File Path: ");
		df.descriptionFileName = sc.nextLine();

		String rep = "";
		while (!rep.equals("OUI") && !rep.equals("NON")) {
			System.out.println("Voulez-vous effectuer une Vérification des données ? Répondre OUI ou NON");
			rep = sc.nextLine();
		}
		if (rep.equals("OUI")) {
			System.out.println("Verification_File¨Path: ");
			df.verificationFileName = sc.nextLine();
			System.out.println("File_OUT Path after verification: ");
			df.fileOUTverif = sc.nextLine();
		}

		rep = "";
		while (!rep.equals("OUI") && !rep.equals("NON")) {
			System.out.println("Voulez-vous effectuer une Anonymisation des données ? Répondre OUI ou NON");
			rep = sc.nextLine();
		}
		if (rep.equals("OUI")) {
			System.out.println("Anonymisation_File ¨Path: ");
			df.anonymisationFileName = sc.nextLine();
			System.out.println("File_OUT Path after anonymisation: ");
			df.fileOUTanonym = sc.nextLine();
		}

		df.buildDataFile();

		if (df.verificationFileName != null)
			df.verification();

		if (df.anonymisationFileName != null) {
			if (df.verificationFileName != null) {
				System.out.println(
						"Voulez-vous que le fichier qui est à anonymiser soit le fichier obtenu après vérification ? (les entrées erronées auront donc été effacées) Répondre par OUI ou par NON.");
				rep = sc.nextLine();
				if (rep.equals("OUI"))
					df.fileIN = df.fileOUTverif;
			}
			df.anonymisation();
		}
		
		sc.close();
		System.out.println("Programme exécuté avec succès.");

	}
}
