package com.ch.registerGuide;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class HomePanel extends JPanel {
	ImageIcon icon;
	Image image;

	public HomePanel() {
		icon = new ImageIcon("pImage2.png");
		image = icon.getImage();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}