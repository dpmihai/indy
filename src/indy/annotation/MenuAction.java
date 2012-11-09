package indy.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 27, 2007
 * Time: 11:27:25 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MenuAction {

    // does not allow for menu names internationalization, because it must be a constant at compile time!
    String path();

    // menu action index (must be different for actions in the same menu)
    int index() default 1;
}
