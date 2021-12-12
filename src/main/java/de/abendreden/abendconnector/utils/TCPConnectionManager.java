package de.abendreden.abendconnector.utils;

import de.abendreden.abendconnector.AbendConnector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class TCPConnectionManager{
    private String host = "127.0.0.1";
    private int port = 9000;
    private String nickname;
    private AbendConnector plugin;
    private PrintStream output = null;

    public void run(AbendConnector plugin) throws IOException {
        this.plugin = plugin;
        Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");
        output = new PrintStream(client.getOutputStream());
        new Thread(() -> {
            try {
                new ReceivedMessagesHandlert(client.getInputStream(), client, plugin, output).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("./plugins/AbendConnector/config.yml"));
        String servername = yamlConfiguration.getString("servername");
        output.println("Servername:" + servername);
    }
}

class ReceivedMessagesHandlert {

    private InputStream server;
    private Socket client;
    private AbendConnector plugin;
    private PrintStream output;

    public ReceivedMessagesHandlert(InputStream server, Socket client, AbendConnector plugin, PrintStream output) {
        this.server = server;
        this.client = client;
        this.plugin = plugin;
        this.output = output;
    }

    public void run() {
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            String command = s.nextLine();
            System.out.println(command);
            if (command.equalsIgnoreCase("!close")) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (command.equalsIgnoreCase("!status")) {
                output.println("online");
            } else {
                Bukkit.getScheduler().runTask(plugin,() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
            }
        }
    }
}


