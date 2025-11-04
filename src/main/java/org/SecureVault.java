package org;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a secure vault with a numeric password.
 * Simulates authentication delays for realistic cracking scenarios.
 */
public class SecureVault {
    private static final int MIN_PASSWORD = 0;
    private static final int MAX_PASSWORD = 9999;
    private static final long AUTHENTICATION_DELAY_MS = 5;
    
    private final int secretPassword;

    /**
     * Creates a vault with a random password between 0 and 9999.
     */
    public SecureVault() {
        this.secretPassword = ThreadLocalRandom.current().nextInt(MIN_PASSWORD, MAX_PASSWORD + 1);
    }

    /**
     * Creates a vault with a specific password.
     * @param password the password to secure the vault with
     * @throws IllegalArgumentException if password is outside valid range
     */
    public SecureVault(int password) {
        if (password < MIN_PASSWORD || password > MAX_PASSWORD) {
            throw new IllegalArgumentException(
                "Password must be between " + MIN_PASSWORD + " and " + MAX_PASSWORD
            );
        }
        this.secretPassword = password;
    }

    /**
     * Compares a guess to the actual password.
     * Includes simulated authentication delay.
     * 
     * @param guess the password guess to compare
     * @return negative if guess < password, 0 if equal, positive if guess > password
     * @throws RuntimeException if the authentication process is interrupted
     */
    public int compareToPassword(int guess) {
        simulateAuthenticationDelay();
        return Integer.compare(guess, secretPassword);
    }

    /**
     * Verifies if a guess matches the vault password.
     * Includes simulated authentication delay.
     * 
     * @param guess the password guess to verify
     * @return true if guess matches the password, false otherwise
     * @throws RuntimeException if the authentication process is interrupted
     */
    public boolean isCorrectPassword(int guess) {
        simulateAuthenticationDelay();
        return guess == secretPassword;
    }

    /**
     * Simulates the time delay of password verification.
     * @throws RuntimeException if interrupted during authentication
     */
    private void simulateAuthenticationDelay() {
        try {
            Thread.sleep(AUTHENTICATION_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Authentication process interrupted", e);
        }
    }

    /**
     * Gets the valid password range.
     * @return array with [min, max] password values
     */
    public static int[] getPasswordRange() {
        return new int[]{MIN_PASSWORD, MAX_PASSWORD};
    }
}
