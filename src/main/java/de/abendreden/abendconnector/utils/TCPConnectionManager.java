package de.abendreden.abendconnector.utils;

import de.abendreden.abendconnector.AbendConnector;
import org.bukkit.Bukkit;

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

    public void run() throws IOException {
        // connect client to server
        Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");
        Bukkit.getScheduler().runTask(AbendConnector.getInstance(), () -> {
            try {
                new ReceivedMessagesHandlert(client.getInputStream(), client).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
       // new Thread(new ReceivedMessagesHandlert(client.getInputStream(), client)).start();
        /*new Thread(() -> {
            try {
                new ReceivedMessagesHandlert(client.getInputStream(), client).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();*/

        PrintStream output = null;
        output = new PrintStream(client.getOutputStream());
        output.println("Servername:Test");
    }
}

class ReceivedMessagesHandlert implements Runnable {

    private InputStream server;
    private Socket client;

    public ReceivedMessagesHandlert(InputStream server, Socket client) {
        this.server = server;
        this.client = client;
        System.out.println("tssfras");
    }

    @Override
    public void run() {
        System.out.println("test");
        // receive server messages and print out to screen
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            String command = s.nextLine();
            System.out.println(command);

            AbendConnector.commandsend(command);
            if (command.equalsIgnoreCase("!close")) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}


