package io.github.portlek.configs;

import io.github.portlek.configs.file.Config;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public final class MyPlugin extends JavaPlugin {

    private final AnnotationProcessor annotationProcessor = new AnnotationProcessor(this);

    public MyPlugin() {
        super();
    }

    protected MyPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, new File("build"), file);
    }

    @Override
    public void onEnable() {
        final Config config = annotationProcessor.load(
            new Config()
        );
    }

}