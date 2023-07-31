package Listeners.Death;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeathTrainPermsChange implements Listener {

    private final Permadeath plugin;

    public DeathTrainPermsChange(Permadeath plugin) {
        this.plugin = plugin;
    }

    public Player getRandomPlayer() {
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (!onlinePlayers.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(onlinePlayers.size());
            return onlinePlayers.get(randomIndex);
        }
        return null; // Si no hay jugadores conectados
    }

    private void grantPermissionToPlayer(Player player) {
        // Obtén la instancia de LuckPerms
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Obtiene el objeto User de LuckPerms para el jugador
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        // Crea un nodo de permiso para 'permadeath.deathtrain true'
        Node node = PermissionNode.builder("permadeath.deathtrain").value(false).build();

        // Agrega el nodo de permiso al jugador usando la API de LuckPerms
        user.data().add(node);

        // Guarda los cambios en la base de datos de LuckPerms
        luckPerms.getUserManager().saveUser(user);
    }

    @EventHandler
    public void DeathTrainPermsChange(WeatherChangeEvent event) {

        Player randomPlayer = getRandomPlayer();

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        Boolean deathtrain = config.getBoolean("Config.deathtrain");

        Bukkit.getScheduler().runTaskLater(Permadeath.getInstance(), () -> {
            if (!event.getWorld().hasStorm()) {
                if (deathtrain) {
                    config.set("Config.deathtrain", false);
                    try {
                        config.save(new File(plugin.getDataFolder(), "days.yml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 65L);
    }
}
