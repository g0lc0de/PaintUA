package PaintUA.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ColorPicker extends JComponent {

    private final Color Color1 = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    private final Color Color2 = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    private final Color Color3 = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    private final Color Color4 = new Color(0.0f, 1.0f, 1.0f, 1.0f);

    public int colorfulPickerHeight = 150;

    private int SelX;
    private int SelY;

    private Graphics2D g2;

    public ColorPicker() {
        super();

        setBounds(25, 50, 150, 200);
        SelX = getWidth() / 2;
        SelY = colorfulPickerHeight / 2;
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g.create();

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < colorfulPickerHeight; y++) {
                float frX = (float)x / getWidth();
                float frY = (float)y / colorfulPickerHeight;

                // Bi-Lineare Interpolation
                Color BilinearInterpolatedColor = BiLinearinterpolateColor(frX,frY);

                g2.setColor(BilinearInterpolatedColor);
                g2.drawLine(x, y, x, y);
            }
        }

        for (int x = 0;x < getWidth(); x++) {
            float frX = (float) x / getWidth();

            Color c = interpolateColor(new Color(0.0f,0.0f,0.0f,1.0f),new Color(1.0f,1.0f,1.0f,1.0f),frX);
            g2.setColor(c);
            g2.drawRect(x, 160, 1, 30);

        }

        g2.setColor(Color.WHITE);
        g2.drawOval(SelX, SelY, 5, 5);

    }

    public Color interpolateColor(final Color COLOR1, final Color COLOR2, float fraction)
    {
        final float INT_TO_FLOAT_CONST = 1f / 255f;
        fraction = Math.min(fraction, 1f);
        fraction = Math.max(fraction, 0f);

        final float RED1 = COLOR1.getRed() * INT_TO_FLOAT_CONST;
        final float GREEN1 = COLOR1.getGreen() * INT_TO_FLOAT_CONST;
        final float BLUE1 = COLOR1.getBlue() * INT_TO_FLOAT_CONST;
        final float ALPHA1 = COLOR1.getAlpha() * INT_TO_FLOAT_CONST;

        final float RED2 = COLOR2.getRed() * INT_TO_FLOAT_CONST;
        final float GREEN2 = COLOR2.getGreen() * INT_TO_FLOAT_CONST;
        final float BLUE2 = COLOR2.getBlue() * INT_TO_FLOAT_CONST;
        final float ALPHA2 = COLOR2.getAlpha() * INT_TO_FLOAT_CONST;

        final float DELTA_RED = RED2 - RED1;
        final float DELTA_GREEN = GREEN2 - GREEN1;
        final float DELTA_BLUE = BLUE2 - BLUE1;
        final float DELTA_ALPHA = ALPHA2 - ALPHA1;

        float red = RED1 + (DELTA_RED * fraction);
        float green = GREEN1 + (DELTA_GREEN * fraction);
        float blue = BLUE1 + (DELTA_BLUE * fraction);
        float alpha = ALPHA1 + (DELTA_ALPHA * fraction);

        red = Math.min(red, 1f);
        red = Math.max(red, 0f);
        green = Math.min(green, 1f);
        green = Math.max(green, 0f);
        blue = Math.min(blue, 1f);
        blue = Math.max(blue, 0f);
        alpha = Math.min(alpha, 1f);
        alpha = Math.max(alpha, 0f);

        return new Color(red, green, blue, alpha);
    }

    // Bilinear Interpolation
    public Color BiLinearinterpolateColor (float fractionX,float fractionY) {
        // Color1 -X- Color2
        //   |			|
        //   Y			Y
        //	 |			|
        // Color3 -X- Color4

        Color interpolateX = interpolateColor(Color1, Color2, fractionX);
        Color interpolateY = interpolateColor(Color3, Color4, fractionX);

        return interpolateColor(interpolateX, interpolateY, fractionY);
    }

    public void setXY(int x,int y) {
        SelX = x - 3;
        SelY = y - 3;
        repaint();
    }

}

