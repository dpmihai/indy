package indy.util;

import java.lang.annotation.Annotation;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 13, 2007
 * Time: 1:21:10 PM
 */
public class AnnotatedClass {

    private Class clazz;
    private Annotation annotation;


    public AnnotatedClass(Class clazz, Annotation annotation) {
        this.clazz = clazz;
        this.annotation = annotation;
    }


    public Class getClazz() {
        return clazz;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
