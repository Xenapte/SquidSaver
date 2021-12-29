package info.bcrc.mc.squid_saver;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SquidSaverRunnable extends BukkitRunnable {
  private SquidSaverPlugin plugin;
  private BukkitTask task;
  private MovementChecker checker;

  public SquidSaverRunnable(SquidSaverPlugin plugin) {
    int gameVersion = Integer.parseInt(plugin.getServer().getClass().getPackage().getName()
                      .split("\\.")[3].replace("v1_", "").replaceAll("_R\\d", ""));

    this.plugin = plugin;

    if (gameVersion < 13)
      checker = new PreAquaticChecker(plugin);
    else if (gameVersion >= 17)
      checker = new PostGlowSquidChecker(plugin);
    else
      checker = new MovementChecker(plugin);
  };

  public void start(long interval) {
    task = this.runTaskTimer(plugin, 0L, interval);
  };

  public void stop() {
    task.cancel();
  };

  @Override
  public void run() {
    checker.checkSquids();
  };
};
