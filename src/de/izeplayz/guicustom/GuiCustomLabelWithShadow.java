package de.izeplayz.guicustom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import de.izeplayz.api.ApiFunctions;

@SuppressWarnings("serial")
public class GuiCustomLabelWithShadow extends JLabel {
	private String text;
	private Color textColor;
	private Color shadowColor;

	public GuiCustomLabelWithShadow(String text, CustomLabelType customLabelType, Font font, int x, int y, int width, int height, boolean center) {
		super();
		if (center) {
			setAlignmentX(JLabel.CENTER_ALIGNMENT);
		}
		setBounds(x, y, width, height);
		setFont(font);
		setForeground(textColor);
		setLabelColors(customLabelType);
		setText(text);
	}

	private void setLabelColors(CustomLabelType customLabelType) {
		switch (customLabelType) {

		case RED:
			this.textColor = ApiFunctions.getColorRGBA(255, 64, 64, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(128, 32, 32, 160);
			break;
		case DARK_RED:
			this.textColor = ApiFunctions.getColorRGBA(128, 16, 16, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(64, 8, 8, 160);
			break;
		case GREEN:
			this.textColor = ApiFunctions.getColorRGBA(64, 255, 64, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(32, 128, 32, 160);
			break;
		case BLUE:
			this.textColor = ApiFunctions.getColorRGBA(64, 64, 255, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(32, 32, 128, 160);
			break;
		case BRIGHT_BLUE:
			this.textColor = ApiFunctions.getColorRGBA(150, 150, 250, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(75, 75, 125, 160);
			break;
		case BRIGHT_RED:
			this.textColor = ApiFunctions.getColorRGBA(250, 150, 150, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(125, 75, 75, 160);
			break;
		case YELLOW:
			this.textColor = ApiFunctions.getColorRGBA(255, 255, 64, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(128, 128, 32, 160);
			break;
		case ORANGE:
			this.textColor = ApiFunctions.getColorRGBA(255, 192, 64, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(128, 96, 32, 160);
			break;
		case GREY_BRIGHT:
			this.textColor = ApiFunctions.getColorRGBA(220, 220, 220, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(110, 110, 110, 160);
			break;
		case GREY:
			this.textColor = ApiFunctions.getColorRGBA(170, 170, 170, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(85, 85, 85, 160);
			break;
		case GREY_DARK:
			this.textColor = ApiFunctions.getColorRGBA(120, 120, 120, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(60, 60, 60, 160);
			break;
		case WHITE:
		default:
			this.textColor = ApiFunctions.getColorRGBA(255, 255, 255, 255);
			this.shadowColor = ApiFunctions.getColorRGBA(128, 128, 128, 160);
			break;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(shadowColor);
		g2D.drawString(this.text, ApiFunctions.scale(11) * 2, ApiFunctions.scale(33) * 2);
		g2D.setColor(textColor);
		g2D.drawString(this.text, ApiFunctions.scale(10) * 2, ApiFunctions.scale(32) * 2);
		g2D.dispose();
	}

	@Override
	public void setText(String text) {
		this.text = text;
		repaint();
	}

	public void setTextWithColor(String text, CustomLabelType customLabelType) {
		setLabelColors(customLabelType);
		this.text = text;
		repaint();
	}

	public static enum CustomLabelType {
		WHITE, RED, GREEN, BLUE, YELLOW, ORANGE, GREY_DARK, GREY, GREY_BRIGHT, BRIGHT_RED, BRIGHT_BLUE, DARK_RED
	}
}