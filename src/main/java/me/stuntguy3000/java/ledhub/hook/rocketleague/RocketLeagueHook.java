package me.stuntguy3000.java.ledhub.hook.rocketleague;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author stuntguy3000
 */
public class RocketLeagueHook {
    public void init() {
        try {
            ServerSocket serversocket = new ServerSocket(1133, 10);

            while (true) {
                Socket client = serversocket.accept();
                Scanner scanner = new Scanner(client.getInputStream());
                String line = scanner.nextLine();

                if (line.equalsIgnoreCase("RL_OPEN")) {

                } else if (line.equalsIgnoreCase("RL_EXIT")) {

                } else if (line.startsWith("RL_BOOST:")) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
