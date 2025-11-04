package org;

import static org.GameState.*;

/**
 * Vault cracker using binary search strategy.
 * Highly efficient algorithm that repeatedly divides the search interval in half.
 * Almost always wins the race due to its logarithmic time complexity.
 */
class VaultCrackerBinary extends VaultCracker {
    private static final String STRATEGY_NAME = "Binary Search Cracker";

    /**
     * Creates a binary search vault cracker.
     * @param vault the vault to crack
     */
    public VaultCrackerBinary(SecureVault vault) {
        super(vault);
        this.setName(STRATEGY_NAME);
    }

    /**
     * Executes the binary search strategy.
     * Narrows down the password range until the correct one is found or game ends.
     */
    @Override
    public void run() {
        int[] range = SecureVault.getPasswordRange();
        int low = range[0];
        int high = range[1];

        while (low <= high && shouldContinue()) {
            int mid = low + (high - low) / 2; // Avoids potential overflow
            attemptCount++;
            GameState.getMonitor().addBinaryAttempt(mid);

            int comparison = targetVault.compareToPassword(mid);

            if (comparison == 0) {
                GameState.getMonitor().reportWinner(this, mid);
                GameState.endGame();
                return;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
    }
}
