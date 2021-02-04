package de.miinoo.factions.quest;

import de.miinoo.factions.model.Faction;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class Quest implements ConfigurationSerializable {

    private UUID id;
    private String name;
    private QuestType type;
    private QuestDescription description;
    private int questActionAmount;
    private Object questActionObject;
    private List<QuestReward> rewards;
    private List<UUID> acceptedFactions;
    private List<UUID> completedFactions;
    //private QuestReward reward;

    public Quest(String name, QuestType type, QuestDescription description, int questActionAmount, Object questActionObject, List<QuestReward> rewards) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.description = description;
        this.questActionAmount = questActionAmount;
        this.questActionObject = questActionObject;
        this.rewards = rewards;

        this.acceptedFactions = new ArrayList<>();
        this.completedFactions = new ArrayList<>();
    }

    public Quest(Map<String, Object> args) {
        this.id = UUID.fromString((String) args.get("id"));
        this.name = (String) args.get("name");
        this.type = (QuestType) args.get("type");
        this.description = (QuestDescription) args.get("description");
        this.questActionAmount = ((Number) args.get("questActionAmount")).intValue();
        this.questActionObject = args.get("questActionObject");
        this.rewards = (List<QuestReward>) args.get("rewards");
        this.acceptedFactions = (List<UUID>) args.get("acceptedFactions");
        this.completedFactions = (List<UUID>) args.get("completedFactions");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public QuestType getType() {
        return type;
    }

    public QuestDescription getDescription() {
        return description;
    }

    public int getQuestActionAmount() {
        return questActionAmount;
    }

    public Object getQuestActionObject() {
        return questActionObject;
    }

    public List<QuestReward> getRewards() {
        return rewards;
    }

    public List<UUID> getAcceptedFactions() {
        return acceptedFactions;
    }

    public boolean hasAccepted(Faction faction) {
        return acceptedFactions.contains(faction.getId());
    }

    public void addFactionToAccepted(Faction faction) {
        acceptedFactions.add(faction.getId());
    }

    public void removeFactionFromAccepted(Faction faction) {
        acceptedFactions.remove(faction.getId());
    }

    public List<UUID> getCompletedFactions() {
        return completedFactions;
    }

    public void addFactionToCompleted(Faction faction) {
        completedFactions.add(faction.getId());
    }

    public boolean hasCompleted(Faction faction) {
        return completedFactions.contains(faction.getId());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id.toString());
        result.put("name", name);
        result.put("type", type.getName());
        result.put("description", description);
        result.put("questActionAmount", questActionAmount);
        result.put("questActionObject", questActionObject);
        result.put("rewards", rewards);
        result.put("acceptedFactions", acceptedFactions);
        result.put("completedFactions", completedFactions);
        return result;
    }
}