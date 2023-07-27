package Listeners.Dia30;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.*;

public class OnEntitySpawnTransform implements Listener {

    private final Permadeath plugin;

    public OnEntitySpawnTransform(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSquidfishSpawn(CreatureSpawnEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            if (event.getEntityType() == EntityType.BAT) {
                event.setCancelled(true); // Cancela el evento de spawneo del muercielago

                // Obtiene la ubicación del calamar
                double x = event.getEntity().getLocation().getX();
                double y = event.getEntity().getLocation().getY();
                double z = event.getEntity().getLocation().getZ();

                // Spawnea un guardián en lugar del calamar
                Blaze blaze = (Blaze) event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.BLAZE);

                // Aplica el efecto de velocidad al guardián
                blaze.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2));

                // Establece la posición del guardián en la ubicación del calamar
                blaze.teleport(event.getEntity().getLocation());

                // Puedes realizar más ajustes y configuraciones al guardián si es necesario
            } else if (event.getEntityType() == EntityType.CREEPER) {

                Creeper creeper = (Creeper) event.getEntity();

                creeper.setPowered(true);

            } else if (event.getEntityType() == EntityType.PILLAGER) {

                Pillager pillager = (Pillager) event.getEntity();

                EntityEquipment equipment = pillager.getEquipment();

                pillager.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));

                Material material = Material.CROSSBOW;
                int cantidad = 1;
                short durabilidad = 0;
                ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                ItemMeta itemMeta = iron_axe.getItemMeta();
                itemMeta.addEnchant(Enchantment.QUICK_CHARGE, 10, true);
                iron_axe.setItemMeta(itemMeta);

                equipment.setItemInMainHand(iron_axe);

            } else if (event.getEntityType() == EntityType.SKELETON) {

                Skeleton skeleton = (Skeleton) event.getEntity();

                EntityEquipment equipment = skeleton.getEquipment();

                Material material = Material.ARROW;
                int cantidad = 1;
                short durabilidad = 0;
                ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                ItemMeta itemMeta = iron_axe.getItemMeta();
                itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                iron_axe.setItemMeta(itemMeta);

                equipment.setItemInOffHand(iron_axe);

            } else if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN) {

                PigZombie pigZombie = (PigZombie) event.getEntity();

                EntityEquipment equipment = pigZombie.getEquipment();

                //Diamond Enchanted Armour
                Material materialHelmet = Material.DIAMOND_HELMET;
                int amountHelmet = 1;
                short durabilityHelmet = 0;
                ItemStack diamondHelmet = new ItemStack(materialHelmet, amountHelmet, durabilityHelmet);

                Material materialChestplate = Material.DIAMOND_CHESTPLATE;
                int amountChestplate = 1;
                short durabilityChestplate = 0;
                ItemStack diamondChestplate = new ItemStack(materialChestplate, amountChestplate, durabilityChestplate);

                Material materialLeggings = Material.DIAMOND_LEGGINGS;
                int amountLeggings = 1;
                short durabilityLeggings = 0;
                ItemStack diamondLeggings = new ItemStack(materialLeggings, amountLeggings, durabilityLeggings);

                Material materialBoots = Material.DIAMOND_BOOTS;
                int amountBoots = 1;
                short durabilityBoots = 0;
                ItemStack diamondBoots = new ItemStack(materialBoots, amountBoots, durabilityBoots);


                equipment.setHelmet(diamondHelmet);
                equipment.setChestplate(diamondChestplate);
                equipment.setLeggings(diamondLeggings);
                equipment.setBoots(diamondBoots);

            } else if (event.getEntityType() == EntityType.IRON_GOLEM) {

                IronGolem ironGolem = (IronGolem) event.getEntity();

                ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));

            } else if (event.getEntityType() == EntityType.ENDERMAN) {

                Enderman enderman = (Enderman) event.getEntity();

                enderman.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));

            } else if (event.getEntityType() == EntityType.SILVERFISH) {

                Silverfish silverfish = (Silverfish) event.getEntity();

                // Verifica si la araña ya tiene el efecto aplicado
                if (!hasEffect(silverfish, PotionEffectType.SPEED)) {
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
                    int numEffects = 5;

                    Set<PotionEffectType> selectedEffects = new HashSet<>();

                    while (selectedEffects.size() < numEffects) {
                        PotionEffectType randomEffect = effectTypes.get(random.nextInt(effectTypes.size()));
                        selectedEffects.add(randomEffect);
                    }

                    for (PotionEffectType effect : selectedEffects) {
                        int amplifier = getAmplifierForEffect(effect);
                        silverfish.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, amplifier));
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
