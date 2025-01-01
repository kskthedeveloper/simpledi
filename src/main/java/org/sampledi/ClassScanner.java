package org.sampledi;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {
    public static Set<Class<?>> scan(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');
        File directory = new File("src/main/java/" + path);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file: files) {
                    if (file.getName().endsWith(".java")) {
                        String className = packageName + "." + file.getName().replace(".java", "");
                        System.out.println("checking class name " + className);

                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e ) {
                            throw new RuntimeException("Class not found " + className, e);
                        }
                    }
                }
            }
        }
        return classes;
    }
}
