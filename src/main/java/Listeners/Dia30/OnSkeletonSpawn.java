package Listeners.Dia30;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.Random;

public class OnSkeletonSpawn implements Listener {

    private final Permadeath plugin;

    public OnSkeletonSpawn(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnSkeletonSpawn(CreatureSpawnEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 30) {
            EntityType entity = event.getEntity().getType();
            Entity entity2 = event.getEntity();


            if (entity.equals(EntityType.SKELETON)) {

                Random random = new Random();
                int numeroAleatorio = random.nextInt(5) + 1; // Genera un número aleatorio entre 1 y 3

                if (numeroAleatorio == 1) {

                    Skeleton skeleton = (Skeleton) event.getEntity();
                    World world = skeleton.getWorld();


                    EntityEquipment equipment = skeleton.getEquipment();

                    skeleton.setCustomName("Esqueleto Guerrero");

                    // Iron Axe
                    Material material = Material.IRON_AXE;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                    iron_axe.setItemMeta(itemMeta);

                    //Diamond Enchanted Armour
                    Material materialHelmet = Material.DIAMOND_HELMET;
                    int amountHelmet = 1;
                    short durabilityHelmet = 0;
                    ItemStack diamondHelmet = new ItemStack(materialHelmet, amountHelmet, durabilityHelmet);
                    ItemMeta diamondHelmetMeta = diamondHelmet.getItemMeta();
                    diamondHelmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    diamondHelmet.setItemMeta(diamondHelmetMeta);


                    Material materialChestplate = Material.DIAMOND_CHESTPLATE;
                    int amountChestplate = 1;
                    short durabilityChestplate = 0;
                    ItemStack diamondChestplate = new ItemStack(materialChestplate, amountChestplate, durabilityChestplate);
                    ItemMeta diamondChestplateMeta = diamondChestplate.getItemMeta();
                    diamondChestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    diamondChestplate.setItemMeta(diamondChestplateMeta);

                    Material materialLeggings = Material.DIAMOND_LEGGINGS;
                    int amountLeggings = 1;
                    short durabilityLeggings = 0;
                    ItemStack diamondLeggings = new ItemStack(materialLeggings, amountLeggings, durabilityLeggings);
                    ItemMeta diamondLeggingsMeta = diamondLeggings.getItemMeta();
                    diamondLeggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    diamondLeggings.setItemMeta(diamondLeggingsMeta);

                    Material materialBoots = Material.DIAMOND_BOOTS;
                    int amountBoots = 1;
                    short durabilityBoots = 0;
                    ItemStack diamondBoots = new ItemStack(materialBoots, amountBoots, durabilityBoots);
                    ItemMeta diamondBootsMeta = diamondBoots.getItemMeta();
                    diamondBootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    diamondBoots.setItemMeta(diamondBootsMeta);


                    equipment.setHelmet(diamondHelmet);
                    equipment.setChestplate(diamondChestplate);
                    equipment.setLeggings(diamondLeggings);
                    equipment.setBoots(diamondBoots);

                } else if (numeroAleatorio == 2) {

                    Skeleton skeleton = (Skeleton) event.getEntity();
                    World world = skeleton.getWorld();

                    skeleton.setCustomName("Esqueleto Infernal");

                    EntityEquipment equipment = skeleton.getEquipment();

                    // Iron Axe
                    Material material = Material.DIAMOND_AXE;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack diamond_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = diamond_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 10, true);
                    diamond_axe.setItemMeta(itemMeta);

                    //Iron Armour
                    Material material_helmet = Material.IRON_HELMET;
                    int cantidad_helmet = 1;
                    short durabilidad_helmet = 0;
                    ItemStack iron_helmet = new ItemStack(material_helmet, cantidad_helmet, durabilidad_helmet);

                    Material material_chestplate = Material.IRON_CHESTPLATE;
                    int cantidad_chestplate = 1;
                    short durabilidad_chestplate = 0;
                    ItemStack iron_chestplate = new ItemStack(material_chestplate, cantidad_chestplate, durabilidad_chestplate);

                    Material material_leggins = Material.IRON_LEGGINGS;
                    int cantidad_leggins = 1;
                    short durabilidad_leggins = 0;
                    ItemStack iron_leggins = new ItemStack(material_leggins, cantidad_leggins, durabilidad_leggins);

                    Material material_boots = Material.IRON_BOOTS;
                    int cantidad_boots = 1;
                    short durabilidad_boots = 0;
                    ItemStack iron_boots = new ItemStack(material_boots, cantidad_boots, durabilidad_boots);


                    equipment.setItemInMainHand(diamond_axe);
                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                } else if (numeroAleatorio == 3) {

                    Skeleton skeleton = (Skeleton) event.getEntity();
                    World world = skeleton.getWorld();

                    skeleton.setCustomName("Esqueleto Asesino");

                    EntityEquipment equipment = skeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.CROSSBOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 15, true);
                    iron_axe.setItemMeta(itemMeta);

                    //Iron Armour
                    Material material_helmet = Material.GOLDEN_HELMET;
                    int cantidad_helmet = 1;
                    short durabilidad_helmet = 0;
                    ItemStack iron_helmet = new ItemStack(material_helmet, cantidad_helmet, durabilidad_helmet);

                    Material material_chestplate = Material.GOLDEN_CHESTPLATE;
                    int cantidad_chestplate = 1;
                    short durabilidad_chestplate = 0;
                    ItemStack iron_chestplate = new ItemStack(material_chestplate, cantidad_chestplate, durabilidad_chestplate);

                    Material material_leggins = Material.GOLDEN_LEGGINGS;
                    int cantidad_leggins = 1;
                    short durabilidad_leggins = 0;
                    ItemStack iron_leggins = new ItemStack(material_leggins, cantidad_leggins, durabilidad_leggins);

                    Material material_boots = Material.GOLDEN_BOOTS;
                    int cantidad_boots = 1;
                    short durabilidad_boots = 0;
                    ItemStack iron_boots = new ItemStack(material_boots, cantidad_boots, durabilidad_boots);


                    equipment.setItemInMainHand(iron_axe);
                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                    boolean ambient = true; // Efecto ambiental
                    skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, ambient));

                } else if (numeroAleatorio == 4) {

                    event.setCancelled(true);
                    Skeleton skeleton = (Skeleton) event.getEntity();
                    WitherSkeleton witherSkeleton = (WitherSkeleton) skeleton.getWorld().spawnEntity(skeleton.getLocation(), EntityType.WITHER_SKELETON);
                    World world = witherSkeleton.getWorld();

                    witherSkeleton.setCustomName("Esqueleto Táctico");

                    EntityEquipment equipment = witherSkeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.BOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 30, true);
                    itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 15, true);
                    iron_axe.setItemMeta(itemMeta);

                    //Iron Armour
                    Material material_helmet = Material.CHAINMAIL_HELMET;
                    int cantidad_helmet = 1;
                    short durabilidad_helmet = 0;
                    ItemStack iron_helmet = new ItemStack(material_helmet, cantidad_helmet, durabilidad_helmet);

                    Material material_chestplate = Material.CHAINMAIL_CHESTPLATE;
                    int cantidad_chestplate = 1;
                    short durabilidad_chestplate = 0;
                    ItemStack iron_chestplate = new ItemStack(material_chestplate, cantidad_chestplate, durabilidad_chestplate);

                    Material material_leggins = Material.CHAINMAIL_LEGGINGS;
                    int cantidad_leggins = 1;
                    short durabilidad_leggins = 0;
                    ItemStack iron_leggins = new ItemStack(material_leggins, cantidad_leggins, durabilidad_leggins);

                    Material material_boots = Material.CHAINMAIL_BOOTS;
                    int cantidad_boots = 1;
                    short durabilidad_boots = 0;
                    ItemStack iron_boots = new ItemStack(material_boots, cantidad_boots, durabilidad_boots);


                    equipment.setItemInMainHand(iron_axe);
                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                } else if (numeroAleatorio == 5) {

                    event.setCancelled(true);
                    Skeleton skeleton = (Skeleton) event.getEntity();
                    WitherSkeleton witherSkeleton = (WitherSkeleton) skeleton.getWorld().spawnEntity(skeleton.getLocation(), EntityType.WITHER_SKELETON);
                    World world = witherSkeleton.getWorld();

                    witherSkeleton.setCustomName("Esqueleto Pesadilla");

                    EntityEquipment equipment = witherSkeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.BOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
                    iron_axe.setItemMeta(itemMeta);

                    //Iron Armour
                    Material material_helmet = Material.LEATHER_HELMET;
                    int cantidad_helmet = 1;
                    short durabilidad_helmet = 0;
                    ItemStack iron_helmet = new ItemStack(material_helmet, cantidad_helmet, durabilidad_helmet);

                    Material material_chestplate = Material.LEATHER_CHESTPLATE;
                    int cantidad_chestplate = 1;
                    short durabilidad_chestplate = 0;
                    ItemStack iron_chestplate = new ItemStack(material_chestplate, cantidad_chestplate, durabilidad_chestplate);

                    Material material_leggins = Material.LEATHER_LEGGINGS;
                    int cantidad_leggins = 1;
                    short durabilidad_leggins = 0;
                    ItemStack iron_leggins = new ItemStack(material_leggins, cantidad_leggins, durabilidad_leggins);

                    Material material_boots = Material.LEATHER_BOOTS;
                    int cantidad_boots = 1;
                    short durabilidad_boots = 0;
                    ItemStack iron_boots = new ItemStack(material_boots, cantidad_boots, durabilidad_boots);


                    equipment.setItemInMainHand(iron_axe);
                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.SKELETON) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            EntityEquipment equipment = skeleton.getEquipment();

            // Limpia el inventario del esqueleto antes de morir
            equipment.setHelmet(null);
            equipment.setChestplate(null);
            equipment.setLeggings(null);
            equipment.setBoots(null);
            equipment.setItemInMainHand(null);
        }
        else if (event.getEntityType() == EntityType.WITHER_SKELETON) {
            WitherSkeleton witherSkeleton = (WitherSkeleton) event.getEntity();
            EntityEquipment equipment = witherSkeleton.getEquipment();

            // Limpia el inventario del esqueleto antes de morir
            equipment.setHelmet(null);
            equipment.setChestplate(null);
            equipment.setLeggings(null);
            equipment.setBoots(null);
            equipment.setItemInMainHand(null);
        }
    }
}
