package Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OnNetheritePickUp implements Listener {

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (isNetheriteItem(item)) {
            event.setCancelled(true);
            event.getItem().remove();
            event.getPlayer().sendMessage("§7[§c§lPERMADEATH§7] §b» §cNo puedes tener ese item en tu inventario!");
        }
    }

    private boolean isNetheriteItem(ItemStack item) {
        Material itemType = item.getType();
        return itemType == Material.NETHERITE_INGOT ||
                itemType == Material.NETHERITE_SCRAP ||
                itemType == Material.NETHERITE_AXE ||
                itemType == Material.NETHERITE_HOE ||
                itemType == Material.NETHERITE_PICKAXE ||
                itemType == Material.NETHERITE_SHOVEL ||
                itemType == Material.NETHERITE_SWORD ||
                itemType == Material.NETHERITE_HELMET ||
                itemType == Material.NETHERITE_CHESTPLATE ||
                itemType == Material.NETHERITE_LEGGINGS ||
                itemType == Material.NETHERITE_BOOTS ||
                itemType == Material.ANCIENT_DEBRIS;
    }

}
