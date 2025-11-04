package org;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        setupLookAndFeel();
        SwingUtilities.invokeLater(Main::createPasswordInputGUI);
    }

    private static void setupLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Failed to set look and feel.");
            }
        }
    }

    private static void createPasswordInputGUI() {
        JFrame inputFrame = new JFrame("Setup Vault Password");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(400, 200);
        inputFrame.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Enter Vault Password (0-9999):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputFrame.add(label, gbc);

        JTextField passwordField = new JPasswordField(10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputFrame.add(passwordField, gbc);

        JButton startButton = new JButton("Start Race");
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputFrame.add(startButton, gbc);

        startButton.addActionListener(e -> {
            try {
                int password = Integer.parseInt(passwordField.getText());
                if (password < 0 || password > 9999) {
                    JOptionPane.showMessageDialog(inputFrame, "Password must be between 0 and 9999.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                inputFrame.dispose();
                startSimulation(password);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputFrame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputFrame.setLocationRelativeTo(null);
        inputFrame.setVisible(true);
    }

    private static void startSimulation(int password) {
        JFrame mainFrame = new JFrame();
        SecureVault vault = new SecureVault(password);
        List<VaultCracker> crackers = new ArrayList<>();
        crackers.add(new VaultCrackerAscending(vault));
        crackers.add(new VaultCrackerDescending(vault));
        crackers.add(new VaultCrackerBinary(vault));

        CrackingMonitor monitor = new CrackingMonitor(mainFrame, crackers);
        
        GameState.reset();
        GameState.setMonitor(monitor);

        List<Thread> threads = new ArrayList<>(crackers);
        threads.add(new PoliceThread());

        for (Thread t : threads) {
            t.start();
        }
    }
}
