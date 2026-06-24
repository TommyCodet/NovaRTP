package de.tommyyt.novartp.manager;

import de.tommyyt.novartp.NovaRTP;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class RTPManager {

    private static final Random RANDOM = new Random();

    public static void teleport(Player player, World.Environment environment) {

        World world = getWorld(environment);

        if (world == null) {
            player.sendMessage(ChatColor.RED + "Welt nicht gefunden.");
            return;
        }

        String path = switch (environment) {
            case NORMAL -> "dimensions.overworld";
            case NETHER -> "dimensions.nether";
            case THE_END -> "dimensions.end";
            default -> null;
        };

        if (path == null) {
            player.sendMessage(ChatColor.RED + "Dimension nicht unterstützt.");
            return;
        }

        int minRadius = NovaRTP.getInstance()
                .getConfig()
                .getInt(path + ".min-radius");

        int maxRadius = NovaRTP.getInstance()
                .getConfig()
                .getInt(path + ".max-radius");

        Location safeLocation = findSafeLocation(
                world,
                minRadius,
                maxRadius
        );

        if (safeLocation == null) {

            player.sendMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            NovaRTP.getInstance().getConfig().getString(
                                    "messages.no-safe-location",
                                    "&cKeine sichere Position gefunden."
                            )
                    )
            );

            return;
        }

        player.teleport(safeLocation);

        player.sendMessage(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        NovaRTP.getInstance().getConfig().getString(
                                "messages.teleporting",
                                "&aDu wurdest zufällig teleportiert."
                        )
                )
        );
    }

    private static Location findSafeLocation(
            World world,
            int minRadius,
            int maxRadius
    ) {

        for (int tries = 0; tries < 50; tries++) {

            int x = generateCoordinate(
                    minRadius,
                    maxRadius
            );

            int z = generateCoordinate(
                    minRadius,
                    maxRadius
            );

            int y = world.getHighestBlockYAt(x, z);

            Location location = new Location(
                    world,
                    x + 0.5,
                    y + 1,
                    z + 0.5
            );

            if (isSafe(location)) {
                return location;
            }
        }

        return null;
    }

    private static int generateCoordinate(
            int min,
            int max
    ) {

        int value = RANDOM.nextInt(
                max - min + 1
        ) + min;

        if (RANDOM.nextBoolean()) {
            value *= -1;
        }

        return value;
    }

    private static boolean isSafe(Location location) {

        Block feet = location.getBlock();

        Block head = location.clone()
                .add(0, 1, 0)
                .getBlock();

        Block ground = location.clone()
                .subtract(0, 1, 0)
                .getBlock();

        if (!feet.getType().isAir()) {
            return false;
        }

        if (!head.getType().isAir()) {
            return false;
        }

        Material groundType = ground.getType();

        if (groundType == Material.LAVA
                || groundType == Material.LAVA_CAULDRON
                || groundType == Material.MAGMA_BLOCK
                || groundType == Material.WATER
                || groundType == Material.KELP
                || groundType == Material.SEAGRASS
                || groundType == Material.TALL_SEAGRASS
                || groundType == Material.VOID_AIR
                || groundType == Material.AIR) {
            return false;
        }

        return true;
    }

    private static World getWorld(
            World.Environment environment
    ) {

        for (World world : Bukkit.getWorlds()) {

            if (world.getEnvironment() == environment) {
                return world;
            }
        }

        return null;
    }
}
