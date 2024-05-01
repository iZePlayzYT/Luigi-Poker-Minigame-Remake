package de.izeplayz.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import de.izeplayz.api.ApiVariables.BACKGROUND_MUSIC_STATE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ApiFunctions {
	public static void loadSettings() {
		File settingsFile = ApiFunctions.getSettingsFile();
		if (!settingsFile.exists() || !settingsFile.isFile()) {
			settingsFile.getParentFile().mkdirs();
			saveSettings();
		} else {
			try {
				Scanner scanner = new Scanner(settingsFile);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (!line.isEmpty() && !line.startsWith("#")) {
						String setting = line.split("=", 2)[0];
						String value = line.split("=", 2)[1];

						switch (setting) {
						// SAVE-DATA
						case "coins":
							ApiVariables.coinAmount = Integer.valueOf(value);
							break;
						case "stars":
							ApiVariables.starAmount = Integer.valueOf(value);
							break;
						case "currentBetValue":
							ApiVariables.currentBetValue = Integer.valueOf(value);
							break;

						// SETTINGS
						case "difficulty":
							ApiVariables.difficulty = Integer.valueOf(value);
							break;
						case "scale":
							ApiVariables.scale = Double.valueOf(value);
							break;
						case "music":
							ApiVariables.music = Boolean.valueOf(value);
							break;
						case "language":
							ApiVariables.language = String.valueOf(value);
							break;

						// STATISTICS - GENERAL
						case "statsAmountGameStarted":
							ApiVariables.statsAmountGameStarted = Integer.valueOf(value);
							break;
						case "statsLastStarted":
							ApiVariables.statsLastStarted = String.valueOf(value);
							break;
						case "statsAmountRoundsPlayed":
							ApiVariables.statsAmountRoundsPlayed = Integer.valueOf(value);
							break;
						case "statsAmountRoundsWon":
							ApiVariables.statsAmountRoundsWon = Integer.valueOf(value);
							break;
						case "statsAmountRoundsLost":
							ApiVariables.statsAmountRoundsLost = Integer.valueOf(value);
							break;
						case "statsAmountRoundsDraw":
							ApiVariables.statsAmountRoundsDraw = Integer.valueOf(value);
							break;
						case "statsAmountGameOver":
							ApiVariables.statsAmountGameOver = Integer.valueOf(value);
							break;

						// STATISTICS - COINS
						case "statsAmountCoinsLost":
							ApiVariables.statsAmountCoinsLost = Integer.valueOf(value);
							break;
						case "statsAmountCoinsWon":
							ApiVariables.statsAmountCoinsWon = Integer.valueOf(value);
							break;
						case "statsAmountCoinsMax":
							ApiVariables.statsAmountCoinsMax = Integer.valueOf(value);
							break;

						// STATISTICS - STARS
						case "statsAmountStarsLost":
							ApiVariables.statsAmountStarsLost = Integer.valueOf(value);
							break;
						case "statsAmountStarsWon":
							ApiVariables.statsAmountStarsWon = Integer.valueOf(value);
							break;
						case "statsAmountStarsMax":
							ApiVariables.statsAmountStarsMax = Integer.valueOf(value);
							break;

						// STATISTICS - BETS
						case "statsAmountBet1Coin":
							ApiVariables.statsAmountBet1Coin = Integer.valueOf(value);
							break;
						case "statsAmountBet2Coins":
							ApiVariables.statsAmountBet2Coins = Integer.valueOf(value);
							break;
						case "statsAmountBet3Coins":
							ApiVariables.statsAmountBet3Coins = Integer.valueOf(value);
							break;
						case "statsAmountBet4Coins":
							ApiVariables.statsAmountBet4Coins = Integer.valueOf(value);
							break;
						case "statsAmountBet5Coins":
							ApiVariables.statsAmountBet5Coins = Integer.valueOf(value);
							break;

						// STATISTICS - PLAYER RESULTS
						case "statsAmountPlayer5Same":
							ApiVariables.statsAmountPlayer5Same = Integer.valueOf(value);
							break;
						case "statsAmountPlayer4Same":
							ApiVariables.statsAmountPlayer4Same = Integer.valueOf(value);
							break;
						case "statsAmountPlayerFullHouse":
							ApiVariables.statsAmountPlayerFullHouse = Integer.valueOf(value);
							break;
						case "statsAmountPlayer3Same":
							ApiVariables.statsAmountPlayer3Same = Integer.valueOf(value);
							break;
						case "statsAmountPlayer2Pairs":
							ApiVariables.statsAmountPlayer2Pairs = Integer.valueOf(value);
							break;
						case "statsAmountPlayer1Pair":
							ApiVariables.statsAmountPlayer1Pair = Integer.valueOf(value);
							break;
						case "statsAmountPlayerNothing":
							ApiVariables.statsAmountPlayerNothing = Integer.valueOf(value);
							break;

						// STATISTICS - ENEMY RESULTS
						case "statsAmountEnemy5Same":
							ApiVariables.statsAmountEnemy5Same = Integer.valueOf(value);
							break;
						case "statsAmountEnemy4Same":
							ApiVariables.statsAmountEnemy4Same = Integer.valueOf(value);
							break;
						case "statsAmountEnemyFullHouse":
							ApiVariables.statsAmountEnemyFullHouse = Integer.valueOf(value);
							break;
						case "statsAmountEnemy3Same":
							ApiVariables.statsAmountEnemy3Same = Integer.valueOf(value);
							break;
						case "statsAmountEnemy2Pairs":
							ApiVariables.statsAmountEnemy2Pairs = Integer.valueOf(value);
							break;
						case "statsAmountEnemy1Pair":
							ApiVariables.statsAmountEnemy1Pair = Integer.valueOf(value);
							break;
						case "statsAmountEnemyNothing":
							ApiVariables.statsAmountEnemyNothing = Integer.valueOf(value);
							break;
						default:
							ApiFunctions.showMsgBox("Invalid Setting \"" + setting + "\" found in Settings-File! Skipping...\n(" + ApiFunctions.getSettingsFile().getAbsolutePath() + ")", JOptionPane.WARNING_MESSAGE);
							break;
						}
					}
				}
				scanner.close();
				ApiVariables.statsLastStarted = ApiFunctions.getCurrentTimeAndDate();
				ApiVariables.statsAmountGameStarted++;
				if (ApiVariables.starAmount > ApiVariables.statsAmountStarsMax) {
					ApiVariables.statsAmountStarsMax = ApiVariables.starAmount;
				}
				if (ApiVariables.coinAmount > ApiVariables.statsAmountCoinsMax) {
					ApiVariables.statsAmountCoinsMax = ApiVariables.coinAmount;
				}
				saveSettings();
			} catch (Exception e) {
				showMsgBoxError("SETTINGS COULD NOT BE LOADED (Corrupted Settings-File)!\nResetting and creating new Settings-File...!\n(" + ApiFunctions.getSettingsFile().getAbsolutePath() + ")", e);
				saveSettings();
			}
		}
	}

	public static void saveSettings() {
		try {
			File settingsFile = ApiFunctions.getSettingsFile();
			FileWriter writer = new FileWriter(settingsFile);

			writer.write("# [SAVEGAME]:" + "\n");
			writer.write("coins" + "=" + String.valueOf(ApiVariables.coinAmount) + "\n");
			writer.write("stars" + "=" + String.valueOf(ApiVariables.starAmount) + "\n");
			writer.write("currentBetValue" + "=" + String.valueOf(ApiVariables.currentBetValue) + "\n");
			writer.write("\n");
			writer.write("\n");
			writer.write("# [SETTINGS]:" + "\n");
			writer.write("difficulty" + "=" + String.valueOf(ApiVariables.difficulty) + "\n");
			writer.write("scale" + "=" + String.valueOf(ApiVariables.scale) + "\n");
			writer.write("music" + "=" + String.valueOf(ApiVariables.music) + "\n");
			writer.write("language" + "=" + String.valueOf(ApiVariables.language) + "\n");
			writer.write("\n");
			writer.write("\n");
			writer.write("# [STATISTICS - GENERAL]:" + "\n");
			writer.write("statsAmountGameStarted" + "=" + String.valueOf(ApiVariables.statsAmountGameStarted) + "\n");
			writer.write("statsLastStarted" + "=" + String.valueOf(ApiVariables.statsLastStarted) + "\n");
			writer.write("statsAmountRoundsPlayed" + "=" + String.valueOf(ApiVariables.statsAmountRoundsPlayed) + "\n");
			writer.write("statsAmountRoundsWon" + "=" + String.valueOf(ApiVariables.statsAmountRoundsWon) + "\n");
			writer.write("statsAmountRoundsLost" + "=" + String.valueOf(ApiVariables.statsAmountRoundsLost) + "\n");
			writer.write("statsAmountRoundsDraw" + "=" + String.valueOf(ApiVariables.statsAmountRoundsDraw) + "\n");
			writer.write("statsAmountGameOver" + "=" + String.valueOf(ApiVariables.statsAmountGameOver) + "\n");
			writer.write("\n");
			writer.write("# [STATISTICS - COINS]:" + "\n");
			writer.write("statsAmountCoinsLost" + "=" + String.valueOf(ApiVariables.statsAmountCoinsLost) + "\n");
			writer.write("statsAmountCoinsWon" + "=" + String.valueOf(ApiVariables.statsAmountCoinsWon) + "\n");
			writer.write("statsAmountCoinsMax" + "=" + String.valueOf(ApiVariables.statsAmountCoinsMax) + "\n");
			writer.write("\n");
			writer.write("# [STATISTICS - STARS]:" + "\n");
			writer.write("statsAmountStarsLost" + "=" + String.valueOf(ApiVariables.statsAmountStarsLost) + "\n");
			writer.write("statsAmountStarsWon" + "=" + String.valueOf(ApiVariables.statsAmountStarsWon) + "\n");
			writer.write("statsAmountStarsMax" + "=" + String.valueOf(ApiVariables.statsAmountStarsMax) + "\n");
			writer.write("\n");
			writer.write("# [STATISTICS - BETS]:" + "\n");
			writer.write("statsAmountBet1Coin" + "=" + String.valueOf(ApiVariables.statsAmountBet1Coin) + "\n");
			writer.write("statsAmountBet2Coins" + "=" + String.valueOf(ApiVariables.statsAmountBet2Coins) + "\n");
			writer.write("statsAmountBet3Coins" + "=" + String.valueOf(ApiVariables.statsAmountBet3Coins) + "\n");
			writer.write("statsAmountBet4Coins" + "=" + String.valueOf(ApiVariables.statsAmountBet4Coins) + "\n");
			writer.write("statsAmountBet5Coins" + "=" + String.valueOf(ApiVariables.statsAmountBet5Coins) + "\n");
			writer.write("\n");
			writer.write("# [STATISTICS - PLAYER RESULTS]:" + "\n");
			writer.write("statsAmountPlayer5Same" + "=" + String.valueOf(ApiVariables.statsAmountPlayer5Same) + "\n");
			writer.write("statsAmountPlayer4Same" + "=" + String.valueOf(ApiVariables.statsAmountPlayer4Same) + "\n");
			writer.write("statsAmountPlayerFullHouse" + "=" + String.valueOf(ApiVariables.statsAmountPlayerFullHouse) + "\n");
			writer.write("statsAmountPlayer3Same" + "=" + String.valueOf(ApiVariables.statsAmountPlayer3Same) + "\n");
			writer.write("statsAmountPlayer2Pairs" + "=" + String.valueOf(ApiVariables.statsAmountPlayer2Pairs) + "\n");
			writer.write("statsAmountPlayer1Pair" + "=" + String.valueOf(ApiVariables.statsAmountPlayer1Pair) + "\n");
			writer.write("statsAmountPlayerNothing" + "=" + String.valueOf(ApiVariables.statsAmountPlayerNothing) + "\n");
			writer.write("\n");
			writer.write("# [STATISTICS - ENEMY RESULTS]:" + "\n");
			writer.write("statsAmountEnemy5Same" + "=" + String.valueOf(ApiVariables.statsAmountEnemy5Same) + "\n");
			writer.write("statsAmountEnemy4Same" + "=" + String.valueOf(ApiVariables.statsAmountEnemy4Same) + "\n");
			writer.write("statsAmountEnemyFullHouse" + "=" + String.valueOf(ApiVariables.statsAmountEnemyFullHouse) + "\n");
			writer.write("statsAmountEnemy3Same" + "=" + String.valueOf(ApiVariables.statsAmountEnemy3Same) + "\n");
			writer.write("statsAmountEnemy2Pairs" + "=" + String.valueOf(ApiVariables.statsAmountEnemy2Pairs) + "\n");
			writer.write("statsAmountEnemy1Pair" + "=" + String.valueOf(ApiVariables.statsAmountEnemy1Pair) + "\n");
			writer.write("statsAmountEnemyNothing" + "=" + String.valueOf(ApiVariables.statsAmountEnemyNothing) + "\n");
			writer.close();
		} catch (IOException e) {
			showMsgBoxError("SETTINGS COULD NOT BE SAVED!\n(Settings-File is currently opened/loaded/accessed in/by an other program!)\n(" + ApiFunctions.getSettingsFile().getAbsolutePath() + ")", e);
		}
	}

	public static int scale(int value) {
		return (int) ((double) value * ApiVariables.scale);
	}

	private static Font font = null;

	public static Font getFont(Float size) {
		if (font == null) {
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, getResourcesFile("program/font_polygon_party.ttf"));
			} catch (Exception e) {
				font = new JLabel().getFont();
			}
		}
		return font.deriveFont(size);
	}

	public static Font getFontVerySmall() {
		return getFont((float) ApiFunctions.scale(6));
	}

	public static Font getFontSmall() {
		return getFont((float) ApiFunctions.scale(12));
	}

	public static Font getFontMedium() {
		return getFont((float) ApiFunctions.scale(18));
	}

	public static Font getFontBig() {
		return getFont((float) ApiFunctions.scale(24));
	}

	public static Font getFontVeryBig() {
		return getFont((float) ApiFunctions.scale(30));
	}

	public static Font getFontHuge() {
		return getFont((float) ApiFunctions.scale(36));
	}

	public static Font getFontVeryHuge() {
		return getFont((float) ApiFunctions.scale(42));
	}

	public static Font getFontGiant() {
		return getFont((float) ApiFunctions.scale(48));
	}

	public static Font getFontVeryGiant() {
		return getFont((float) ApiFunctions.scale(54));
	}

	public static void toggleMusic(boolean on) {
		if (ApiVariables.backgroundMusicLoaded == BACKGROUND_MUSIC_STATE.NOT_LOADED) {
			setupBackgroundMusic();
		}

		if (ApiVariables.backgroundMusicLoaded == BACKGROUND_MUSIC_STATE.LOADED_SUCCESS) {
			if (on == true) {
				startBackgroundMusic();
			} else {
				stopBackgroundMusic();
			}
		} else if (ApiVariables.backgroundMusicLoaded == BACKGROUND_MUSIC_STATE.LOADED_ERROR) {
			if (on == true) {
				ApiFunctions.showMsgBox("An error occured while starting the background music!\nThis is no critical error, the program will still work normally without any restrictions!", JOptionPane.WARNING_MESSAGE);
			} else {
				ApiFunctions.showMsgBox("An error occured while stopping the background music!\nThis is no critical error, the program will still work normally without any restrictions!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			ApiFunctions.crash("UNKNOWN ERROR (API.toggleMusic)!");
		}
	}

	public static void startBackgroundMusic() {
		if (ApiVariables.backgroundMusicLoaded == BACKGROUND_MUSIC_STATE.LOADED_SUCCESS) {
			if (ApiVariables.backgroundMusicClip.isRunning() == true) {
				stopBackgroundMusic();
			}

			FloatControl gainControl = (FloatControl) ApiVariables.backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gainControl.getMaximum());
			ApiVariables.backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public static void stopBackgroundMusic() {
		if (ApiVariables.backgroundMusicLoaded == BACKGROUND_MUSIC_STATE.LOADED_SUCCESS) {
			ApiVariables.backgroundMusicClip.stop();
		}
	}

	public static void setupBackgroundMusic() {
		try {
			File downloadTarget = new File(System.getenv("APPDATA") + File.separator + ApiVariables.folderName + File.separator + ApiVariables.backgroundMusicFilename);
			boolean correctAudioFile = ApiFunctions.checkAndFixBackgroundMusicFile(downloadTarget);
			if (correctAudioFile == true) {
				ApiVariables.backgroundMusicClip = AudioSystem.getClip();
				ApiVariables.backgroundMusicClip.open(AudioSystem.getAudioInputStream(downloadTarget));
				ApiVariables.backgroundMusicLoaded = BACKGROUND_MUSIC_STATE.LOADED_SUCCESS;
			} else {
				ApiVariables.backgroundMusicLoaded = BACKGROUND_MUSIC_STATE.LOADED_ERROR;
				ApiFunctions.showMsgBox("An error occured while loading the background music!\nThis is no critical error, the program will still work normally without any restrictions! (Error Type 1)", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			ApiVariables.backgroundMusicLoaded = BACKGROUND_MUSIC_STATE.LOADED_ERROR;
			ApiFunctions.showMsgBox("An error occured while loading the background music!\nThis is no critical error, the program will still work normally without any restrictions! (Error Type 2)", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static String getCurrentTimeAndDate() {
		return getTimeAndDate(System.currentTimeMillis());
	}

	public static String getTimeAndDate(long currentTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd__HH-mm-ss-SSS");
		Date resultdate = new Date(currentTimeMillis);
		return sdf.format(resultdate);
	}

	public static int getRandomNumber(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}

	public static Color getColorRGBA(int red, int green, int blue, int alpha) {
		return new Color(red, green, blue, alpha);
	}

	public static BufferedImage resizeImage(BufferedImage in, int w, int h) {
		BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		outputImage.getGraphics().drawImage(in.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
		return outputImage;
	}

	public static File getSettingsFile() {
		return new File(System.getenv("APPDATA") + File.separator + ApiVariables.folderName + File.separator + ApiVariables.settingsFileName);
	}

	public static InputStream getResourcesFile(String name) {
		return ApiFunctions.class.getClassLoader().getResourceAsStream("de/izeplayz/res/" + name);
	}

	public static String getDirectDownloadLinkMediafireZip(String url, String fileName) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("aria-label=\"Download file\"")) {
					String input = inputLine.split(" href=\"", 2)[1].split("\" ", 2)[0];
					if (fileName.endsWith(fileName)) {
						in.close();
						return input;
					}
				}
			}
			in.close();
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFileChecksumSHA256(File file) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			FileInputStream fis = new FileInputStream(file);
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
			fis.close();
			byte[] bytes = digest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	public static void showMsgBox(String msg, int icon) {
		if (icon == JOptionPane.PLAIN_MESSAGE || icon == JOptionPane.INFORMATION_MESSAGE) {
			JOptionPane.showMessageDialog(null, msg, ApiVariables.titleComplete, icon);
		} else {
			JOptionPane.showMessageDialog(null, msg + "\n\n(Contact " + ApiVariables.discord + " on Discord for help)", ApiVariables.titleComplete, icon);
		}
	}

	public static boolean checkAndFixBackgroundMusicFile(File downloadTarget) {
		if (!downloadTarget.getParentFile().exists()) {
			downloadTarget.getParentFile().mkdirs();
		}

		if (downloadTarget.exists() && downloadTarget.isFile()) {
			if (ApiFunctions.getFileChecksumSHA256(downloadTarget).equals(ApiVariables.backgroundMusicHash)) {
				return true;
			}
		}

		// String directDownloadLink = getDirectDownloadLinkMediafireZip(ApiVariables.backgroundMusicLink, ApiVariables.backgroundMusicFilename);
		String directDownloadLink = ApiVariables.backgroundMusicLink;
		if (directDownloadLink != null) {
			try (BufferedInputStream in = new BufferedInputStream(new URL(directDownloadLink).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(downloadTarget)) {
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					fileOutputStream.write(dataBuffer, 0, bytesRead);
				}

				if (ApiFunctions.getFileChecksumSHA256(downloadTarget).equals(ApiVariables.backgroundMusicHash)) {
					return true;
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	public static void showMsgBoxError(String msg, Exception e) {
		String exceptionAsString = "";
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			sw.close();
		} catch (Exception e2) {
			exceptionAsString = e.toString();
		}
		showMsgBox(msg + "\n\n" + exceptionAsString, JOptionPane.ERROR_MESSAGE);
	}

	public static void showMsgBoxUnknownError(Exception e, boolean crash) {
		String exceptionAsString = "";
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			sw.close();
		} catch (Exception e2) {
			exceptionAsString = e.toString();
		}
		showMsgBox("AN UNKNOWN ERROR OCCURED!:\n\n" + exceptionAsString, JOptionPane.ERROR_MESSAGE);
		if (crash) {
			exit();
		}
	}

	public static void crash(String msg) {
		showMsgBox("AN CRITICAL ERROR OCCURED:\n\n" + msg, JOptionPane.ERROR_MESSAGE);
		exit();
	}

	public static void exit() {
		System.exit(0);
	}
}
