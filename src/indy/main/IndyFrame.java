package indy.main;

import indy.util.ClassScanner;
import indy.util.DisposeWindowAdapter;
import indy.util.Globals;
import indy.util.GuiUtil;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 6, 2007
 * Time: 3:07:50 PM
 */
public class IndyFrame extends JXFrame implements PropertyChangeListener {

    private static final String TITLE = "Indy";
    private static final Dimension SIZE = new Dimension(800, 600);
    private static final String ICON = "indy.png";

    private IndySheet indySheet;
    private boolean exit = false;
    private boolean animatedClose;

    public IndyFrame(boolean animatedClose) {

        this.animatedClose = animatedClose;

        indySheet = new IndySheet(this);

        setTitle(TITLE);
        setSize(SIZE);
        setIconImage(GuiUtil.getImage(ICON));

        // scan from the specified package/subpackage (ex: "indy.action")
        ClassScanner scanner = new ClassScanner("indy");
        Globals.setScanner(scanner);

        init();

        IndyToolbar tb = new IndyToolbar();
        getRootPaneExt().setToolBar(tb);

        IndyMenuBar menu = new IndyMenuBar();
        getRootPaneExt().setJMenuBar(menu);

        addWindowListener(new DisposeWindowAdapter());

        if (animatedClose) {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        } else {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        GuiUtil.center(this);
    }


    public void exit() {
        if (animatedClose) {
            JOptionPane optionPane = new JOptionPane("Do you want to exit?",
                    JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            optionPane.addPropertyChangeListener(IndyFrame.this);
            JDialog dialog = optionPane.createDialog(IndyFrame.this, "irrelevant");
            GuiUtil.setBackground(dialog, GuiUtil.getBasicColor());
            indySheet.showJDialogAsSheet(dialog);
        } else {
            System.exit(0);
        }
    }    

    // for JOptionPane
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) {
            if (Integer.parseInt(pce.getNewValue().toString()) == 0) {
                exit = true;
            }
            indySheet.setExit(exit);
            indySheet.hideSheet();
        }
    }

    public boolean isBlocked() {
        return indySheet.isBlocked();
    }
    
    protected void init() {
    }

}
