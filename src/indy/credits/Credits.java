package indy.credits;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * Author: Mihai Dinca-Panaitescu
 * <p/>
 * User: mihai.panaitescu
 * <p/>
 * Date: Jul 1, 2005 Time: 4:08:10 PM
 */
public class Credits extends JPanel {

    private Picture imgPanel;
    private TypeWriter infoPanel;
    private Person[] persons;
    private volatile boolean stop = false;

    private static int cardWidth = 210;
    private static int height = 120;

    private int speedMillis = 80; // less means faster writing
    private int waitMillis = 8000; // wait millis between two persons cards : must be big enough so every card can be fully written

    public Credits(Person[] persons) {
        this (persons, cardWidth, height);
    }

    public Credits(Person[] persons, int _cardWidth, int _height) {

        cardWidth = _cardWidth;
        height = _height;
        this.persons = persons;

        imgPanel = new Picture(persons[0].getImage());
        infoPanel = new TypeWriter(persons[0].getDescription(), speedMillis, true, 6, false);

        int h = getPreferredHeight();
        Dimension cardDim = new Dimension(cardWidth, h);
        infoPanel.setPreferredSize(cardDim);
        Dimension imgDimension = new Dimension(h, h);
        System.out.println("imgDim="+imgDimension);
        imgPanel.setPreferredSize(imgDimension);

        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(imgPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(1, 1, 1, 0), 0, 0));
        add(infoPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(1, 1, 1, 1), 0, 0));

        Thread t = new Thread(new Animator());
        t.start();
    }

    public void stop() {        
        stop = true;
        PlayAudio.stop();
        infoPanel.stop();
    }

    class Animator implements Runnable {

        public void run() {
            try {
                int no = 1;
                while (!stop) {
                    for (int i = no, size = persons.length; i < size; i++) {
                        if (stop) {
                            infoPanel.stop();
                            return;
                        }
                        Thread.sleep(waitMillis);
                        if (stop) {
                            break;
                        }
                        imgPanel.setImage(persons[i].getImage());
                        infoPanel.setLines(persons[i].getDescription());
                    }
                    no =0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPreferredHeight() {
        int maxLines = 3;
        for (int i=0, size = persons.length; i<size; i++) {
            int lines = persons[i].getDescription().length;
            if (lines > maxLines) {
                maxLines = lines;
            }
        }
        return height*maxLines/3;
    }

}
