package indy.util;

import com.sun.imageio.plugins.common.ImageUtil;

import javax.swing.*;
import java.awt.*;

import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.MattePainter;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 6, 2007
 * Time: 3:26:15 PM
 */
public class GuiUtil {

    private GuiUtil(){        
    }

    public static  void center(JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point center = ge.getCenterPoint();
        Rectangle bounds = ge.getMaximumWindowBounds();
        int w = Math.max(bounds.width / 2, Math.min(frame.getWidth(), bounds.width));
        int h = Math.max(bounds.height / 2, Math.min(frame.getHeight(), bounds.height));
        int x = center.x - w / 2, y = center.y - h / 2;
        frame.setBounds(x, y, w, h);
        if (w == bounds.width && h == bounds.height) {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        frame.validate();
    }
    
    public static void center(Component parent, Component c) {
        Dimension dimParent = parent.getSize();
        Point p = parent.getLocation();
        Dimension dimComp = c.getSize();
        c.setLocation(p.x + (dimParent.width - dimComp.width)/2,
                      p.y + (dimParent.height - dimComp.height)/2);
    }

    public static ImageIcon getImageIcon(String image) {
        return new ImageIcon(ImageUtil.class.getResource("/images/" + image));
    }

    public static Image getImage(String image) {
        return getImageIcon(image).getImage();
    }

    public static Painter getPainter(int width, int height) {        
        Color color1 = new Color(255, 255, 255, 126);
        Color color2 = new Color(132, 168, 232, 126);
        LinearGradientPaint gradientPaint =
                new LinearGradientPaint(0.0f, 0.0f, width, height,
                        new float[]{0.0f, 1.0f},
                        new Color[]{color1, color2});
        MattePainter mattePainter = new MattePainter(gradientPaint);
        return mattePainter;
    }

    public static Color getBasicColor() {
        return new Color(206, 220, 236);
    }

    public static void setBackground(Component c, Color color) {
        c.setBackground(color);
        if (c instanceof Container) {
            Component[] cs = ((Container) c).getComponents();
            for (Component c1 : cs) {
                c1.setBackground(color);
                if (c1 instanceof Container) {
                    setBackground(c1, color);
                }
            }
        }
    }
}
