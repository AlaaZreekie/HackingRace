package org;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrackingMonitor {

    private final SearchSpacePanel searchSpacePanel;
    private final JLabel statusLabel;
    private final Timer animationTimer;
    private final List<VaultCracker> crackers;
    private final JPanel infoPanel;
    private VaultCracker winner = null;
    private final JLabel[] attemptLabels;

    // --- Styling ---
    private static final String FRAME_TITLE = "Vault Search Space Visualization";
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 700;
    private static final Color BG_COLOR = Color.WHITE;
    private static final Color PANEL_BG_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Font UI_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Color WINNER_COLOR = new Color(0, 150, 50);
    private static final Color POLICE_COLOR = new Color(210, 50, 45);

    public CrackingMonitor(JFrame frame, List<VaultCracker> crackers) {
        this.crackers = crackers;
        this.attemptLabels = new JLabel[crackers.size()];
        frame.setTitle(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new BorderLayout(10, 10));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        searchSpacePanel = new SearchSpacePanel();
        frame.add(searchSpacePanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Staring crackers...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        statusLabel.setForeground(TEXT_COLOR);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Cracker Stats"));

        for (int i = 0; i < crackers.size(); i++) {
            attemptLabels[i] = new JLabel("Attempts: 0", SwingConstants.LEFT);
            attemptLabels[i].setFont(UI_FONT);
            attemptLabels[i].setBorder(new EmptyBorder(5, 10, 5, 10));
            infoPanel.add(attemptLabels[i]);
        }

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(statusLabel, BorderLayout.CENTER);
        southPanel.add(new JSeparator(), BorderLayout.NORTH);
        southPanel.add(infoPanel, BorderLayout.SOUTH);
        frame.add(southPanel, BorderLayout.SOUTH);

        animationTimer = new Timer(30, e -> {
            searchSpacePanel.repaint();
            updateCrackerInfo();
        });
        animationTimer.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // --- Public Update Methods ---

    public void updatePoliceTime(int seconds) {
        double progress = (10.0 - seconds) / 10.0;
        SwingUtilities.invokeLater(() -> searchSpacePanel.setPoliceProgress(progress));
    }

    public void addAscendingAttempt(int guess) {
        searchSpacePanel.addAttempt(0, guess);
    }

    public void addDescendingAttempt(int guess) {
        searchSpacePanel.addAttempt(1, guess);
    }

    public void addBinaryAttempt(int guess) {
        searchSpacePanel.addAttempt(2, guess);
    }

    public synchronized void reportWinner(VaultCracker winner, int password) {
        if (GameState.isGameOver()) return;
        this.winner = winner;
        GameState.endGame();
        animationTimer.stop();
        searchSpacePanel.setFinalPassword(password);

        String winnerMessage = String.format(
                "%s found password: %d in %d attempts!",
                winner.getName(), password, winner.getAttemptCount()
        );

        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("SUCCESS! " + winnerMessage);
            statusLabel.setForeground(WINNER_COLOR);
            updateCrackerInfo();
        });
    }

    public synchronized void gameOver() {
        if (GameState.isGameOver()) return;
        GameState.endGame();
        animationTimer.stop();
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("FAILURE! The police arrived before the vault was cracked.");
            statusLabel.setForeground(POLICE_COLOR);
            updateCrackerInfo();
        });
    }

    private void updateCrackerInfo() {
        for (int i = 0; i < crackers.size(); i++) {
            VaultCracker cracker = crackers.get(i);
            String winStatus = "";
            if (GameState.isGameOver()) {
                if (winner != null && cracker == winner) {
                    winStatus = " - Won";
                } else {
                    winStatus = " - Lost";
                }
            }
            attemptLabels[i].setText(cracker.getName() + " Attempts: " + cracker.getAttemptCount() + winStatus);
        }
    }

    /**
     * Inner class for the custom search space visualization.
     */
    private static class SearchSpacePanel extends JPanel {
        private final List<List<Integer>> attempts = new ArrayList<>();
        private double policeProgress = 0.0;
        private Integer finalPassword = null;

        private final String[] crackerNames = {"Ascending", "Descending", "Binary Search"};
        // Added alpha for transparency
        private final Color[] crackerColors = {new Color(100, 180, 255, 150), new Color(255, 180, 100, 150), new Color(150, 220, 120, 150)};

        public SearchSpacePanel() {
            this.setBackground(PANEL_BG_COLOR);
            for (int i = 0; i < 3; i++) {
                attempts.add(Collections.synchronizedList(new ArrayList<>()));
            }
        }

        public void addAttempt(int crackerIndex, int guess) {
            attempts.get(crackerIndex).add(guess);
        }

        public void setPoliceProgress(double progress) {
            this.policeProgress = progress;
        }

        public void setFinalPassword(int password) {
            this.finalPassword = password;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 50;

            // Draw crackers' attempts
            for (int i = 0; i < 3; i++) {
                g2d.setColor(crackerColors[i]);
                List<Integer> crackerAttempts = attempts.get(i);
                synchronized (crackerAttempts) {
                    for (Integer guess : crackerAttempts) {
                        int x = padding + (int) ((double) guess / 10000 * (width - 2 * padding));
                        int y = padding + i * (height / 5) + 10; // Center in the lane
                        g2d.fillOval(x - 3, y - 3, 6, 6); // Draw a small, elegant circle
                    }
                }
            }

            // Draw Police Progress Bar
            g2d.setColor(POLICE_COLOR);
            int policeY = padding + 3 * (height / 5);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(padding, policeY, width - 2 * padding, 30);
            g2d.fillRect(padding, policeY, (int) (policeProgress * (width - 2 * padding)), 30);

            // Draw Final Password Location
            if (finalPassword != null) {
                g2d.setColor(WINNER_COLOR);
                g2d.setStroke(new BasicStroke(3));
                int x = padding + (int) ((double) finalPassword / 10000 * (width - 2 * padding));
                g2d.drawLine(x, padding, x, height - padding);
                g2d.setFont(UI_FONT);
                g2d.drawString("PASSWORD: " + finalPassword, x + 5, padding - 5);
            }

            // Draw Legends
            g2d.setFont(UI_FONT);
            for (int i = 0; i < 3; i++) {
                g2d.setColor(crackerColors[i].darker());
                g2d.drawString(crackerNames[i], padding, padding + i * (height / 5) - 10);
            }
            g2d.setColor(POLICE_COLOR);
            g2d.drawString("Police Timer", padding, padding + 3 * (height / 5) - 10);
        }
    }
}
