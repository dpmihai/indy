package indy.main;

import com.jgoodies.looks.Options;
import com.jgoodies.looks.HeaderStyle;

import javax.swing.*;

import indy.util.*;
import indy.annotation.ToolbarAction;

import java.util.List;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 7, 2007
 * Time: 10:46:17 AM
 */
public class IndyToolbar extends JToolBar {

    @SuppressWarnings({"unchecked"})
    public IndyToolbar() {
        putClientProperty("JToolBar.isRollover", Boolean.TRUE);       // hide buttons borders
        putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
        setBackground(Color.WHITE);

        //GlobalHotkeyManager hotkeyManager = GlobalHotkeyManager.getInstance();

        // Add all actions which have a ToolbarAction annotation        
        try {
            List<AnnotatedClass> actions = Globals.getScanner().getToolbarActionsClasses();
            for (AnnotatedClass c : actions) {
                System.out.println("toolbarClass="+c.getClazz().getName());

                if ( ((ToolbarAction)c.getAnnotation()).separator().equals(ToolbarAction.ToolbarSeparator.YES) ) {
                     addCustomSeparator();
                }
                AbstractAction action = (AbstractAction)c.getClazz().newInstance();

                // all toolbar actions are globally registered with the keystroke from accelerator key (if exists)
//                if (((ToolbarAction) c.getAnnotation()).globalShortcut()) {
//                    Object key = action.getValue(Action.NAME);
//                    KeyStroke ks = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
//                    if (ks != null) {
//                        hotkeyManager.getInputMap().put(ks, key);
//                        hotkeyManager.getActionMap().put(key, action);
//                    }
//                }

                add(action);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addCustomSeparator() {
        JLabel label = new JLabel(GuiUtil.getImageIcon("separator.gif"));
        addSeparator();
        add(label);
        addSeparator();
    }


}
