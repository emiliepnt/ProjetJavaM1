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

	
}
