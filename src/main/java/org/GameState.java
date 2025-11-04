package org;

/**
 * Manages the shared state of the vault cracking competition.
 * Provides thread-safe access to game status and UI monitor.
 */
class GameState {
    private static CrackingMonitor monitorInstance;
    private static volatile boolean isGameOver = false;

    /**
     * Gets the display monitor for updating UI.
     * @return the CrackingMonitor instance
     */
    public static CrackingMonitor getMonitor() {
        return monitorInstance;
    }

    /**
     * Sets the display monitor for the game.
     * @param monitor the CrackingMonitor to use
     */
    public static void setMonitor(CrackingMonitor monitor) {
        monitorInstance = monitor;
    }

    /**
     * Checks if the game has ended.
     * @return true if game is over, false otherwise
     */
    public static boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Marks the game as finished.
     */
    public static void endGame() {
        isGameOver = true;
    }

    /**
     * Resets the game state for a new round.
     */
    public static void reset() {
        isGameOver = false;
        monitorInstance = null;
    }
}
