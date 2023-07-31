package Listeners.Death;

import net.luckperms.api.node.Node;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class OnDeath implements Listener {

    public OnDeath(Permadeath plugin) {
        this.plugin = plugin;
    }

    private static final String driver = "com.mysql.jdbc.Driver";
    private final Permadeath plugin;
    private int globalHours = 0;
    int duration = 3600 * 20;


    private Connection getConnection() throws SQLException {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        String DB_URL = config.getString("Config.Database.DB_URL");
        String DB_USER = config.getString("Config.Database.DB_USER");
        String DB_PASSWD = config.getString("Config.Database.DB_PASSWORD");

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
    }

    private void grantPermissionToPlayer(Player player) {
        // Obtén la instancia de LuckPerms
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Obtiene el objeto User de LuckPerms para el jugador
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        // Crea un nodo de permiso para 'permadeath.deathtrain true'
        Node node = PermissionNode.builder("permadeath.deathtrain").value(true).build();

        // Agrega el nodo de permiso al jugador usando la API de LuckPerms
        user.data().add(node);

        // Guarda los cambios en la base de datos de LuckPerms
        luckPerms.getUserManager().saveUser(user);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) throws ClassNotFoundException, IOException {
        Player player = event.getEntity();
        String playerName = player.getName();
        World world = Bukkit.getWorld("world");

        FileConfiguration config = plugin.getConfig();
        config.set("Config.deathtrain", true);
        config.save(new File(plugin.getDataFolder(), "days.yml"));


        Class.forName(driver);
        try (Connection connection = getConnection()) {
            // Verifica si el jugador ya tiene vidas registradas
            int lives = getPlayerLives(playerName, connection);

            if (lives != -1) {
                // El jugador ya tiene vidas registradas
                if (lives > 1) {
                    // Resta una vida al jugador

                    //updatePlayerLives(playerName, lives - 1, connection);

                    // Realiza las acciones necesarias cuando el jugador pierde una vida
                    player.sendMessage("Has perdido una vida. Vidas restantes: " + (lives - 1));
                    Bukkit.getServer().broadcastMessage("§7[§c§lPERMADEATH§7] §b» §cEl jugador §b" + playerName + " §cha perdido una de sus vidas. Vidas restantes: " + (lives - 1));
                    player.getWorld().setStorm(true);
                    world.setWeatherDuration(duration);
                    //addOneHourToWeatherDuration(world);

                } else {
                    // El jugador ha perdido todas sus vidas
                    // Aplica el ban y realiza las acciones correspondientes
                    String banReason = "§cHas sido PERMABANEADO"; // Puedes personalizar el motivo del ban
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(playerName, banReason, null, null);
                    player.kickPlayer("§cHas sido PERMABANEADO");
                    player.getWorld().setStorm(true);
                    world.setWeatherDuration(duration);
                    //addOneHourToWeatherDuration(world);

                }
            } else {
                // El jugador no tiene vidas registradas, asigna la cantidad inicial de vidas

                //insertPlayerLives(playerName, 2, connection);

                player.sendMessage("Has perdido una vida. Vidas restantes: 2");
                Bukkit.getServer().broadcastMessage("§7[§c§lPERMADEATH§7] §b» §cEl jugador §b" + playerName + " §cha perdido una de sus vidas. Vidas restantes: 2");
                player.getWorld().setStorm(true);
                world.setWeatherDuration(duration);
                //addOneHourToWeatherDuration(world);

            }

        } catch (SQLException e) {
            // Manejo de errores de la base de datos
            e.printStackTrace();
        }

        sendTitleToAll(ChatColor.RED + "¡Permadeath!", playerName + " ha muerto");
            playSoundToAllPlayers(Sound.ENTITY_WARDEN_HEARTBEAT, 2.0f, 0.8f);
        Bukkit.getScheduler().runTaskLater(Permadeath.getInstance(), () -> {
            playSoundToAllPlayers(Sound.ENTITY_WARDEN_HEARTBEAT, 2.0f, 0.8f);
        }, 20L);
        Bukkit.getScheduler().runTaskLater(Permadeath.getInstance(), () -> {
            playSoundToAllPlayers(Sound.ENTITY_WARDEN_HEARTBEAT, 2.0f, 0.8f);
        }, 40L);
        Bukkit.getScheduler().runTaskLater(Permadeath.getInstance(), () -> {
            playSoundToAllPlayers(Sound.ENTITY_ENDER_DRAGON_AMBIENT, 2.0f, 0.8f);
        }, 60L);
    }

    public static void playSoundToAllPlayers(Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    private void sendTitleToAll(String title, String subtitle) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle(title, subtitle, 20, 60, 20);
        }
    }

    private int getPlayerLives(String playerName, Connection connection) throws SQLException {
        String selectQuery = "SELECT vidas FROM jugadores WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("vidas");
            } else {
                return -1;
            }
        }
    }

    private void updatePlayerLives(String playerName, int lives, Connection connection) throws SQLException {
        String updateQuery = "UPDATE jugadores SET vidas = ? WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, lives);
            statement.setString(2, playerName);
            statement.executeUpdate();
        }
    }

    private void insertPlayerLives(String playerName, int lives, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO jugadores (nombre, vidas) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, playerName);
            statement.setInt(2, lives);
            statement.executeUpdate();
        }
    }

 //   private void addOneHourToWeatherDuration(World world) {
 //       if (world.hasStorm()) {
 //           int currentDuration = world.getWeatherDuration();
 //           int newDuration = currentDuration + 3600; // Sumar una hora (3600 segundos)
 //           world.setWeatherDuration(newDuration);
//
 //           globalHours++;
//
 //           for (Player onlinePlayer : world.getPlayers()) {
 //               int remainingTime = (newDuration - currentDuration) / 20; // Convertir de ticks a segundos
 //               // Puedes utilizar remainingTime para mostrar el tiempo restante en el chat
 //           }
 //       }
 //   }



    private HashMap<String, Integer> playerLives = new HashMap<>();

}
