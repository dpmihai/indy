package indy.application;

import indy.annotation.Implement;
import indy.credits.Person;
import indy.main.IndyConfig;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 26, 2007
 * Time: 2:44:57 PM
 */
public class ApplicationConfig implements IndyConfig {

    private static ApplicationConfig instance;
    
    public static final String MENU_SEPARATOR = "/";
    public static final String FILE_MENU_PATH = "File";
    public static final String ACTION_MENU_PATH = "File/Actions";
    public static final String HELP_MENU_PATH = "Help";

    private ApplicationConfig(){
    }

    public static synchronized ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    @Implement
    public Person[] getCredits() {
        String copyright = "\u00a9";
        Person[] p = new Person[5];
        String[] desc1 = {"Milowsky Deater", "Business Analyst", "Milowsky.Deater@west.com"};
        String[] desc2 = {"Jonh Zukowxan", "Senior Developer", "Jonh.Zukowxan@west.com"};
        String[] desc3 = {"Homer Simpson", "Joker", "homer.simpson@simpsons.uk"};
        String[] desc4 = {"John Doe", "Singer", "john.doe@company.it"};
        String[] desc5 = {" ", "WEST " + copyright + "2005", " "};
        String img1 = "img1.jpg";
        String img2 = "img2.jpg";
        String img3 = "img3.jpg";
        String img4 = "img4.jpg";
        String img5 = "img5.jpg";
        p[0] = new Person(desc1, img1);
        p[1] = new Person(desc2, img2);
        p[2] = new Person(desc3, img3);
        p[3] = new Person(desc4, img4);
        p[4] = new Person(desc5, img5);
        return p;
    }
}
