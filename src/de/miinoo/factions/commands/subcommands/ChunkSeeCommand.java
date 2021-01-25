package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.OtherMessages;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;


/**
 * @author Mino
 * 11.04.2020
 */
public class ChunkSeeCommand extends PlayerCommand {


    public ChunkSeeCommand() {
        super("chunksee", new CommandDescription(OtherMessages.Help_ChunkSeeCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        Chunk chunk = player.getLocation().getChunk();

        int minX = chunk.getX()*16;
        int minZ = chunk.getZ()*16;
        int minY = player.getLocation().getBlockY();
        //Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);

        for(int x = minX; x < minX+17; x++) {
            for(int y = minY; y < minY+1; y++) {
                for(int z = minZ; z < minZ+17; z++) {
                    player.spawnParticle(Particle.BUBBLE_POP, minX, y, z, 5);
                    player.spawnParticle(Particle.BUBBLE_POP, x , y, minZ, 5);
                    player.spawnParticle(Particle.BUBBLE_POP, minX+16, y, z, 5);
                    player.spawnParticle(Particle.BUBBLE_POP, x , y, minZ+17, 5);
                }
            }
        }


        return true;
    }
}
