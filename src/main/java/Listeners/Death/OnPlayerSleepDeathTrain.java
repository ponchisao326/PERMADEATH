package Listeners.Death;

import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class OnPlayerSleepDeathTrain implements Listener {

    private final Permadeath plugin;

    public OnPlayerSleepDeathTrain(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerSleepDeathTrain(PlayerBedEnterEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        Boolean DeathTrain = config.getBoolean("Config.deathtrain");
        if (diaActual == 0 || diaActual == 10) {
            Player player = event.getPlayer();

            if (DeathTrain) {
                player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                event.setCancelled(true);
                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cNo es posible dormir mientras la death train esté activa");
            }
        }
    }
}
