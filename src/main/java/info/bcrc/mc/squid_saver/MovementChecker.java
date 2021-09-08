package info.bcrc.mc.squid_saver;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Squid;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class MovementChecker extends BukkitRunnable {
  private SquidSaverPlugin plugin;
  private BukkitTask task;
  private int gameVersion;

  public MovementChecker(SquidSaverPlugin plugin) {
    this.plugin = plugin;
    gameVersion = Integer.parseInt(plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].replace("v1_", "").replaceAll("_R\\d", ""));
  };

  private boolean hasWater(Location location) {
    Block block = location.getBlock();
    if (block == null)
      return false;

    if (gameVersion < 13) {
      if (block.isLiquid() && !block.getType().equals(Material.LAVA))
        return true;

      return false;
    };

    if (block.getType().equals(Material.WATER))
      return true;

    if (block instanceof Waterlogged)
      return ((Waterlogged) block).isWaterlogged();

    return false;
  }

  private Location getNearbyWaterLocation(Location location) {
    World world = location.getWorld();
    double x = location.getX(),
           y = location.getY(),
           z = location.getZ();
    for (int x1 = -1; x1 <= 1; x1 ++) {
      for (int y1 = -1; y1 <= 1; y1 ++) {
        for (int z1 = -1; z1 <= 1; z1 ++) {
          Location blockLocation = new Location(world, x + x1, y + y1, z + z1);
          if (hasWater(blockLocation))
            return blockLocation.getBlock().getLocation().add(0.5, 0, 0.5);
        };
      }
    };
    return null;
  };

  public void start(long interval) {
    task = this.runTaskTimer(plugin, 0L, interval);
  };

  public void stop() {
    task.cancel();
  };

  @Override
  public void run() {
    plugin.getServer().getWorlds().forEach(world -> {
      List<Entity> entities = world.getEntities();
      for (Entity e : entities) {
        if (e instanceof Squid) {
          if (!hasWater(e.getLocation())) {
            Location nearbyWaterLocation = getNearbyWaterLocation(e.getLocation());
            if (nearbyWaterLocation != null)
              e.teleport(nearbyWaterLocation);
          };
        };
      };
    });
  };
}
