package de.miinoo.factions.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestReward implements ConfigurationSerializable {

    private UUID uuid;
    private String name;
    private String rewardText;

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public QuestReward(String name, String rewardText) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.rewardText = rewardText;
    }

    public QuestReward(Map<String, Object> args) {
        this.uuid = UUID.fromString((String) args.get("id"));
        this.name = (String) args.get("name");
        this.rewardText = (String) args.get("rewardText");
    }

    public String getRewardText() {
        return rewardText.replace("&", "§");
    }

    public void give(Player player){}

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", uuid.toString());
        result.put("name", name);
        result.put("rewardText", rewardText);
        return result;
    }
}
