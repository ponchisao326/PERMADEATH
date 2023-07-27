package Listeners.Dia30.Attacks;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ponchisaosserver.duckdns.org.permadeath.Permadeath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTNTGenerator implements Listener {

    private final Permadeath plugin;
    private List<Player> onlinePlayers;
    private EnderDragon dragon;
    private BukkitRunnable tntRunnable;

    public RandomTNTGenerator(Permadeath plugin) {
        this.plugin = plugin;
        this.onlinePlayers = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        if (dragon != null && !dragon.isDead() && world.equals(dragon.getWorld())
                && playerLocation.distanceSquared(dragon.getLocation()) <= 70 * 70) {

            if (tntRunnable == null || tntRunnable.isCancelled()) {
                tntRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Choose 1 or 2 random players from the onlinePlayers list
                        List<Player> selectedPlayers = selectRandomPlayers(2);

                        for (Player selectedPlayer : selectedPlayers) {
                            generateRandomTNT(selectedPlayer);
                        }
                    }
                };
                tntRunnable.runTaskTimer(plugin, 0, 20 * 60); // Execute every 60 seconds
            }

        } else if (tntRunnable != null) {
            tntRunnable.cancel();
        }
    }

    @EventHandler
    public void onDragonSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            dragon = (EnderDragon) event.getEntity();
        }
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            dragon = null;
        }
    }

    private List<Player> selectRandomPlayers(int maxPlayers) {
        List<Player> selectedPlayers = new ArrayList<>();
        int playersToSelect = Math.min(maxPlayers, onlinePlayers.size());
        List<Player> playersCopy = new ArrayList<>(onlinePlayers);

        for (int i = 0; i < playersToSelect; i++) {
            Random random = new Random();
            int selectedIndex = random.nextInt(playersCopy.size());
            selectedPlayers.add(playersCopy.get(selectedIndex));
            playersCopy.remove(selectedIndex);
        }

        return selectedPlayers;
    }

    private void generateRandomTNT(Player player) {
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        // Generate 3 TNT at random locations around the player
        for (int i = 0; i < 3; i++) {
            Location randomLocation = getRandomLocationAroundPlayer(playerLocation, 5);
            TNTPrimed tnt = world.spawn(randomLocation, TNTPrimed.class);
            tnt.setFuseTicks(40); // Adjust the time until TNT explodes (default is 80 ticks)
        }
    }

    private Location getRandomLocationAroundPlayer(Location center, int radius) {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double x = center.getX() + radius * Math.cos(angle);
        double z = center.getZ() + radius * Math.sin(angle);
        double y = center.getWorld().getHighestBlockYAt((int) x, (int) z);

        return new Location(center.getWorld(), x, y, z);
    }

}
