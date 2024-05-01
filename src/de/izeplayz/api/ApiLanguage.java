package de.izeplayz.api;

public class ApiLanguage {

	public static enum LANGUAGE {
		ENGLISH, GERMAN
	}

	public static LANGUAGE getLanguage(String languageCode) {
		if (languageCode.equalsIgnoreCase("EN")) {
			return LANGUAGE.ENGLISH;
		} else if (languageCode.equalsIgnoreCase("DE")) {
			return LANGUAGE.GERMAN;
		} else {
			ApiFunctions.crash("INVALID LANGUAGE CODE!");
			return null;
		}
	}

	public static String getText(String key) {
		LANGUAGE language = null;
		if (ApiVariables.language.equalsIgnoreCase("EN")) {
			language = LANGUAGE.ENGLISH;
		} else if (ApiVariables.language.equalsIgnoreCase("DE")) {
			language = LANGUAGE.GERMAN;
		} else {
			ApiFunctions.crash("INVALID LANGUAGE CODE!");
			return "";
		}

		return getText(key, language);
	}

	public static String getText(String key, LANGUAGE language) {
		switch (key) {
		case "start":
			return language == LANGUAGE.ENGLISH ? "START" : "STARTEN";
		case "hold":
			return language == LANGUAGE.ENGLISH ? "HOLD" : "HALTEN";
		case "swap":
			return language == LANGUAGE.ENGLISH ? "SWAP" : "TAUSCHEN";
		case "betmore":
			return language == LANGUAGE.ENGLISH ? "BET MORE" : "EINSATZ +";
		case "betless":
			return language == LANGUAGE.ENGLISH ? "BET LESS" : "EINSATZ -";
		case "scale":
			return language == LANGUAGE.ENGLISH ? "Scale" : "Skalierung";
		case "language":
			return language == LANGUAGE.ENGLISH ? "Language: English" : "Sprache: Deutsch";
		case "music":
			return language == LANGUAGE.ENGLISH ? "Music" : "Musik";
		case "on":
			return language == LANGUAGE.ENGLISH ? "ON" : "AN";
		case "off":
			return language == LANGUAGE.ENGLISH ? "OFF" : "AUS";
		case "difficulty":
			return language == LANGUAGE.ENGLISH ? "Difficulty" : "Schwierigkeit";
		case "gameover":
			return language == LANGUAGE.ENGLISH ? "GAME OVER!" : "GAME OVER!";
		case "lost":
			return language == LANGUAGE.ENGLISH ? "YOU LOST!" : "VERLOREN!";
		case "won":
			return language == LANGUAGE.ENGLISH ? "YOU WON!" : "GEWONNEN!";
		case "draw":
			return language == LANGUAGE.ENGLISH ? "IT'S A DRAW!" : "UNENTSCHIEDEN!";
		case "next":
			return language == LANGUAGE.ENGLISH ? "NEXT" : "WEITER";
		case "5same":
			return language == LANGUAGE.ENGLISH ? "JACKPOT! 5 OF A KIND!" : "JACKPOT! 5 GLEICHE!";
		case "4same":
			return language == LANGUAGE.ENGLISH ? "4 OF A KIND!" : "4 GLEICHE";
		case "fullhouse":
			return language == LANGUAGE.ENGLISH ? "FULL HOUSE!" : "FULL HOUSE!";
		case "3same":
			return language == LANGUAGE.ENGLISH ? "3 OF A KIND!" : "3 GLEICHE!";
		case "2pairs":
			return language == LANGUAGE.ENGLISH ? "2 PAIRS!" : "2 PAARE!";
		case "1pair":
			return language == LANGUAGE.ENGLISH ? "1 PAIR!" : "1 PAAR!";
		case "nothing":
			return language == LANGUAGE.ENGLISH ? "NOTHING!" : "NICHTS!";
		case "popupsave":
			return language == LANGUAGE.ENGLISH ? "Save" : "Speichern";
		case "popupsaverestart":
			return language == LANGUAGE.ENGLISH ? "Save and restart program" : "Speichern und Program neustarten";
		case "popupcancel":
			return language == LANGUAGE.ENGLISH ? "Cancel" : "Abbrechen";
		case "popupscaletitle":
			return language == LANGUAGE.ENGLISH ? "Change Scale" : "Skalierung ändern";
		case "popupscalelabel":
			return language == LANGUAGE.ENGLISH ? "New Scale:" : "Neue Skalierung:";
		case "popupdifficultytitle":
			return language == LANGUAGE.ENGLISH ? "Change Difficulty" : "Schwierigkeit ändern";
		case "popupdifficultylabel":
			return language == LANGUAGE.ENGLISH ? "New Difficulty:" : "Neue Schwierigkeit:";
		default:
			ApiFunctions.crash("INVALID LANGUAGE KEY!");
			return "";
		}
	}
}
