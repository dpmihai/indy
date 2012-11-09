package indy.util;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 27, 2007
 * Time: 3:22:10 PM
 */
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.Locale;
import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Aug 3, 2006
 * Time: 1:16:24 PM
 */
public class I18nSupport {

    private static final ResourceBundle resBundle = PropertyResourceBundle.getBundle("i18n/client");

    public static String getString(String propertyName, Object ... params) {
        String value = resBundle.getString(propertyName);
        if (params.length > 0) {
            return MessageFormat.format(value, params);
        } else {
            return value;
        }
    }
}
