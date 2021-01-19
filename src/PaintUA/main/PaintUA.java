package PaintUA.main;

import PaintUA.gui.Canvas;
import PaintUA.gui.MenuBar;
import PaintUA.gui.UserInterface;

import javax.swing.*;
import java.awt.*;

public class PaintUA {

    private static String appState = "DRAW";

    private JFrame screen = new JFrame();

    private final Canvas canvas = new Canvas();
    private final UserInterface ui = new UserInterface(this);
    private final MenuBar MenuBar = new MenuBar();

    public PaintUA() {
        JLayeredPane LayeredFrameContainer = new JLayeredPane();

        screen.setSize(1200,825);
        screen.setBackground(Color.DARK_GRAY);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setTitle("PaintUA Experimental");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        screen.setLocation(dim.width/2-screen.getSize().width/2, dim.height/2-screen.getSize().height/2);

        screen.setResizable(false);
        screen.getContentPane().setBackground(Color.DARK_GRAY);

        // Menu bar
        MenuBar.setBounds(0,0, 1200, 825);

        LayeredFrameContainer.add(canvas,JLayeredPane.FRAME_CONTENT_LAYER);
        LayeredFrameContainer.add(ui,JLayeredPane.PALETTE_LAYER);
        LayeredFrameContainer.add(MenuBar,JLayeredPane.POPUP_LAYER);

        screen.getContentPane().add(LayeredFrameContainer);
        screen.setVisible(true);
    }

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        PaintUA.appState = appState;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public UserInterface getUi() {
        return ui;
    }

    public MenuBar getMenuBar() {
        return MenuBar;
    }

    public static void main(String[] args) {
        System.out.println("PAINT_UA START");
        //System.out.println(new java.io.File("src").getAbsolutePath());
        new PaintUA();
    }
}

