
package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MatchStats {

    @SerializedName("kills")
    @Expose
    private Integer kills;
    @SerializedName("assists")
    @Expose
    private Integer assists;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("mvps")
    @Expose
    private Integer mvps;
    @SerializedName("score")
    @Expose
    private Integer score;

    /**
     * @return The kills
     */
    public Integer getKills() {
        return kills;
    }

    /**
     * @param kills The kills
     */
    public void setKills(Integer kills) {
        this.kills = kills;
    }

    /**
     * @return The assists
     */
    public Integer getAssists() {
        return assists;
    }

    /**
     * @param assists The assists
     */
    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    /**
     * @return The deaths
     */
    public Integer getDeaths() {
        return deaths;
    }

    /**
     * @param deaths The deaths
     */
    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    /**
     * @return The mvps
     */
    public Integer getMvps() {
        return mvps;
    }

    /**
     * @param mvps The mvps
     */
    public void setMvps(Integer mvps) {
        this.mvps = mvps;
    }

    /**
     * @return The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

}
