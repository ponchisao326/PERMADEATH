package Listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.io.File;

public class onplayerjoin implements Listener {

    private final Permadeath plugin;

    public onplayerjoin(Permadeath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getDisplayName();

        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fBienvenido al Servidor");
        if (name.equals("Ponchisao326")){
            event.setJoinMessage("§7[§c§lPERMADEATH§7] §b» §ePonchisao326 §fha entrado al servidor");

            sendTitleToAll(ChatColor.AQUA + "Ponchisao", ChatColor.YELLOW + "ha entrado al " + ChatColor.RED + "servidor");
        }
        else {
            event.setJoinMessage("§7[§c§lBLANCHINIILAND§7] §b» "+ ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " ha entrado al servidor");
        };
    }

    private void sendTitleToAll(String title, String subtitle) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle(title, subtitle, 20, 60, 20);
        }
    }
}
