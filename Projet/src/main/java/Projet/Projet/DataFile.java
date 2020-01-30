package Projet.Projet;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
				this.data.get(name).dataType=type;
				this.colNames[i] = name.trim();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier de description est introuvable.");
			e.printStackTrace();
		}

		if (this.verificationFileName!=null) {
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

		if (this.anonymisationFileName!=null) {
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
						this.data.get(name).changeTo =chg;
					} else
						this.data.get(name).changeTo="";
				}
			} catch (FileNotFoundException e) {
				System.out.println("Le fichier d'Anonymisation est introuvable.");
				e.printStackTrace();
			}
		}

	}
}
