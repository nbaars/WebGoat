package org.owasp.webgoat.plugins;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class PluginBackgroundLoader implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        String pluginPath = event.getServletContext().getRealPath("plugin_lessons");
        String targetPath = event.getServletContext().getRealPath("plugin_extracted");
        String libPath = event.getServletContext().getRealPath("WEB-INF/classes");

        scheduler = Executors.newSingleThreadScheduledExecutor();
       // scheduler.scheduleAtFixedRate(new PluginsLoader(Paths.get(pluginPath), Paths.get(targetPath), Paths.get(libPath)), 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
