package de.tommyyt.novartp.listener;

import de.tommyyt.novartp.gui.RTPGUI;
import de.tommyyt.novartp.manager.RTPManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        String title = ChatColor.stripColor(
                event.getView().getTitle()
        );

        String guiTitle = ChatColor.stripColor(
                RTPGUI.GUI_TITLE
        );

        if (!title.equals(guiTitle)) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Material clicked = event.getCurrentItem().getType();

        switch (clicked) {

            case GRASS_BLOCK -> {

                player.closeInventory();

                RTPManager.teleport(
                        player,
                        World.Environment.NORMAL
                );
            }

            case NETHERRACK -> {

                player.closeInventory();

                RTPManager.teleport(
                        player,
                        World.Environment.NETHER
                );
            }

            case END_STONE -> {

                player.closeInventory();

                RTPManager.teleport(
                        player,
                        World.Environment.THE_END
                );
            }

            default -> {
                // Nichts machen
            }
        }
    }
}
