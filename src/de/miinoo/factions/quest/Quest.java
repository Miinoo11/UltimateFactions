package de.miinoo.factions.quest;

import de.miinoo.factions.model.Faction;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.*;

public class Quest implements ConfigurationSerializable {

    private UUID uuid;
    private String name;
    private String description;
    private Material material;
    private QuestAction action;
    private QuestType type;
    private QuestReward reward;
    private List<String> acceptedFactions;
    private List<String> completedFactions;
    private List<String> rewardClaimedPlayers;
    private Map<String, Integer> process;

    public Quest(String name, String description, Material material, QuestAction action, QuestType type, QuestReward reward) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.material = material;
        this.action = action;
        this.type = type;
        this.reward = reward;

        this.acceptedFactions = new ArrayList<>();
        this.completedFactions = new ArrayList<>();
        this.rewardClaimedPlayers = new ArrayList<>();
        this.process = new HashMap<>();
    }

    public Quest(Map<String, Object> args) {
        this.uuid = UUID.fromString((String) args.get("id"));
        this.name = (String) args.get("name");
        this.description = (String) args.get("description");
        this.material = Material.getMaterial(String.valueOf(args.get("material")));
        this.action = (QuestAction) args.get("action");
        this.type = (QuestType) args.get("type");
        this.reward = (QuestReward) args.get("reward");
        this.acceptedFactions = (List<String>) args.get("accepted");
        this.completedFactions = (List<String>) args.get("completed");
        this.rewardClaimedPlayers = (List<String>) args.get("rewardClaimed");
        this.process = (Map<String, Integer>) args.get("process");
    }

    public void handle(Event event) {
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public QuestAction getAction() {
        return action;
    }

    public QuestType getType() {
        return type;
    }

    public QuestReward getReward() {
        return reward;
    }

    public boolean hasCompleted(Faction faction) {
        return completedFactions.contains(faction.getId().toString());
    }

    public void addToCompleted(Faction faction) {
        process.remove(faction.getId().toString());
        acceptedFactions.remove(faction.getId().toString());
        completedFactions.add(faction.getId().toString());
    }

    public void addToAccepted(Faction faction) {
        acceptedFactions.add(faction.getId().toString());
        process.put(faction.getId().toString(), 0);
    }

    public void addToClaimed(Player player) {
        rewardClaimedPlayers.add(player.getUniqueId().toString());
    }

    public boolean hasAccepted(Faction faction) {
        return acceptedFactions.contains(faction.getId().toString());
    }

    public int getProcess(Faction faction) {
        return ((Number)process.get(faction.getId().toString())).intValue();
    }

    public boolean hasClaimed(Player player) {
        return rewardClaimedPlayers.contains(player.getUniqueId().toString());
    }

    public void addQuestProcess(Faction faction, int amount) {
        int i = ((Number)process.get(faction.getId().toString())).intValue();
        i += amount;
        process.replace(faction.getId().toString(), i);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", uuid.toString());
        result.put("name", name);
        result.put("description", description);
        result.put("material", material.toString());
        result.put("action", action);
        result.put("type", type);
        result.put("reward", reward);
        result.put("accepted", acceptedFactions);
        result.put("completed", completedFactions);
        result.put("rewardClaimed", rewardClaimedPlayers);
        result.put("process", process);
        return result;
    }
}
