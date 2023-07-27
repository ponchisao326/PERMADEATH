package Listeners.Dia25;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;


public class GigaMobs implements Listener {

    private final Permadeath plugin;

    public GigaMobs(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void GigaMobs(CreatureSpawnEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            if (event.getEntityType() == EntityType.SLIME) {
                Slime slime = (Slime) event.getEntity();

                slime.setSize(15);
                AttributeInstance maxHealth = slime.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                maxHealth.setBaseValue(32);
            } else if (event.getEntityType() == EntityType.MAGMA_CUBE) {
                MagmaCube magmaCube = (MagmaCube) event.getEntity();

                magmaCube.setSize(16);
            } else if (event.getEntityType() == EntityType.GHAST) {
                Ghast ghast = (Ghast) event.getEntity();

                Random random = new Random();
                int min = 40;
                int max = 60;

                int randomNumber = random.nextInt(max - min + 1) + min;

                AttributeInstance maxHealth = ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                maxHealth.setBaseValue(randomNumber);
            }
        }
    }
}
