package Listeners.Dia20;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;

public class onmobdrop implements Listener {

    private final Permadeath plugin;

    public onmobdrop(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMobDrop(EntityDeathEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 20) {

            EntityType entity1 = event.getEntity().getType();

            if (entity1.equals(EntityType.IRON_GOLEM)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.ZOMBIFIED_PIGLIN)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.GHAST)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.GUARDIAN)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.MAGMA_CUBE)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.ENDERMAN)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.WITCH)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.WITHER_SKELETON)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.EVOKER)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.PHANTOM)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.SLIME)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.DROWNED)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.BLAZE)) {
                event.getDrops().clear();
            } else if (entity1.equals(EntityType.RAVAGER)) {
                double random = Math.random();
                if (random < 0.01) { //Cambiar Valor el dia 25
                    event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                }
            }
        }
    }
}
