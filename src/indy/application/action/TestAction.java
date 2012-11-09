package indy.application.action;

import indy.annotation.ToolbarAction;
import indy.annotation.MenuAction;
import indy.util.GuiUtil;
import static indy.application.ApplicationConfig.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 13, 2007
 * Time: 11:40:29 AM
 */
@ToolbarAction(index =1)
@MenuAction(path=ACTION_MENU_PATH, index=2)
public class TestAction extends AbstractAction {

    public TestAction() {
        putValue(Action.NAME, "Test");        
        putValue(Action.SMALL_ICON, GuiUtil.getImageIcon("Hat.jpg"));
        putValue(Action.MNEMONIC_KEY, new Integer('T'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.SHORT_DESCRIPTION, "Test");
        putValue(Action.LONG_DESCRIPTION, "Test");
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("-------------------->TEST<---------------------");
    }


}
