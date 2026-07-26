// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\SettingsMenu.java

package org.b3llabug.type_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SettingsMenu extends JFrame {
    private JComboBox<String> themeSelector;
    private JButton applyButton;
    private JButton backButton;
    private String[] themes = {"original", "twoo"};
    //FIX: the window that opened settings, so we have something to preview the theme on
    private JFrame parentFrame;

    public SettingsMenu() {
        this(null);
    }

    public SettingsMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setTitle("settings");
        setSize(600, 600);
        //FIX: only the title screen should quit the whole app on close
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        themeSelector = new JComboBox<>(themes);
        themeSelector.setFont(new Font("Arial", Font.PLAIN, 16));
        add(themeSelector);

        applyButton = new JButton("Apply Theme");
        applyButton.setFont(new Font("Arial", Font.BOLD, 16));
        applyButton.addActionListener(new ApplyThemeAction());
        add(applyButton);

        backButton = new JButton("Back to Title Screen");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            // Logic to return to the title screen
            dispose(); // Close settings menu
            new TitleScreen().setVisible(true); // Open title screen
        });
        add(backButton);
    }

    private class ApplyThemeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTheme = (String) themeSelector.getSelectedItem();
            // Logic to apply the selected theme
            ThemeManager.applyTheme(selectedTheme);
            //FIX: actually restyle the window that opened settings, not just store the choice
            ThemeManager.applyThemeTo(parentFrame);
            JOptionPane.showMessageDialog(SettingsMenu.this, "theme applied: " + selectedTheme);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SettingsMenu settingsMenu = new SettingsMenu();
            settingsMenu.setVisible(true);
        });
    }
}