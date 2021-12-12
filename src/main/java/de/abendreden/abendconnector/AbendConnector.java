package de.abendreden.abendconnector;

import de.abendreden.abendconnector.commands.InfoCommand;
import de.abendreden.abendconnector.utils.TCPConnectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AbendConnector extends JavaPlugin {
    @Override
    public void onEnable() {
        initCommands();

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.getScheduler().runTask(this, this::run), 20*1L);

    }

    public void run(){

        try {
            new TCPConnectionManager().run(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initCommands() {
        getCommand("info").setExecutor(new InfoCommand());
    }

}
