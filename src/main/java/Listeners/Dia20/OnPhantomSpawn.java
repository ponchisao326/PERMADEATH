package Listeners.Dia20;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class OnPhantomSpawn implements Listener {

    private final Permadeath plugin;

    public OnPhantomSpawn(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 20 || diaActual == 25 || diaActual == 30) {
            if (event.getEntity() instanceof Phantom) {
                Phantom phantom = (Phantom) event.getEntity();

                phantom.setSize(9);
                AttributeInstance maxHealth = phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                maxHealth.setBaseValue(40);
            }
        }
    }
}
