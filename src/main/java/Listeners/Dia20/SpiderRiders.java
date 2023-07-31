package Listeners.Dia20;

import com.sun.source.tree.UsesTree;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.ItemMeta;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.net.http.WebSocket;
import java.util.Random;
import java.util.UUID;

public class SpiderRiders implements Listener {

    private final Permadeath plugin;

    public SpiderRiders(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void SpiderRiders(CreatureSpawnEvent event) {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 20 || diaActual == 25 || diaActual == 30) {

            EntityType entity = event.getEntity().getType();
            Entity entity2 = event.getEntity();


            if (entity.equals(EntityType.SPIDER)) {

                Random random = new Random();
                int numeroAleatorio = random.nextInt(5) + 1; // Genera un número aleatorio entre 1 y 3

                if (numeroAleatorio == 1) {

                    Spider spider = (Spider) event.getEntity();
                    World world = spider.getWorld();

                    Skeleton skeleton = (Skeleton) world.spawnEntity(spider.getLocation(), EntityType.SKELETON);

                    EntityEquipment equipment = skeleton.getEquipment();

                    // Iron Axe
                    Material material = Material.IRON_AXE;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                    iron_axe.setItemMeta(itemMeta);

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


                    equipment.setItemInMainHand(iron_axe);
                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                    spider.addPassenger(skeleton);

                } else if (numeroAleatorio == 2) {
                    Spider spider = (Spider) event.getEntity();
                    World world = spider.getWorld();

                    Skeleton skeleton = (Skeleton) world.spawnEntity(spider.getLocation(), EntityType.SKELETON);

                    EntityEquipment equipment = skeleton.getEquipment();

                    //Diamond Armour
                    Material material_helmet = Material.DIAMOND_HELMET;
                    int cantidad_helmet = 1;
                    short durabilidad_helmet = 0;
                    ItemStack iron_helmet = new ItemStack(material_helmet, cantidad_helmet, durabilidad_helmet);

                    Material material_chestplate = Material.DIAMOND_CHESTPLATE;
                    int cantidad_chestplate = 1;
                    short durabilidad_chestplate = 0;
                    ItemStack iron_chestplate = new ItemStack(material_chestplate, cantidad_chestplate, durabilidad_chestplate);

                    Material material_leggins = Material.DIAMOND_LEGGINGS;
                    int cantidad_leggins = 1;
                    short durabilidad_leggins = 0;
                    ItemStack iron_leggins = new ItemStack(material_leggins, cantidad_leggins, durabilidad_leggins);

                    Material material_boots = Material.DIAMOND_BOOTS;
                    int cantidad_boots = 1;
                    short durabilidad_boots = 0;
                    ItemStack iron_boots = new ItemStack(material_boots, cantidad_boots, durabilidad_boots);


                    equipment.setHelmet(iron_helmet);
                    equipment.setChestplate(iron_chestplate);
                    equipment.setLeggings(iron_leggins);
                    equipment.setBoots(iron_boots);

                    spider.addPassenger(skeleton);
                } else if (numeroAleatorio == 3) {
                    Spider spider = (Spider) event.getEntity();
                    World world = spider.getWorld();

                    WitherSkeleton witherSkeleton = (WitherSkeleton) world.spawnEntity(spider.getLocation(), EntityType.WITHER_SKELETON);
                    LivingEntity livingEntity = event.getEntity();
                    AttributeInstance maxHealth = witherSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    maxHealth.setBaseValue(40);

                    EntityEquipment equipment = witherSkeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.BOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 20, true);
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

                    spider.addPassenger(witherSkeleton);
                } else if (numeroAleatorio == 4) {
                    Spider spider = (Spider) event.getEntity();
                    World world = spider.getWorld();

                    Skeleton skeleton = (Skeleton) world.spawnEntity(spider.getLocation(), EntityType.SKELETON);
                    LivingEntity livingEntity = event.getEntity();
                    AttributeInstance maxHealth = skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    maxHealth.setBaseValue(40);

                    EntityEquipment equipment = skeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.CROSSBOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 20, true);
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

                    spider.addPassenger(skeleton);
                } else if (numeroAleatorio == 5) {
                    Spider spider = (Spider) event.getEntity();
                    World world = spider.getWorld();

                    WitherSkeleton witherSkeleton = (WitherSkeleton) world.spawnEntity(spider.getLocation(), EntityType.WITHER_SKELETON);
                    LivingEntity livingEntity = event.getEntity();
                    AttributeInstance maxHealth = witherSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    maxHealth.setBaseValue(40);

                    EntityEquipment equipment = witherSkeleton.getEquipment();

                    // Bow Punch
                    Material material = Material.BOW;
                    int cantidad = 1;
                    short durabilidad = 0;
                    ItemStack iron_axe = new ItemStack(material, cantidad, durabilidad);
                    ItemMeta itemMeta = iron_axe.getItemMeta();
                    itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);
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

                    spider.addPassenger(witherSkeleton);
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
