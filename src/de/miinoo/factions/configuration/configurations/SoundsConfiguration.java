package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.core.configuration.Configuration;
import de.miinoo.factions.hooks.xseries.XSound;
import org.bukkit.Sound;

import java.io.File;

public class SoundsConfiguration extends Configuration {

    public SoundsConfiguration(File file) {
        super(file);
    }

    public Sound getSuccessSound() {
        return XSound.matchXSound(configuration.getString("Sounds.success")).orElse(XSound.ENTITY_PLAYER_LEVELUP).parseSound();
    }

    public Sound getErrorSound() {
        return XSound.matchXSound(configuration.getString("Sounds.error")).orElse(XSound.BLOCK_ANVIL_LAND).parseSound();
    }

    public Sound getQuestCompleteSound() {
        return XSound.matchXSound(configuration.getString("Sounds.quest_completed")).orElse(XSound.ENTITY_PLAYER_LEVELUP).parseSound();
    }
}
