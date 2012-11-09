package indy.action;

import indy.util.GuiUtil;
import indy.util.Globals;
import indy.annotation.MenuAction;
import static indy.application.ApplicationConfig.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 27, 2007
 * Time: 2:05:59 PM
 */
@MenuAction(path=FILE_MENU_PATH, index=3)
public class ExitAction extends AbstractAction {

    public ExitAction() {
        putValue(Action.NAME, "Exit");
        putValue(Action.SMALL_ICON, GuiUtil.getImageIcon("Boulder.jpg"));
        putValue(Action.MNEMONIC_KEY, new Integer('X'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.SHORT_DESCRIPTION, "Exit Indy");
        putValue(Action.LONG_DESCRIPTION, "Exit Indy");
    }

    public void actionPerformed(ActionEvent e) {
        Globals.getMainFrame().exit();
    }


}
