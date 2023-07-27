package commands;

import Listeners.Death.OnDeath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class PermadeathCommands implements CommandExecutor {

    private final Permadeath plugin;
    public PermadeathCommands(Permadeath plugin) {
        this.plugin = plugin;
    }
    private static final String driver = "com.mysql.jdbc.Driver";
    private static Logger logger;

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        String DB_URL = config.getString("Config.Database.DB_URL");
        String DB_USER = config.getString("Config.Database.DB_USER");
        String DB_PASSWD = config.getString("Config.Database.DB_PASSWORD");

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        logger = getLogger();
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage("§7[§c§lBLANCHINIILAND§7] §b» §fNo has especificado que comando deseas ejecutar!");
                player.sendMessage("§7[§c§lBLANCHINIILAND§7] §b» §c/help para mostrar la ayuda");
            }
            else if (args.length == 1) {
                String word_info = args[0];
                if (word_info.equalsIgnoreCase("info")) {
                    player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fBienvenido a la info, este es un plugin desarrollado por §ePonchisao326 §fpara el servidor de §c§lPERMADEATH §f basandose en el mismo. Si necesitas ayuda sobre mi, usa /permadeath help");
                } else if (word_info.equalsIgnoreCase("enderdragon")) {
                    if (player.hasPermission("permadeath.modify")) {
                        modificarDragonDelEnd();
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §aDragon modificado correctamente");
                    }
                    else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §c¡No tienes permisos para ejecutar este comando!");
                    }
                } else if (word_info.equalsIgnoreCase("vidas")) {

                    try (Connection connection = getConnection()) {
                        int lives = getPlayerLives(player.getName(), connection);
                        if (lives != -1) {
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fTienes " + lives + " vidas restantes.");
                        } else {
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fActualmente tienes las 3 vidas.");
                        }
                    }
                    catch (SQLException e) {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cError al obtener las vidas del jugador, por favor contacte con un administrador.");
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (word_info.equalsIgnoreCase("help")) {
                    player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fBienvenido a la §eayuda §f del plugin §c§lPERMADEATH§f, los comandos que tienes disponibles son:");
                    player.sendMessage("- §e/info: §fTe muestra la información sobre el plugin");
                    player.sendMessage("- §e/dia: §fTe muestran los cambios del dia actual");
                    player.sendMessage("- §e/remaining: §fSi el DeathTrain está activo te mostrará el tiempo restante de tormenta");
                    player.sendMessage("- §e/help: §fel menu que estás viendo ahora");
                }
                else if (word_info.equalsIgnoreCase("dia")) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
                    int diaActual = config.getInt("Config.dia");
                    if (diaActual == 10 || diaActual == 20 || diaActual == 25 || diaActual == 30) {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fActualmente nos encontramos con los cambios del dia §e" + diaActual + ". §fLo que implica varios cambios, ver el discord para ver cuales");
                        player.sendMessage("§bLink del Discord: §fhttps://discord.gg/fDUNzgcDqr");
                    } else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fActualmente nos encontramos con los cambios del dia §e" + diaActual + ". §fLo que implica que solo está implementado el DeathTrain");
                    }
                }
                else if (word_info.equalsIgnoreCase("ticket")) {
                    player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fPara abrir un ticket con un problema, por favor dirigete al discord y abre un ticket");
                    player.sendMessage("§bLink del Discord: §fhttps://discord.gg/fDUNzgcDqr");
                }
                else if (word_info.equalsIgnoreCase("remaining")) {
                    World world = player.getWorld();

                    long time = world.getFullTime();
                    boolean isStorming = world.hasStorm();
                    boolean isThundering = world.isThundering();
                    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
                    Boolean deathtrain = config.getBoolean("Config.deathtrain");
                    if (deathtrain) {
                        if (!world.hasStorm()) {
                            // No hay tormenta activa
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cNo hay tormenta activa en este momento.");
                        } else {
                            int remainingSeconds = getRemainingStormTime(world);
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fEl tiempo restante de la tormenta es de " + remainingSeconds + " segundos.");
                        }
                    }
                    else {
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fEl DeathTrain no está activo.");
                    }
                    return true;
                }
                else if (word_info.equalsIgnoreCase("reload")) {
                    if (player.hasPermission("permadeath.reload")) {
                        plugin.registerDays();
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §aEl plugin ha sido recargado correctamente.");
                        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "days.yml"));
                        int diaActual = config.getInt("Config.dia");
                        logger.info("§7[§c§lPERMADEATH§7] §b» INFO: §eDia actual: " + diaActual);
                    } else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cNo tienes permisos para recargar el plugin.");
                    }
                }
                else if (word_info.equalsIgnoreCase("resetlives")) {
                    if (player.hasPermission("permadeath.reset")) {
                        String playerName;

                        playerName = player.getName();
                        try (Connection connection = getConnection()) {
                            // Verifica si el jugador ya tiene vidas registradas
                            int lives = getPlayerLives(playerName, connection);

                            if (lives != -1) {
                                // El jugador ya tiene vidas registradas, actualiza las vidas a 2
                                updatePlayerLives(playerName, 3, connection);
                                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fLas vidas de §e" + playerName + "§f han sido actualizadas a 3");
                            } else {
                                // El jugador no tiene vidas registradas, inserta la cantidad inicial de vidas (2)
                                insertPlayerLives(playerName, 3, connection);
                                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fLas vidas de §e" + playerName + "§f han sido actualizadas a 3");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §c¡No tienes permisos para ejecutar este comando!");
                    }
                }
            }
            else if (args.length == 2) {
                String word = args[0]; //changeday command.
                if (word.equalsIgnoreCase("changeday")) {
                    if (player.hasPermission("permadeath.daychange")) {
                        if (args.length < 2) {
                            player.sendMessage("Uso correcto: /permadeath changeday <dia>");
                            return true;
                        }
                        try {
                            int dia = Integer.parseInt(args[1]);

                            if (dia != 10 && dia != 20 && dia != 25 && dia != 30 && dia != 0) {
                                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cSolo se pueden configurar los días 0, 10, 20, 25 y 30.");
                                return true;
                            }

                            FileConfiguration config = plugin.getConfig();
                            config.set("Config.dia", dia);
                            config.save(new File(plugin.getDataFolder(), "days.yml"));

                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §aSe ha actualizado el día en la configuración correctamente.");
                        }
                        catch (IOException e) {
                            player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cHubo un error al guardar la configuración. Por favor, contacta al administrador.");
                        }
                    } else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §cNo tienes permisos para usar este comando.");
                    }
                }
                else if (word.equalsIgnoreCase("resetlives")) {
                    if (player.hasPermission("permadeath.reset")) {
                        String playerName;

                        playerName = args[1];
                        try (Connection connection = getConnection()) {
                            // Verifica si el jugador ya tiene vidas registradas
                            int lives = getPlayerLives(playerName, connection);

                            if (lives != -1) {
                                // El jugador ya tiene vidas registradas, actualiza las vidas a 2
                                updatePlayerLives(playerName, 3, connection);
                                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fLas vidas de §e" + playerName + "§f han sido actualizadas a 3");
                            } else {
                                // El jugador no tiene vidas registradas, inserta la cantidad inicial de vidas (2)
                                insertPlayerLives(playerName, 3, connection);
                                player.sendMessage("§7[§c§lPERMADEATH§7] §b» §fLas vidas de §e" + playerName + "§f han sido actualizadas a 3");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        player.sendMessage("§7[§c§lPERMADEATH§7] §b» §c¡No tienes permisos para ejecutar este comando!");
                    }
                }
            }
        }
        else {
            logger.info("§7[§c§lPERMADEATH§7] §b» §cEste comando solo lo pueden usar los jugadores");
        }
        return true;
    }
    private int getRemainingStormTime(World world) {
        int remainingTime = world.getWeatherDuration();
        return (remainingTime / 20); // Convertir de ticks a segundos
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

    private void modificarDragonDelEnd() {
        // Obtener el mundo del End (puedes cambiar el nombre del mundo si es diferente)
        World endWorld = Bukkit.getWorld("world_the_end");

        // Obtener todas las entidades del End
        for (org.bukkit.entity.Entity entity : ((World) endWorld).getEntities()) {
            // Verificar si la entidad es un dragón del End
            if (entity.getType() == EntityType.ENDER_DRAGON) {
                EnderDragon enderDragon = (EnderDragon) entity;

                // Cambiar el nombre del dragón
                enderDragon.setCustomName("§6§lPERMADEATH DEMON");

                // Cambiar la vida del dragón (por ejemplo, establecer su vida al máximo)
                AttributeInstance maxHealth = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                maxHealth.setBaseValue(600);
                enderDragon.setHealth(600);

                // Finalizar el bucle después de encontrar y modificar el dragón (opcional)
                break;
            }
        }
    }
}
