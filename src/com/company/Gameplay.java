package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 5; // controls speed of the ball
    private int playerX = 310;
    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPositionX,ballPositionY,20,20);

        if(ballPositionY >570) {
            play = false;
            ballXDirection =0;
            ballYDirection =0;
            g.setColor(Color.red);
            g.setFont(new Font ("serif", Font.BOLD, 60));
            g.drawString("Game Over", 200,300);

            g.setFont(new Font ("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 210,350);
        }


        if(totalBricks <= 0) {
            play = false;
            ballXDirection =0;
            ballYDirection =0;
            g.setColor(Color.red);
            g.setFont(new Font ("serif", Font.BOLD, 60));
            g.drawString("You Won", 200,300);

            g.setFont(new Font ("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 210,350);
        }

        //score
        g.setColor(Color.cyan);
        g.setFont(new Font ("arial", Font.BOLD, 30));
        g.drawString(""+score, 10,30);

        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){

            if(new Rectangle(ballPositionX,ballPositionY,20,20).intersects((new Rectangle(playerX,550,100,8)))) {
                ballYDirection = -ballYDirection;
            }
            A : for(int i = 0; i< map.map.length; i++){
                for (int j = 0; j< map.map[0].length; j++){
                    if(map.map[i][j]> 0) {
                        int brickX = j * map.brickWidth +80;
                        int brickY = i * map.brickHeight +50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects((brickRect))){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+= 10;

                            if(ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width){
                                ballXDirection = -ballXDirection;
                            }
                            else {
                                ballYDirection = -ballYDirection;
                            }

                            break A;

                        }
                    }
                }

            }


            ballPositionX += ballXDirection;
            ballPositionY += ballYDirection;
            if (ballPositionX < 0){
                ballXDirection = -ballXDirection;
            }
            if (ballPositionY < 0){
                ballYDirection = -ballYDirection;
            }
            if (ballPositionX > 670){
                ballXDirection = -ballXDirection;
            }
        }


        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}


    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >=600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXDirection = -1;
                ballYDirection = -2;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }

    }

    public void moveRight() {
        play = true;
        playerX+=20;

    }
    public void moveLeft() {
        play = true;
        playerX-=20;

    }


}
