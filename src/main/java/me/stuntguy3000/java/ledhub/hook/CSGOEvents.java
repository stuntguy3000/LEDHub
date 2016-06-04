package me.stuntguy3000.java.ledhub.hook;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.JSON.Map;
import com.brekcel.csgostate.JSON.MatchStats;
import com.brekcel.csgostate.JSON.Player;
import com.brekcel.csgostate.JSON.Round;
import com.brekcel.csgostate.JSON.State;
import com.brekcel.csgostate.JSON.Weapon;
import com.brekcel.csgostate.post.PostHandler;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.LEDColour;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionType;
import me.stuntguy3000.java.ledhub.object.LEDServiceQueueCondition;

/**
 * @author stuntguy3000
 */
public class CSGOEvents implements PostHandler {
    private boolean bombFlashing = false;
    private LinkedList<Long> bombTriggers;

    public CSGOEvents() {
        bombTriggers = new LinkedList<>();

        try {
            InputStream cpResource = this.getClass().getClassLoader().getResourceAsStream("csgoBombTimer");
            File tmpFile = File.createTempFile("file", "temp");
            FileUtils.copyInputStreamToFile(cpResource, tmpFile); // FileUtils from apache-io
            try {
                FileInputStream fis = new FileInputStream(tmpFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String line;
                while ((line = br.readLine()) != null) {
                    Long number = Long.valueOf(line);
                    bombTriggers.add(number);
                }

                System.out.println(bombTriggers.toString());

                br.close();
            } finally {
                tmpFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivedJsonResponse(JsonResponse jsonResponse) {

    }

    @Override
    public void newMap(Map map) {

    }

    @Override
    public void receivedMap(Map map) {

    }

    @Override
    public void mapReset() {

    }

    @Override
    public void mapNameChange(String mapName) {

    }

    @Override
    public void modeChange(String mode) {

    }

    @Override
    public void roundChange(int round) {

    }

    @Override
    public void teamNameChange(String team, String name) {

    }

    @Override
    public void scoreChange(int ct, int t) {

    }

    @Override
    public void phaseChange(String phase) {

    }

    @Override
    public void newRound(Round round) {

    }

    @Override
    public void receivedRound(Round round) {

    }

    @Override
    public void roundReset() {
        bombFlashing = false;
    }

    @Override
    public void bombPlanted() {
        bombFlashing = true;
        ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
        LEDService service = serviceHandler.getService("csgo");
        LEDServiceAction bombFlash = service.getServiceActions().get("bombFlash");

        if (bombFlash != null) {
            for (int i = 0; i < 45; i++) {
                LEDServiceAction newBombFlash = bombFlash.clone();
                newBombFlash.setActionLife(1000);
                serviceHandler.addToServiceQueue(newBombFlash);
            }
        }
    }

    @Override
    public void bombExploded() {
        if (bombFlashing) {
            bombFlashing = false;
            ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
            LEDService service = serviceHandler.getService("csgo");
            LEDServiceAction bombExplode1 = service.getServiceActions().get("bombExplode1");
            LEDServiceAction bombExplode2 = service.getServiceActions().get("bombExplode2");
            LEDServiceAction black = service.getServiceActions().get("black");

            serviceHandler.getServiceQueue().clear();
            serviceHandler.addToServiceQueue(black);

            for (int i = 0; i < 5; i++) {
                serviceHandler.addToServiceQueue(bombExplode1);
                serviceHandler.addToServiceQueue(bombExplode2);
            }
        }
    }

    @Override
    public void bombDefused() {
        if (bombFlashing) {
            bombFlashing = false;
            ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
            LEDService service = serviceHandler.getService("csgo");
            LEDServiceAction bombDefuse1 = service.getServiceActions().get("bombDefuse1");
            LEDServiceAction bombDefuse2 = service.getServiceActions().get("bombDefuse2");
            LEDServiceAction black = service.getServiceActions().get("black");

            serviceHandler.getServiceQueue().clear();
            serviceHandler.addToServiceQueue(black);

            for (int i = 0; i < 5; i++) {
                serviceHandler.addToServiceQueue(bombDefuse1);
                serviceHandler.addToServiceQueue(bombDefuse2);
            }
        }
    }

    @Override
    public void roundWinningTeamChange(String team) {

    }

    @Override
    public void roundLive() {

    }

    @Override
    public void roundFreezeTime() {

    }

    @Override
    public void roundOver() {
        if (bombFlashing) {
            ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
            serviceHandler.getServiceQueue().clear();
            serviceHandler.processQueue();
            bombFlashing = false;
        }
    }

    @Override
    public void newPlayer(Player player) {

    }

    @Override
    public void receivedPlayer(Player player) {

    }

    @Override
    public void playerReset() {

    }

    @Override
    public void playerNameChange(String name) {

    }

    @Override
    public void playerSteamIDChange(String steamID) {

    }

    @Override
    public void playerTeamChange(String team) {

    }

    @Override
    public void playerActivityChange(String activity) {

    }

    @Override
    public void playerStateChange(State state) {

    }

    @Override
    public void playerHealthChange(int health) {
        ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
        if (health == 100) {
            serviceHandler.addToServiceQueue(serviceHandler.getService("csgo").getServiceActions().get("onHitGreen"));
        } else {
            serviceHandler.addToServiceQueue(serviceHandler.getService("csgo").getServiceActions().get("onHitRed"));
        }
    }

    @Override
    public void playerArmorChange(int armor) {

    }

    @Override
    public void playerHelmetChange(boolean helmet) {

    }

    @Override
    public void playerFlashedChange(int flashed) {
        if (!bombFlashing) {
            LEDServiceAction flashAction = new LEDServiceAction(LEDServiceActionType.STATIC,
                    new LEDColour(flashed, flashed, flashed), null, LEDServiceQueueCondition.JUMP_QUEUE, (flashed > 0 ? 7500 : 250)
            );

            LEDHub.getInstance().getServiceHandler().addToServiceQueue(flashAction);
        }
    }

    @Override
    public void playerSmokeChange(int smoked) {

    }

    @Override
    public void playerBurningChange(int burning) {

    }

    @Override
    public void playerMoneyChange(int money) {

    }

    @Override
    public void playerRoundKillsChange(int kills) {

    }

    @Override
    public void playerRoundKillsHSChange(int killsHS) {

    }

    @Override
    public void newWeapons(Weapon[] weapons) {

    }

    @Override
    public void weaponsChange(Weapon[] weapons) {

    }

    @Override
    public void weaponActiveChange(Weapon weapon) {

    }

    @Override
    public void weaponShoot(Weapon weapon) {

    }

    @Override
    public void weaponReload(Weapon weapon) {

    }

    @Override
    public void playerMatchStatsChange(MatchStats ms) {

    }

    @Override
    public void playerMatchStatsReceived(MatchStats ms) {

    }

    @Override
    public void playerMatchKillsChange(int kills) {

    }

    @Override
    public void playerMatchAssistsChange(int assists) {

    }

    @Override
    public void playerMatchDeathsChange(int deaths) {

    }

    @Override
    public void playerMatchMVPSChange(int mvps) {

    }

    @Override
    public void playerMatchScoreChange(int score) {

    }
}
