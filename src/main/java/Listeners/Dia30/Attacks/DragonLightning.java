package Listeners.Dia30.Attacks;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class DragonLightning implements Listener {

    private final Permadeath plugin;
    private static Logger logger;

    public DragonLightning(Permadeath plugin) {
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
        Node node = PermissionNode.builder("permadeath.dragon").value(true).build();

        // Agrega el nodo de permiso al jugador usando la API de LuckPerms
        user.data().add(node);

        // Guarda los cambios en la base de datos de LuckPerms
        luckPerms.getUserManager().saveUser(user);
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        logger = getLogger();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30 && event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            logger.info("Pasa el if");
            if (!getRandomPlayer().hasPermission("permadeath.dragon")) {
                logger.info("No tiene permiso");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    grantPermissionToPlayer(player);
                }
                Player player = event.getPlayer();
                World world = player.getWorld();
                EnderDragon dragon = getEnderDragon(world);
                if (dragon != null) {
                    logger.info("Dragon != null");

                    // Cambiar el nombre y la vida del Ender Dragon
                    dragon.setCustomName("§6§lPERMADEATH DEMON");
                    dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(600);
                    dragon.setHealth(600);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // Generar rayo en una ubicación aleatoria alrededor del dragón
                            Location randomLocation = getRandomLocationAroundDragon(dragon.getLocation(), 10);
                            world.strikeLightning(randomLocation);

                            if (new Random().nextInt(4) == 0) {
                                for (int i = 0; i < 4; i++) {
                                    Location endermiteLocation = getRandomLocationAroundDragon(dragon.getLocation(), 5);
                                    world.spawn(endermiteLocation, Endermite.class);
                                }
                            }
                            logger.info("Rayo");
                        }
                    }.runTaskTimer(plugin, 0, 20 * 2); // Ejecutar cada 60 segundos (20 ticks = 1 segundo)
                }
                else {
                    logger.info("Dragon == null");
                }
            }
        }
    }

    private EnderDragon getEnderDragon(World world) {
        for (org.bukkit.entity.Entity entity : world.getEntities()) {
            if (entity instanceof EnderDragon) {
                EnderDragon enderDragon = (EnderDragon) entity;
                if (enderDragon.getCustomName() != null && enderDragon.getCustomName().equals("PERMADEATH DEMON")) {
                    return enderDragon; // Return the custom Ender Dragon
                }
            }
        }
        return null; // Return null if the custom Ender Dragon is not found
    }

    private Location getRandomLocationAroundDragon(Location center, int radius) {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double x = center.getX() + radius * Math.cos(angle);
        double z = center.getZ() + radius * Math.sin(angle);
        double y = center.getWorld().getHighestBlockYAt((int) x, (int) z);

        return new Location(center.getWorld(), x, y, z);
    }
}
