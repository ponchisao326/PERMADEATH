package Listeners.Dia20;

import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class OnPlayerSleep implements Listener {

    private final Permadeath plugin;

    public OnPlayerSleep(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerSleep(PlayerBedEnterEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 20) {
            Player player = event.getPlayer();
            String name = player.getDisplayName();
            World world = event.getPlayer().getWorld();

            player.sendMessage("§7[§c§lPERMADEATH§7] §b» INFO: §eLa noche no se puede saltar pero se ha reseteado el contador de dias de los phantoms");
            player.setStatistic(Statistic.TIME_SINCE_REST, 0);
            event.setCancelled(true);
        }
    }
}
