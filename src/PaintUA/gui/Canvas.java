package PaintUA.gui;

import PaintUA.main.Form;
import PaintUA.util.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JComponent implements MouseListener {
    private ArrayList<Form> Forms = new ArrayList<>();

    private String Mode = "DRAW"; // DRAW, ANIMATE, PLAYING
    private String Tool = "NEW"; // DRAW: New,Move,Resize, Color, Delete
    // ANIMATE: AnimateSize, Rotation,Color
    private String Shape = "CIRCLE"; // Circle, Rectangle, Own1,Own2,Own3

    private ArrayList<Form> Custom1 = new ArrayList<>();

    // Variables for creating new Forms
    private Color color = Color.GRAY;
    private Color AnimationDisplayColor = Color.RED;
    private Boolean currentlyDrawing = false;
    private Vector2 pos1;
    private Vector2 pos2;

    public Canvas() {
        super();
        setBounds(200,0,1000,800);
        addMouseListener(this);

        //Forms.add(new Form(new Vector2(100,100),new Vector2(100,100),Color.DARK_GRAY,"CIRCLE"));
        //Forms.add(new Form(new Vector2(100,200),new Vector2(50,100),Color.GREEN,"RECTANGLE"));

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                pos2 = new Vector2(e.getX(),e.getY());

                final int dist = Vector2.distance(pos1, pos2);
                final int diffX = (int) Math.abs(pos2.x - pos1.x);
                final int diffY = (int) Math.abs(pos2.y - pos1.y);

                if (Mode.equals("DRAW") && Tool.equals("NEW")) {
                    if (currentlyDrawing == true) {
                        if (Shape.equals("C1")) {
                            for (@SuppressWarnings("unused") Form f : Custom1) {
                                Forms.remove(Forms.size() - 1);
                            }
                        } else {
                            Forms.remove(Forms.size() - 1);
                        }
                    }

                    if (Shape.equals("CIRCLE")) {
                        Forms.add(new Form(new Vector2(pos1.x - dist,pos1.y - dist),new Vector2(dist*2,dist*2),color,"CIRCLE"));
                    } else if(Shape.equals("RECTANGLE")) {
                        Forms.add(new Form(new Vector2(Math.min(pos1.x,pos2.x),Math.min(pos1.y,pos2.y)),new Vector2(diffX,diffY),color,"RECTANGLE"));
                    } else if(Shape.equals("C1")) {
                        for (Form f : Custom1) {
                            f.setPos(new Vector2(pos2.x + f.getDistanceToMiddle().x,pos2.y + f.getDistanceToMiddle().y));
                            Forms.add(new Form(f.getPos(),f.getSize(),f.getColor(),f.getShape()));
                        }
                    }

                    currentlyDrawing = true;
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(new Color(220,220,220));
        g2.fillRect(0, 0, 1000, 800);

        for(Form f : Forms) {
            f.draw(g2);
            if (Mode.equals("ANIMATE")) {
                g2.setColor(AnimationDisplayColor);
                g2.fillOval((int) f.getPos().x + ((int) f.getSize().x / 2) - 5,
                        (int) f.getPos().y + ((int) f.getSize().y / 2) - 5,
                        10, 10);

                g2.fillOval((int) f.getAnimEndPos().x + ((int) f.getSize().x / 2) - 5,
                        (int) f.getAnimEndPos().y + ((int) f.getSize().y / 2) - 5, 10, 10);

                g2.setStroke(new BasicStroke(5));
                g2.drawLine((int) f.getPos().x + ((int) f.getSize().x / 2),
                        (int) f.getPos().y + ((int) f.getSize().y / 2),
                        (int) f.getAnimEndPos().x + ((int) f.getSize().x / 2),
                        (int) f.getAnimEndPos().y + ((int) f.getSize().y / 2));

                if (f.selected) {
                    g2.setStroke(new BasicStroke(3));
                    if (f.getShape().equals("CIRCLE")) {
                        g2.drawOval((int) f.getPos().x,(int) f.getPos().y,(int) f.getSize().x,(int) f.getSize().y);
                    } else if (f.getShape().equals("RECTANGLE")) {
                        g2.drawRect((int) f.getPos().x,(int) f.getPos().y,(int) f.getSize().x,(int) f.getSize().y);
                    }
                }

            } else if (Mode.equals("DRAW")) {
                f.selected = false;
            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    long lastPressedTime = 0;
    @Override
    public void mousePressed(MouseEvent e) {
        pos1 = new Vector2(e.getX(),e.getY());

        if (System.currentTimeMillis() - lastPressedTime < 350) {
            lastPressedTime = 0;
            Forms.removeIf(f -> Vector2.distance(pos1, new Vector2(f.getPos().x + f.getSize().x/2,f.getPos().y + f.getSize().y/2)) < 30);
        }

        if (Mode.equals("DRAW"))  {
            switch (Tool) {
                case "FILL":
                    for (Form f : Forms) {
                        final Vector2 middlePos = new Vector2(f.getPos().x + f.getSize().x / 2,f.getPos().y + f.getSize().y / 2);
                        if (Vector2.distance(pos1,middlePos) <= 30) {
                            f.setColor(color);
                            repaint();
                        }
                    }
                    break;
            }
        }

        lastPressedTime = System.currentTimeMillis();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pos2 = new Vector2(e.getX(),e.getY());

        if (Mode.equals("DRAW") == true && Tool.equals("NEW")) {

            if (currentlyDrawing == true) {
                currentlyDrawing = false;
            } else {
                // Stamp
                for (Form f : Custom1) {
                    if(Shape.equals("C1")) {
                        f.setPos(new Vector2(pos2.x + f.getDistanceToMiddle().x,pos2.y + f.getDistanceToMiddle().y));
                        Forms.add(new Form(f.getPos(),f.getSize(),f.getColor(),f.getShape()));
                    }
                }
            }

        } else if (Mode.equals("ANIMATE")) {
            // Find Object and select it
            for (Form f : Forms) {
                final Vector2 middlePos = new Vector2(f.getPos().x + f.getSize().x / 2,f.getPos().y + f.getSize().x / 2);
                if (Vector2.distance(pos2,middlePos) <= 25) {
                    f.selected = !f.selected;
                    repaint();
                } else if (f.selected) {
                    f.setAnimEndPos(
                            new Vector2(pos2.x - (f.getSize().x / 2),pos2.y - (f.getSize().y / 2)));
                }

            }

        }

        repaint();
    }


    // Getter and Setter
    public Color getColor() {
        final Color color = this.color;
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        this.Mode = mode;
        repaint();
    }

    public ArrayList<Form> getForms() {
        return Forms;
    }

    public void setTool(String tool) {
        this.Tool = tool;
    }

    public String getTool() {
        return Tool;
    }

    public String getShape() {
        return Shape;
    }

    public void setShape(String shape) {
        Shape = shape;
    }

    public void setForms(ArrayList<Form> forms) {
        Forms = forms;
    }

    public ArrayList<Form> getCustom1() {
        return Custom1;
    }

    public void setCustom1(ArrayList<Form> custom1) {
        Custom1 = custom1;
    }

}
