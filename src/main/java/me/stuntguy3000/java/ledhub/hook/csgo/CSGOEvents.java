package me.stuntguy3000.java.ledhub.hook.csgo;

import com.brekcel.csgostate.JSON.*;
import com.brekcel.csgostate.post.PostHandler;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.*;

import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
public class CSGOEvents implements PostHandler {
    private boolean bombFlashing = false;
    private boolean roundLive = false;
    private ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
    private int tScore = 0, ctScore = 0;
    private String currentTeam = null;

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
        /*bombFlashing = true;

        LEDService service = serviceHandler.getService("csgo");
        LinkedList<LEDAction> bombFlash = service.getServiceActions().get("bombFlash");

        if (bombFlash != null) {
            for (int i = 0; i < 45; i++) {
                newBombFlash.setActionLife(1000);
                serviceHandler.addToServiceQueue(newBombFlash);
            }
        }*/
    }

    @Override
    public void bombExploded() {
        if (bombFlashing) {
            bombFlashing = false;
            LEDService service = serviceHandler.getService("csgo");
            LinkedList<LEDAction> bombExplode1 = service.getServiceActions().get("bombExplode1");
            LinkedList<LEDAction> bombExplode2 = service.getServiceActions().get("bombExplode2");
            LinkedList<LEDAction> black = service.getServiceActions().get("black");

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
            LinkedList<LEDAction> bombDefuse1 = service.getServiceActions().get("bombDefuse1");
            LinkedList<LEDAction> bombDefuse2 = service.getServiceActions().get("bombDefuse2");
            LinkedList<LEDAction> black = service.getServiceActions().get("black");

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
                LinkedList<LEDAction> team = service.getServiceActions().get("tTeamWin");
                serviceHandler.addToServiceQueue(team);
            }
        } else {
            LEDService service = serviceHandler.getService("csgo");

            for (int i = 0; i < 5; i++) {
                LinkedList<LEDAction> team = service.getServiceActions().get("ctTeamWin");
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
            serviceHandler.setServiceBackground(
                    new LEDBackground(
                            "ct",
                            serviceHandler.getService("csgo").getServiceActions().get("ctTeam"),
                            false));
            serviceHandler.processQueue();
            currentTeam = "ct";
        } else {
            serviceHandler.setServiceBackground(
                    new LEDBackground(
                            "t", serviceHandler.getService("csgo").getServiceActions().get("tTeam"),
                            false));
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
            LEDAction flashAction = new LEDAction(LEDServiceActionType.STATIC,
                    null, new LEDColour(flashed, flashed, flashed), LEDServiceQueueCondition.JUMP_QUEUE, (flashed > 0 ? 7500 : 250)
            );

            LEDHub.getInstance().getServiceHandler().addToServiceQueue(flashAction);
        }
    }

    @Override
    public void playerSmokeChange(int smoked) {
        if (!bombFlashing && roundLive) {
            LEDAction flashAction = new LEDAction(LEDServiceActionType.STATIC,
                    null, new LEDColour(smoked, smoked, smoked), LEDServiceQueueCondition.JUMP_QUEUE, (smoked > 0 ? 7500 : 250)
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
