package Listeners.Dia30;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.metadata.FixedMetadataValue;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;

public class EnderGhastCreeper implements Listener {

    private final Permadeath plugin;

    public EnderGhastCreeper(Permadeath plugin) {
        this.plugin = plugin;
    }

    private final Random random = new Random();
    private final int maxGhasts = 3; // Máximo de Ghasts a generar
    private final int maxCreepers = 5; // Máximo de Creepers a generar
    private final int spawnInterval = 800; // Intervalo de tiempo en ticks entre generaciones (20 ticks = 1 segundo)
    private long lastSpawnTime = 0; // Último tiempo de generación

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            World world = event.getWorld();

            // Verificar si el mundo es el End
            if (world.getEnvironment() == World.Environment.THE_END && shouldSpawn()) {
                spawnGhasts(event.getChunk(), world);
                spawnCreepers(event.getChunk(), world);
                lastSpawnTime = System.currentTimeMillis();
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            // Verificar si el atacante es una flecha y el objetivo es un Creeper teletransportable
            if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Creeper) {
                Arrow arrow = (Arrow) event.getDamager();
                Creeper creeper = (Creeper) event.getEntity();

                // Verificar si el Creeper es teletransportable
                if (creeper.hasMetadata("teleportable")) {
                    // Obtener la ubicación de destino para el teletransporte
                    Location destination = arrow.getLocation();
                    creeper.teleport(destination);
                }
            }
        }
    }

    private boolean shouldSpawn() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastSpawnTime) >= (spawnInterval * 50L); // Convertir ticks a milisegundos
    }

    private void spawnGhasts(org.bukkit.Chunk chunk, World world) {
        for (int i = 0; i < maxGhasts; i++) {
            int x = chunk.getX() * 16 + random.nextInt(16);
            int z = chunk.getZ() * 16 + random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Ghast ghast = (Ghast) world.spawnEntity(new Location(world, x, y, z), EntityType.GHAST);
            ghast.setCustomName("End Ghast");
            // Configurar cualquier otra propiedad del Ghast si es necesario
        }
    }

    private void spawnCreepers(org.bukkit.Chunk chunk, World world) {
        for (int i = 0; i < maxCreepers; i++) {
            int x = chunk.getX() * 16 + random.nextInt(16);
            int z = chunk.getZ() * 16 + random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Creeper creeper = (Creeper) world.spawnEntity(new Location(world, x, y, z), EntityType.CREEPER);
            creeper.setPowered(true);
            creeper.setMetadata("teleportable", new FixedMetadataValue(Permadeath.getInstance(), true));
            // Configurar cualquier otra propiedad del Creeper si es necesario
        }
    }

}
