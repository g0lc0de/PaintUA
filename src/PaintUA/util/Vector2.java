package PaintUA.util;

public class Vector2 {

    public double x;
    public double y;


    public Vector2(int x1,int y1) {
        this.x = x1;
        this.y = y1;
    }

    public Vector2(double x1,double y1) {
        this.x = x1;
        this.y = y1;
    }

    public static int distance(Vector2 vec1,Vector2 vec2) {

        double distance = Math.sqrt(Math.pow((vec1.x - vec2.x),2) + Math.pow((vec1.y - vec2.y),2));

        return (int) Math.round(distance);
    }

    public Vector2 add(Vector2 vec) {
        return new Vector2(this.x + vec.x,this.y + vec.y);
    }

}