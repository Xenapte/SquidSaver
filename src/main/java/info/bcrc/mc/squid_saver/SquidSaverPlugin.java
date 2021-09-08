package info.bcrc.mc.squid_saver;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SquidSaverPlugin extends JavaPlugin {
  private MovementChecker checker;
  private FileConfiguration config;
  private long checkInterval;

  private void loadConfig() {
    if (config != null)
      reloadConfig();

    config = getConfig();
    config.addDefault("squid-check-interval", 100);

    config.options().copyDefaults(true);
    saveDefaultConfig();
    saveConfig();

    checkInterval = config.getLong("squid-check-interval");
  };

  private void reload() {
    checker.stop();
    loadConfig();
    checker = new MovementChecker(this);
    checker.start(checkInterval);
  };

  private void showStatus(CommandSender sender) {
    sender.sendMessage(
      ChatColor.AQUA + "[SquidSaver] Status: checking squid position every "
      + Long.toString(checkInterval) + " ticks ("+ Double.toString((double) checkInterval / 20) + " seconds)."
    );
  };

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("squid")) {
      if (args.length == 0) {
        showStatus(sender);
        return true;
      };
      if (args.length > 0) {
        String action = args[0].toLowerCase();
        switch (action) {
          case "status":
            showStatus(sender);
            return true;
          case "reload":
            reload();
            sender.sendMessage(ChatColor.GREEN + "[SquidSaver] Reloaded the config.");
            showStatus(sender);
            return true;
          case "setinterval":
            if (args.length > 1) {
              try {
                checkInterval = Long.parseLong(args[1]);
              } catch (NumberFormatException e) {
                checkInterval = 100;
                sender.sendMessage(ChatColor.RED + "[SquidSaver] Error on conversion.");
                sender.sendMessage(ChatColor.RED + "[SquidSaver] Falling back to the default settings.");
              };
              config.set("squid-check-interval", checkInterval);
              saveConfig();
              showStatus(sender);
              reload();
              return true;
            };
        };
      };
    };
    return false;
  };

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (command.getName().equalsIgnoreCase("squid") && args.length <= 1) {
      return Arrays.asList("status", "reload", "setinterval");
    };
    return null;
  }

  @Override
  public void onEnable() {
    loadConfig();
    checker = new MovementChecker(this);
    checker.start(checkInterval);
  };

  @Override
  public void onDisable() {
    checker.stop();
  }
};
