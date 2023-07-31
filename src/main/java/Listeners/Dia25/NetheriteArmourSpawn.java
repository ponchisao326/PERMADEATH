package Listeners.Dia25;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class NetheriteArmourSpawn implements Listener {

    private final Permadeath plugin;

    public NetheriteArmourSpawn(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void NetheriteArmourSpawn(EntityDropItemEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25) {
            if (event.getEntityType() == EntityType.GHAST) {
                double random = Math.random();
                if (random < 0.15) {
                    ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
                    ItemMeta helmetMeta = helmet.getItemMeta();
                    helmetMeta.setUnbreakable(true);
                    helmet.setItemMeta(helmetMeta);
                    event.getItemDrop().setItemStack(helmet);
                }

            } else if (event.getEntityType() == EntityType.CAVE_SPIDER) {
                double random = Math.random();
                if (random < 0.15) {
                    ItemStack helmet = new ItemStack(Material.NETHERITE_LEGGINGS);
                    ItemMeta helmetMeta = helmet.getItemMeta();
                    helmetMeta.setUnbreakable(true);
                    helmet.setItemMeta(helmetMeta);
                    event.getItemDrop().setItemStack(helmet);
                }

            } else if (event.getEntityType() == EntityType.SLIME) {
                double random = Math.random();
                if (random < 0.15) {
                    ItemStack helmet = new ItemStack(Material.NETHERITE_CHESTPLATE);
                    ItemMeta helmetMeta = helmet.getItemMeta();
                    helmetMeta.setUnbreakable(true);
                    helmet.setItemMeta(helmetMeta);
                    event.getItemDrop().setItemStack(helmet);
                }

            } else if (event.getEntityType() == EntityType.MAGMA_CUBE) {
                double random = Math.random();
                if (random < 0.15) {
                    ItemStack helmet = new ItemStack(Material.NETHERITE_BOOTS);
                    ItemMeta helmetMeta = helmet.getItemMeta();
                    helmetMeta.setUnbreakable(true);
                    helmet.setItemMeta(helmetMeta);
                    event.getItemDrop().setItemStack(helmet);
                }

            }
        }
    }
}
