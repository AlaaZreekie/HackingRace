package org;
class BinarySearchHackerThread extends HackerThread {
    public BinarySearchHackerThread(Vault vault) { super(vault); }
    @Override
    public void run() {
        int low = 0, high = 9999;
        while (low <= high && !Shared.gameOver) {
            int mid = (low + high) / 2;
            attempts++;
            Shared.monitor.updateBin(attempts);

            int cmp = ((Vault)vault).compareToPassword(mid);
            if (cmp == 0) {
                Shared.monitor.reportWinner("Binary Search Hacker found password: " + mid + " in " + attempts + " attempts!");
                Shared.gameOver=true;
                return;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
    }
}
