package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Round {
    @SerializedName("phase")
    @Expose
    private String phase;
    @SerializedName("win_team")
    @Expose
    private String winTeam;
    @SerializedName("bomb")
    @Expose
    private String bomb;

    /**
     * @return The phase
     */
    public String getPhase() {
        return phase;
    }

    /**
     * @param phase The phase
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /**
     * @return The winTeam
     */
    public String getWinTeam() {
        return winTeam;
    }

    /**
     * @param winTeam The win_team
     */
    public void setWinTeam(String winTeam) {
        this.winTeam = winTeam;
    }

    /**
     * @return The bomb
     */
    public String getBomb() {
        return bomb;
    }

    /**
     * @param bomb The bomb
     */
    public void setBomb(String bomb) {
        this.bomb = bomb;
    }
}
