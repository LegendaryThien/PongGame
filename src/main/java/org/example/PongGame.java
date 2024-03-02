import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongGame extends JPanel implements ActionListener, KeyListener {
    private int ballX = 300;
    private int ballY = 200;
    private int ballSpeedX = 2;
    private int ballSpeedY = 2;

    private int paddle1Y = 150;
    private int paddle2Y = 150;
    private int paddleWidth = 10;
    private int paddleHeight = 80;
    private int paddleSpeed = 3;

    private int player1Score = 0;
    private int player2Score = 0;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    public PongGame() {
        Timer timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballY <= 0 || ballY >= 380) {
            ballSpeedY = -ballSpeedY;
        }

        // Check for paddle collisions
        if (ballX <= 30 && ballY > paddle1Y && ballY < paddle1Y + paddleHeight) {
            ballSpeedX = -ballSpeedX;
        } else if (ballX <= 0) {
            // Player 2 scores a point
            player2Score++;
            resetBall();
        }

        if (ballX >= 560 && ballY > paddle2Y && ballY < paddle2Y + paddleHeight) {
            ballSpeedX = -ballSpeedX;
        } else if (ballX >= 600) {
            // Player 1 scores a point
            player1Score++;
            resetBall();
        }

        if (ballX <= 0 || ballX >= 600) {
            // Reset the ball
            ballX = 300;
            ballY = 200;
            ballSpeedX = -ballSpeedX;
        }

        if (upPressed && paddle2Y > 0) {
            paddle2Y -= paddleSpeed;
        }
        if (downPressed && paddle2Y < 400 - paddleHeight) {
            paddle2Y += paddleSpeed;
        }

        if (wPressed && paddle1Y > 0) {
            paddle1Y -= paddleSpeed;
        }
        if (sPressed && paddle1Y < 400 - paddleHeight) {
            paddle1Y += paddleSpeed;
        }
        
        increaseBallSpeed();


        repaint();
    }
    private void resetBall() {
        ballX = 300;
        ballY = 200;
        ballSpeedX = -ballSpeedX;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 400);

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(20, paddle1Y, paddleWidth, paddleHeight);
        g.fillRect(570, paddle2Y, paddleWidth, paddleHeight);

        // Draw ball
        g.fillOval(ballX, ballY, 20, 20);

        // Draw scores
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Player 1: " + player1Score, 50, 30);
        g.drawString("Player 2: " + player2Score, 450, 30);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (key == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (key == KeyEvent.VK_S) {
            sPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (key == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (key == KeyEvent.VK_S) {
            sPressed = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    private void increaseBallSpeed() {
        if (ballSpeedX > 0) {
            ballSpeedX += 0.5; // Increase speed slightly in the X direction
        } else {
            ballSpeedX -= 0.5; // Decrease speed slightly in the X direction (for negative values)
        }

        if (ballSpeedY > 0) {
            ballSpeedY += 0.5; // Increase speed slightly in the Y direction
        } else {
            ballSpeedY -= 0.5; // Decrease speed slightly in the Y direction (for negative values)
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
