package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    JFrame frameObject = new JFrame();
	    Gameplay gamePlay = new Gameplay();
        frameObject.setBounds(10,10,700,600);//setting size
        frameObject.setTitle("BrickHunter"); // title
        frameObject.setResizable(false);//allows you to change window size
        frameObject.setVisible(true);
        frameObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameObject.add(gamePlay);
    }
}
