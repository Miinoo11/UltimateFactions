package de.miinoo.factions.commands.subcommands;

import com.google.common.collect.Lists;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Mino
 * 14.04.2020
 */
public class MapCommand extends PlayerCommand {

    public MapCommand() {
        super("map", new CommandDescription(OtherMessages.Help_MapCommand.getMessage()));
    }

    private static final String[] TOKENS;
    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        Faction myFaction = factions.getFaction(player);

        final int height = FactionsSystem.getSettings().getHeight();
        final int width = FactionsSystem.getSettings().getWidth();
        final int halfHeight = height / 2 + 1;
        final int halfWidth = width / 2 + 1;
        final LinkedList<StringBuilder> rows = Lists.newLinkedList();
        final LinkedList<String> indicators = new LinkedList<String>(Arrays.asList(TOKENS));
        final Map<Faction, String> factionEntries = new HashMap<Faction, String>();
        final FactionChunk topLeft = new FactionChunk(player.getLocation()).getRelative(-halfWidth, -halfHeight);
        final Faction factionHere = factions.getFaction(player.getLocation().getChunk());
        for (int h = 0; h < height; ++h) {
            final StringBuilder row = new StringBuilder();
            for (int w = 0; w < width; ++w) {
                if (w == halfWidth && h == halfHeight) {
                    row.append(ChatColor.valueOf(FactionsSystem.getSettings().getPlayerIndicatorColor()) + "\u271c");
                } else {
                    String indicator = "\u2589";
                    final FactionChunk here = topLeft.getRelative(w, h);
                    final Faction faction = factions.getFaction(here);
                    if (here.isOutsideBorder()) {
                        row.append(ChatColor.valueOf(FactionsSystem.getSettings().getBorderIndicatorColor()) + indicator);
                    } else {
                        final ChatColor color = Factions.getColor(myFaction, faction);
                        if (faction != null) {
                            final String s2 = indicators.pollFirst();
                            if (s2 != null) {
                                indicator = s2;
                            }
                        }
                        factionEntries.putIfAbsent(faction, color + indicator);
                        row.append(factionEntries.containsKey(faction) ? factionEntries.get(faction) : (color + indicator));
                    }
                }
            }
            rows.add(row);
        }
        String header;
        if (factionHere == null) {
            header = OtherMessages.Map_Header.getMessage().replace("%faction%", "Wilderness")
                    .replace("%direction%", getLookDirection(player));
        } else {
            if(myFaction != null) {
                header = OtherMessages.Map_Header.getMessage()
                        .replace("%faction%", Factions.getColor(myFaction, factionHere) + factionHere.getName())
                        .replace("%direction%", getLookDirection(player));
            } else {
                header = OtherMessages.Map_Header.getMessage()
                        .replace("%faction%", FactionsSystem.getSettings().getChatColor("enemy") + factionHere.getName())
                        .replace("%direction%", getLookDirection(player));
            }
        }
        final StringBuilder legend = new StringBuilder();
        boolean isEmpty = true;
        for (final Map.Entry<Faction, String> entry : factionEntries.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }
            if (isEmpty) {
                isEmpty = false;
            }
            legend.append(entry.getValue()).append("§8: §7" + entry.getKey().getName() + " ");
        }
        player.sendMessage(header);
        rows.forEach(s -> player.sendMessage(s.toString()));
        if (!isEmpty) {
            player.sendMessage(legend.toString());
        }

        return true;
    }

    static {
        TOKENS = new String[]{"\u2726", "\u2734", "\u2735", "\u2777", "\u2778", "\u2779", "\u277a", "\u277b", "\u277c", "\u277d", "\u277e", "\u277f"};
    }

    private String getLookDirection(final Player player) {
        double rotation = (player.getLocation().getYaw() - 180.0f) % 360.0f;
        if (rotation < 0.0) {
            rotation += 360.0;
        }
        if (0.0 <= rotation && rotation < 22.5) {
            return "N";
        }
        if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        }
        if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        }
        if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        }
        if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        }
        if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        }
        if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        }
        if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        }
        if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        }
        return "N/A";
    }
}
