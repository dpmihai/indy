package indy.application;

import indy.main.IndyLoader;
import indy.main.IndySplash;
import indy.annotation.Implement;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 26, 2007
 * Time: 2:30:46 PM
 */
public class ApplicationLoader implements IndyLoader {

    private static ApplicationLoader instance;

    private ApplicationLoader(){
    }

    public static synchronized ApplicationLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationLoader();
        }
        return instance;
    }

    @Implement
    public void load(IndySplash splash) {
        final String[] stages = {"stage 1", "stage 2", "stage 3"};
        int stage = 0;
        for (int i = 0; i <= 100; i += 5) {
            String status = "Initialising " + stages[stage] + "...";
            if (splash != null) splash.updateSplash(status, i);
            try {
                Thread.sleep(10);
                if (i == 30) stage = 1;
                else if (i == 60) stage = 2;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}
