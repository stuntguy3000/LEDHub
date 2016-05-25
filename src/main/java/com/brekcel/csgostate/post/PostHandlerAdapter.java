package com.brekcel.csgostate.post;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.JSON.Map;
import com.brekcel.csgostate.JSON.MatchStats;
import com.brekcel.csgostate.JSON.Player;
import com.brekcel.csgostate.JSON.Round;
import com.brekcel.csgostate.JSON.State;
import com.brekcel.csgostate.JSON.Weapon;

// Simple class that implements PostHandler.
// Use this when you only want to use specific methods from PostHandler.
// If using ALL methods of PostHandler, might as well just implement that
// instead of extending this.
public class PostHandlerAdapter implements PostHandler {
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
    }

    @Override
    public void roundWinningTeamChange(String team) {
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
    }

    @Override
    public void playerArmorChange(int armor) {
    }

    @Override
    public void playerHelmetChange(boolean helmet) {
    }

    @Override
    public void playerFlashedChange(int flashed) {
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

    @Override
    public void playerMatchStatsReceived(MatchStats ms) {
    }

    @Override
    public void bombPlanted() {
    }

    @Override
    public void bombExploded() {
    }

    @Override
    public void bombDefused() {
    }

    @Override
    public void roundLive() {
    }

    @Override
    public void roundFreezeTime() {
    }

    @Override
    public void roundOver() {
    }
}
