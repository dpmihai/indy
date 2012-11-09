package indy.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 13, 2007
 * Time: 11:30:19 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ToolbarAction {
    
    public enum ToolbarSeparator {
        YES,
        NO
    }

    // toolbar separator present before action
    ToolbarSeparator separator() default ToolbarSeparator.NO;

    // toolbar action index
    int index() default 1;

    // register action as a global shortcut
    boolean globalShortcut() default true;
}
