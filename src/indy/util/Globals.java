package indy.util;

import indy.main.IndyLoader;
import indy.main.IndyFrame;

import javax.swing.*;


/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 7, 2007
 * Time: 11:16:33 AM
 */
public class Globals {

    private static IndyFrame mainFrame;
    private static TransparentWindow aboutWindow;
    private static JDialog creditsWindow;
    private static ClassScanner scanner;

    public static IndyFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(IndyFrame mainFrame) {
        Globals.mainFrame = mainFrame;
    }


    public static TransparentWindow getAboutWindow() {
        return aboutWindow;
    }

    public static void setAboutWindow(TransparentWindow aboutWindow) {
        Globals.aboutWindow = aboutWindow;
    }


    public static JDialog getCreditsWindow() {
        return creditsWindow;
    }

    public static void setCreditsWindow(JDialog creditsWindow) {
        Globals.creditsWindow = creditsWindow;
    }


    public static ClassScanner getScanner() {
        return scanner;
    }

    public static void setScanner(ClassScanner scanner) {
        Globals.scanner = scanner;
    }
}
