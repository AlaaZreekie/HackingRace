package org;

import static org.GameState.*;

/**
 * Represents the police timer, acting as the antagonist in the simulation.
 * Counts down from a set time, ending the game if no cracker succeeds.
 */
class PoliceThread extends Thread {
    private static final int COUNTDOWN_SECONDS = 10;
    private static final long SLEEP_INTERVAL_MS = 1000;

    /**
     * Creates a new police timer thread.
     */
    public PoliceThread() {
        this.setName("Police Timer");
    }

    /**
     * Runs the police countdown timer.
     * Ends the game if time runs out before a cracker finds the password.
     */
    @Override
    public void run() {
        for (int i = COUNTDOWN_SECONDS; i >= 0 && !isGameOver(); i--) {
            updateTimerDisplay(i);
            try {
                Thread.sleep(SLEEP_INTERVAL_MS);
            } catch (InterruptedException e) {
                // Game ended by a cracker, so just exit
                return;
            }
        }

        // If loop finishes and game is not over, police win
        if (!isGameOver()) {
            getMonitor().gameOver();
            endGame();
        }
    }

    /**
     * Updates the UI with the current countdown time.
     * @param secondsRemaining the time left on the clock
     */
    private void updateTimerDisplay(int secondsRemaining) {
        getMonitor().updatePoliceTime(secondsRemaining);
    }
}
