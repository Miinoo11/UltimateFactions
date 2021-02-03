package de.miinoo.factions.hooks.placeholderapi;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.TopUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 17.04.2020
 */
public class FactionPlaceholders extends PlaceholderExpansion {

    private FactionsSystem plugin;


    @Override
    public boolean canRegister(){
        return (plugin = (FactionsSystem) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String getAuthor() { return "Miinoo_"; }

    @Override
    public String getIdentifier() {
        return "ultimatefactions";
    }

    @Override
    public String getRequiredPlugin() {
        return "UltimateFactions";
    }

    @Override
    public String getVersion() {
        return FactionsSystem.getPlugin().getDescription().getVersion();
    }



    @Override
    public String onPlaceholderRequest(Player player, String s) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        List<Faction> topFactions = new ArrayList<>(TopUtil.getTopFactions());
        if (faction == null) {
            return OtherMessages.PlaceHolder_Faction_Not_Found.getMessage();
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
        if (s.equals("faction_player")) {
            return player.getDisplayName();
        }
        if(s.equals("topfaction_1")) {
            if(topFactions.get(0) != null) {
                return topFactions.get(0).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_2")) {
            if(topFactions.get(1) != null) {
                return topFactions.get(1).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_3")) {
            if(topFactions.get(2) != null) {
                return topFactions.get(2).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_4")) {
            if(topFactions.get(3) != null) {
                return topFactions.get(3).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_5")) {
            if(topFactions.get(4) != null) {
                return topFactions.get(4).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_6")) {
            if(topFactions.get(5) != null) {
                return topFactions.get(5).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_7")) {
            if(topFactions.get(6) != null) {
                return topFactions.get(6).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_8")) {
            if(topFactions.get(7) != null) {
                return topFactions.get(7).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_9")) {
            if(topFactions.get(8) != null) {
                return topFactions.get(8).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        if(s.equals("topfaction_10")) {
            if(topFactions.get(9) != null) {
                return topFactions.get(9).getName();
            }
            return OtherMessages.TopFaction_None.getMessage();
        }
        return null; // if wrong placeholder was used
    }


    @Override
    public boolean persist() {
        return true;
    }
}
