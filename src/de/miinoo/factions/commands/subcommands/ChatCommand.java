package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mino
 * 14.04.2020
 */
public class ChatCommand extends PlayerCommand {

    public ChatCommand() {
        super("chat", new CommandDescription("Switch chat mode", "[faction, ally, truce, public]"), RankPermission.CHAT);
    }

    public static HashMap<Player, String> chatMode = new HashMap<>();
    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);

        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (args.hasAtLeast(1)) {
            if (!chatMode.containsKey(player)) {
                chatMode.put(player, "public");
            }
            if (args.get(0).equalsIgnoreCase("public") || args.get(0).equalsIgnoreCase("p")) {
                chatMode.remove(player);
                chatMode.put(player, "public");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "public"));
                return true;
            }
            if (args.get(0).equalsIgnoreCase("faction") || args.get(0).equalsIgnoreCase("f")) {
                chatMode.remove(player);
                chatMode.put(player, "faction");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "faction"));
                return true;
            }
            if (args.get(0).equalsIgnoreCase("ally") || args.get(0).equalsIgnoreCase("a")) {
                chatMode.remove(player);
                chatMode.put(player, "ally");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "ally"));
                return true;
            }
            if (args.get(0).equalsIgnoreCase("truce") || args.get(0).equalsIgnoreCase("t")) {
                chatMode.remove(player);
                chatMode.put(player, "truce");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "truce"));
                return true;
            }
        }

        if (chatMode.containsKey(player)) {
            if (chatMode.get(player).equalsIgnoreCase("faction")) {
                chatMode.remove(player);
                chatMode.put(player, "ally");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "ally"));
                return true;
            } else if (chatMode.get(player).equalsIgnoreCase("ally")) {
                chatMode.remove(player);
                chatMode.put(player, "truce");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "truce"));
                return true;
            } else if (chatMode.get(player).equalsIgnoreCase("truce")) {
                chatMode.remove(player);
                chatMode.put(player, "public");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "public"));
                return true;
            } else if (chatMode.get(player).equalsIgnoreCase("public")) {
                chatMode.remove(player);
                chatMode.put(player, "faction");
                player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "faction"));
                return true;
            }
        } else {
            chatMode.put(player, "faction");
            player.sendMessage(OtherMessages.Entered_ChatMode.getMessage().replace("%chat%", "faction"));
            return true;
        }

        return true;
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> list = new ArrayList<>();
        list.add("alliance");
        list.add("truce");
        list.add("public");
        list.add("faction");
        return list;
    }
}
