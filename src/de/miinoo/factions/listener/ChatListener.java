package de.miinoo.factions.listener;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.ChatCommand;
import de.miinoo.factions.configuration.configurations.SettingsConfiguration;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * @author Mino
 * 11.04.2020
 */
public class ChatListener implements Listener {

    SettingsConfiguration settings = FactionsSystem.getSettings();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player sender = event.getPlayer();
        String message = event.getMessage();
        Faction senderFaction = FactionsSystem.getFactions().getFaction(sender);
        Faction receiverFaction = null;

        if(FactionsSystem.getSettings().enableChatSystem()) {
            if (FactionsSystem.getSettings().useExtendedChatFormat()) {
                event.setCancelled(true);
                if (ChatCommand.chatMode.containsKey(sender)) {
                    if (ChatCommand.chatMode.get(sender).equalsIgnoreCase("public")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            receiverFaction = FactionsSystem.getFactions().getFaction(all);
                            if (senderFaction != null) {
                                if (receiverFaction != null) {
                                    if (senderFaction.equals(receiverFaction)) {
                                        all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getFactionColor(), message));
                                    } else if (senderFaction.getTrucesRelation().contains(receiverFaction.getId())) {
                                        all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getTruceColor(), message));
                                    } else if (senderFaction.getAlliesRelation().contains(receiverFaction.getId())) {
                                        all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getAllyColor(), message));
                                    } else if (senderFaction.getEnemyRelation().contains(receiverFaction.getId())) {
                                        System.out.println("sender: " + senderFaction.getName());
                                        System.out.println("rec: " + receiverFaction.getName());
                                        all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getEnemyColor(), message));
                                    } else {
                                        all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getDefaultColor(), message));
                                    }
                                } else {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getDefaultColor(), message));
                                }
                            } else {
                                all.sendMessage(extendedFormat2(sender, message));
                            }
                        }
                    }
                } else {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        receiverFaction = FactionsSystem.getFactions().getFaction(all);
                        if (senderFaction != null) {
                            if (receiverFaction != null) {
                                if (senderFaction.equals(receiverFaction)) {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getFactionColor(), message));

                                } else if (senderFaction.getTrucesRelation().contains(receiverFaction.getId())) {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getTruceColor(), message));

                                } else if (senderFaction.getAlliesRelation().contains(receiverFaction.getId())) {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getAllyColor(), message));

                                } else if (receiverFaction.getEnemyRelation().contains(senderFaction.getId())) {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getEnemyColor(), message));

                                } else {
                                    all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getDefaultColor(), message));
                                }
                            } else {
                                all.sendMessage(extendedFormat(sender, senderFaction, settings.getExtendedFormat(), settings.getDefaultColor(), message));
                            }
                        } else {
                            all.sendMessage(extendedFormat2(sender, message));
                        }
                    }
                }
            } else {
                if (ChatCommand.chatMode.containsKey(sender)) {
                    if (ChatCommand.chatMode.get(sender).equalsIgnoreCase("faction")) {
                        event.setCancelled(true);
                        for (UUID uuid : senderFaction.getPlayers()) {
                            Player all = Bukkit.getPlayer(uuid);
                            if (all != null && all.isOnline()) {
                                all.sendMessage(factionChat(senderFaction, sender, message));
                            }
                        }
                    } else if (ChatCommand.chatMode.get(sender).equalsIgnoreCase("ally")) {
                        event.setCancelled(true);
                        for (UUID uuid : senderFaction.getAlliesRelation()) {
                            Faction ally = FactionsSystem.getFactions().getFactionByUUID(uuid);
                            if (ally != null) {
                                for (UUID pUUID : ally.getPlayers()) {
                                    Player player1 = Bukkit.getPlayer(pUUID);
                                    if (player1 != null && player1.isOnline()) {
                                        player1.sendMessage(allyChat(senderFaction, sender, message));
                                    }
                                }
                            }
                        }
                        for (UUID uuid : senderFaction.getPlayers()) {
                            Player all = Bukkit.getPlayer(uuid);
                            if (all != null && all.isOnline())
                                all.sendMessage(allyChat(senderFaction, sender, message));
                        }
                    } else if (ChatCommand.chatMode.get(sender).equalsIgnoreCase("truce")) {
                        event.setCancelled(true);
                        for (UUID uuid : senderFaction.getTrucesRelation()) {
                            Faction truce = FactionsSystem.getFactions().getFactionByUUID(uuid);
                            if (truce != null) {
                                for (UUID pUUID : truce.getPlayers()) {
                                    Player player1 = Bukkit.getPlayer(pUUID);
                                    if (player1 != null && player1.isOnline()) {
                                        player1.sendMessage(truceChat(senderFaction, sender, message));
                                    }
                                }
                            }
                        }
                        for (UUID uuid : senderFaction.getPlayers()) {
                            Player all = Bukkit.getPlayer(uuid);
                            if (all != null && all.isOnline()) {
                                all.sendMessage(truceChat(senderFaction, sender, message));
                            }
                        }
                    } else if (ChatCommand.chatMode.get(sender).equalsIgnoreCase("public")) {
                        if (senderFaction != null) {
                            event.setFormat(publicChat(senderFaction, sender, message));
                        } else {
                            event.setFormat(publicChatNoFaction(sender, message));
                        }
                    }
                } else {
                    if (senderFaction != null) {
                        event.setFormat(publicChat(senderFaction, sender, message));
                    } else {
                        event.setFormat(publicChatNoFaction(sender, message));
                    }
                }
            }
        }
    }

    private String factionChat(Faction faction, Player player, String message) {
        Rank rank = faction.getRankOfPlayer(player.getUniqueId());
        if (FactionsSystem.isPlaceHolderAPIFound) {
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
        if (FactionsSystem.isPlaceHolderAPIFound) {
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
        if (FactionsSystem.isPlaceHolderAPIFound) {
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
        if (FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Public_Chat.getMessage()
                    .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Public_Chat.getMessage()
                .replace("%faction%", faction.getName()).replace("%rank%", rank.getPrefix()).replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }

    private String publicChatNoFaction(Player player, String message) {
        if (FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(player, OtherMessages.Public_Chat_No_Faction.getMessage()
                    .replace("%faction%", "[]").replace("%player%", player.getDisplayName())
                    + message.replace("%", "%%"));
        }
        return OtherMessages.Public_Chat.getMessage()
                .replace("%faction%", "[]").replace("%player%", player.getDisplayName())
                + message.replace("%", "%%");
    }

    private String extendedFormat(Player sender, Faction senderFaction, String format, String color, String message) {
        final String replace = format.replace("&", "§")
                .replace("%color%", color.replace("&", "§"));
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(sender, replace
                    .replace("%faction%", senderFaction.getName())
                    .replace("%player%", sender.getDisplayName())
                    .replace("%rank%", senderFaction.getRankOfPlayer(sender.getUniqueId()).getName())
                    .replace("%message%", message.replace("%", "%%")));
        }
        return replace
                .replace("%faction%", senderFaction.getName())
                .replace("%player%", sender.getDisplayName())
                .replace("%rank%", senderFaction.getRankOfPlayer(sender.getUniqueId()).getName())
                .replace("%message%", message.replace("%", "%%"));
    }

    private String extendedFormat2(Player sender, String message) {
        if(FactionsSystem.isPlaceHolderAPIFound) {
            return PlaceholderAPI.setPlaceholders(sender, settings.getExtendedFormat2()
                    .replace("&", "§")
                    .replace("%color%", settings.getEnemyColor().replace("&", "§"))
                    .replace("%player%", sender.getDisplayName())
                    .replace("%message%", message.replace("%", "%%")));
        } else {
            return settings.getExtendedFormat2()
                    .replace("&", "§")
                    .replace("%color%", settings.getEnemyColor().replace("&", "§"))
                    .replace("%player%", sender.getDisplayName())
                    .replace("%message%", message.replace("%", "%%"));
        }
    }

}
