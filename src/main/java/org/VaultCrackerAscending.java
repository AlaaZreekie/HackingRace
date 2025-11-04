package org;

/**
 * Vault cracker using ascending linear search strategy.
 * Attempts passwords sequentially from lowest to highest (0 to 9999).
 * Most effective when the password is in the lower range.
 */
class VaultCrackerAscending extends VaultCracker {
    private static final String STRATEGY_NAME = "Ascending Cracker";
    private static final int START_PASSWORD = 0;
    private static final int END_PASSWORD = 9999;

    /**
     * Creates an ascending vault cracker.
     * @param vault the vault to crack
     */
    public VaultCrackerAscending(SecureVault vault) {
        super(vault);
        this.setName(STRATEGY_NAME);
    }

    /**
     * Executes the ascending search strategy.
     * Tries passwords from 0 to 9999 until finding the correct one or game ends.
     */
    @Override
    public void run() {
        for (int passwordGuess = START_PASSWORD; 
             passwordGuess <= END_PASSWORD && shouldContinue(); 
             passwordGuess++) {
            
            attemptCount++;
            GameState.getMonitor().addAscendingAttempt(passwordGuess);
            
            if (targetVault.isCorrectPassword(passwordGuess)) {
                GameState.getMonitor().reportWinner(this, passwordGuess);
                GameState.endGame();
                return;
            }
        }
    }
}
