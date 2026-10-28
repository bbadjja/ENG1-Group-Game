package com.bbadjja.ENG1;

public class GameLogic {
    public static boolean isPaused = true;

    public static void togglePause() {
        isPaused = !isPaused;
        // Add calls to other Classes with pause / onPause methods etc?
    }

    public static void update(GameScreen screen, float delta) {
        if (!isPaused){
            screen.player.move(delta);
        }
    }
}
