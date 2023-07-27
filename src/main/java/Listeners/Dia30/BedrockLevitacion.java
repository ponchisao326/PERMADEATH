package Listeners.Dia30;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class BedrockLevitacion implements Listener {

    private final Permadeath plugin;

    public BedrockLevitacion(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            Player player = event.getPlayer();
            Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();

            // Verifica si el jugador está pisando un bloque específico (por ejemplo, piedra)
            if (blockBelow.getType() == Material.BEDROCK) {
                PotionEffect levitation = new PotionEffect(PotionEffectType.LEVITATION, 7, 24);
                player.addPotionEffect(levitation);
            }
        }
    }
}
