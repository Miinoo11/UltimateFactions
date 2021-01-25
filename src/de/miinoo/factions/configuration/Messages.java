package de.miinoo.factions.configuration;

import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import org.bukkit.plugin.Plugin;

/**
 * @author Mino
 * 10.04.2020
 */
public class Messages extends Configuration {

    public Messages(Plugin plugin, String fileName, String pluginDirectory) {
        super(plugin, fileName, pluginDirectory);
        initFile();
    }

    @Override
    protected void setupConfig() {

        for (SuccessMessage message : SuccessMessage.values()) {
            config.addDefault("Success_Messages." + message.name(), message.getDefaultMessage());
        }

        for (ErrorMessage message : ErrorMessage.values()) {
            config.addDefault("Error_Messages." + message.name(), message.getDefaultMessage());
        }

        for (GUITags tag : GUITags.values()) {
            config.addDefault("GUI_Tags." + tag.name(), tag.getDefaultMessage());
        }

        for (OtherMessages other : OtherMessages.values()) {
            config.addDefault("Other." + other.name(), other.getDefaultMessage());
        }
        config.options().copyDefaults(true);
        save();
    }

    public String getMessage(String path) {
        return config.getString(path);
    }

}
