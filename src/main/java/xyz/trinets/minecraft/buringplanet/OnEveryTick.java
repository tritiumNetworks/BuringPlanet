package xyz.trinets.minecraft.buringplanet;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public class OnEveryTick implements Runnable {
    Plugin plugin = BuringPlanet.getPlugin(BuringPlanet.class);
    Server server = plugin.getServer();
    Collection<Player> players = (Collection<Player>) server.getOnlinePlayers();
    List<World> worlds = server.getWorlds();

    @Override
    public void run() {
        worlds.forEach((world) -> {
            World.Environment environment = world.getEnvironment();
            int time = (int) world.getTime();

            boolean isNightTime = time > 12786 && time < 23216;
            boolean isNormalWorld = environment.equals(World.Environment.NORMAL);
            boolean isRaining = world.hasStorm();

            if (!isNightTime && isNormalWorld && isRaining)
                world.setStorm(false);
        });

        players.forEach((player) -> {
            World world = player.getWorld();

            Location location = player.getLocation();
            Block block = location.getBlock();
            Material material = block.getType();

            Player.Spigot playerSpigot = player.spigot();
            int time = (int) world.getTime();

            switch (time) {
                case 12786:
                    playerSpigot.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a해가 저물었습니다"));
                    break;

                case 23000:
                    playerSpigot.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§d곧 해가 떠오릅니다"));
                    break;

                case 23216:
                    playerSpigot.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c해가 떠올랐습니다!"));
                    break;
            }

            boolean isNormalWorld = world.getEnvironment().equals(World.Environment.NORMAL);
            boolean isPlayerInWater = material == Material.WATER;
            boolean isNightTime = time > 12786 && time < 23216;
            boolean isBlockedSky = false;
            for (int c = location.getBlockY() + 2; c < 255; c++) {
                Location checkLoc = location.clone();
                checkLoc.setY(c);
                Block checkBlo = checkLoc.getBlock();
                Material checkMat = checkBlo.getType();

                if (checkMat != Material.AIR) isBlockedSky = true;
            }

            if (isNormalWorld && !isPlayerInWater && !isNightTime && !isBlockedSky)
                player.setFireTicks(20);
        });
    }
}
