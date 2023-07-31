package Listeners.Dia25;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;
import java.util.*;

public class OnNetheriteSuit implements Listener {

    private final Permadeath plugin;

    public OnNetheriteSuit(Permadeath plugin) {
        this.plugin = plugin;
    }

    private static final double ADDITIONAL_HEALTH = 8.0;
    private static final UUID HEALTH_BOOST_UUID = UUID.randomUUID();
    private static final String HEALTH_BOOST_NAME = "NetheriteHealthBoost";
    private Map<UUID, AttributeModifier> healthBoostModifiers = new HashMap<>();


    @EventHandler
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            Player player = event.getPlayer();
            checkArmor(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            Player player = (Player) event.getWhoClicked();
            checkArmor(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
        int diaActual = config.getInt("Config.dia");
        if (diaActual == 25 || diaActual == 30) {
            Player player = event.getPlayer();
            checkArmor(player);
        }
    }

    private void checkArmor(Player player) {
        boolean hasFullNetheriteArmor = hasFullNetheriteArmor(player);

        if (hasFullNetheriteArmor) {
            giveHealthBoost(player);
        } else {
            removeHealthBoost(player);
        }
    }

    private void giveHealthBoost(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(28.0);
    }

    private void removeHealthBoost(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
    }

    private boolean hasFullNetheriteArmor(Player player) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        return armorContents[0] != null && armorContents[0].getType() == Material.NETHERITE_BOOTS &&
                armorContents[1] != null && armorContents[1].getType() == Material.NETHERITE_LEGGINGS &&
                armorContents[2] != null && armorContents[2].getType() == Material.NETHERITE_CHESTPLATE &&
                armorContents[3] != null && armorContents[3].getType() == Material.NETHERITE_HELMET;
    }

}
