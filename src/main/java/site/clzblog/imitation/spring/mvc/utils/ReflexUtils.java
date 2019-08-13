package site.clzblog.imitation.spring.mvc.utils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflexUtils {
    private final static char SLASH = '/';
    private final static char POINT = '.';
    private final static String SUFFIX = ".class";
    private final static String JAR = "jar";
    private final static String FILE = "file";

    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String packageDirName = packageName.replace(POINT, SLASH);
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {

                URL url = dirs.nextElement();
                String protocol = url.getProtocol();

                if (FILE.equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.toString());
                    addClass(packageName, filePath, true, classes);

                } else if (JAR.equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();

                        if (name.charAt(0) == SLASH) name = name.substring(1);
                        if (!name.startsWith(packageDirName)) continue;

                        int idx = name.lastIndexOf(SLASH);
                        if (idx != -1) packageName = name.substring(0, idx).replace(SLASH, POINT);
                        if ((idx == -1)) continue;

                        if (name.endsWith(SUFFIX) && !entry.isDirectory()) {
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            classes.add(Class.forName(packageName + POINT + className));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void addClass(String name, String path, final boolean recursive, Set<Class<?>> classes) throws Exception {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(SUFFIX)));
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) addClass(getName(name, file), file.getAbsolutePath(), recursive, classes);
            else classes.add(Thread.currentThread().getContextClassLoader().loadClass(getNameSubStr(name, file)));
        }
    }

    private static String getName(String name, File file) {
        return String.format("%s.%s", name, file.getName());
    }

    private static String getNameSubStr(String name, File file) {
        return String.format("%s.%s", name, file.getName().substring(0, file.getName().length() - 6));
    }
}
