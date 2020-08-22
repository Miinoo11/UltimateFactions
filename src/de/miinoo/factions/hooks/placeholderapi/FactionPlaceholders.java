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
        return "ultimatefaction";
    }

    @Override
    public String getAuthor() {return "Miinoo_";}

    @Override
    public String getVersion() {
        return FactionsSystem.getPlugin().getDescription().getVersion();
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        final Faction faction = factions.getFaction(player);
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
            return String.valueOf(faction.getClaimed());
        }
        if (!s.equals("faction_role")) {
            return null;
        }
        return faction.getRankOfPlayer(player.getUniqueId()).getName();
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
