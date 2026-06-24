package de.tommyyt.novartp;

import de.tommyyt.novartp.command.RTPCommand;
import de.tommyyt.novartp.listener.GUIListener;
import org.bukkit.plugin.java.JavaPlugin;

public class NovaRTP extends JavaPlugin {

    private static NovaRTP instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        // Commands
        getCommand("rtp").setExecutor(new RTPCommand());

        // Events
        getServer().getPluginManager().registerEvents(
                new GUIListener(),
                this
        );

        getLogger().info("NovaRTP wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NovaRTP wurde deaktiviert!");
    }

    public static NovaRTP getInstance() {
        return instance;
    }
}
