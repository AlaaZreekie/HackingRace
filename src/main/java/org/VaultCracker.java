package org;

/**
 * Abstract base class for vault cracking threads.
 * Implements different password guessing strategies to crack the vault.
 */
abstract class VaultCracker extends Thread {
    protected static final int HIGH_PRIORITY = Thread.MAX_PRIORITY;
    
    protected final SecureVault targetVault;
    protected int attemptCount;

    /**
     * Creates a new vault cracker thread.
     * @param vault the vault to attempt to crack
     */
    public VaultCracker(SecureVault vault) {
        if (vault == null) {
            throw new IllegalArgumentException("Vault cannot be null");
        }
        this.targetVault = vault;
        this.attemptCount = 0;
        this.setPriority(HIGH_PRIORITY);
    }

    /**
     * Gets the current number of password attempts made.
     * @return the number of attempts
     */
    public int getAttemptCount() {
        return attemptCount;
    }

    /**
     * Checks if the cracking process should continue.
     * @return true if game is still active, false otherwise
     */
    protected boolean shouldContinue() {
        return !GameState.isGameOver();
    }


    /**
     * Implements the specific cracking strategy.
     * Must be overridden by subclasses.
     */
    @Override
    public abstract void run();
}
