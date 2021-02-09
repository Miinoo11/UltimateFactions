package de.miinoo.factions;

import de.miinoo.factions.configuration.configurations.QuestsConfiguration;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;

import java.util.*;

public class Quests {

    private final FactionsSystem factionsSystem;
    private QuestsConfiguration configuration = new QuestsConfiguration();
    private Set<Quest> quests;

    public Quests(FactionsSystem factionsSystem) {
        this.factionsSystem = factionsSystem;
        quests = new HashSet<>();
        quests.addAll(configuration.getQuests());
    }

    public Set<Quest> getQuests() {
        return quests;
    }

    public Quest getQuest(String name) {
        return quests.stream().filter(quest -> quest.getName().equals(name)).findFirst().orElseGet(null);
    }

    public void saveQuest(Quest quest) {
        quests.add(quest);
        configuration.saveQuest(quest);
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
        configuration.removeQuest(quest);
    }

    public boolean hasReachedMaxActiveQuests(Faction faction) {
        List<Quest> actual = new ArrayList<>();
        quests.stream().filter(quest -> quest.hasAccepted(faction)).forEach(actual::add);
        return actual.size() >= faction.maxActiveQuests();
    }

    public List<Quest> getNonCompletedQuests(Faction faction) {
        List<Quest> ncq = new ArrayList<>();
        quests.stream().filter(quest -> !quest.hasCompleted(faction)).forEach(ncq::add);
        return ncq;
    }

    public List<Quest> getCompletedQuests(Faction faction) {
        List<Quest> cq = new ArrayList<>();
        quests.stream().filter(quest -> quest.hasCompleted(faction)).forEach(cq::add);
        return cq;
    }
}
