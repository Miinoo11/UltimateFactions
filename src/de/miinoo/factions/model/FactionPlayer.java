package de.miinoo.factions.model;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class FactionPlayer implements ConfigurationSerializable {

    private final UUID player;
    private final UUID rank;

    public FactionPlayer(UUID player, UUID rank) {
        this.player = player;
        this.rank = rank;
    }

    public FactionPlayer(Map<String, Object> args) {
        player = UUID.fromString((String) args.get("player"));
        rank = UUID.fromString((String) args.get("rank"));
    }

    public UUID getPlayer() {
        return player;
    }

    public UUID getRank() {
        return rank;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("player", player.toString());
        result.put("rank", rank.toString());
        return result;
    }
}
