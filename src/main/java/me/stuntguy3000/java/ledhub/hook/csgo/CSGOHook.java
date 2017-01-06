package me.stuntguy3000.java.ledhub.hook.csgo;

import com.brekcel.csgostate.Server;

import java.io.IOException;

/**
 * @author stuntguy3000
 */
public class CSGOHook {

    public void init() {
        try {
            System.out.println("Starting CSGO Hook");
            new Server(3000, new CSGOEvents(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
