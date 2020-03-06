package xyz.trinets.minecraft.buringplanet;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BuringPlanet extends JavaPlugin {
    Logger logger = getLogger();
    Server server = getServer();
    PluginManager plMng = server.getPluginManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("TriNet Buring Planet V1 is Loaded!");
        server.getScheduler().scheduleSyncRepeatingTask(this, new OnEveryTick(), 1l, 1l);
        plMng.registerEvents(new RandomFire(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
