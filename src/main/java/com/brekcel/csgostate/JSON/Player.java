package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Player {
    @SerializedName("steamid")
    @Expose
    private String steamid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("weapons")
    @Expose
    private LinkedHashMap<String, Weapon> weapons;
    // private Weapons weapons;
    @SerializedName("match_stats")
    @Expose
    private MatchStats matchStats;

    /**
     * @return The steamid
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * @param steamid The steamid
     */
    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team The team
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return The activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity The activity
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return The state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return The weapons
     */
    public LinkedHashMap<String, Weapon> getWeapons() {
        return weapons;
    }

    /**
     * @param weapons The weapons
     */
    public void setWeapons(LinkedHashMap<String, Weapon> weapons) {
        this.weapons = weapons;
    }

    /**
     * @return The matchStats
     */
    public MatchStats getMatchStats() {
        return matchStats;
    }

    /**
     * @param matchStats The match_stats
     */
    public void setMatchStats(MatchStats matchStats) {
        this.matchStats = matchStats;
    }

    //TODO: Create this method
    //ALWAYS_QUEUE goes in order of: KNIFE, PRIMARY, SECONDARY, NADE0, NADE1, NADE2, NADE3, TASER, BOMB
    public Weapon[] getWeaponsArr() {
        Weapon[] weapons = new Weapon[9];
        return weapons;
    }
}
