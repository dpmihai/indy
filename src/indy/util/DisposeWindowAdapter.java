package indy.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 21, 2007
 * Time: 4:18:47 PM
 */
public class DisposeWindowAdapter extends WindowAdapter {

    public void windowActivated(WindowEvent e) {
        job();
    }

    private void job() {
        System.out.println("DISPOSE");
        if (Globals.getAboutWindow() != null) {
            Globals.getAboutWindow().dispose();
            Globals.setAboutWindow(null);
        }
        if (Globals.getCreditsWindow() != null) {
            Globals.getCreditsWindow().dispose();
            Globals.setCreditsWindow(null);
        }
    }
}
