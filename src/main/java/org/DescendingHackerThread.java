package org;

class DescendingHackerThread extends HackerThread {
    public DescendingHackerThread(Vault vault) { super(vault); }
    @Override
    public void run() {
        for (int guess = 9999; guess >= 0 && !Shared.gameOver; guess--) {
            attempts++;
            Shared.monitor.updateDesc(attempts);
            if (vault.isCorrectPassword(guess)) {
                Shared.monitor.reportWinner("Descending Hacker found password: " + guess + " in " + attempts + " attempts!");
                Shared.gameOver=true;
                return;
            }
        }
    }
}
