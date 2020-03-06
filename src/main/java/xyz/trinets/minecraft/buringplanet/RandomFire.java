package xyz.trinets.minecraft.buringplanet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomFire implements Listener {
    Random random = new Random();
    Material[] saplingsArr = {
            Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING,
            Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING };
    List<Material> saplings = Arrays.asList(saplingsArr);

    @EventHandler
    public void onPlayerMove (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        int time = (int) world.getTime();
        boolean isNightTime = time > 12786 && time < 23216;

        if (world.getEnvironment() == World.Environment.NORMAL && isNightTime) return;

        int randX = location.getBlockX() + random.nextInt(20) - 10;
        int randY = location.getBlockY() + random.nextInt(6) - 3;
        int randZ = location.getBlockZ() + random.nextInt(20) - 10;

        Location target = new Location(world, randX, randY, randZ);
        if (location.equals(target)) return;

        Block block = target.getBlock();
        Material material = block.getType();

        if (material == Material.AIR) block.setType(Material.FIRE);
        if (material == Material.GRASS_BLOCK) block.setType(Material.COARSE_DIRT);
        if (material == Material.WATER) block.setType(Material.AIR);
        if (material == Material.STONE) block.setType(Material.GRAVEL);
        if (material == Material.NETHERRACK) block.setType(Material.LAVA);
        if (material == Material.NETHER_BRICKS) block.setType(Material.NETHER_BRICK_SLAB);
        if (material == Material.NETHER_BRICK_SLAB) block.setType(Material.AIR);
        if (saplings.contains(material)) block.setType(Material.DEAD_BUSH);
    }
}
