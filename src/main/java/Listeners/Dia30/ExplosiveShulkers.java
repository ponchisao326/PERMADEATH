package Listeners.Dia30;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class ExplosiveShulkers implements Listener {

    private final Permadeath plugin;

    public ExplosiveShulkers(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void ExplosiveShulkers(EntityDeathEvent event) {

        if (event.getEntityType() == EntityType.SHULKER) {

            Location location = event.getEntity().getLocation();

            World world = location.getWorld();

            TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT);

            tnt.setFuseTicks(60);

        }

    }

    @EventHandler
    public void OnShulkerDeath(EntityDeathEvent event) throws InterruptedException {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            if (event.getEntityType() == EntityType.SHULKER) {

                Location location = event.getEntity().getLocation();
                if (!event.getDrops().isEmpty()) {
                    ItemStack item = event.getDrops().get(0);
                    event.getDrops().clear();

                    Bukkit.getScheduler().runTaskLater(Permadeath.getInstance(), () -> {
                        spawnItem(location, item);
                    }, 65L);
                }
            }
        }
    }

    public void spawnItem(Location location, ItemStack itemStack) {
        World world = location.getWorld();
        world.dropItem(location, itemStack);
    }
}
