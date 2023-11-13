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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class TomAndJerryChaseGame extends JFrame implements ActionListener, KeyListener {

    private int tomX, tomY;
    private int jerryX, jerryY;
    private int cheeseX, cheeseY;
    private int trapX, trapY;
    private int holeX, holeY;
    private int score;
    private Timer timer;
    private int objectSpeed;
    private boolean allowMoveRight;

    private JLabel tomLabel;
    private JLabel jerryLabel;
    private JLabel cheeseLabel;
    private JLabel trapLabel;
    private JLabel holeLabel;
    private ImageIcon backgroundIcon;

    private JLabel scoreLabel;
    private JLabel gameOverLabel;
    private JLabel winLabel;
    private JButton closeButton;
    private JButton playAgainButton;

    private WelcomeScreen welcomeScreen;
    private GameScreen gameScreen;

    public TomAndJerryChaseGame() {
        setTitle("Tom and Jerry Game");
        setSize(607, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        backgroundIcon = new ImageIcon("Resources/background.gif");
        welcomeScreen = new WelcomeScreen();
        setContentPane(welcomeScreen);

        pack();
        setLocationRelativeTo(null);

        welcomeScreen.playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    private void startGame() {
        gameScreen = new GameScreen(backgroundIcon.getImage());
        setContentPane(gameScreen);

        tomX = 30;
        tomY = 530;
        jerryX = 250;
        jerryY = 600;
        cheeseX = 700;
        cheeseY = jerryY;
        trapX = 700;
        trapY = jerryY;
        holeX = 470;
        holeY = 485;
        score = 0;
        objectSpeed = 1;
        allowMoveRight = false;

        timer = new Timer(10, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);

        tomLabel = new JLabel();
        tomLabel.setBounds(tomX, tomY, 200, 157);
        tomLabel.setIcon(new ImageIcon("Resources/tom.gif"));
        gameScreen.add(tomLabel);

        jerryLabel = new JLabel();
        jerryLabel.setBounds(jerryX, jerryY, 60, 45);
        jerryLabel.setIcon(new ImageIcon("Resources/jerry.gif"));
        gameScreen.add(jerryLabel);

        cheeseLabel = new JLabel();
        cheeseLabel.setBounds(cheeseX, cheeseY, 29, 28);
        cheeseLabel.setIcon(new ImageIcon("Resources/cheese.png"));
        gameScreen.add(cheeseLabel);

        trapLabel = new JLabel();
        trapLabel.setBounds(trapX, trapY, 43, 30);
        trapLabel.setIcon(new ImageIcon("Resources/trap.png"));
        gameScreen.add(trapLabel);

        holeLabel = new JLabel();
        holeLabel.setBounds(holeX, holeY, 230, 250);
        holeLabel.setIcon(new ImageIcon("Resources/holee.png"));

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.RED);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        gameScreen.setLayout(null);
        scoreLabel.setBounds(20, 20, 150, 30);
        scoreLabel.setAlignmentX(LEFT_ALIGNMENT);
        scoreLabel.setAlignmentY(TOP_ALIGNMENT);

        gameScreen.add(scoreLabel);
        
        gameOverLabel = new JLabel();
        gameOverLabel.setBounds(170, 80, 300, 300);
        gameOverLabel.setIcon(new ImageIcon("Resources/game over.png"));
        gameScreen.add(gameOverLabel);
        gameOverLabel.setVisible(false);
        
        winLabel = new JLabel();
        winLabel.setBounds(155, 70, 360, 360);
        winLabel.setIcon(new ImageIcon("Resources/win.png"));
        gameScreen.add(winLabel);
        winLabel.setVisible(false);
        
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setBackground(Color.GREEN);
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBorderPainted(false);
        playAgainButton.setFocusPainted(false);
        playAgainButton.setBounds(getWidth() / 2 - 75, getHeight() / 2 - 50, 150, 50);
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        playAgainButton.setVisible(false);
        gameScreen.add(playAgainButton);

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setBounds(getWidth() / 2 - 75, getHeight() / 2, 150, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        closeButton.setVisible(false);
        gameScreen.add(closeButton);

        
        requestFocus();
    }
    private void restartGame() {

        // Clear existing labels and buttons
        gameScreen.remove(tomLabel);
        gameScreen.remove(jerryLabel);
        gameScreen.remove(cheeseLabel);
        gameScreen.remove(trapLabel);
        gameScreen.remove(holeLabel);
        gameScreen.remove(scoreLabel);
        gameScreen.remove(closeButton);
        gameScreen.remove(playAgainButton);
        gameScreen.remove(gameOverLabel);
        gameScreen.remove(winLabel);

        startGame();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        checkCollision();
        moveObjects();
        checkWinCondition();

        scoreLabel.setText("Score: " + score);

        tomLabel.setBounds(tomX, tomY, 200, 157);
        jerryLabel.setBounds(jerryX, jerryY, 60, 45);
        cheeseLabel.setBounds(cheeseX, cheeseY, 29, 28);
        trapLabel.setBounds(trapX, trapY, 43, 30);
        holeLabel.setBounds(holeX, holeY, 230, 250);

        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);  // You might not need this line, depends on the overall structure
    }

    private void moveObjects() {
        cheeseX -= objectSpeed;
        trapX -= objectSpeed;

        if (cheeseX < 0) {
            cheeseX = getWidth();
            int randomOffset = (int) (Math.random() * 2);
            cheeseY = jerryY - randomOffset * 30;
        }

        if (trapX < 0) {
            trapX = getWidth();
            int randomOffset = (int) (Math.random() * 2);
            trapY = jerryY - randomOffset * 30;
        }
    }

    private void checkCollision() {
        Rectangle jerryRect = new Rectangle(jerryX, jerryY, 60, 45);
        Rectangle cheeseRect = new Rectangle(cheeseX, cheeseY, 29, 28);
        Rectangle trapRect = new Rectangle(trapX, trapY, 43, 30);

        if (jerryRect.intersects(cheeseRect)) {
            cheeseX = getWidth();
            int randomOffset = (int) (Math.random() * 2);
            cheeseY = jerryY - randomOffset * 30;
        
            score++;
        
            if (score % 5 == 0) {
                objectSpeed += 1;
            }
        
            if (allowMoveRight) {
                jerryX += 2;
            }
        }          

        if (jerryRect.intersects(trapRect)) {
            trapX = getWidth();
            int randomOffset = (int) (Math.random() * 2);
            trapY = jerryY - randomOffset * 30;

            jerryX -= 5;
            score--;

            if (score % 5 == 0) {
                objectSpeed -= 1;
                objectSpeed = Math.max(1, objectSpeed);
            }

            tomX += 5;
        }

        Rectangle tomRect = new Rectangle(30, 420, 200, 157);
        Rectangle jerryRectForTrap = new Rectangle(jerryX, jerryY, 60, 45);

        if (tomRect.contains(jerryRect) || jerryRect.contains(tomRect)) {
            timer.stop();
            gameOverLabel.setVisible(true);
            playAgainButton.setVisible(true);
            closeButton.setVisible(true);
        }

        if (tomRect.intersects(jerryRectForTrap)) {
            timer.stop();
         
            gameOverLabel.setVisible(true);
            playAgainButton.setVisible(true);
            closeButton.setVisible(true);
        }
    }

    private void checkWinCondition() {
        Rectangle jerryRect = new Rectangle(jerryX, jerryY, 60, 45);
        Rectangle holeRect = new Rectangle(holeX, holeY, 230, 250);

        if (score >= 15) {
            holeLabel = new JLabel();
            holeLabel.setBounds(holeX, holeY, 230, 250);
            holeLabel.setIcon(new ImageIcon("Resources/holee.png"));
            gameScreen.add(holeLabel);
            allowMoveRight = true;
        }
        if (score >= 15 && jerryRect.intersects(holeRect)) {
            timer.stop();
            winLabel.setVisible(true);
            playAgainButton.setVisible(true);
            closeButton.setVisible(true);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (allowMoveRight && key == KeyEvent.VK_RIGHT && jerryX < getWidth() - 50) {
            jerryX += 5;
        }

        if (key == KeyEvent.VK_UP && jerryY > 0) {
            jerryY -= 5;
        } else if (key == KeyEvent.VK_DOWN && jerryY < getHeight() - 50) {
            jerryY += 5;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TomAndJerryChaseGame game = new TomAndJerryChaseGame();
            game.setVisible(true);
        });
    }

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

    class GameScreen extends BackgroundPanel {
        public GameScreen(Image backgroundImage) {
            super(backgroundImage);
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

