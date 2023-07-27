package Listeners.Dia30;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class TotemFailure implements Listener {

    private final Permadeath plugin;

    public TotemFailure(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void TotemFailure(EntityResurrectEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            double random = Math.random();
            if (random < 0.01) {
                event.setCancelled(true);
            }
        }
    }
}
