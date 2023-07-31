package ponchisaosserver.duckdns.org.permadeath;

import Completers.PermadeathCompleter;
import Listeners.*;
import Listeners.Death.*;
import Listeners.Dia10.*;
import Listeners.Dia20.*;
import Listeners.Dia25.*;
import Listeners.Dia30.*;
import Listeners.Dia30.Attacks.*;
import commands.PermadeathCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public final class Permadeath extends JavaPlugin {

    private static Permadeath instance;
    private FileConfiguration config;
    private FileConfiguration config1;
    private Scoreboard scoreboard;
    public String rutaConfig;
    public String DBConfig;
    private static Logger logger;
    private static final String driver = "com.mysql.jdbc.Driver";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        String DB_URL = config1.getString("Config.Database.DB_URL");
        String DB_USER = config1.getString("Config.Database.DB_USER");
        String DB_PASSWD = config1.getString("Config.Database.DB_PASSWORD");

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
    }

    @Override
    public void onEnable() {
        logger = getLogger();
        //Cargar la config del plugin
        registerDays();
        registerConfig();
        // Plugin startup logic
        logger.info("The plugin has started!");
        logger.info("¡Plugin creado por Ponchisao326!");
        instance = this;
        int stormDuration = 3600;

        crearTabla();

        String path = "Config.dia";
        String diaValue = config.getString(path);
        if (diaValue != null) {
            if (diaValue.equals("10")) {
                logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "¡Aplicados los cambios del dia 10!");
            } else if (diaValue.equals("20")) {
                logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "¡Aplicados los cambios del dia 20!");
            } else if (diaValue.equals("25")) {
                logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "¡Aplicados los cambios del dia 25!");
            } else if (diaValue.equals("30")) {
                logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "¡Aplicados los cambios del dia 30!");
            } else {
                logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "Estamos en el dia 0 así que solo está aplicado el DeathTrain y la prohibición de netherite");
            }
        } else {
            // Handle the case when the "dia" configuration value is missing or null
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "El valor para 'dia' en la configuración no está especificado.");
        }

        //Default
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        getCommand("permadeath").setExecutor(new PermadeathCommands(this));
        getCommand("permadeath").setTabCompleter(new PermadeathCompleter());
        getServer().getPluginManager().registerEvents(new onplayerjoin(this), this);
        getServer().getPluginManager().registerEvents(new DeathTrainPermsChange(this), this);
        getServer().getPluginManager().registerEvents(new OnNetheriteCollect(), this);
        getServer().getPluginManager().registerEvents(new OnNetheritePickUp(), this);
        OnDeath onDeathListener = new OnDeath(this);
        getServer().getPluginManager().registerEvents(onDeathListener, this);

        //Repetidos varios dias
        getServer().getPluginManager().registerEvents(new onmobdeath(this), this);
        getServer().getPluginManager().registerEvents(new SpiderRiders(this), this);
        getServer().getPluginManager().registerEvents(new PigmansAgresivos(this), this);
        getServer().getPluginManager().registerEvents(new OnRavagerSpawn(this), this);
        getServer().getPluginManager().registerEvents(new OnPhantomSpawn(this), this);
        getServer().getPluginManager().registerEvents(new MobEffects(this), this); //death train dia 25
        getServer().getPluginManager().registerEvents(new OnMobDrop25(this), this);
        getServer().getPluginManager().registerEvents(new GigaMobs(this), this);
        getServer().getPluginManager().registerEvents(new GhastExplosionPower(this), this);
        getServer().getPluginManager().registerEvents(new OnNetheriteSuit(this), this);

        //Dia 10
        getServer().getPluginManager().registerEvents(new OnSpiderSpawnEffects(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerSleepDeathTrain(this), this);

        //Dia 20
        getServer().getPluginManager().registerEvents(new onmobdrop(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerSleep(this), this);
        getServer().getPluginManager().registerEvents(new OnSpiderSpawnEffects20(this), this);

        //Dia 25
        getServer().getPluginManager().registerEvents(new OnSpiderSpawnEffects25(this), this);
        getServer().getPluginManager().registerEvents(new NetheriteArmourSpawn(this), this);

        //Dia 30
        getServer().getPluginManager().registerEvents(new OnSkeletonSpawn(this), this);
        getServer().getPluginManager().registerEvents(new TotemFailure(this), this);
        getServer().getPluginManager().registerEvents(new OnEntitySpawnTransform(this), this);
        getServer().getPluginManager().registerEvents(new ExplosiveShulkers(this), this);
        getServer().getPluginManager().registerEvents(new EnderGhastCreeper(this), this);
        getServer().getPluginManager().registerEvents(new BedrockLevitacion(this), this);
        getServer().getPluginManager().registerEvents(new DragonLightning(this), this);
        getServer().getPluginManager().registerEvents(new RandomTNTGenerator(this), this);

        String DB_URL = config1.getString("Config.Database.DB_URL");
        String DB_USER = config1.getString("Config.Database.DB_USER");
        String DB_PASSWD = config1.getString("Config.Database.DB_PASSWORD");

        if (DB_URL == null || DB_USER == null ||DB_PASSWD == null) {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "¡No se ha introducido ningun dato en el archivo config.yml!");
        }

        if (DB_URL == "jdbc:mysql://localhost/PONCHISAO326" || DB_USER == "root" || DB_PASSWD == "1234") {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "¡No has introducido los datos de tu base de datos en el archivo config.yml!");
        }

        try (Connection connection = getConnection()) {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "INFO: " + ChatColor.GREEN + "Base de datos activada");
        }
        catch (SQLException e) {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Ha ocurrido un error al configurar la base de datos, por favor revise el archivo config.yml.");
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Deshabilitando plugin...");
            e.printStackTrace();
            disablePlugin();
        }
        catch (ClassNotFoundException e) {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Ha ocurrido un error al configurar la base de datos, por favor revise el archivo config.yml.");
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Deshabilitando plugin...");
            disablePlugin();
            throw new RuntimeException(e);
        }

    }

    public static Permadeath getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown login
        logger.info("The plugin has been disabled!");
    }

    public void registerDays() {
        File dayFile = new File(this.getDataFolder(), "days.yml");
        rutaConfig = dayFile.getPath();
        if (!dayFile.exists()) {
            this.saveResource("days.yml", false); // Guarda la configuración predeterminada
        }
        config = YamlConfiguration.loadConfiguration(dayFile); // Carga la configuración desde el archivo
    }
    public void registerConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        DBConfig = configFile.getPath();
        if (!configFile.exists()) {
            this.saveResource("config.yml", false); // Guarda la configuración predeterminada
        }
        config1 = YamlConfiguration.loadConfiguration(configFile); // Carga la configuración desde el archivo
    }
    public void disablePlugin() {
        // Obtiene el administrador de plugins y deshabilita el plugin actual
        getServer().getPluginManager().disablePlugin(this);
    }

    private void crearTabla() {
        try (Connection connection = getConnection()) {
            // Crear una declaración para ejecutar el comando SQL
            try (Statement statement = connection.createStatement()) {
                // Comando SQL para crear la tabla jugadores si no existe
                String createTableQuery = "CREATE TABLE IF NOT EXISTS jugadores (" +
                        "nombre VARCHAR(255) NOT NULL, " +
                        "vidas INT NOT NULL, " +
                        "PRIMARY KEY (nombre)" +
                        ")";
                // Ejecutar el comando
                statement.executeUpdate(createTableQuery);
            }
        } catch (SQLException e) {
            // Manejo de errores de la base de datos
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Han habido uno o varios problemas en la creación de tablas");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(ChatColor.GRAY + "[" + ChatColor.RED + "PERMADEATH" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "ERROR: " + ChatColor.RED + "Han habido uno o varios problemas en la creación de tablas");
            throw new RuntimeException(e);
        }
    }


}
