package com.brekcel.csgostate.post;

import com.google.gson.Gson;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.JSON.Map;
import com.brekcel.csgostate.JSON.MatchStats;
import com.brekcel.csgostate.JSON.Player;
import com.brekcel.csgostate.JSON.Round;
import com.brekcel.csgostate.JSON.State;
import com.brekcel.csgostate.JSON.Weapon;
import com.brekcel.csgostate.Server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostReceiver implements HttpHandler {
    private PostHandler handle;
    private Server serv;
    private Gson gson;
    private BufferedWriter writer;
    private JsonResponse currentJSR;

    public PostReceiver(Server serv, PostHandler handle) {
        this.serv = serv;
        this.handle = handle;
        gson = new Gson();
        try {
            new File("A:/csgoLog.txt").createNewFile();
            writer = new BufferedWriter(new FileWriter(new File("A:/csgoLog.txt"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exc) throws IOException {
        StringBuffer response = new StringBuffer();
        StringBuffer printResponse = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(exc.getRequestBody()));
            String inl;
            while ((inl = in.readLine()) != null) {
                response.append(inl);
                printResponse.append(inl + "\n");
            }
            in.close();
            writer.write(response.toString());
            writer.newLine();
            writer.flush();
            JsonResponse jsr = gson.fromJson(response.toString(), JsonResponse.class);
            if ((jsr.getAuth() == null && serv.getAuthToken() != null) && (serv.getAuthToken() == null && jsr.getAuth() != null) && !jsr.getAuth().getToken().equals(serv.getAuthToken())) {
                System.out.println("Invalid connection attempt from: " + exc.getLocalAddress());
                return;
            }
            exc.sendResponseHeaders(200, -1);
            // System.out.println(response.toString());
            callMethods(jsr);
        } catch (Exception e) {
            System.out.println(printResponse.toString());
            e.printStackTrace();
        }
    }

    private void callMethods(JsonResponse jsr) {
        try {
            handle.receivedJsonResponse(jsr);
            if (currentJSR == null)
                currentJSR = new JsonResponse();
            // MAP
            if (jsr.getMap() != null) {
                handle.receivedMap(jsr.getMap());
                if (currentJSR.getMap() == null || !currentJSR.getMap().equals(jsr.getMap())) {
                    Map nm = jsr.getMap();
                    Map cm = currentJSR.getMap();
                    if (currentJSR.getMap() == null) {
                        handle.newMap(nm);
                        if (nm.getMode() != null)
                            handle.modeChange(nm.getMode());
                        if (nm.getName() != null)
                            handle.mapNameChange(nm.getName());
                        if (nm.getRound() != null)
                            handle.roundChange(nm.getRound());
                        if (nm.getTeamCt().getName() != null || nm.getTeamT().getName() != null)
                            handle.teamNameChange(nm.getTeamCt().getName(), nm.getTeamT().getName());
                        if (nm.getTeamCt().getScore() != null && nm.getTeamT() != null)
                            handle.scoreChange(nm.getTeamCt().getScore(), nm.getTeamT().getScore());
                        if (nm.getPhase() != null)
                            handle.phaseChange(nm.getPhase());
                    } else {
                        if (nm.getMode() != null && (cm.getMode() == null || !cm.getMode().equals(nm.getMode()))) {
                            handle.modeChange(nm.getMode());
                        }
                        if (nm.getName() != null && (cm.getName() == null || !cm.getName().equals(nm.getName()))) {
                            handle.mapNameChange(nm.getName());
                        }
                        if (nm.getPhase() != null && (cm.getPhase() == null || !cm.getPhase().equals(nm.getPhase()))) {
                            handle.phaseChange(nm.getPhase());
                        }
                        if (nm.getPhase() != null && (cm.getRound() == null || cm.getRound() != nm.getRound())) {
                            handle.roundChange(nm.getRound());
                        }
                        if ((nm.getTeamCt().getScore() != null && nm.getTeamT().getScore() != null) && (cm.getTeamCt().getScore() == null || cm.getTeamT().getScore() == null || cm.getTeamCt().getScore() != nm.getTeamCt().getScore() || cm.getTeamT().getScore() != nm.getTeamT().getScore())) {
                            handle.scoreChange(nm.getTeamCt().getScore(), nm.getTeamT().getScore());
                        }
                        if ((nm.getTeamCt().getName() != null && (cm.getTeamCt().getName() == null || !cm.getTeamCt().getName().equals(nm.getTeamCt().getName())))) {
                            handle.teamNameChange("ct", nm.getTeamCt().getName());
                        }
                        if ((nm.getTeamT().getName() != null && (cm.getTeamT().getName() == null || !cm.getTeamT().getName().equals(cm.getTeamT().getName())))) {
                            handle.teamNameChange("t", nm.getTeamT().getName());
                        }
                        if ((nm.getTeamCt().getName() == null && cm.getTeamCt().getName() != null)) {
                            handle.teamNameChange("ct", nm.getTeamCt().getName());
                        }
                        if ((nm.getTeamT().getName() == null && cm.getTeamT().getName() != null)) {
                            handle.teamNameChange("t", nm.getTeamT().getName());
                        }
                    }
                }
            } else {
                handle.mapReset();
            }
            // ROUND
            if (jsr.getRound() != null) {
                if (currentJSR.getRound() == null || !currentJSR.getRound().equals(jsr.getRound())) {
                    Round nr = jsr.getRound();
                    Round cr = currentJSR.getRound();
                    if (currentJSR.getRound() == null) {
                        if (nr.getPhase() != null) {
                            if (nr.getPhase().equals("live"))
                                handle.roundLive();
                            else if (nr.getPhase().equals("freezetime"))
                                handle.roundFreezeTime();
                            else if (nr.getPhase().equals("over"))
                                handle.roundOver();
                        }
                        if (nr.getBomb() != null) {
                            if (nr.getBomb().equals("planted"))
                                handle.bombPlanted();
                            else if (nr.getBomb().equals("exploded"))
                                handle.bombExploded();
                            else if (nr.getBomb().equals("defused"))
                                handle.bombDefused();
                        }
                        if (nr.getWinTeam() != null)
                            handle.roundWinningTeamChange(nr.getWinTeam());
                    } else {
                        if (nr.getBomb() != null && (cr.getBomb() == null || !cr.getBomb().equals(nr.getBomb()))) {
                            if (nr.getBomb().equals("planted"))
                                handle.bombPlanted();
                            else if (nr.getBomb().equals("exploded"))
                                handle.bombExploded();
                            else if (nr.getBomb().equals("defused"))
                                handle.bombDefused();
                        }
                        // Needs to be tested.
                        // Tested for 5 minutes. Worked.
                        if (nr.getWinTeam() != null && (cr.getPhase() != null && !cr.getPhase().equals(nr.getPhase()))) {
                            handle.roundWinningTeamChange(nr.getWinTeam());
                        }
                        if (nr.getPhase() != null && (cr.getPhase() == null || !cr.getPhase().equals(nr.getPhase()))) {
                            if (nr.getPhase().equals("live"))
                                handle.roundLive();
                            else if (nr.getPhase().equals("freezetime"))
                                handle.roundFreezeTime();
                            else if (nr.getPhase().equals("over"))
                                handle.roundOver();
                        }
                    }
                }
            } else {
                handle.roundReset();
            }
            // PLAYER
            if (jsr.getPlayer() != null) {
                // This can't be right.. I'm just dumb
                // Maybe it is right..
                if (!serv.onlyUser || jsr.getPlayer().getSteamid().equals(jsr.getProvider().getSteamid())) {
                    handle.receivedPlayer(jsr.getPlayer());
                    if (currentJSR.getPlayer() == null || !currentJSR.getPlayer().equals(jsr.getPlayer())) {
                        Player cp = currentJSR.getPlayer();
                        Player np = jsr.getPlayer();
                        if (cp == null) {
                            handle.newPlayer(np);
                            if (np.getSteamid() != null)
                                handle.playerSteamIDChange(np.getSteamid());
                            if (np.getTeam() != null)
                                handle.playerTeamChange(np.getTeam());
                            if (np.getActivity() != null)
                                handle.playerActivityChange(np.getActivity());
                            if (np.getName() != null)
                                handle.playerNameChange(np.getName());
                            if (np.getState() != null) {
                                handle.playerStateChange(np.getState());
                                State s = np.getState();
                                handle.playerHealthChange(s.getHealth());
                                handle.playerArmorChange(s.getArmor());
                                handle.playerHelmetChange(s.getHelmet());
                                handle.playerFlashedChange(s.getFlashed());
                                handle.playerSmokeChange(s.getSmoked());
                                handle.playerBurningChange(s.getBurning());
                                handle.playerMoneyChange(s.getMoney());
                                handle.playerRoundKillsChange(s.getRoundKills());
                                handle.playerRoundKillsHSChange(s.getRoundKillhs());
                            }
                            if (np.getWeapons() != null) {
                                handle.newWeapons(np.getWeapons().values().toArray(new Weapon[np.getWeapons().size()]));
                                handle.weaponsChange(np.getWeapons().values().toArray(new Weapon[np.getWeapons().size()]));
                                Weapon[] weapons = jsr.getPlayer().getWeapons().values().toArray(new Weapon[jsr.getPlayer().getWeapons().size()]);
                                for (Weapon w : weapons)
                                    if (w.getState().equals("active")) {
                                        handle.weaponActiveChange(w);
                                        break;
                                    }
                            }
                            if (np.getMatchStats() != null) {
                                MatchStats m = np.getMatchStats();
                                handle.playerMatchKillsChange(m.getKills());
                                handle.playerMatchAssistsChange(m.getAssists());
                                handle.playerMatchDeathsChange(m.getDeaths());
                                handle.playerMatchMVPSChange(m.getMvps());
                                handle.playerMatchScoreChange(m.getScore());
                            }
                        } else {
                            if (np.getSteamid() != null && (cp.getSteamid() == null || !cp.getSteamid().equals(np.getSteamid()))) {
                                handle.playerSteamIDChange(np.getSteamid());
                            }
                            if (np.getName() != null && (cp.getName() == null || !cp.getName().equals(np.getName()))) {
                                handle.playerNameChange(np.getName());
                            }
                            if (np.getTeam() != null && (cp.getTeam() == null || !cp.getTeam().equals(np.getTeam()))) {
                                handle.playerTeamChange(np.getTeam());
                            }
                            if (np.getActivity() != null && (cp.getActivity() == null || !cp.getActivity().equals(np.getActivity()))) {
                                handle.playerActivityChange(np.getActivity());
                            }
                            if (np.getState() != null && (cp.getState() == null || !cp.getState().equals(np.getState()))) {
                                handle.playerStateChange(np.getState());
                                State ns = np.getState();
                                State cs = cp.getState();
                                if (cp.getState() != null) {
                                    if (ns.getHealth() != cs.getHealth())
                                        handle.playerHealthChange(ns.getHealth());
                                    if (ns.getArmor() != cs.getArmor())
                                        handle.playerArmorChange(ns.getArmor());
                                    if (ns.getHelmet() != cs.getHelmet())
                                        handle.playerHelmetChange(ns.getHelmet());
                                    if (ns.getFlashed() != cs.getFlashed())
                                        handle.playerFlashedChange(ns.getFlashed());
                                    if (ns.getSmoked() != cs.getSmoked())
                                        handle.playerSmokeChange(ns.getSmoked());
                                    if (ns.getBurning() != cs.getBurning())
                                        handle.playerBurningChange(ns.getBurning());
                                    if (ns.getMoney() != cs.getMoney())
                                        handle.playerMoneyChange(ns.getMoney());
                                    if (ns.getRoundKills() != cs.getRoundKills())
                                        handle.playerRoundKillsChange(ns.getRoundKillhs());
                                    if (ns.getRoundKillhs() != cs.getRoundKillhs())
                                        handle.playerRoundKillsHSChange(ns.getRoundKillhs());
                                } else {
                                    handle.playerHealthChange(ns.getHealth());
                                    handle.playerArmorChange(ns.getArmor());
                                    handle.playerHelmetChange(ns.getHelmet());
                                    handle.playerFlashedChange(ns.getFlashed());
                                    handle.playerSmokeChange(ns.getSmoked());
                                    handle.playerBurningChange(ns.getBurning());
                                    handle.playerMoneyChange(ns.getMoney());
                                    handle.playerRoundKillsChange(ns.getRoundKillhs());
                                    handle.playerRoundKillsHSChange(ns.getRoundKillhs());
                                }
                            }
                            // TODO: Weapon Logic
                            if (np.getWeapons() != null && (cp.getWeapons() == null || !cp.getWeapons().equals(np.getWeapons()))) {
                                handle.weaponsChange(np.getWeapons().values().toArray(new Weapon[np.getWeapons().size()]));
                            }
                            if (np.getMatchStats() != null && (cp.getMatchStats() == null || !cp.getMatchStats().equals(np.getMatchStats()))) {
                                MatchStats nm = np.getMatchStats();
                                MatchStats cm = cp.getMatchStats();
                                handle.playerMatchStatsChange(nm);
                                if (cm == null) {
                                    handle.playerMatchKillsChange(nm.getKills());
                                    handle.playerMatchAssistsChange(nm.getAssists());
                                    handle.playerMatchDeathsChange(nm.getDeaths());
                                    handle.playerMatchMVPSChange(nm.getMvps());
                                    handle.playerMatchScoreChange(nm.getScore());
                                } else {
                                    if (nm.getKills() != cm.getKills())
                                        handle.playerMatchKillsChange(nm.getKills());
                                    if (nm.getAssists() != cm.getAssists())
                                        handle.playerMatchAssistsChange(nm.getAssists());
                                    if (nm.getDeaths() != cm.getDeaths())
                                        handle.playerMatchDeathsChange(nm.getDeaths());
                                    if (nm.getMvps() != cm.getMvps())
                                        handle.playerMatchMVPSChange(nm.getMvps());
                                    if (nm.getScore() != cm.getScore())
                                        handle.playerMatchScoreChange(nm.getScore());
                                }
                            }
                        }
                    }
                } else {
                    jsr.setPlayer(null);
                }
            } else {
                handle.playerReset();
            }
            // Updates the JSR to the most updated one
            // Might not be the correct way of doing... Who knows?
            currentJSR = jsr;
        } catch (Exception e) {
            System.out.println(gson.toJson(jsr));
            e.printStackTrace();
        }
    }

    public JsonResponse getCurrentJSR() {
        return currentJSR;
    }
}
