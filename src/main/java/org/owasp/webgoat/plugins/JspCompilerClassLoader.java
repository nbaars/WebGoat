package org.owasp.webgoat.plugins;

import org.apache.catalina.loader.WebappLoader;

public class JspCompilerClassLoader extends WebappLoader {

    public JspCompilerClassLoader() {

    }

    public JspCompilerClassLoader(ClassLoader parent) {
        super(parent);
    }
}
