package de.miinoo.factions.listener;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.ChatCommand;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;

/**
 * @author Mino
 * 11.04.2020
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        Faction faction = FactionsSystem.getFactions().getFaction(player);

        if (faction == null) {
            event.setFormat(publicChat(player, message));
        } else {
            if (ChatCommand.chatMode.containsKey(player)) {
                if (ChatCommand.chatMode.get(player).equalsIgnoreCase("faction")) {
                    event.setCancelled(true);
                    for (UUID uuid : faction.getPlayers()) {
                        Player all = Bukkit.getPlayer(uuid);
                        if (all != null && all.isOnline()) {
                            all.sendMessage(factionChat(faction, player, message));
                        }
                    }
                } else if (ChatCommand.chatMode.get(player).equalsIgnoreCase("ally")) {
                    event.setCancelled(true);
                    for(UUID uuid : faction.getAlliesRelation()) {
                        Faction ally = FactionsSystem.getFactions().getFactionByUUID(uuid);
                        if(ally != null) {
                            for(UUID pUUID : ally.getPlayers()) {
                                Player player1 = Bukkit.getPlayer(pUUID);
                                if(player1 != null && player1.isOnline()) {
                                    player1.sendMessage(allyChat(faction, player, message));
                                }
                            }
                        }
                    }
                    for (UUID uuid : faction.getPlayers()) {
                        Player all = Bukkit.getPlayer(uuid);
                        if (all != null && all.isOnline())
                            all.sendMessage(allyChat(faction, player, message));
                    }
                } else if (ChatCommand.chatMode.get(player).equalsIgnoreCase("truce")) {
                    event.setCancelled(true);
                    for(UUID uuid : faction.getTrucesRelation()) {
                        Faction truce = FactionsSystem.getFactions().getFactionByUUID(uuid);
                        if(truce != null) {
                            for(UUID pUUID : truce.getPlayers()) {
                                Player player1 = Bukkit.getPlayer(pUUID);
                                if(player1 != null && player1.isOnline()) {
                                    player1.sendMessage(truceChat(faction, player, message));
                                }
                            }
                        }
                    }
                    for (UUID uuid : faction.getPlayers()) {
                        Player all = Bukkit.getPlayer(uuid);
                        if (all != null && all.isOnline()) {
                            all.sendMessage(truceChat(faction, player, message));
                        }
                    }
                } else if (ChatCommand.chatMode.get(player).equalsIgnoreCase("public")) {
                    event.setFormat(publicChat(faction, player, message));
                }
            } else {
                event.setFormat(publicChat(faction, player, message));
            }
        }
    }

    private String factionChat(Faction faction, Player player, String message) {
        Rank rank = faction.getRankOfPlayer(player.getUniqueId());
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Faction_Chat.getMessage()
                    .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Faction_Chat.getMessage()
                .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }
    private String allyChat(Faction faction, Player player, String message) {
        Rank rank = faction.getRankOfPlayer(player.getUniqueId());
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Ally_Chat.getMessage()
                    .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Ally_Chat.getMessage()
                .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }
    private String truceChat(Faction faction, Player player, String message) {
        Rank rank = faction.getRankOfPlayer(player.getUniqueId());
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Truce_Chat.getMessage()
                    .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Truce_Chat.getMessage()
                .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }
    private String publicChat(Faction faction, Player player, String message) {
        Rank rank = faction.getRankOfPlayer(player.getUniqueId());
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Public_Chat.getMessage()
                    .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Public_Chat.getMessage()
                .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }
    private String publicChat(Player player, String message) {
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Public_Chat_No_Faction.getMessage()
                    .replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Public_Chat_No_Faction.getMessage()
                .replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }

}
