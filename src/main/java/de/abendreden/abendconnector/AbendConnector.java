package de.abendreden.abendconnector;

import de.abendreden.abendconnector.commands.InfoCommand;
import de.abendreden.abendconnector.utils.TCPConnectionManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AbendConnector extends JavaPlugin {

    @Override
    public void onEnable() {
        initCommands();
        if (!new File("./plugins/AbendConnector/config.yml").exists()) {
            saveDefaultConfig();
        }
        new Thread(() -> {
            try {
                new TCPConnectionManager().run(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initCommands() {
        Objects.requireNonNull(getCommand("info")).setExecutor(new InfoCommand());
    }
}
