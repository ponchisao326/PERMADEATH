package Listeners.Dia20;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class PigmansAgresivos implements Listener {

    private final Permadeath plugin;

    public PigmansAgresivos(Permadeath plugin) {
        this.plugin = plugin;
    }

    public void PigmansAgresivos(EntitySpawnEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 20 || diaActual == 25 || diaActual == 30) {
            if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN) {

                PigZombie pigZombie = (PigZombie) event.getEntity();

                pigZombie.setAnger(Integer.MAX_VALUE);

            }
        }
    }

}
