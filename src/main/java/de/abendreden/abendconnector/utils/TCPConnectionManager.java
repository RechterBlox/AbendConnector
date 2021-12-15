package de.abendreden.abendconnector.utils;

import de.abendreden.abendconnector.AbendConnector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPConnectionManager {
    private PrintStream output = null;

    public void run(final AbendConnector plugin) throws IOException {
        final int port = 9000;
        final String host = "127.0.0.1";
        final Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");
        this.output = new PrintStream(client.getOutputStream());
        new Thread(() -> {
            try {
                new ReceivedMessagesHandlert(client.getInputStream(), client, plugin, this.output).run();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }).start();
        final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("./plugins/AbendConnector/config.yml"));
        final String servername = yamlConfiguration.getString("servername");
        this.output.println("Servername:" + servername);
    }
}

record ReceivedMessagesHandlert(InputStream server, Socket client, AbendConnector plugin, PrintStream output) {
    void run() {
        final Scanner s = new Scanner(this.server);
        while (s.hasNextLine()) {
            final String command = s.nextLine();
            System.out.println(command);
            if (command.equalsIgnoreCase("!close")) {
                try {
                    this.client.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            } else if (command.equalsIgnoreCase("!status")) {
                this.output.println("online");
            } else {
                Bukkit.getScheduler().runTask(this.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
            }
        }
    }
}


