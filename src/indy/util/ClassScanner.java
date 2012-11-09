package indy.util;

import indy.annotation.ToolbarAction;
import indy.annotation.MenuAction;

import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.io.File;
import java.net.URL;
import java.net.JarURLConnection;
import java.net.URLDecoder;
import java.lang.annotation.Annotation;

/**
 * Created by IntelliJ IDEA.
 * User: mihai.panaitescu
 * Date: Sep 13, 2007
 * Time: 11:52:46 AM
 */
public class ClassScanner {

    private static final String FILE_PROTOCOL = "file";
    private static final String JAR_PROTOCOL = "jar";
    private static final String EXT_CLASS = ".class";

    private String packageName;
    private String packagePath;
    private String packagePathWithPrefix;

    private static List<AnnotatedClass> annotatedClasses;

    public ClassScanner() {
    }

    public ClassScanner(String packageName) {
        setPackageName(packageName);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
        packagePath = packageName.replace('.', '/');
        packagePathWithPrefix = packagePath + '/';        
    }

    @SuppressWarnings("unchecked")
    public synchronized List<AnnotatedClass> getAnnotatedClasses() throws Exception {
        if (annotatedClasses == null) {
            Set<String> classSet = getClassNames();

            annotatedClasses = new ArrayList<AnnotatedClass>();

            for (String className : classSet) {
                Class clazz = Class.forName(className);
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation annotation : annotations) {
                    AnnotatedClass ac = new AnnotatedClass(clazz, annotation);
                    annotatedClasses.add(ac);
                }
            }
        }
        return annotatedClasses;
    }


    @SuppressWarnings("unchecked")
    public List<AnnotatedClass> getToolbarActionsClasses() throws Exception {
        getAnnotatedClasses();
        List<AnnotatedClass> classList = new ArrayList<AnnotatedClass>();
        for (AnnotatedClass annotatedClass : annotatedClasses) {
            Annotation annotation = annotatedClass.getAnnotation();
            if (annotation instanceof ToolbarAction) {                            
                classList.add(annotatedClass);
            }
        }
        // order actions by the index (order) inside toolbar
        Collections.sort(classList, new Comparator<AnnotatedClass>() {
            public int compare(AnnotatedClass o1, AnnotatedClass o2) {
                return ((ToolbarAction)o1.getAnnotation()).index() -
                       ((ToolbarAction)o2.getAnnotation()).index();
            }
        });
        return classList;
    }

    @SuppressWarnings("unchecked")
    public List<AnnotatedClass> getMenuActionsClasses() throws Exception {
        getAnnotatedClasses();
        List<AnnotatedClass> classList = new ArrayList<AnnotatedClass>();
        for (AnnotatedClass annotatedClass : annotatedClasses) {
            Annotation annotation = annotatedClass.getAnnotation();
            if (annotation instanceof MenuAction) {
                classList.add(annotatedClass);
            }
        }
        // order actions by the index (order) inside menu
        Collections.sort(classList, new Comparator<AnnotatedClass>() {
            public int compare(AnnotatedClass o1, AnnotatedClass o2) {
                return ((MenuAction)o1.getAnnotation()).index() -
                       ((MenuAction)o2.getAnnotation()).index();
            }
        });
        return classList;
    }


    private Set<String> getClassNames() throws Exception {
        Set<String> classSet = new HashSet<String>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = classLoader.getResources(packagePath);
        while (urls.hasMoreElements()) {
            URL packageUrl = urls.nextElement();
            String urlString = URLDecoder.decode(packageUrl.getFile(), "UTF-8");
            String protocol = packageUrl.getProtocol().toLowerCase();
            if (FILE_PROTOCOL.equals(protocol)) {
                File packageDirectory = new File(urlString);
                if (packageDirectory.isDirectory()) {
                    findClassesInDirectory(packageName, classSet, packageDirectory);
                }
            } else if (JAR_PROTOCOL.equals(protocol)) {
                JarURLConnection connection = (JarURLConnection) packageUrl.openConnection();
                JarFile jarFile = connection.getJarFile();
                findClassesInJar(classSet, jarFile);
            } else {
                throw new Exception("Unsuported protocol");
            }
        }

        return classSet;
    }

    // for finding classes in system directories we must use File.separator instead of '/'
    private void findClassesInDirectory(String packageName, Set<String> classSet, File packageDirectory) {
        File[] fileNames = packageDirectory.listFiles();
        for (File file : fileNames) {
            if (file.isFile()) {
                if (file.getName().endsWith(EXT_CLASS)) {
                    String className = removeFileClassExtension(file.getName());
                    if (className != null) {
                        classSet.add(packageName + '.' + className);
                    }
                }
            } else {
                // recursive in directory
                String path = file.getAbsolutePath();
                String pName = this.packageName.replace(".", File.separator);
                int index = path.lastIndexOf(pName);
                if ( index != -1 ) {
                    String packageN = path.substring(index);
                    packageN = packageN.replace(File.separatorChar, '.');
                    findClassesInDirectory(packageN, classSet, new File(path));
                }
            }
        }
    }

    // does not apply for jar inside jar
    private void findClassesInJar(Set<String> classSet, JarFile jarFile) {
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry jarEntry = jarEntryEnumeration.nextElement();
            String absoluteFileName = jarEntry.getName();

            if (absoluteFileName.endsWith(EXT_CLASS)) {
                if (absoluteFileName.startsWith("/")) {
                    absoluteFileName = absoluteFileName.substring(1);
                }                
                boolean isLeaf = true;
                if (absoluteFileName.startsWith(packagePathWithPrefix)) {
                    String qualifiedName = absoluteFileName.replace('/', '.');
                    String className = removeFileClassExtension(qualifiedName);
                    if (className != null) {
                        classSet.add(className);
                    }
                }
            }
        }
    }

    private String removeFileClassExtension(String fileName) {
        return fileName.substring(0, fileName.length() - EXT_CLASS.length());
    }
}
