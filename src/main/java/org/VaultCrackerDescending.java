package org;

/**
 * Vault cracker using descending linear search strategy.
 * Attempts passwords sequentially from highest to lowest (9999 to 0).
 * Most effective when the password is in the upper range.
 */
class VaultCrackerDescending extends VaultCracker {
    private static final String STRATEGY_NAME = "Descending Cracker";
    private static final int START_PASSWORD = 9999;
    private static final int END_PASSWORD = 0;

    /**
     * Creates a descending vault cracker.
     * @param vault the vault to crack
     */
    public VaultCrackerDescending(SecureVault vault) {
        super(vault);
        this.setName(STRATEGY_NAME);
    }

    /**
     * Executes the descending search strategy.
     * Tries passwords from 9999 down to 0 until finding the correct one or game ends.
     */
    @Override
    public void run() {
        for (int passwordGuess = START_PASSWORD; 
             passwordGuess >= END_PASSWORD && shouldContinue(); 
             passwordGuess--) {
            
            attemptCount++;
            GameState.getMonitor().addDescendingAttempt(passwordGuess);
            
            if (targetVault.isCorrectPassword(passwordGuess)) {
                GameState.getMonitor().reportWinner(this, passwordGuess);
                GameState.endGame();
                return;
            }
        }
    }
}
