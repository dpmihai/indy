package indy.action;

import indy.util.GuiUtil;
import indy.util.Globals;
import indy.util.I18nSupport;
import indy.credits.Person;
import indy.credits.Credits;
import indy.annotation.ToolbarAction;
import indy.annotation.MenuAction;
import indy.application.ApplicationConfig;
import static indy.application.ApplicationConfig.HELP_MENU_PATH;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 6, 2007
 * Time: 4:45:10 PM
 */
@ToolbarAction(index =200)
@MenuAction(path=HELP_MENU_PATH, index=200)
public class CreditsAction extends AbstractAction {

    public CreditsAction() {
        putValue(Action.NAME, I18nSupport.getString("CreditsAction.name"));
        putValue(Action.SMALL_ICON, GuiUtil.getImageIcon("Whip.jpg"));
        putValue(Action.MNEMONIC_KEY, new Integer('C'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.SHORT_DESCRIPTION, I18nSupport.getString("CreditsAction.shortDescription"));
        putValue(Action.LONG_DESCRIPTION, I18nSupport.getString("CreditsAction.longDescription"));        
    }

    public void actionPerformed(ActionEvent e) {        

        Person[] p = ApplicationConfig.getInstance().getCredits();

        final Credits panel = new Credits(p);
        final JDialog dialog = new JDialog(Globals.getMainFrame()) {
            public void dispose() {
                panel.stop();
                super.dispose();
            }
        };
        dialog.setUndecorated(true);        
        dialog.setLayout(new GridBagLayout());

        JLabel label = new JLabel("<html><b><font size=4>&nbsp;&nbsp;"+
                I18nSupport.getString("CreditsAction.title")+"</font></b></html>");

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setOpaque(true);
        label.setBackground(new Color(200, 180, 180));
        //label.setBackground(new Color(210, 210, 50));
        
        dialog.add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        dialog.add(panel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        dialog.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                dialog.dispose();
            }
            public void mousePressed(MouseEvent event) {                
                dialog.dispose();
            }
        });

        dialog.pack();
        GuiUtil.center(dialog.getParent(), dialog);
        Globals.setCreditsWindow(dialog);
        dialog.setVisible(true);

    }

}
