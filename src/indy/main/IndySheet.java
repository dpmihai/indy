package indy.main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Jan 23, 2008
 * Time: 4:51:21 PM
 */
public class IndySheet implements ActionListener {

    private static final int INCOMING = 1;
    private static final int OUTGOING = -1;
    private static final float ANIMATION_DURATION = 600f;
    private static final int ANIMATION_SLEEP = 50;

    private JComponent sheet;
    private JPanel glass;
    private AnimatingSheet animatingSheet;
    private boolean animating;
    private int animationDirection;
    private Timer animationTimer;
    private long animationStart;
    private BufferedImage offscreenImage;
    private IndyFrame parentFrame;
    private boolean exit = false;

    public IndySheet(IndyFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JComponent showJDialogAsSheet(JDialog dialog) {
        glass = (JPanel) parentFrame.getGlassPane();
        // block user inputs
        glass.addMouseListener(new MouseAdapter() {});
        glass.addMouseMotionListener(new MouseMotionAdapter() {});
        glass.addKeyListener(new KeyAdapter() {});
        glass.setFocusTraversalKeysEnabled(false);           // disable TAB
        glass.addComponentListener(new ComponentAdapter() {  // disable keys
            public void componentShown(ComponentEvent e) {
                parentFrame.requestFocusInWindow();
            }
        });


        glass.setLayout(new GridBagLayout());
        animatingSheet = new AnimatingSheet();
        animatingSheet.setBorder(new LineBorder(Color.black, 1));

        sheet = (JComponent) dialog.getContentPane();
        sheet.setBorder(new LineBorder(Color.black, 1));
        glass.removeAll();
        animationDirection = INCOMING;
        startAnimation();
        return sheet;
    }

    public boolean isBlocked() {
        return ((glass != null) && glass.isVisible());
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void hideSheet() {
        animationDirection = OUTGOING;
        startAnimation();
    }

    private void startAnimation() {
        glass.repaint();
        // clear glasspane and set up animatingSheet
        animatingSheet.setSource(sheet);
        glass.removeAll();
        glass.add(animatingSheet, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
        glass.add(Box.createGlue(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0));
        glass.setVisible(true);

        // start animation timer
        animationStart = System.currentTimeMillis();
        if (animationTimer == null) {
            animationTimer = new Timer(ANIMATION_SLEEP, this);
        }
        animating = true;
        animationTimer.start();
    }

    private void stopAnimation() {
        animationTimer.stop();
        animating = false;
    }

    // used by the Timer
    public void actionPerformed(ActionEvent e) {
        if (animating) {
            // calculate height to show
            float animationPercent = (System.currentTimeMillis() - animationStart) / ANIMATION_DURATION;
            animationPercent = Math.min(1.0f, animationPercent);
            int animatingHeight = 0;
            if (animationDirection == INCOMING) {
                animatingHeight = (int) (animationPercent * sheet.getHeight());
            } else {
                animatingHeight = (int) ((1.0f - animationPercent) * sheet.getHeight());
            }
            // clip off that much from sheet and blit it into animatingSheet
            animatingSheet.setAnimatingHeight(animatingHeight);
            animatingSheet.repaint();

            if (animationPercent >= 1.0f) {
                stopAnimation();
                if (animationDirection == INCOMING) {
                    finishShowingSheet();
                } else {
                    glass.removeAll();
                    glass.setVisible(false);
                }
                if (exit) {
                    System.exit(0);
                }
            }
        }
    }

    private void finishShowingSheet() {
        glass.removeAll();
        glass.add(sheet, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
        glass.add(Box.createGlue(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0));
        glass.revalidate();
        glass.repaint();
    }


    class AnimatingSheet extends JPanel {
        Dimension animatingSize = new Dimension(0, 1);
        JComponent source;
        BufferedImage offscreenImage;

        public AnimatingSheet() {
            super();
            setOpaque(true);
        }

        public void setSource(JComponent source) {
            this.source = source;
            animatingSize.width = source.getWidth();
            makeOffscreenImage(source);
        }

        public void setAnimatingHeight(int height) {
            animatingSize.height = height;
            setSize(animatingSize);
        }

        private void makeOffscreenImage(JComponent source) {
            GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();
            offscreenImage = gfxConfig.createCompatibleImage(source.getWidth(), source.getHeight());
            Graphics2D offscreenGraphics = (Graphics2D) offscreenImage.getGraphics();
            source.paint(offscreenGraphics);
        }

        public Dimension getPreferredSize() {
            return animatingSize;
        }

        public Dimension getMinimumSize() {
            return animatingSize;
        }

        public Dimension getMaximumSize() {
            return animatingSize;
        }

        public void paint(Graphics g) {
            // get the bottom-most n pixels of source and
            // paint them into g, where n is height
            BufferedImage fragment =
                    offscreenImage.getSubimage(0,
                            offscreenImage.getHeight() - animatingSize.height,
                            source.getWidth(),
                            animatingSize.height);
            g.drawImage(fragment, 0, 0, this);
            g.drawRect(0, 0, fragment.getWidth() - 1, fragment.getHeight() - 1);
        }
    }


}
