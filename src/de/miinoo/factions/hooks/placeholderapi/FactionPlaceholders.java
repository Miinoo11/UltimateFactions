package de.miinoo.factions.hooks.placeholderapi;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 17.04.2020
 */
public class FactionPlaceholders extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "ultimatefactions";
    }

    @Override
    public String getAuthor() {return "Miinoo_";}

    @Override
    public String getVersion() {
        return FactionsSystem.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            return "N/A";
        }
        if (s.equals("faction_name")) {
            return faction.getName();
        }
        if (s.equals("faction_power")) {
            return String.valueOf(faction.getPower());
        }
        if (s.equals("faction_claims")) {
            return String.valueOf(faction.getClaimed().size());
        }
        if (s.equals("faction_role")) {
            return faction.getRankOfPlayer(player.getUniqueId()).getName();
        }
        if (s.equals("faction_members")) {
            return String.valueOf(faction.getPlayers().size());
        }
        if (s.equals("faction_level")) {
            return String.valueOf(faction.getLevel());
        }
        if (s.equals("faction_leader")) {
            return String.valueOf(faction.getLeader().getName());
        }
        if (s.equals("faction_description")) {
            return faction.getDescription();
        }
        if (s.equals("faction_powercap")) {
            return String.valueOf(faction.getPowerCap());
        }
        if (s.equals("faction_bank")) {
            return String.valueOf(faction.getBank());
        }
        return null; // if wrong placeholder was used
    }

    @Override
    public boolean canRegister() {
        return false;
    }

    @Override
    public boolean persist() {
        return true;
    }
}
