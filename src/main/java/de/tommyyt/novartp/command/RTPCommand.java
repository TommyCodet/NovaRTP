package de.tommyyt.novartp.command;

import de.tommyyt.novartp.gui.RTPGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(
                    ChatColor.RED + "Nur Spieler können diesen Command verwenden."
            );
            return true;
        }

        if (!player.hasPermission("novartp.use")) {
            player.sendMessage(
                    ChatColor.RED + "Dazu hast du keine Berechtigung."
            );
            return true;
        }

        RTPGUI.open(player);

        return true;
    }
}
