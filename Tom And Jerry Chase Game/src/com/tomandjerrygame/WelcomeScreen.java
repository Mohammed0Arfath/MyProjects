/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tomandjerrygame;

/**
 *
 * @author moham
 */
import javax.swing.*;
import java.awt.*;

class WelcomeScreen extends JPanel {
        private JButton playButton;

        public WelcomeScreen() {
            setLayout(new BorderLayout());
            playButton = new JButton("Play");
            playButton.setFont(new Font("Arial", Font.PLAIN, 30));
            playButton.setForeground(Color.WHITE);
            playButton.setBackground(Color.BLUE);
            add(playButton, BorderLayout.SOUTH);

            ImageIcon welcomeImageIcon = new ImageIcon("Resources/welcome.jpg");
            JLabel welcomeImageLabel = new JLabel(welcomeImageIcon);
            add(welcomeImageLabel, BorderLayout.CENTER);
        }
    }
