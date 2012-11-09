package indy.application.action.other;

import indy.util.GuiUtil;
import indy.annotation.ToolbarAction;
import indy.annotation.MenuAction;
import static indy.application.ApplicationConfig.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 13, 2007
 * Time: 3:05:40 PM
 */
@ToolbarAction(index =2)
@MenuAction(path=ACTION_MENU_PATH, index=1)
public class OtherAction extends AbstractAction {

    public OtherAction() {
        putValue(Action.NAME, "Other");
        putValue(Action.SMALL_ICON, GuiUtil.getImageIcon("Sandbag.jpg"));
        putValue(Action.MNEMONIC_KEY, new Integer('O'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.SHORT_DESCRIPTION, "Other");
        putValue(Action.LONG_DESCRIPTION, "Other");
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("-------------------->Other<---------------------");
    }


}
