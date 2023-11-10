import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TomAndJerryGame extends JFrame implements ActionListener, KeyListener, MouseMotionListener {
    private JLabel tomLabel, jerryLabel, homeHoleLabel;
    private JLabel[] cheeseLabels;
    private int tomSpeed = 1;
    private int jerryPoints = 0;
    private JLabel scoreLabel;

    public TomAndJerryGame() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
    
        JLabel welcomeLabel = new JLabel(new ImageIcon("welcome.jpg"));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
    
        JButton playButton = new JButton("Play");
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(Color.BLUE);  // Set the background color to blue
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.addActionListener(e -> {
            remove(welcomePanel);
            initializeGame();
        });
        welcomePanel.add(playButton, BorderLayout.SOUTH);
    
        add(welcomePanel);
    
        setTitle("Tom and Jerry Chase Game");
        setSize(607, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    

    private void initializeGame() {
        setContentPane(new JLabel(new ImageIcon("background.jpeg")));  // Set background image

        tomLabel = new JLabel(new ImageIcon("tom.png"));
        jerryLabel = new JLabel(new ImageIcon("jerry.png"));
        homeHoleLabel = new JLabel(new ImageIcon("hole.png"));

        setLayout(null);

        tomLabel.setBounds(80, 550, 198, 132);
        jerryLabel.setBounds(550, 80, 62, 40);
        homeHoleLabel.setBounds(460, 350, 186, 192);  // Move home hole to right bottom-most corner

        add(tomLabel);
        add(jerryLabel);
        add(homeHoleLabel);

        createScoreBoard();
        initializeCheese();

        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Timer timer = new Timer(100, this);
        timer.start();
    }

    private void createScoreBoard() {
        scoreLabel = new JLabel("Score: " + jerryPoints);
        scoreLabel.setForeground(Color.RED);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setBounds(20, 20, 150, 30);

        getContentPane().add(scoreLabel);
    }

    private void initializeCheese() {
        int numCheeses = 5;
        cheeseLabels = new JLabel[numCheeses];

        for (int i = 0; i < numCheeses; i++) {
            cheeseLabels[i] = new JLabel(new ImageIcon("cheese.png"));
            cheeseLabels[i].setBounds(generateRandomX(), generateRandomY(), 100, 95);
            add(cheeseLabels[i]);
        }
    }

    private int generateRandomX() {
        Random random = new Random();
        return random.nextInt(1100);
    }

    private int generateRandomY() {
        Random random = new Random();
        return random.nextInt(700);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TomAndJerryGame::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveTom();
        checkCollision();
        checkCheeseCollected();
        checkHomeHole();
        increaseTomSpeedOverTime();
        updateScoreboard();
        repaint();
    }

    private void moveTom() {
        int tomX = tomLabel.getX();
        int tomY = tomLabel.getY();

        int jerryX = jerryLabel.getX();
        int jerryY = jerryLabel.getY();

        if (tomX < jerryX) {
            tomX += tomSpeed;
        } else {
            tomX -= tomSpeed;
        }

        if (tomY < jerryY) {
            tomY += tomSpeed;
        } else {
            tomY -= tomSpeed;
        }

        tomLabel.setLocation(tomX, tomY);
    }

    private void checkCollision() {
        Rectangle tomBounds = tomLabel.getBounds();
        Rectangle jerryBounds = jerryLabel.getBounds();

        if (tomBounds.intersects(jerryBounds)) {
            endGame("Jerry caught by Tom! Game Over!\nYour Score: " + jerryPoints);
        }
    }

    private void checkCheeseCollected() {
        Rectangle jerryBounds = jerryLabel.getBounds();

        for (JLabel cheeseLabel : cheeseLabels) {
            Rectangle cheeseBounds = cheeseLabel.getBounds();
            if (jerryBounds.intersects(cheeseBounds)) {
                jerryPoints++;
                remove(cheeseLabel);
                cheeseLabel.setBounds(generateRandomX(), generateRandomY(), 100, 95);
                add(cheeseLabel);
            }
        }
    }

    private void checkHomeHole() {
        Rectangle jerryBounds = jerryLabel.getBounds();
        Rectangle homeHoleBounds = homeHoleLabel.getBounds();

        if (jerryBounds.intersects(homeHoleBounds)) {
            endGame("Jerry reached home hole! Game Over!\nYour Score: " + jerryPoints);
        }
    }

    private void increaseTomSpeedOverTime() {
        // Increase Tom's speed every 5 seconds
        if (System.currentTimeMillis() % 5000 == 0) {
            tomSpeed++;
            System.out.println("Tom's speed increased to " + tomSpeed);
        }
    }

    private void updateScoreboard() {
        scoreLabel.setText("Score: " + jerryPoints);
    }

    private void endGame(String message) {
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBackground(new Color(34, 49, 63));
    
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setForeground(new Color(231, 76, 60));
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel scoreLabel = new JLabel(message);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBackground(new Color(52, 152, 219));
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(a -> {
            remove(gameOverPanel);
            initializeGame();
        });
    
        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(192, 57, 43));
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(a -> System.exit(0));
    
        gameOverPanel.add(Box.createVerticalGlue());
        gameOverPanel.add(gameOverLabel);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gameOverPanel.add(scoreLabel);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gameOverPanel.add(playAgainButton);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameOverPanel.add(exitButton);
        gameOverPanel.add(Box.createVerticalGlue());
    
        JOptionPane.showMessageDialog(this, gameOverPanel, "Game Over", JOptionPane.PLAIN_MESSAGE);
        resetGame();
    }
    

    private void resetGame() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Do you want to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            jerryPoints = 0;
            tomSpeed = 1;
            initializeGame();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        jerryLabel.setLocation(e.getX(), e.getY());
        checkCollision();
        checkHomeHole();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not needed for this example
    }
}
