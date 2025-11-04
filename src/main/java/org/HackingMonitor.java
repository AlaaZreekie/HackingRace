package org;

import javax.swing.*;
import java.awt.*;

public class HackingMonitor {
    private final JLabel policeLabel;
    private final JProgressBar ascBar, descBar, binBar;
    private final JLabel ascAttempts, descAttempts, binAttempts;
    private final JLabel statusLabel;
    private volatile boolean stopped = false;

    public HackingMonitor(JFrame frame) {
        frame.setTitle("Vault Hacking Race — Parallel Programming");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setResizable(false);

        // Police Timer (Top)
        policeLabel = new JLabel("Police: 10s", SwingConstants.CENTER);
        policeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        policeLabel.setForeground(Color.RED);
        frame.add(policeLabel, BorderLayout.NORTH);

        // Hackers Panel (Center)
        JPanel hackersPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        hackersPanel.setBorder(BorderFactory.createTitledBorder("Hackers Progress"));

        ascBar = new JProgressBar(0, 100);
        descBar = new JProgressBar(0, 100);
        binBar = new JProgressBar(0, 100);

        ascAttempts = new JLabel("Attempts: 0");
        descAttempts = new JLabel("Attempts: 0");
        binAttempts = new JLabel("Attempts: 0");

        hackersPanel.add(new JLabel("Ascending:"));
        hackersPanel.add(ascBar);
        hackersPanel.add(ascAttempts);

        hackersPanel.add(new JLabel("Descending:"));
        hackersPanel.add(descBar);
        hackersPanel.add(descAttempts);

        hackersPanel.add(new JLabel("Binary Search:"));
        hackersPanel.add(binBar);
        hackersPanel.add(binAttempts);

        frame.add(hackersPanel, BorderLayout.CENTER);

        // Status (Bottom)
        statusLabel = new JLabel("Hacking in progress...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Thread-safe UI updates
    public void updatePoliceTime(int seconds) {
        if (stopped) return;
        SwingUtilities.invokeLater(() -> policeLabel.setText("Police: " + seconds + "s"));
    }

    public void updateAsc(int attempts) {
        if (stopped) return;
        int progress = Math.min(100, (attempts * 100) / 10000);
        SwingUtilities.invokeLater(() -> {
            ascBar.setValue(progress);
            ascAttempts.setText("Attempts: " + attempts);
        });
    }

    public void updateDesc(int attempts) {
        if (stopped) return;
        int progress = Math.min(100, (attempts * 100) / 10000);
        SwingUtilities.invokeLater(() -> {
            descBar.setValue(progress);
            descAttempts.setText("Attempts: " + attempts);
        });
    }

    public void updateBin(int attempts) {
        if (stopped) return;
        // Binary doesn't map linearly, but we cap at 100%
        int progress = Math.min(100, attempts * 8); // ~13 attempts → 100%
        SwingUtilities.invokeLater(() -> {
            binBar.setValue(progress);
            binAttempts.setText("Attempts: " + attempts);
        });
    }

    public void reportWinner(String winner) {
        if (Shared.gameOver) return;
        Shared.gameOver = true;
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(winner);
            statusLabel.setForeground(Color.GREEN);
        });

    }

    public void gameOver() {
        if (stopped) return;
        stopped = true;
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Game over for you hackers!");
            statusLabel.setForeground(Color.RED);
        });

    }
}
