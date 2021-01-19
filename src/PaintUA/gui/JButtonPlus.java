package PaintUA.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.JButton;

public class JButtonPlus extends JButton {

    private Color pressedColor = Color.DARK_GRAY;
    private Color rolloverColor = Color.GRAY;

    public Color getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public Color getRolloverColor() {
        return rolloverColor;
    }

    public void setRolloverColor(Color rolloverColor) {
        this.rolloverColor = rolloverColor;
    }

    public JButtonPlus() {
        init();
    }

    public JButtonPlus(String text) {
        setText(text);
        init();
    }

    private void init() {
        setFocusPainted(false);
        setVisible(true);
        setBorder(null);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedColor);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else if(getModel().isRollover()) {
            g.setColor(rolloverColor);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            g.setColor(getBackground());
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); // AFTER fill rect to draw the text over the bg
    }

    public void setJButtonPlusColor(Color c1,Color c2) {
        setBackground(c1);
        setPressedColor(c2);
        setRolloverColor(c2);
    }


}
