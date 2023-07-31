package Listeners.Dia25;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class OnRavagerSpawn implements Listener {

    private final Permadeath plugin;

    public OnRavagerSpawn(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnRavagerSpawn(CreatureSpawnEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            if (event.getEntityType() == EntityType.RAVAGER) {
                Ravager ravager = (Ravager) event.getEntity();

                ravager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
                ravager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            }
        }
    }
}