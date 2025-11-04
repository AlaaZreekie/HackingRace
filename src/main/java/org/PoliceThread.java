package org;

// PoliceThread.java
class PoliceThread extends Thread {
    @Override
    public void run() {
        for (int i = 10; i >= 0 && !Shared.gameOver; i--) {
            Shared.monitor.updatePoliceTime(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
        if (!Shared.gameOver) {
            Shared.gameOver=true;
            Shared.monitor.gameOver();
        }
    }
}
