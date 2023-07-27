package Listeners.Dia10;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;

public class onmobdeath implements Listener {

    private final Permadeath plugin;

    public onmobdeath(Permadeath plugin) {
        this.plugin = plugin;
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Location createEntityLocation(Location eLocation){
        Location nLocation = eLocation.add(getRandomNumber(0,3),getRandomNumber(0,3),getRandomNumber(0,3));
        return nLocation;
    }

    @EventHandler
    public void OnMobDeath(EntityDeathEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 10 || diaActual == 20 || diaActual == 25 || diaActual == 30) {
            int random_number;
            if (getRandomBoolean()) {
                if (event.getEntity() instanceof LivingEntity && (event.getEntity().getKiller() instanceof Player) && (event.getEntity().getType() == EntityType.ZOMBIE) || (event.getEntity().getType() == EntityType.ENDERMAN) || (event.getEntity().getType() == EntityType.HUSK) || (event.getEntity().getType() == EntityType.CREEPER) || (event.getEntity().getType() == EntityType.SLIME) || (event.getEntity().getType() == EntityType.DROWNED) || (event.getEntity().getType() == EntityType.WITHER_SKELETON) || (event.getEntity().getType() == EntityType.WITCH) || (event.getEntity().getType() == EntityType.PIGLIN) || (event.getEntity().getType() == EntityType.PIGLIN_BRUTE)) {
                    random_number = getRandomNumber(1, 1);
                    for (int i = 0; i < random_number; i++) {
                        event.getEntity().getWorld().spawnEntity(createEntityLocation(event.getEntity().getLocation()), event.getEntityType());
                    }
                }
            }
        }
    }
}
