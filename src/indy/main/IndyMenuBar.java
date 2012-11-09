package indy.main;

import indy.util.AnnotatedClass;
import indy.util.Globals;
import indy.annotation.MenuAction;
import indy.application.ApplicationConfig;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 27, 2007
 * Time: 12:13:02 PM
 */
public class IndyMenuBar extends JMenuBar {

    public IndyMenuBar() {

        setBackground(Color.WHITE);
        try {
            List<AnnotatedClass> actions = Globals.getScanner().getMenuActionsClasses();
            List<JMenu> menus = new ArrayList<JMenu>();
            for (AnnotatedClass c : actions) {
                System.out.println("menuClass="+c.getClazz().getName());

                String menuPath = ((MenuAction)c.getAnnotation()).path();                

                String[] menuNames = menuPath.split(ApplicationConfig.MENU_SEPARATOR);
                String parentMenuName = null;
                JMenu menuParent = null;
                int size = size=menuNames.length;
                for (int i=0 ; i<size; i++) {
                    if (i > 0) {
                        parentMenuName = menuNames[i-1];
                    }
                    if (!findMenu(menus, menuNames[i],parentMenuName)) {
                        JMenu menu = new JMenu(menuNames[i]);
                        if (menuParent == null) {
                            add(menu);
                        } else {
                            menuParent.add(menu);
                        }
                        menus.add(menu);
                        menuParent = menu;
                    }
                }

                AbstractAction action = (AbstractAction)c.getClazz().newInstance();
                JMenuItem menuItem = new JMenuItem(action);
                JMenu findMenu = getMenu(menus, menuNames[size-1], parentMenuName);
                findMenu.add(menuItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // two menus with the same name may exists if they have different menu parents
    // but a same sequence (parent menu, child menu) in different menus is not taken in
    // consideration because it is unlikely to appear
    private boolean findMenu(List<JMenu> menus, String menuName, String menuParentName) {
        for (JMenu menu : menus) {
//            System.out.println("menu="+menu);
//            System.out.println("menu.name="+menu.getText());
            if (menu.getText().equals(menuName)) {
                Component parent = menu.getParent();
                if (parent instanceof JMenu) {
                     return ((JMenu)parent).getText().equals(menuParentName);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private JMenu getMenu(List<JMenu> menus, String menuName, String menuParentName) {
       for (JMenu menu : menus) {
//            System.out.println("menu="+menu);
//            System.out.println("menu.name="+menu.getText());
            if (menu.getText().equals(menuName)) {
                Component parent = menu.getParent();
                if (parent instanceof JMenu) {
                     if  (((JMenu)parent).getText().equals(menuParentName)) {
                         return menu;
                     }
                } else {
                    return menu;
                }
            }
        }
        return null; 
    }


}
