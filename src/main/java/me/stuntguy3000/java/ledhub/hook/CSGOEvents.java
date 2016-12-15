package me.stuntguy3000.java.ledhub.hook;

import com.brekcel.csgostate.JSON.*;
import com.brekcel.csgostate.post.PostHandler;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
public class CSGOEvents implements PostHandler {
    private boolean bombFlashing = false;
    private boolean roundLive = false;
    private LinkedList<Long> bombTriggers;
    private ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
    private int tScore = 0, ctScore = 0;
    private String currentTeam = null;

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
    public void roundWinningTeamChange(String teamName) {
        if (teamName.equalsIgnoreCase("T")) {
            LEDService service = serviceHandler.getService("csgo");

            for (int i = 0; i < 5; i++) {
                LEDServiceAction team = service.getServiceActions().get("tTeamWin");
                serviceHandler.addToServiceQueue(team);
            }
        } else {
            LEDService service = serviceHandler.getService("csgo");

            for (int i = 0; i < 5; i++) {
                LEDServiceAction team = service.getServiceActions().get("ctTeamWin");
                serviceHandler.addToServiceQueue(team);
            }
        }
    }

    @Override
    public void roundLive() {
        roundLive = true;
        serviceHandler.getServiceQueue().clear();
        serviceHandler.processQueue();
    }

    @Override
    public void roundFreezeTime() {

    }

    @Override
    public void roundOver() {
        roundLive = false;
        if (bombFlashing) {
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
        if (team.equalsIgnoreCase("ct")) {
            serviceHandler.setServiceBackground(serviceHandler.getService("csgo").getServiceActions().get("ctTeam"));
            serviceHandler.processQueue();
            currentTeam = "ct";
        } else {
            serviceHandler.setServiceBackground(serviceHandler.getService("csgo").getServiceActions().get("tTeam"));
            serviceHandler.processQueue();
            currentTeam = "t";
        }
    }

    @Override
    public void playerActivityChange(String activity) {

    }

    @Override
    public void playerStateChange(State state) {

    }

    @Override
    public void playerHealthChange(int health) {
        if (!roundLive) {
            return;
        }

        if (health < 100 && health > 0) {
            ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
            serviceHandler.addToServiceQueue(serviceHandler.getService("csgo").getServiceActions().get("onHitRed"));
        } else if (health == 0) {
            ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
            serviceHandler.addToServiceQueue(serviceHandler.getService("csgo").getServiceActions().get("onDeath"));
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
        if (!bombFlashing && roundLive) {
            LEDServiceAction flashAction = new LEDServiceAction(LEDServiceActionType.STATIC,
                    new LEDColour(flashed, flashed, flashed), null, LEDServiceQueueCondition.JUMP_QUEUE, (flashed > 0 ? 7500 : 250)
            );

            LEDHub.getInstance().getServiceHandler().addToServiceQueue(flashAction);
        }
    }

    @Override
    public void playerSmokeChange(int smoked) {
        if (!bombFlashing && roundLive) {
            LEDServiceAction flashAction = new LEDServiceAction(LEDServiceActionType.STATIC,
                    new LEDColour(smoked, smoked, smoked), null, LEDServiceQueueCondition.JUMP_QUEUE, (smoked > 0 ? 7500 : 250)
            );

            LEDHub.getInstance().getServiceHandler().addToServiceQueue(flashAction);
        }
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
