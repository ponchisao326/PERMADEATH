package Listeners.Dia25;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;

public class GhastExplosionPower implements Listener {

    private final Permadeath plugin;

    public GhastExplosionPower(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            if (event.getEntity() instanceof Ghast && event.getProjectile() instanceof Fireball) {
                Fireball fireball = (Fireball) event.getProjectile();
                int minPower = 3;
                int maxPower = 5;
                int randomPower = getRandomPower(minPower, maxPower);
                fireball.setYield(randomPower);
            }
        }
    }

    private int getRandomPower(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
