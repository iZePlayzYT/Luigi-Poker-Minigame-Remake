package de.izeplayz.api;

import javax.sound.sampled.Clip;

public class ApiVariables {
	// ENUMERATIONS
	public static enum BACKGROUND_MUSIC_STATE {
		NOT_LOADED, LOADED_SUCCESS, LOADED_ERROR
	}

	public static enum CARD_VALUE {
		DEFAULT, STAR, MARIO, LUIGI, FIREFLOWER, MUSHROOM, CLOUD
	}

	public static enum RESULT_TYPE {
		RESULT_5SAME, RESULT_4SAME, RESULT_FULLHOUSE, RESULT_3SAME, RESULT_2PAIRS, RESULT_1PAIR, RESULT_NOTHING
	}

	public static enum GAME_STATE {
		ROUND_BETTING, ROUND_PLAYING, ROUND_FINISHED
	}

	// BASIC
	public static String titlePart1 = "SM64DS/NSMBDS LUIGI-POKER-MINIGAME";
	public static String titlePart2 = "VERSION 1.1 (DEVELOPED BY IZEPLAYZ)";
	public static String titleComplete = titlePart1 + " " + titlePart2;
	public static String folderName = "SM64DS-NSMBDS_LUIGI-POKER-MINIGAME";
	public static String settingsFileName = "data.save";
	public static String discord = "iZePlayz#8403";

	// BACKGROUND MUSIC
	public static String backgroundMusicHash = "B4C0601956D8784B8D37CDD4F272B2C7139F55AEDEB396C651E0A4C2B67E9788";
	public static String backgroundMusicLink = "https://www.dropbox.com/scl/fi/s66f9rd6xgnihu6ondfz5/sm64ds_nsmbds_luigi_poker_music.wav?rlkey=78gebn47hvm2nx5igpfid5cz9&st=ydm7woqg&dl=1";
	public static String backgroundMusicFilename = "sm64ds_nsmbds_luigi_poker_music.wav";
	public static BACKGROUND_MUSIC_STATE backgroundMusicLoaded = BACKGROUND_MUSIC_STATE.NOT_LOADED;
	public static Clip backgroundMusicClip = null;

	// CARD-VALUE MULTIPLICATORS
	public static int multiplicator5x = 100000000;
	public static int multiplicator4x = 10000000;
	public static int multiplicatorFH = 1000000;
	public static int multiplicator3x = 100000;
	public static int multiplicator2x2x = 1000;
	public static int multiplicator2x = 100;
	
	// GAME
	public static int coinAmount = 10;
	public static int starAmount = 0;
	public static int currentBetValue = 1;
	
	// SETTINGS
	public static int difficulty = 3;
	public static double scale = 1;
	public static boolean music = true;
	public static String language = "EN";
	
	// STATISTIC
	public static int statsAmountGameStarted = 0;
	public static int statsAmountRoundsPlayed = 0;
	public static int statsAmountRoundsWon = 0;
	public static int statsAmountRoundsLost = 0;
	public static int statsAmountRoundsDraw = 0;
	public static int statsAmountCoinsLost = 0;
	public static int statsAmountCoinsWon = 0;
	public static int statsAmountCoinsMax = 0;
	public static int statsAmountStarsWon = 0;
	public static int statsAmountStarsLost = 0;
	public static int statsAmountStarsMax = 0;
	public static int statsAmountGameOver = 0;
	public static String statsLastStarted = ApiFunctions.getCurrentTimeAndDate();

	public static int statsAmountBet1Coin = 0;
	public static int statsAmountBet2Coins = 0;
	public static int statsAmountBet3Coins = 0;
	public static int statsAmountBet4Coins = 0;
	public static int statsAmountBet5Coins = 0;

	public static int statsAmountPlayer5Same = 0;
	public static int statsAmountPlayer4Same = 0;
	public static int statsAmountPlayerFullHouse = 0;
	public static int statsAmountPlayer3Same = 0;
	public static int statsAmountPlayer2Pairs = 0;
	public static int statsAmountPlayer1Pair = 0;
	public static int statsAmountPlayerNothing = 0;

	public static int statsAmountEnemy5Same = 0;
	public static int statsAmountEnemy4Same = 0;
	public static int statsAmountEnemyFullHouse = 0;
	public static int statsAmountEnemy3Same = 0;
	public static int statsAmountEnemy2Pairs = 0;
	public static int statsAmountEnemy1Pair = 0;
	public static int statsAmountEnemyNothing = 0;
}
