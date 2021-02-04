package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.configuration.Configuration;
import de.miinoo.factions.quest.Quest;

import java.util.Collection;
import java.util.UUID;

public class QuestsConfiguration extends Configuration {

    public QuestsConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/quests.json");
    }

    public void saveQuest(Quest quest) {
        set(quest.getId().toString(), quest);
    }

    public void removeQuest(Quest quest) {
        set(quest.getId().toString(), null);
    }

    public Quest getQuest(UUID uuid) {
        return get(uuid.toString(), Quest.class);
    }

    public Collection<Quest> getQuests() {
        return (Collection) getValues().values();
    }
}
