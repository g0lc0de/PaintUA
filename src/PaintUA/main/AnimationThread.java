package PaintUA.main;

import PaintUA.util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class AnimationThread extends Thread {
    private ArrayList<Form> Forms;
    private PaintUA app;
    public int FrameRate = 60; // In Frames per Second(fps)
    public int FrameCount = 120;
    public int activeFrame;

    private String OldCMode = "";

    double AnimationProgress = 0;

    public AnimationThread(PaintUA app) {
        super();
        this.Forms = app.getCanvas().getForms();
        this.app = app;
    }

    public AnimationThread(PaintUA app,int FrameRate,int FrameCount) {
        super();
        this.app = app;
        this.Forms = app.getCanvas().getForms();
        this.FrameRate = FrameRate;
        this.FrameCount = FrameCount;
    }

    @Override
    public void run() {
        OldCMode = app.getCanvas().getMode();
        app.getCanvas().setMode("ANIMATING");
        app.getUi().PlayButton.setJButtonPlusColor(Color.RED, new Color(220,50,50));

        System.out.println("ANIMATION STARTED");
        for (activeFrame = 0;activeFrame <= FrameCount;activeFrame++) {

            AnimationProgress = (double) activeFrame / (double) FrameCount;

            for (Form f : Forms) {

                if (activeFrame == FrameCount) {
                    f.setPos(f.getOriginalPos());
                } else {
                    // Get Pixels to move between Animation Start and Animation End
                    Vector2 dist = new Vector2(f.getAnimEndPos().x - f.getOriginalPos().x,f.getAnimEndPos().y - f.getOriginalPos().y);
                    Vector2 step = new Vector2(dist.x / FrameCount,dist.y / FrameCount);
                    //System.out.printf("%f : %f \n",step.x,step.y);

                    f.setPos(f.getPos().add(step));
                }
                app.getCanvas().repaint();
            }

            try {
                sleep(1000/FrameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("ANIMATION ENDED");

        app.getUi().PlayButton.setJButtonPlusColor(Color.GREEN, new Color(20,170,20));
        app.getCanvas().setMode(OldCMode);
    }

}

