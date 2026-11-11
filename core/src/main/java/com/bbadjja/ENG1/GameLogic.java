package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class GameLogic {
    private static final int FIVE_MINUTES = 5 * 60 * 1000;

    public static boolean isPaused = false;
    public static int score = 0;
    public static int itemsUsed = 0;
    public static int eventsTriggered = 0;

    // All times in milliseconds for consistency, except displayTime
    public static long startTime;
    private static int timeRemaining = FIVE_MINUTES;
    private static long pauseStart;
    private static long timePaused = 0;

    public static String displayTime = "5:00";
    public static ArrayList<Event> currentEvents = new ArrayList<>();

    public static boolean gameOver = false;

    /**
     * Toggles the state of game execution between paused and unpaused
     */
    public static void togglePause() {
        if (gameOver) {
            return;
        }

        isPaused = !isPaused;
        if (!isPaused) {
            timePaused += System.currentTimeMillis() - pauseStart;
        } else {
            pauseStart = System.currentTimeMillis();
        }
    }

    /**
     * Handles any event which should happen every frame
     *
     * @param screen the screen containing all game content
     * @param delta the time in seconds since the previous frame
     */
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

    public static void handleCollisions(GameScreen screen) {
        if (!isPaused) {
            for (Event event : Event.eventList) {
                if (event.sprite != null) {
                    Sprite sprite = event.sprite;
                    Rectangle eventHitbox = event.triggerArea;
                    Rectangle playerHitbox;
                    if (event instanceof Puddle || event instanceof Mud) {
                        playerHitbox = screen.player.feetHitbox;
                    } else {
                        playerHitbox = screen.player.hitbox;
                    }

                    if (eventHitbox.overlaps(playerHitbox)) {
                        if (!currentEvents.contains(event)) {
                            event.onPlayerEnter(screen.player);
                            currentEvents.add(event);
                        }
                    } else if (currentEvents.contains(event)) {
                        event.onPlayerExit(screen.player);
                        currentEvents.remove(event);
                    }
                }
            }

            for (Goose goose : Goose.gooseList) {
                // Can replace with goose.hitbox later
                if (goose.sprite.getBoundingRectangle().overlaps(screen.player.hitbox)) {
                    goose.onPlayerCollision(screen.player);
                }
            }

            // TODO might need to change this depending on how Item class works
            for (Item item : Item.itemList) {
                if (item.sprite.getBoundingRectangle().overlaps(screen.player.hitbox)) {
                    screen.player.pickupItem(item);
                }
            }
        }
    }

    /**
     * Convenience method to translate time into the format M:SS
     *
     * @param milliseconds the time in milliseconds
     * @return the time in the format M:SS | M = Minutes and S = Seconds
     */
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

    /**
     * Handles ending the game
     *
     * @param win true if the player won the game, else false
     */
    public static void endGame(boolean win) {
        String runTime = getDisplayTime(FIVE_MINUTES - timeRemaining);
        GameLogic.togglePause();
        gameOver = true;
    }
}
