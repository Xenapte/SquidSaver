package info.bcrc.mc.squid_saver;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
  private MovementChecker checker;
  private BukkitTask task;

  @Override
  public void onEnable() {
    checker = new MovementChecker(this);
    task = checker.runTaskTimer(this, 0L, 100L);
  };

  @Override
  public void onDisable() {
    task.cancel();
  }
};
