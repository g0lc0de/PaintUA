package PaintUA.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JPanel {

    private final JButtonPlus File = new JButtonPlus("File");
    private final JPanel FileHoverItemsPanel = new JPanel();
    private final JButtonPlus OpenFile = new JButtonPlus("Open File");
    private final JButtonPlus SaveAs = new JButtonPlus("Save As");

    private final Color bgWhite = new Color(245,245,250);
    private final Font normalFont = new Font("Monospace", Font.PLAIN, 15);

    private final JButtonPlus Help = new JButtonPlus("Help");
    //private JMenuItem Instructions = new JMenuItem("Instructions");

    private final JButtonPlus AnimationSettings = new JButtonPlus("Animation Settings");
    private final JDialog AnimationSettingsDialog = new JDialog();
    private final JButtonPlus SubmitBtn = new JButtonPlus("Submit");

    private int FrameCount = 120;
    private int FPS = 60;

    public MenuBar() {
        super();

        setVisible(true);
        setOpaque(false);
        setBackground(new Color(0,0,0,0));
        setLayout(null);

        File.setBounds(0,0,50, 25);
        File.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        File.setFont(normalFont);
        File.setJButtonPlusColor(bgWhite,new Color(235,235,250));
        makeJPanelVisibleWhileOnBtn(FileHoverItemsPanel, File);


        FileHoverItemsPanel.setLayout(null);
        FileHoverItemsPanel.setBounds(0, 25, 100, 60);
        FileHoverItemsPanel.setLayout(new GridLayout(0,1));
        FileHoverItemsPanel.setBackground(bgWhite);
        FileHoverItemsPanel.setVisible(false);
        FileHoverItemsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                FileHoverItemsPanel.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                FileHoverItemsPanel.setVisible(false);
            }
        });
        add(FileHoverItemsPanel);

        OpenFile.setFont(normalFont);
        makeJPanelVisibleWhileOnBtn(FileHoverItemsPanel, OpenFile);
        OpenFile.setJButtonPlusColor(bgWhite, Color.LIGHT_GRAY);
        FileHoverItemsPanel.add(OpenFile);

        SaveAs.setFont(normalFont);
        makeJPanelVisibleWhileOnBtn(FileHoverItemsPanel, SaveAs);
        SaveAs.setJButtonPlusColor(bgWhite, Color.LIGHT_GRAY);
        FileHoverItemsPanel.add(SaveAs);

        Help.setBounds(50,0,50, 25);
        Help.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Help.setFont(normalFont);
        Help.setJButtonPlusColor(bgWhite,new Color(235,235,250));

        AnimationSettings.setBounds(100,0,150, 25);
        AnimationSettings.setJButtonPlusColor(bgWhite,new Color(235,235,250));
        AnimationSettings.setFont(normalFont);
        AnimationSettings.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        AnimationSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                AnimationSettingsDialog.setVisible(true);
            }
        });

        AnimationSettingsDialog.setBounds(getWidth() / 2 - 200,getHeight() / 2 - 200,400, 300);
        AnimationSettingsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        AnimationSettingsDialog.setLocationRelativeTo(null);
        AnimationSettingsDialog.setResizable(false);

        JPanel FilePanel = new JPanel();
        FilePanel.setVisible(true);
        FilePanel.setBounds(0, 50, 100, 100);
        FilePanel.setBackground(Color.RED);

        JLabel DialogHeader = new JLabel("Animation Settings");
        DialogHeader.setVisible(true);
        DialogHeader.setBounds(0, 0, AnimationSettingsDialog.getWidth(), 50);
        DialogHeader.setBorder(null);
        DialogHeader.setOpaque(false);
        DialogHeader.setHorizontalAlignment(JLabel.CENTER);

        JTextField FPS_Input = new JTextField();
        FPS_Input.setVisible(true);
        FPS_Input.setBounds(140, 60, 160, 30);
        FPS_Input.setBorder(null);
        FPS_Input.setText(Integer.toString(FPS));

        JTextField FrameCount_Input = new JTextField();
        FrameCount_Input.setVisible(true);
        FrameCount_Input.setBounds(140, 120, 160, 30);
        FrameCount_Input.setBorder(null);
        FrameCount_Input.setText(Integer.toString(FrameCount));

        JLabel FPS_Label = new JLabel("FPS: ");
        FPS_Label.setVisible(true);
        FPS_Label.setBorder(null);
        FPS_Label.setOpaque(false);
        FPS_Label.setBounds(105, 60, 100, 30);

        JLabel FrameCount_Label = new JLabel("Frame Count: ");
        FrameCount_Label.setVisible(true);
        FrameCount_Label.setBorder(null);
        FrameCount_Label.setOpaque(false);
        FrameCount_Label.setBounds(55, 120, 100, 30);

        SubmitBtn.setBounds(190 - 50, 190, 120, 50);
        SubmitBtn.setJButtonPlusColor(new Color(220,220,220), new Color(180,180,180));
        SubmitBtn.setFont(normalFont);
        SubmitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        FrameCount_Input.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent arg0) {}
            public void removeUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                // Revalidate String
                Runnable revalidate = new Runnable() {
                    @Override
                    public void run() {
                        String input = FrameCount_Input.getText().replaceAll("[^0-9]", "");
                        FrameCount_Input.setText(input);
                    }
                };
                SwingUtilities.invokeLater(revalidate);
            }
        });

        FPS_Input.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent arg0) {}
            public void removeUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                // Revalidate String
                Runnable revalidate = new Runnable() {
                    @Override
                    public void run() {
                        String input = FPS_Input.getText().replaceAll("[^0-9]", "");
                        FPS_Input.setText(input);
                    }
                };
                SwingUtilities.invokeLater(revalidate);
            }
        });

        SubmitBtn.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                AnimationSettingsDialog.setVisible(false);
                if (FPS_Input.getText().equalsIgnoreCase("")) {
                    FPS_Input.setText(Integer.toString(FPS));
                } else {
                    FPS = Integer.parseInt(FPS_Input.getText());
                }

                if (FrameCount_Input.getText().equalsIgnoreCase("")) {
                    FrameCount_Input.setText(Integer.toString(FrameCount));
                } else {
                    FrameCount = Integer.parseInt(FrameCount_Input.getText());
                }

            }

        });

        JPanel AnDialogContent = new JPanel();
        AnDialogContent.setLayout(null);
        AnDialogContent.add(SubmitBtn);
        AnDialogContent.add(FPS_Input);
        AnDialogContent.add(FPS_Label);
        AnDialogContent.add(FrameCount_Input);
        AnDialogContent.add(FrameCount_Label);
        AnDialogContent.add(DialogHeader);

        AnimationSettingsDialog.add(AnDialogContent);

        add(File);
        add(Help);
        add(AnimationSettings);
        add(FileHoverItemsPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(bgWhite);
        g2d.fillRect(0, 0, getWidth(), 25);
    }

    public int getFrameCount() {
        return FrameCount;
    }

    public JPanel getMenu() {
        return this;
    }

    public int getFPS() {
        return FPS;
    }

    private void makeJPanelVisibleWhileOnBtn(JPanel panel,JButtonPlus btn) {
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                panel.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                panel.setVisible(false);
            }
        });

    }

}