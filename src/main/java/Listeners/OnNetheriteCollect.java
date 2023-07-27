package Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnNetheriteCollect implements Listener {

    @EventHandler
    public void OnNetheriteCollect(BlockBreakEvent event) {

        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§7[§c§lPERMADEATH§7] §b» §c¡No puedes minar Netherite!");
        }

    }

}
