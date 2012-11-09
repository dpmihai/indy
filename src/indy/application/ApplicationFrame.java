package indy.application;

import indy.annotation.Implement;
import indy.main.IndyFrame;
import indy.util.GuiUtil;
import org.jdesktop.swingx.JXPanel;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 26, 2007
 * Time: 2:49:21 PM
 */
public class ApplicationFrame extends IndyFrame {

    public ApplicationFrame() {
        super(true);
    }

    @Implement
    @Override
    protected void init() {
        //Locale.setDefault(new Locale("ro", "RO"));
        JXPanel panel = new JXPanel();
        int width = getSize().width / 2;
        int height = 200;
        panel.setBackgroundPainter(GuiUtil.getPainter(width, height));        
        addComponent(panel);
    }

//    private Painter getPainter() {
//        GlossPainter gloss = new GlossPainter();
//
//        PinstripePainter stripes = new PinstripePainter();
//        stripes.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.17f));
//        stripes.setSpacing(5.0);
//
//        MattePainter matte = new MattePainter(new Color(51, 51, 51));
//
//        return new CompoundPainter(matte, stripes, gloss);
//    }

//    private Painter getPainter() {
//        MattePainter mp = new MattePainter(new Color(132, 168, 232, 126));
//        GlossPainter gp = new GlossPainter(new Color(204, 232, 249, 76), GlossPainter.GlossPosition.TOP);
//        PinstripePainter pp = new PinstripePainter(new Color(180, 183, 190, 51), 45d);
//        return (new CompoundPainter(mp, pp, gp));
//    }

    
}
