package de.tommyyt.novartp.gui;

import de.tommyyt.novartp.NovaRTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RTPGUI {

    public static final String GUI_TITLE =
            ChatColor.translateAlternateColorCodes(
                    '&',
                    NovaRTP.getInstance().getConfig().getString("gui.title", "&bNovaRTP")
            );

    public static void open(Player player) {

        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                GUI_TITLE
        );

        fillGlass(inventory);

        FileConfiguration config = NovaRTP.getInstance().getConfig();

        if (config.getBoolean("dimensions.overworld.enabled")) {
            inventory.setItem(
                    11,
                    createWorldItem(
                            Material.GRASS_BLOCK,
                            "&aOverworld",
                            config.getInt("dimensions.overworld.min-radius"),
                            config.getInt("dimensions.overworld.max-radius")
                    )
            );
        }

        if (config.getBoolean("dimensions.nether.enabled")) {
            inventory.setItem(
                    13,
                    createWorldItem(
                            Material.NETHERRACK,
                            "&cNether",
                            config.getInt("dimensions.nether.min-radius"),
                            config.getInt("dimensions.nether.max-radius")
                    )
            );
        }

        if (config.getBoolean("dimensions.end.enabled")) {
            inventory.setItem(
                    15,
                    createWorldItem(
                            Material.END_STONE,
                            "&5The End",
                            config.getInt("dimensions.end.min-radius"),
                            config.getInt("dimensions.end.max-radius")
                    )
            );
        }

        player.openInventory(inventory);
    }

    private static ItemStack createWorldItem(
            Material material,
            String name,
            int minRadius,
            int maxRadius
    ) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes('&', name)
        );

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Klicke zum Teleportieren");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Min Radius: "
                + ChatColor.WHITE + minRadius);

        lore.add(ChatColor.YELLOW + "Max Radius: "
                + ChatColor.WHITE + maxRadius);

        lore.add("");
        lore.add(ChatColor.GREEN + "➜ Teleportieren");

        meta.setLore(lore);

        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );

        item.setItemMeta(meta);

        return item;
    }

    private static void fillGlass(Inventory inventory) {

        ItemStack glass = new ItemStack(
                Material.GRAY_STAINED_GLASS_PANE
        );

        ItemMeta meta = glass.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {

            if (i == 11 || i == 13 || i == 15) {
                continue;
            }

            inventory.setItem(i, glass);
        }
    }
}
