package de.miinoo.factions.model;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mino
 * 29.04.2020
 */
public class WarPiece implements ConfigurationSerializable {

    private UUID uuid;
    private long amount;

    public WarPiece(UUID uuid, long amount) {
        this.uuid = uuid;
        this.amount = amount;
    }

    public WarPiece(Map<String, Object> args) {
        uuid = UUID.fromString((String) args.get("uuid"));
        amount = (long) args.get("amount");
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid.toString());
        result.put("amount", amount);
        return result;
    }
}
