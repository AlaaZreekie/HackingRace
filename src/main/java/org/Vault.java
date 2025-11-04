package org;

import java.util.Random;

public class Vault {
    private final int password;

    public Vault() {
        Random random = new Random();
        this.password = random.nextInt(10000);
    }
    public Vault(int _password){
        this.password = _password;
    }

    public int compareToPassword(int guess) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing interrupted", e);
        }
        return Integer.compare(guess, password);
    }

    public boolean isCorrectPassword(int guess)
    {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing interrupted", e);
        }
        return guess == password;
    }
}
