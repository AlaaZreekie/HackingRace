package org;

abstract class HackerThread extends Thread {
    protected Vault vault;
    protected int attempts = 0;

    public HackerThread(Vault vault) {
        this.vault = vault;
        this.setPriority(Thread.MAX_PRIORITY);
    }
}

