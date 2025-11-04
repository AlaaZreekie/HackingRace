package org;

class AscendingHackerThread extends HackerThread {
    public AscendingHackerThread(Vault vault) { super(vault); }
    @Override
    public void run() {
        for (int guess = 0; guess <= 9999 && !Shared.gameOver; guess++) {
            attempts++;
            Shared.monitor.updateAsc(attempts);
            if (vault.isCorrectPassword(guess)) {
                Shared.monitor.reportWinner("Ascending Hacker found password: " + guess + " in " + attempts + " attempts!");
                Shared.gameOver=true;
                return;
            }
        }
    }
}
