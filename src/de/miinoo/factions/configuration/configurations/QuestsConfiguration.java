package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.configuration.Configuration;
import de.miinoo.factions.quest.Quest;

import java.util.Collection;

public class QuestsConfiguration extends Configuration {

    public QuestsConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/quests.json");
    }

    public void saveQuest(Quest quest) {
        set(quest.getUuid().toString(), quest);
    }

    public void removeQuest(Quest quest) {
        set(quest.getUuid().toString(), null);
    }

    public Quest getQuest(String name) {
        return get(name, Quest.class);
    }

    public Collection<Quest> getQuests() {
        return (Collection) getValues().values();
    }
}
