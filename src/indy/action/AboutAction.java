package indy.action;

import indy.util.GuiUtil;
import indy.util.StarTransparentWindow;
import indy.util.Globals;
import indy.util.I18nSupport;
import indy.annotation.ToolbarAction;
import indy.annotation.MenuAction;
import static indy.application.ApplicationConfig.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 20, 2007
 * Time: 5:15:39 PM
 */
@ToolbarAction(separator=ToolbarAction.ToolbarSeparator.YES, index =199)
@MenuAction(path=HELP_MENU_PATH, index=199)
public class AboutAction extends AbstractAction {

    public AboutAction() {
        putValue(Action.NAME, I18nSupport.getString("AboutAction.name"));
        putValue(Action.SMALL_ICON, GuiUtil.getImageIcon("Idol.jpg"));
        putValue(Action.MNEMONIC_KEY, new Integer('A'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.SHORT_DESCRIPTION, I18nSupport.getString("AboutAction.shortDescription"));
        putValue(Action.LONG_DESCRIPTION, I18nSupport.getString("AboutAction.longDescription"));
    }

    public void actionPerformed(ActionEvent e) {
        StarTransparentWindow stw = new StarTransparentWindow(Globals.getMainFrame(), 250, 250, true);
        Globals.setAboutWindow(stw);
    }


}
