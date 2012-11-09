package indy.util;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 26, 2007
 * Time: 11:48:06 AM
 */
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Jul 31, 2007
 * Time: 10:16:04 AM
 */
public class GlobalHotkeyManager extends EventQueue {
    
    private static final boolean DEBUG = false;
    private static final GlobalHotkeyManager instance = new GlobalHotkeyManager();
    private final InputMap keyStrokes = new InputMap();
    private final ActionMap actions = new ActionMap();

    static {
        // here we register ourselves as a new link in the chain of responsibility
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(instance);
    }

    private GlobalHotkeyManager() {
    }

    public static GlobalHotkeyManager getInstance() {
        return instance;
    }

    public InputMap getInputMap() {
        return keyStrokes;
    }

    public ActionMap getActionMap() {
        return actions;
    }

    protected void dispatchEvent(AWTEvent event) {
        if (event instanceof KeyEvent) {
            // KeyStroke.getKeyStrokeForEvent converts an ordinary KeyEvent
            // to a keystroke, as stored in the InputMap.  Keep in mind that
            // Numpad keystrokes are different to ordinary keys,

            // the event is dispatch only if main frame is active (that means two global
            // actions which block the main frame cannot coexist)            
            if (Globals.getMainFrame().isActive() && !Globals.getMainFrame().isBlocked()) {
                KeyStroke ks = KeyStroke.getKeyStrokeForEvent((KeyEvent) event);
                if (DEBUG) System.out.println("KeyStroke=" + ks);
                String actionKey = (String) keyStrokes.get(ks);
                if (actionKey != null) {
                    if (DEBUG) System.out.println("ActionKey=" + actionKey);
                    Action action = actions.get(actionKey);
                    if (action != null && action.isEnabled()) {
                        action.actionPerformed(
                                new ActionEvent(event.getSource(), event.getID(),
                                        actionKey, ((KeyEvent) event).getModifiers()));
                        return; // consume event
                    }
                }
            }
        }        
        super.dispatchEvent(event);
    }
}
