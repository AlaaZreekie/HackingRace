package org;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Launch UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            Shared.monitor = new HackingMonitor(frame);

            // Start background threads AFTER UI is ready
            Vault vault = new Vault(2500); // or any password

            new AscendingHackerThread(vault).start();
            new DescendingHackerThread(vault).start();
            new BinarySearchHackerThread(vault).start();
            new PoliceThread().start();
        });
    }
}