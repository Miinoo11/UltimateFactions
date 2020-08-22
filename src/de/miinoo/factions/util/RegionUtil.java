package de.miinoo.factions.util;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RegionUtil {

    public static HashMap<Player, Location> pos1 = new HashMap<>();
    public static HashMap<Player, Location> pos2 = new HashMap<>();

    public static ItemStack wand = Items.createItem(XMaterial.STICK.parseMaterial()).setDisplayName("§8» §cUF §7| §cWand")
            .setLore(GUITags.Regions_Lore.getMessage(), GUITags.Regions_Lore1.getMessage()).addGlow().getItem();

    public static boolean isInRegion(Player player) {
        for(Region region : FactionsSystem.getRegions().getRegions()) {
            return region.getCuboid().contains(player.getLocation());
        }
        return false;
    }

}
