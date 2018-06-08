import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Inspired by: https://www.youtube.com/watch?v=K9qMm3JbOH0

// Child of JPanel so object can be added to frame
// implements KeyListener to look for key presses
// implements ActionListener to move the ball
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;   // will trigger game initialization
    private int score = 0;
    private int totalBricks = 21;   // 7x3 map
    private Timer time;
    private int delay = 8;
    private int playerX = 310;      // starting position of slider
    private int ballposX = 120;     // ball's starting x
    private int ballposY = 350;     // ball's starting y
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGen map;

    public Gameplay(){
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.map = new MapGen(3, 7);
        this.time = new Timer(delay, this);
        this.time.start();
    }

    @Override
    public void paint(Graphics g){
        // creating background
        g.setColor(Color.BLACK);
        g.fillRect(1,1, 692, 592);

        // drawing map
        this.map.draw((Graphics2D) g);

        // creating borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // drawing scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // creating paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        // creating ball
        g.setColor(Color.CYAN);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(ballposY >  570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300 );

            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press enter to restart", 230, 400 );

        }

        if(totalBricks == 0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300 );

            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press enter to restart", 230, 400 );
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        //this.time.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYDir = -ballYDir;
            }

            A: for(int i = 0; i < map.getMap().length; i++){
                for(int j = 0; j < map.getMap()[0].length ; j++){
                    if(map.getMap()[i][j] > 0) {
                        int brickX = j * map.getBrickWidth() + 80;
                        int brickY = i * map.getBrickHeight() + 50;
                        int brickWidth = map.getBrickWidth();
                        int brickHeight = map.getBrickHeight();

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXDir = -ballXDir;
                            }else{
                                ballYDir = -ballYDir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXDir;
            ballposY += ballYDir;

            if(ballposX < 0) ballXDir = -ballXDir;
            if(ballposX > 670) ballXDir = -ballXDir;
            if(ballposY < 0) ballYDir = -ballYDir;

        }
        this.repaint();
    }

    public void moveRight(){
        this.play = true;
        playerX += 20;      // we are moving twenty pixels
    }

    public void moveLeft(){
        this.play = true;
        playerX -= 20;      // we are moving twenty pixels
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // if the right key is pressed
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;  // keeping things to borders
            }else{
                moveRight();

            }
        }
        // if the left key is pressed
        else if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;  // keeping things to borders
            }else{
                moveLeft();

            }
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGen(3, 7);
                repaint();
            }
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) play = !play;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
