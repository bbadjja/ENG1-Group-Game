package com.bbadjja.ENG1;

public class GameLogic {
    private static final int FIVE_MINUTES = 5 * 60 * 1000;

    public static boolean isPaused = false;
    public static int score = 0;

    // All times in milliseconds for consistency, except displayTime
    public static long startTime;
    private static int timeRemaining = FIVE_MINUTES;
    private static long pauseStart;
    private static long timePaused = 0;

    public static String displayTime = "5:00";

    public static void togglePause() {
        isPaused = !isPaused;
        // Add calls to other Classes with pause / onPause methods etc?
        if (!isPaused) {
            timePaused += System.currentTimeMillis() - pauseStart;
        } else {
            pauseStart = System.currentTimeMillis();
        }
    }

    public static void update(GameScreen screen, float delta) {
        if (!isPaused){
            screen.player.move(delta);

            if (timeRemaining <= 0) {
                endGame(false);
            } else {
                long currentTime = System.currentTimeMillis();
                long timeElapsed = (currentTime - startTime);
                timeRemaining = FIVE_MINUTES - (int) (timeElapsed - timePaused);
                displayTime = getDisplayTime(timeRemaining);
            }
        }

        // Centers Camera on player
        float playerX = screen.player.sprite.getX();
        float playerY = screen.player.sprite.getY();
        float offsetX = screen.player.sprite.getWidth()/2;
        float offsetY = screen.player.sprite.getHeight()/2;
        screen.game.viewport.getCamera().position.set(playerX+offsetX,playerY+offsetY,0);
    }

    public static String getDisplayTime(long milliseconds) {
        int seconds = (int) Math.round(milliseconds/1000.0);
        int minutes = seconds / 60;
        seconds -= minutes * 60;

        String splitter = ":";
        if (seconds < 10) {
            splitter += "0";
        }

        return minutes + splitter + seconds;
    }

    public static void endGame(boolean win) {
        String runTime = getDisplayTime(FIVE_MINUTES - timeRemaining);

        // Temporary - need permanent way to show end of game
        // E.g. switch screen to screen that just says you lose/win
        GameLogic.togglePause();
    }
}
