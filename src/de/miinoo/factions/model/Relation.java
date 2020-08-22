package de.miinoo.factions.model;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 20.04.2020
 */
public class Relation implements ConfigurationSerializable {

    private UUID uuid;
    private String identifier;
    private Collection<RelationPermission> permissions;

    public Relation(UUID uuid, String identifier, Collection<RelationPermission> permissions) {
        this.uuid = uuid;
        this.identifier = identifier;
        this.permissions = permissions;
    }

    public Relation(Map<String, Object> args) {
        uuid = UUID.fromString((String) args.get("id"));
        identifier = (String) args.get("identifier");
        permissions = ((List<String>) args.get("permissions")).stream().map(RelationPermission::valueOf).collect(Collectors.toList());
    }

    public UUID getId() {
        return uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Collection<RelationPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<RelationPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(RelationPermission permission) {
        return permissions.contains(permission);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", uuid.toString());
        result.put("identifier", identifier);
        result.put("permissions", permissions.stream().map(Enum::name).collect(Collectors.toList()));
        return result;
    }
}
