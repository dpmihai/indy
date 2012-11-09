package indy.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 7, 2007
 * Time: 12:37:53 PM
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface Implement {

    public enum Severity {
        CRITICAL,
        IMPORTANT,
        TRIVIAL,
        DOCUMENTATION
    }

    Severity severity() default Severity.IMPORTANT;

    String item() default "modify";

    String assignedTo() default "Indy";

    String dateAssigned() default "NA";

}
