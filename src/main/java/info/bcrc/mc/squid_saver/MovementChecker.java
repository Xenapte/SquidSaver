package info.bcrc.mc.squid_saver;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Squid;

public class MovementChecker {
  protected SquidSaverPlugin plugin;

  public MovementChecker(SquidSaverPlugin plugin) {
    this.plugin = plugin;
  };

  protected boolean hasWater(Location location) {
    Block block = location.getBlock();
    if (block == null)
      return false;

    if (block.getType().equals(Material.WATER))
      return true;

    if (block instanceof Waterlogged)
      return ((Waterlogged) block).isWaterlogged();

    return false;
  };

  protected Location getNearbyWaterLocation(Location location) {
    World world = location.getWorld();
    double x0 = location.getX(),
           y0 = location.getY() - 1,
           z0 = location.getZ();
    for (int x = -1; x <= 1; x ++) {
      for (int z1 = -1; z1 <= 1; z1 ++) {
        Location blockLocation = new Location(world, x0 + x, y0, z0 + z1);
        if (hasWater(blockLocation))
          return blockLocation.getBlock().getLocation().add(0.5, 0, 0.5);
      };
    };
    return null;
  };

  protected boolean isSquid(Entity e) {
    return (e instanceof Squid);
  };

  protected void checkSquids() {
    plugin.getServer().getWorlds().forEach(world -> {
      world.getEntities().forEach(e -> {
        if (!isSquid(e)) {
          return;
        };

        if (!hasWater(e.getLocation())) {
          Location nearbyWaterLocation = getNearbyWaterLocation(e.getLocation());
          if (nearbyWaterLocation != null)
            e.teleport(nearbyWaterLocation);
        };
      });
    });
  };
};


class PreAquaticChecker extends MovementChecker {
  public PreAquaticChecker(SquidSaverPlugin plugin) {
    super(plugin);
  };

  @Override
  protected boolean hasWater(Location location) {
    Block block = location.getBlock();
    if (block == null)
      return false;
    
    if (block.isLiquid() && !block.getType().equals(Material.LAVA))
      return true;

    return false;
  };
};


class PostGlowSquidChecker extends MovementChecker {
  public PostGlowSquidChecker(SquidSaverPlugin plugin) {
    super(plugin);
  };

  @Override
  protected boolean isSquid(Entity e) {
    return (e instanceof Squid || e instanceof GlowSquid);
  };
};