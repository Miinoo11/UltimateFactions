package de.miinoo.factions;

import de.miinoo.factions.configuration.configurations.QuestsConfiguration;
import de.miinoo.factions.quest.Quest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quests {

    private final FactionsSystem system;
    private final QuestsConfiguration configuration = new QuestsConfiguration();
    private final Set<Quest> quests;

    public Quests(FactionsSystem system) {
        this.system = system;
        quests = new HashSet<>();
        quests.addAll(configuration.getQuests());
    }

    public Set<Quest> getQuests() {
        return quests;
    }

    public void saveQuest(Quest quest) {
        quests.add(quest);
        configuration.saveQuest(quest);
    }

    public void removeQuest(Quest quest) {
        configuration.removeQuest(quest);
        quests.remove(quest);
    }

    public Quest getQuest(UUID uuid) {
        return quests.stream().filter(quest -> quest.getId().equals(uuid)).findFirst().get();
    }
    public Quest getQuest(String name) {
        return quests.stream().filter(quest -> quest.getName().equals(name)).findFirst().get();
    }

    public boolean exists(String name) {
        return quests.stream().anyMatch(quest -> quest.getName().equalsIgnoreCase(name));
    }

}
