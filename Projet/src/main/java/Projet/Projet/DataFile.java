package Projet.Projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class DataFile {
	String fileIN;
	String fileOUTverif;
	String fileOUTanonym;
	String sep;
	String descriptionFileName;
	String verificationFileName;
	String anonymisationFileName;
	String[] colNames;
	int nbCol;
	int nbRules;
	int nbChanges;
	HashMap<String, Column> data;

	public static final String UTF8_BOM = "\uFEFF";

	DataFile() {
		sep = ",";
		nbCol = 0;
		nbRules = 0;
		nbChanges = 0;
		data = new HashMap<String, Column>();
	}

	DataFile(String filename) {
		super();
		fileIN = filename;
	}

	void buildDataFile() {

		String name;
		JsonArray descriptionOBJ;
		
		try {
			descriptionOBJ = new Gson().fromJson(new FileReader(this.descriptionFileName), JsonArray.class);

			this.nbCol = descriptionOBJ.size();
			this.colNames = new String[this.nbCol];

			String type;

			for (int i = 0; i < this.nbCol; i++) {
				name = descriptionOBJ.get(i) // get the ième JsonElement
						.getAsJsonObject() // get it as a JsonObject
						.get("name") // get the nested 'name' JsonElement
						.getAsString(); // get it as a String
				type = descriptionOBJ.get(i) // get the ième JsonElement
						.getAsJsonObject() // get it as a JsonObject
						.get("dataType") // get the nested 'dataType' JsonElement
						.getAsString(); // get it as a String
				this.data.put(name, new Column(name));
				this.data.get(name).dataType = type;
				this.colNames[i] = name.trim();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier de description est introuvable.");
			e.printStackTrace();
		}

		if (this.verificationFileName != null) {
			JsonArray verificationOBJ;
			try {

				verificationOBJ = new Gson().fromJson(new FileReader(this.verificationFileName), JsonArray.class);

				this.nbRules = verificationOBJ.size();

				String shld;

				for (int i = 0; i < this.nbRules; i++) {
					name = verificationOBJ.get(i) // get the ième JsonElement
							.getAsJsonObject() // get it as a JsonObject
							.get("name") // get the nested 'name' JsonElement
							.getAsString(); // get it as a String
					if (verificationOBJ.get(i) // get the ième JsonElement
							.getAsJsonObject() // get it as a JsonObject
							.get("should") != null) {
						shld = verificationOBJ.get(i) // get the ième JsonElement
								.getAsJsonObject() // get it as a JsonObject
								.get("should") // get the nested 'should' JsonElement
								.toString(); // get it as a String (instead of a table of Strings)
						this.data.get(name).should = shld;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Le fichier de Vérification est introuvable.");
				e.printStackTrace();
			}

		}

		if (this.anonymisationFileName != null) {
			JsonArray anonymisationOBJ;
			try {

				anonymisationOBJ = new Gson().fromJson(new FileReader(this.anonymisationFileName), JsonArray.class);

				this.nbChanges = anonymisationOBJ.size();

				String chg;

				for (int i = 0; i < this.nbChanges; i++) {
					name = anonymisationOBJ.get(i) // get the ième JsonElement
							.getAsJsonObject() // get it as a JsonObject
							.get("name") // get the nested 'name' JsonElement
							.getAsString(); // get it as a String
					if (anonymisationOBJ.get(i) // get the ième JsonElement
							.getAsJsonObject() // get it as a JsonObject
							.get("changeTo") != null) {
						chg = anonymisationOBJ.get(i) // get the ième JsonElement
								.getAsJsonObject() // get it as a JsonObject
								.get("changeTo") // get the nested 'changeTo' JsonElement
								.getAsString(); // get it as a String
						this.data.get(name).changeTo = chg;
					} else
						this.data.get(name).changeTo = "";
				}
			} catch (FileNotFoundException e) {
				System.out.println("Le fichier d'Anonymisation est introuvable.");
				e.printStackTrace();
			}
		}

	}

	void checkNames(String line) throws Exception {
		if (line.startsWith(UTF8_BOM)) {
			line = line.substring(1).trim();
		}
		String[] names = line.split(this.sep);

		for (int i = 0; i < this.nbCol; i++) {
			if (!names[i].trim().equals(this.colNames[i].trim())) {
				System.out.println(
						"Incohérence entre les noms de colonnes dans le fichier de description et le fichier de données.");
				System.out.println(names[i] + " vs " + this.colNames[i]);
				throw new Exception();
			}
		}
	}

	void verification() throws Exception {
		BufferedReader br = null;
		String line; // ligne du csv
		String[] dataTemp = new String[this.nbCol]; // données de la ligne splitée selon le séparateur
		int entryNum = 0; // Compteur de lignes
		boolean entryOK = true; // Variable enregistrant si une entrée est correcte
		File dataOUT = new File(this.fileOUTverif);
		FileWriter fw = new FileWriter(dataOUT);
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			br = new BufferedReader(new FileReader(this.fileIN));
			line = br.readLine();

			this.checkNames(line); // Vérification des entêtes des colonnes (data vs description)
			bw.write(line);
			bw.write("\n");
			entryNum++;

			while ((line = br.readLine()) != null) {
				dataTemp = line.split(this.sep);
				entryOK = true;
				for (int i = 0; i < this.nbCol; i++) {

					// Verification des différentes règles
					if (this.data.get(this.colNames[i]).should != null
							&& this.data.get(this.colNames[i]).should.contains("BE_AN_AGE")) {
						if (!Regles.ageValid(dataTemp[i], this.data.get(this.colNames[i]).dataType)) {
							System.out.println("Attention, cet age n'est pas valide: " + dataTemp[i] + " (ligne "
									+ entryNum + ")");
							entryOK = false;
						}

					}

					if (this.data.get(this.colNames[i]).should != null
							&& this.data.get(this.colNames[i]).should.contains("BE_AN_EMAIL")) {
						if (!Regles.emailValid(dataTemp[i])) {
							System.out.println("Attention, cet email n'est pas valide: " + dataTemp[i] + " (ligne "
									+ entryNum + ")");
							entryOK = false;
						}
					}

					if (this.data.get(this.colNames[i]).should != null
							&& this.data.get(this.colNames[i]).should.contains("BE_AN_DAUPHINE_EMAIL")) {
						if (!Regles.dauphineEmail(dataTemp[i])) {
							System.out.println("Attention, cet email n'est pas un mail dauphine valide: " + dataTemp[i]
									+ " (ligne " + entryNum + ")");
							entryOK = false;
						}
					}

				}
				if (entryOK) {
					// Ecriture de la ligne dans le fichier csv
					bw.write(line);
					bw.write("\n");
				}
				entryNum++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					bw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void anonymisation() throws IOException {
		BufferedReader br = null;
		String line; // ligne du csv
		String[] dataTemp = new String[this.nbCol]; // données de la ligne splitée selon le séparateur
		File dataOUT = new File(this.fileOUTanonym);
		FileWriter fw = new FileWriter(dataOUT);
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			br = new BufferedReader(new FileReader(this.fileIN));
			line = br.readLine();
			bw.write(line);
			bw.write("\n");

			while ((line = br.readLine()) != null) {
				dataTemp = line.split(this.sep);
				for (int i = 0; i < this.nbCol; i++) {

					// Verification des différentes règles
					if (this.data.get(this.colNames[i]).changeTo != null
							&& this.data.get(this.colNames[i]).changeTo.contains("RANDOM_LETTER_FOR_LOCAL_PART")) {
						bw.write(Anonym.localRandomLetter(dataTemp[i]));
					} else if (this.data.get(this.colNames[i]).changeTo != null
							&& this.data.get(this.colNames[i]).changeTo.contains("RANDOM_LETTER")) {
						bw.write(Anonym.randomLetter(dataTemp[i]));
					}

					else if (this.data.get(this.colNames[i]).changeTo != null) {
						bw.write(dataTemp[i]);
					}

					if (i == this.nbCol - 1)
						bw.write("\n");
					else
						bw.write(this.sep);
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					bw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
