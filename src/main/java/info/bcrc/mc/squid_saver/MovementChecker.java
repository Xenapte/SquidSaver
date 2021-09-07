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

public class MovementChecker extends BukkitRunnable {
  private Main plugin;

  public MovementChecker(Main plugin) {
    this.plugin = plugin;
  };

  private boolean hasWater(Location location) {
    Block block = location.getBlock();
    if (block == null)
      return false;

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
            return blockLocation;
        };
      }
    };
    return null;
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
