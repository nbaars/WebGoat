package org.owasp.webgoat.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.context.JclContext;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginClassLoader extends ClassLoader {

    public static ThreadLocal<PluginClassLoader> CLASS_LOADER = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            PluginClassLoader pluginClassLoader = new PluginClassLoader(Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(pluginClassLoader);
            return pluginClassLoader;
        }
    };

    private final List<Class<?>> classes = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(Plugin.class);

    public Class<?> loadClass(String nameOfClass, byte[] classFile) {
        logger.debug("Loading class " + nameOfClass + ", classloader: " + super.getClass().getClassLoader().toString());
        Class clazz;
        try {
            clazz = super.loadClass(nameOfClass);
        } catch (ClassNotFoundException e) {
            clazz = super.defineClass(nameOfClass, classFile, 0, classFile.length);
            classes.add(clazz);
        }
        return clazz;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return super.getResourceAsStream(name);
    }

    @Override
    public URL getResource(String name) {
        return super.getResource(name);
    }

    public PluginClassLoader() {
    }

    public PluginClassLoader(ClassLoader contextClassLoader) {
        super(contextClassLoader);
    }

    public Class findClass(final String name) throws ClassNotFoundException {
        JarClassLoader jcl = JclContext.get();
        if (jcl != null) {
            Class cl = jcl.loadClass(name, true);
            //  super.resolveClass(cl);
            if (cl != null) {
                return cl;
            }
        }
        return super.findClass(name);

//
//        logger.debug("Finding class " + , name);
//        Optional<Class<?>> foundClass = FluentIterable.from(classes)
//                .firstMatch(new Predicate<Class<?>>() {
//                    @Override
//                    public boolean apply(Class<?> clazz) {
//                        return clazz.getName().equals(name);
//                    }
//                });
//        if (foundClass.isPresent()) {
//            return foundClass.get();
//        }
//        throw new ClassNotFoundException("Class " + name + " not found");
    }

}

