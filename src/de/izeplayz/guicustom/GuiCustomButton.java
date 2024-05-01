package de.izeplayz.guicustom;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

import de.izeplayz.api.ApiFunctions;

@SuppressWarnings("serial")
public class GuiCustomButton extends JButton {
	private Color COLOR_TOP;
	private Color COLOR_BOTTOM;
	private Color COLOR_TOP_HOVER;
	private Color COLOR_BOTTOM_HOVER;
	private Color ssc;
	private Color bgc;
	private int r;

	private boolean cardPlayer = false;
	private int cardValue = 0;
	private boolean cardSelected = false;

	public boolean isCardPlayer() {
		return cardPlayer;
	}

	public void setCardPlayer(boolean cardPlayer) {
		this.cardPlayer = cardPlayer;
	}

	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

	public boolean isCardSelected() {
		return cardSelected;
	}

	public void setCardSelected(boolean cardSelected) {
		this.cardSelected = cardSelected;
	}

	public GuiCustomButton(String text, CustomButtonType customButtonType) {
		super(text);
		r = ApiFunctions.scale(40);
		setButtonColors(customButtonType);
	}

	public GuiCustomButton(String text, Color colorTop, Color colorBottom, Color colorTopHover, Color colorBottomHover) {
		super(text);
		this.COLOR_TOP = colorTop;
		this.COLOR_BOTTOM = colorBottom;
		this.COLOR_TOP_HOVER = colorTopHover;
		this.COLOR_BOTTOM_HOVER = colorBottomHover;
	}

	public void setButtonColors(CustomButtonType customButtonType) {
		switch (customButtonType) {
		case RED:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(200, 0, 0, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(100, 0, 0, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(200, 0, 0, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(100, 0, 0, 255);
			break;
		case BRIGHT_RED:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(250, 100, 100, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(125, 50, 50, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(250, 100, 100, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(125, 50, 50, 255);
			break;
		case GREEN:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(0, 200, 0, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(0, 100, 0, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(0, 200, 0, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(0, 100, 0, 255);
			break;
		case GREEN_BRIGHT:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(20, 250, 20, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(10, 125, 10, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(20, 250, 20, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(10, 125, 10, 255);
			break;
		case GREEN_DARK:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(0, 150, 0, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(0, 75, 0, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(0, 150, 0, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(0, 75, 0, 255);
			break;
		case YELLOW:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(240, 240, 0, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(120, 120, 0, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(240, 240, 0, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(120, 120, 0, 255);
			break;
		case ORANGE:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(200, 150, 0, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(100, 75, 0, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(200, 150, 0, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(100, 75, 0, 255);
			break;
		case PINK:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(200, 0, 200, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(100, 0, 100, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(200, 0, 200, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(100, 0, 100, 255);
			break;
		case PURPLE:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(200, 0, 100, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(100, 0, 50, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(200, 0, 100, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(100, 0, 50, 255);
			break;
		case PURPLE_DARK:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(100, 0, 50, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(50, 0, 25, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(100, 0, 50, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(50, 0, 25, 255);
			break;
		case GREY:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(100, 100, 100, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(50, 50, 50, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(100, 100, 100, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(50, 50, 50, 255);
			break;
		case CYAN:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(0, 200, 200, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(0, 100, 100, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(0, 200, 200, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(0, 100, 100, 255);
			break;
		case CYAN2:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(0, 150, 200, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(0, 75, 100, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(0, 150, 200, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(0, 75, 100, 255);
			break;
		case BRIGHT_BLUE:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(150, 150, 250, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(75, 75, 125, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(150, 150, 250, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(75, 75, 125, 255);
			break;
		case BLUE:
		default:
			this.COLOR_TOP = ApiFunctions.getColorRGBA(50, 50, 250, 160);
			this.COLOR_BOTTOM = ApiFunctions.getColorRGBA(25, 25, 125, 160);
			this.COLOR_TOP_HOVER = ApiFunctions.getColorRGBA(50, 50, 250, 255);
			this.COLOR_BOTTOM_HOVER = ApiFunctions.getColorRGBA(25, 25, 125, 255);
			break;
		}
	}

	@Override
	public void updateUI() {
		super.updateUI();
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setForeground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 0;
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape area = new RoundRectangle2D.Float(x, y, w - 1, h - 1, r, r);
		ssc = COLOR_TOP;
		bgc = COLOR_BOTTOM;
		ButtonModel m = getModel();
		if (m.isPressed()) {
			ssc = COLOR_BOTTOM_HOVER;
			bgc = COLOR_TOP_HOVER;
		} else if (m.isRollover()) {
			ssc = COLOR_TOP_HOVER;
			bgc = COLOR_BOTTOM_HOVER;
		}
		g2.setPaint(new GradientPaint(x, y, ssc, x, y + h, bgc, true));
		g2.fill(area);
		g2.setPaint(COLOR_BOTTOM);
		g2.draw(area);
		g2.dispose();
		super.paintComponent(g);
	}

	public static enum CustomButtonType {
		RED, GREEN, GREEN_BRIGHT, GREEN_DARK, BLUE, YELLOW, ORANGE, GREY, PINK, PURPLE, PURPLE_DARK, CYAN, CYAN2, BRIGHT_RED, BRIGHT_BLUE
	}
}