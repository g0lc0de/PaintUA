package PaintUA.main;

import PaintUA.util.Vector2;
import java.awt.*;

public class Form {

    private Vector2 originalPos;
    private Vector2 pos;
    private Vector2 size;

    private Color color;
    private String shape;

    private Vector2 DistanceToMiddle = new Vector2(0,0);

    public boolean selected = false;

    private Vector2 animEndPos;

    //public[] int xPoints = new Int;

    public Form(Vector2 pos,Vector2 size,Color color,String shape) {
        this.pos = pos;
        this.originalPos = pos;
        this.animEndPos = new Vector2(pos.x,pos.y);
        this.size = size;
        this.shape = shape;
        this.color = color;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2 getAnimEndPos() {
        return animEndPos;
    }

    public void setAnimEndPos(Vector2 animEndPos) {
        this.animEndPos = animEndPos;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getOriginalPos() {
        return originalPos;
    }

    public void setOriginalPos(Vector2 originalPos) {
        this.originalPos = originalPos;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        if (shape.equals("CIRCLE")) {
            g.fillOval((int) getPos().x, (int) getPos().y, (int) getSize().x, (int) getSize().y);
        } else if (shape.equals("RECTANGLE")) {
            g.fillRect((int) getPos().x, (int) getPos().y, (int) getSize().x, (int) getSize().y);
        } else if (shape.equals("Polygon")) {
            //g.fillPolygon();
        } // end of if-else
    }

    public Vector2 getDistanceToMiddle() {
        return DistanceToMiddle;
    }

    public void setDistanceToMiddle(Vector2 distanceToMiddle) {
        DistanceToMiddle = distanceToMiddle;
    }

}
