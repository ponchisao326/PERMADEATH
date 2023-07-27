package Listeners.Dia10;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.*;

public class OnSpiderSpawnEffects implements Listener {

    private final Permadeath plugin;

    public OnSpiderSpawnEffects(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 10) {
            if (event.getEntityType() == EntityType.SPIDER) {
                Spider spider = (Spider) event.getEntity();

                // Verifica si la araña ya tiene el efecto aplicado
                if (!hasEffect(spider, PotionEffectType.SPEED)) {
                    // Aplica el efecto de velocidad a la araña
                    List<PotionEffectType> effectTypes = new ArrayList<>();
                    effectTypes.add(PotionEffectType.SPEED);
                    effectTypes.add(PotionEffectType.INCREASE_DAMAGE);
                    effectTypes.add(PotionEffectType.JUMP);
                    effectTypes.add(PotionEffectType.GLOWING);
                    effectTypes.add(PotionEffectType.REGENERATION);
                    effectTypes.add(PotionEffectType.INVISIBILITY);
                    effectTypes.add(PotionEffectType.SLOW_FALLING);
                    effectTypes.add(PotionEffectType.DAMAGE_RESISTANCE);

                    Random random = new Random();
                    int numEffects = random.nextInt(3) + 1;

                    Set<PotionEffectType> selectedEffects = new HashSet<>();

                    while (selectedEffects.size() < numEffects) {
                        PotionEffectType randomEffect = effectTypes.get(random.nextInt(effectTypes.size()));
                        selectedEffects.add(randomEffect);
                    }

                    for (PotionEffectType effect : selectedEffects) {
                        int amplifier = getAmplifierForEffect(effect);
                        spider.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, amplifier));
                    }

                }
            }
            else if (event.getEntityType() == EntityType.CAVE_SPIDER) {
                CaveSpider caveSpider = (CaveSpider) event.getEntity();

                // Verifica si la araña ya tiene el efecto aplicado
                if (!hasEffect(caveSpider, PotionEffectType.SPEED)) {
                    // Aplica el efecto de velocidad a la araña
                    List<PotionEffectType> effectTypes = new ArrayList<>();
                    effectTypes.add(PotionEffectType.SPEED);
                    effectTypes.add(PotionEffectType.INCREASE_DAMAGE);
                    effectTypes.add(PotionEffectType.JUMP);
                    effectTypes.add(PotionEffectType.GLOWING);
                    effectTypes.add(PotionEffectType.REGENERATION);
                    effectTypes.add(PotionEffectType.INVISIBILITY);
                    effectTypes.add(PotionEffectType.SLOW_FALLING);
                    effectTypes.add(PotionEffectType.DAMAGE_RESISTANCE);

                    Random random = new Random();
                    int numEffects = random.nextInt(3) + 1;

                    Set<PotionEffectType> selectedEffects = new HashSet<>();

                    while (selectedEffects.size() < numEffects) {
                        PotionEffectType randomEffect = effectTypes.get(random.nextInt(effectTypes.size()));
                        selectedEffects.add(randomEffect);
                    }

                    for (PotionEffectType effect : selectedEffects) {
                        int amplifier = getAmplifierForEffect(effect);
                        caveSpider.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, amplifier));
                    }

                }
            }
        }
    }

    private int getAmplifierForEffect(PotionEffectType effectType) {
        switch (effectType.getName()) {
            case "GLOWING":
                return 1;
            case "REGENERATION":
                return 4;
            case "INVISIBILITY":
                return 1;
            case "SLOW_FALLING":
                return 1;
            case "DAMAGE_RESISTANCE":
                return 3;
            case "JUMP":
                return 5;
            case "SPEED":
                return 3;
            case "INCREASE DAMAGE":
                return 4;
            default:
                return 0; // Valor predeterminado si el efecto no está mapeado
        }
    }

    // Verifica si la entidad ya tiene un efecto específico
    private boolean hasEffect(LivingEntity entity, PotionEffectType effectType) {
        for (PotionEffect effect : entity.getActivePotionEffects()) {
            if (effect.getType() == effectType) {
                return true;
            }
        }
        return false;
    }
}
