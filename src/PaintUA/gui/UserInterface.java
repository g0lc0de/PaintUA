package PaintUA.gui;

import PaintUA.main.AnimationThread;
import PaintUA.main.Form;
import PaintUA.main.PaintUA;
import PaintUA.util.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UserInterface extends JPanel {
    private static final long serialVersionUID = -1824421380373108895L;

    public JButtonPlus PlayButton = new JButtonPlus("Play");

    private final JButtonPlus Draw = new JButtonPlus("Draw");
    private final JButtonPlus Animate = new JButtonPlus("Animate");

    private final ColorPicker colPicker = new ColorPicker();
    private final JButtonPlus MoreColors = new JButtonPlus();
    private final JLabel DisplayActiveColor = new JLabel();

    private final JButtonPlus FillColorButton = new JButtonPlus();
    private final JButtonPlus NewButton = new JButtonPlus();
    private final JButtonPlus MoveButton = new JButtonPlus("Resize");

    private final JButtonPlus Circle = new JButtonPlus();
    private final JButtonPlus Rectangle= new JButtonPlus();

    private final JButtonPlus DrawHouse = new JButtonPlus("Custom 1 Draw");
    private final JButtonPlus SaveHouse = new JButtonPlus("Custom 1 Save");

    // Saved Images Buttons

    // Images
    private BufferedImage FillColorLogo = null;
    private BufferedImage FillColorLogo_Color = null;

    private BufferedImage NewLogoLight = null;
    private BufferedImage NewLogoDark = null;

    private BufferedImage CircleImage = null;
    private BufferedImage BiggerCircleImage = null;

    AnimationThread AT;

    private PaintUA app;

    public UserInterface() {
        super();
        init();
    }

    public UserInterface(PaintUA app) {
        super();
        this.app = app;
        this.AT = new AnimationThread(app);
        init();
    }

    private void init() {

        setLayout(null);
        setBackground(new Color(0,0,0,0));
        setBounds(0,0,1200,800);
        setOpaque(false);

        // Load Images
        // Fill Color Button

        try {

            AffineTransform at = new AffineTransform();
            at.scale(1.4, 1.4);
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

            FillColorLogo = ImageIO.read(new File("res/PaintBucket.png"));
            FillColorLogo_Color = ImageIO.read(new File("res/PaintBucketColor.png"));

            NewLogoLight = ImageIO.read(new File("res/LightPaintNew.png"));
            NewLogoDark = ImageIO.read(new File("res/DarkPaintNew.png"));

            CircleImage = ImageIO.read(new File("res/Circle.png"));
            BiggerCircleImage = new BufferedImage(70,70,BufferedImage.TYPE_INT_ARGB);
            BiggerCircleImage = scaleOp.filter(CircleImage, BiggerCircleImage);

        } catch (Exception e) {
            // TODO: handle exception

        }


        // setBounds
        PlayButton.setBounds(1080,680,75,50);
        Draw.setBounds(25,680,75,50);
        Animate.setBounds(100,680,75,50);
        NewButton.setBounds(120, 320, 50, 50);
        FillColorButton.setBounds(25, 320, 50, 50);
        Rectangle.setBounds(120, 390, 50, 50);
        Circle.setBounds(15, 380, 70, 70);
        SaveHouse.setBounds(10, 500, 80, 50);
        DrawHouse.setBounds(110, 500, 80, 50);
        MoreColors.setBounds(180,50,15,15);
        DisplayActiveColor.setBounds(25,250,75,50);
        MoveButton.setBounds(120,460,50,50);

        // setColor
        PlayButton.setForeground(Color.BLACK);
        PlayButton.setJButtonPlusColor(Color.GREEN, new Color(20,170,20));
        Draw.setJButtonPlusColor(Color.GRAY,Color.GRAY);
        Animate.setJButtonPlusColor(new Color(220,220,220), new Color(220,220,220));
        NewButton.setJButtonPlusColor(Color.DARK_GRAY,Color.DARK_GRAY);
        FillColorButton.setJButtonPlusColor(Color.DARK_GRAY,Color.DARK_GRAY);
        SaveHouse.setJButtonPlusColor(Color.WHITE, Color.WHITE);
        DrawHouse.setJButtonPlusColor(Color.WHITE, Color.WHITE);
        MoreColors.setJButtonPlusColor(Color.CYAN, Color.GREEN);
        DisplayActiveColor.setBackground(Color.GRAY);
        MoveButton.setJButtonPlusColor(Color.blue,Color.blue);

        // setFont
        SaveHouse.setFont(new Font("Monospace", Font.PLAIN,11));
        DrawHouse.setFont(new Font("Monospace", Font.PLAIN,11));

        // Bi Settings
        NewButton.setIcon(new ImageIcon(NewLogoLight));
        changeBufferedImageColor(FillColorLogo, new Color(80,80,180));
        FillColorButton.setIcon(new ImageIcon(FillColorLogo));
        changeBufferedImageColor(BiggerCircleImage, app.getCanvas().getColor());
        Circle.setIcon(new ImageIcon(BiggerCircleImage));

        if (AT.activeFrame == AT.FrameCount) {
            PlayButton.setJButtonPlusColor(Color.GREEN, new Color(20,170,20));
        }

        PlayButton.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                if (AT.getState() == Thread.State.TERMINATED || AT.getState() == Thread.State.NEW) {
                    AT = new AnimationThread(app,app.getMenuBar().getFPS(),app.getMenuBar().getFrameCount());
                    AT.start();
                }
            }
        });

        Draw.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                if (AT.getState() != Thread.State.RUNNABLE && AT.getState() != Thread.State.TIMED_WAITING) {
                    Draw.setJButtonPlusColor(Color.GRAY, Color.GRAY);
                    Animate.setJButtonPlusColor(new Color(220,220,220), new Color(220,220,220));

                    NewButton.setVisible(true);
                    FillColorButton.setVisible(true);

                    app.getCanvas().setMode("DRAW");
                }
            }

        });

        Animate.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                if (AT.getState() != Thread.State.RUNNABLE && AT.getState() != Thread.State.TIMED_WAITING) {
                    Animate.setBackground(Color.GRAY);
                    Animate.setPressedColor(Color.GRAY);
                    Animate.setRolloverColor(Color.GRAY);

                    Draw.setBackground(new Color(220,220,220));
                    Draw.setPressedColor(new Color(220,220,220));
                    Draw.setRolloverColor(new Color(220,220,220));

                    NewButton.setVisible(false);
                    FillColorButton.setVisible(false);

                    app.getCanvas().setMode("ANIMATE");
                }
            }
        });

        colPicker.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                Color newColor = colPicker.BiLinearinterpolateColor((float) evt.getX() / colPicker.getWidth(), (float) evt.getY() / colPicker.colorfulPickerHeight);
                if (evt.getY() > colPicker.colorfulPickerHeight) {
                    newColor = colPicker.interpolateColor(new Color(0.0f,0.0f,0.0f,1.0f),new Color(1.0f,1.0f,1.0f,1.0f), (float) evt.getX() / colPicker.getWidth());
                }
                colPicker.setXY(evt.getX(), evt.getY());
                DisplayActiveColor.setBackground(newColor);
                setUIButtonColors(newColor);
            }

            public void mouseReleased(MouseEvent evt) {
                Color newColor = colPicker.BiLinearinterpolateColor((float) evt.getX() / colPicker.getWidth(), (float) evt.getY() / colPicker.colorfulPickerHeight);
                if (evt.getY() > colPicker.colorfulPickerHeight) {
                    newColor = colPicker.interpolateColor(new Color(0.0f,0.0f,0.0f,1.0f),new Color(1.0f,1.0f,1.0f,1.0f), (float) evt.getX() / colPicker.getWidth());
                }
                DisplayActiveColor.setBackground(newColor);
                colPicker.setXY(evt.getX(), evt.getY());
                app.getCanvas().setColor(newColor);
                setUIButtonColors(newColor);
            }
        });
        colPicker.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                Color newColor = colPicker.BiLinearinterpolateColor((float) evt.getX() / colPicker.getWidth(), (float) evt.getY() / colPicker.colorfulPickerHeight);
                if (evt.getY() > colPicker.colorfulPickerHeight) {
                    newColor = colPicker.interpolateColor(new Color(0.0f,0.0f,0.0f,1.0f),new Color(1.0f,1.0f,1.0f,1.0f), (float) evt.getX() / colPicker.getWidth());
                }
                DisplayActiveColor.setBackground(newColor);
                colPicker.setXY(evt.getX(), evt.getY());
                setUIButtonColors(newColor);
            }
        });

        NewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                app.getCanvas().setTool("NEW");
                NewButton.setIcon(new ImageIcon(NewLogoLight));
                changeBufferedImageColor(FillColorLogo, new Color(80,80,180));
                setUIButtonColors(app.getCanvas().getColor());
                FillColorButton.setIcon(new ImageIcon(overlayBufferedImages(FillColorLogo,FillColorLogo_Color)));
                Circle.setVisible(true);
                Rectangle.setVisible(true);
            }
        });

        FillColorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                app.getCanvas().setTool("FILL");
                NewButton.setIcon(new ImageIcon(NewLogoDark));
                changeBufferedImageColor(FillColorLogo, new Color(100,100,255));
                FillColorButton.setIcon(new ImageIcon(overlayBufferedImages(FillColorLogo,FillColorLogo_Color)));
                setUIButtonColors(app.getCanvas().getColor());
                Circle.setVisible(false);
                Rectangle.setVisible(false);
            }
        });

        DisplayActiveColor.setOpaque(true);
        DisplayActiveColor.setVisible(true);

        // circle
        Circle.setJButtonPlusColor(Color.DARK_GRAY, Color.DARK_GRAY);
        Circle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                changeBufferedImageColor(BiggerCircleImage, app.getCanvas().getColor());
                Circle.setBounds(15, 380, 70, 70);
                Circle.setIcon(new ImageIcon(BiggerCircleImage));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (!app.getCanvas().getShape().equals("CIRCLE")) {
                    changeBufferedImageColor(CircleImage, app.getCanvas().getColor());
                    Circle.setBounds(25, 390, 50, 50);
                    Circle.setIcon(new ImageIcon(CircleImage));
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                app.getCanvas().setShape("CIRCLE");
                setUIButtonColors(app.getCanvas().getColor());
                Circle.setBounds(15, 380, 70, 70);
                Circle.setIcon(new ImageIcon(BiggerCircleImage));

                Rectangle.setBounds(120, 390, 50, 50);
            }
        });

        // Rectangle
        Rectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                Rectangle.setBounds(110, 380, 70, 70);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (!app.getCanvas().getShape().equals("RECTANGLE")) {
                    Rectangle.setBounds(120, 390, 50, 50);
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                app.getCanvas().setShape("RECTANGLE");
                setUIButtonColors(app.getCanvas().getColor());
                Rectangle.setBounds(110, 380, 70, 70);
                Circle.setBounds(25, 390, 50, 50);
            }
        });


        // Save in Custom 1
        SaveHouse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                // Get the Middle
                Vector2 middle = new Vector2(0,0);
                int minX = 0;
                int maxX = 0;
                int minY = 0;
                int maxY = 0;
                int widthSum = 0;
                int heightSum = 0;
                ArrayList<Form> MultiForm = app.getCanvas().getForms();
                for (Form f : MultiForm) {
                    widthSum += f.getPos().x;
                    heightSum += f.getPos().y;
                    if (f.getPos().x + f.getSize().x > maxX) {
                        maxX = (int) (f.getPos().x + f.getSize().x/2);
                    } else if(f.getPos().x < minX) {
                        minX = (int) (f.getPos().x);
                    }

                    if (f.getPos().y + f.getSize().y > maxY) {
                        maxY = (int) (f.getPos().y + f.getSize().y);
                    } else if(f.getPos().y < minY) {
                        minY = (int) (f.getPos().y);
                    }
                }
                middle.x = (maxX + minX) / 2;
                middle.y = (maxY + minY) / 2;

                // Set Distance to middle
                for (Form f : MultiForm) {
                    int diffX = (int) (f.getPos().x - middle.x - widthSum/MultiForm.size()/2);
                    int diffY = (int) (f.getPos().y - middle.y - heightSum/MultiForm.size()/2);
                    f.setDistanceToMiddle(new Vector2(diffX,diffY));
                }
                app.getCanvas().setCustom1(MultiForm);
                app.getCanvas().setForms(new ArrayList<>());
                app.getCanvas().repaint();
            }
        });

        DrawHouse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                app.getCanvas().setShape("C1");
            }
        });

        MoreColors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                Color col = null;
                col = JColorChooser.showDialog(null, "Choose a color", col);
                app.getCanvas().setColor(col);
                setUIButtonColors(col);
            }
        });

        // Color of the Colorful parts of the ui
        Color starterColor = app.getCanvas().getColor();
        setUIButtonColors(starterColor);
        DisplayActiveColor.setBackground(starterColor);

        add(PlayButton);
        add(Draw);
        add(Animate);
        add(colPicker);
        add(DisplayActiveColor);
        add(NewButton);
        add(FillColorButton);
        add(Circle);
        add(Rectangle);
        add(SaveHouse);
        add(DrawHouse);
        add(MoreColors);
        add(MoveButton);
    }


    public void setUIButtonColors(Color c) {
        if (app.getCanvas().getTool().equals("FILL")) {
            FillColorLogo_Color = changeBufferedImageColor(FillColorLogo_Color,c);
        } else {
            FillColorLogo_Color = changeBufferedImageColor(FillColorLogo_Color,Color.GRAY);
        }
        FillColorButton.setIcon(new ImageIcon(overlayBufferedImages(FillColorLogo, FillColorLogo_Color)));

        if (app.getCanvas().getShape().equals("CIRCLE")) {
            changeBufferedImageColor(CircleImage, c);
            changeBufferedImageColor(BiggerCircleImage, c);
            Circle.setIcon(new ImageIcon(BiggerCircleImage));
            Rectangle.setJButtonPlusColor(Color.GRAY,Color.GRAY);
        } else if (app.getCanvas().getShape().equals("RECTANGLE")) {
            changeBufferedImageColor(CircleImage, Color.GRAY);
            Circle.setIcon(new ImageIcon(CircleImage));
            Rectangle.setJButtonPlusColor(c, c);
        } else {
            changeBufferedImageColor(CircleImage, Color.GRAY);
            Circle.setIcon(new ImageIcon(CircleImage));
            Rectangle.setJButtonPlusColor(Color.GRAY,Color.GRAY);
        }

    }

    public BufferedImage overlayBufferedImages(BufferedImage bi1, BufferedImage bi2) {
        BufferedImage combinedBi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics g1 = combinedBi.getGraphics();
        g1.drawImage(bi1, 0, 0, null);
        g1.drawImage(bi2, 0, 0, null);
        return combinedBi;
    }

    public BufferedImage changeBufferedImageColor(BufferedImage Bi,Color colorToSet) {
        for (int x = 0; x < Bi.getWidth(); x++) {
            for (int y = 0; y < Bi.getHeight(); y++) {
                int c1 = Bi.getRGB(x, y);
                if (c1 < 0) {
                    Bi.setRGB(x, y, colorToSet.getRGB());
                }

            }
        }
        return Bi;
    }

}

