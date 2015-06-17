package org.owasp.webgoat.plugins;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.context.JclContext;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class PluginsLoader implements Runnable {

    protected static final String WEBGOAT_PLUGIN_EXTENSION = "jar";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Path pluginSource;
    private Path pluginTarget;

    public PluginsLoader(Path pluginSource, Path pluginTarget) {
        Preconditions.checkNotNull(pluginSource, "plugin source cannot be null");
        Preconditions.checkNotNull(pluginTarget, "plugin target cannot be null");

        this.pluginSource = pluginSource;
        this.pluginTarget = pluginTarget;
    }

    public List<Plugin> loadPlugins(final boolean reload) {
        final List<Plugin> plugins = new ArrayList<Plugin>();

        try {
            Files.walkFileTree(pluginSource, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        if (PluginFileUtils.fileEndsWith(file, WEBGOAT_PLUGIN_EXTENSION)) {
                            JclContext.get().add(file.toUri().toURL());
                            PluginExtractor extractor = new PluginExtractor(file);
                            extractor.extract(pluginTarget);

                            Plugin plugin = new Plugin(pluginTarget, extractor.getClasses());
                            if (plugin.getLesson().isPresent()) {
                                PluginFileUtils.createDirsIfNotExists(pluginTarget);
                                plugin.loadFiles(extractor.getFiles(), reload);
                                plugin.rewritePaths(pluginTarget);
                                plugins.add(plugin);
                            }
                        }
                    } catch (Plugin.PluginLoadingFailure e) {
                        logger.error("Unable to load plugin, continue loading others...", e);
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            logger.error("Loading plugins failed", e);
        }
        return plugins;
    }

    @Override
    public void run() {
        loadPlugins(true);
    }

}
