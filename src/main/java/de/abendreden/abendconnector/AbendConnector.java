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
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                new Thread(() -> {
                    try {
                        new TCPConnectionManager().run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, 20*1L);
    }
    public void test() {


    }

    public void initCommands() {
        getCommand("info").setExecutor(new InfoCommand());
    }

    public static void commandsend(String command) {
       Bukkit.broadcastMessage(command);
    }
}
