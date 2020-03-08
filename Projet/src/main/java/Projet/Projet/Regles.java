package Projet.Projet;

import org.apache.commons.validator.routines.EmailValidator;

public class Regles {

	static boolean ageValid(String strAge, String dataType) {
		if (dataType.equals("INT")) {
			Integer intAge = Integer.parseInt(strAge);
			if (intAge >= 0)
				return (intAge <= 120);
		}
		if (dataType.equals("DOUBLE")) {
			Double dblAge = Double.parseDouble(strAge);
			if (dblAge >= 0)
				return (dblAge <= 120);
		}

		return false;
	}

	static boolean emailValid(String str) {
		EmailValidator ev = EmailValidator.getInstance();
		return ev.isValid(str);
	}

	static boolean dauphineEmail(String str) {
		if (!emailValid(str))
			return false;
		if (!str.endsWith("@dauphine.eu"))
			if (!str.endsWith("@lamsade.dauphine.fr"))
				if (!str.endsWith("@dauphine.psl.eu"))
					return false;
		return true;

	}
}
