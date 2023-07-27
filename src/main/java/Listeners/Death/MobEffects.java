package Listeners.Death;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobEffects implements Listener {

    private final Permadeath plugin;

    public MobEffects(Permadeath plugin) {
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

    public void MobEffects(CreatureSpawnEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            Entity entity = event.getEntity();
            EntityType entityType = entity.getType();
            Player randomPlayer = getRandomPlayer();

            boolean isHostile = isHostileEntity(entityType);

            if (isHostileEntity(entityType)) {
                if (randomPlayer.hasPermission("permadeath.deathtrain")) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
                    PotionEffect ressistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
                    livingEntity.addPotionEffect(strength);
                    livingEntity.addPotionEffect(speed);
                    livingEntity.addPotionEffect(ressistance);
                }
            }
        }
    }

    private boolean isHostileEntity(EntityType entityType) {
        switch (entityType) {
            case ZOMBIE:
            case SKELETON:
            case CREEPER:
            case SPIDER:
            case WITCH:
            case DROWNED:
            case ZOMBIE_VILLAGER:
            case BLAZE:
            case MAGMA_CUBE:
            case ENDERMITE:
            case WITHER_SKELETON:
            case GHAST:
            case GUARDIAN:
            case HOGLIN:
            case EVOKER:
            case PILLAGER:
            case SHULKER:
            case SLIME:
            case VINDICATOR:
            case ZOGLIN:
                return true;
            default:
                return false;
        }
    }

}
