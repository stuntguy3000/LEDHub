
package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class JsonResponse {

    @SerializedName("provider")
    @Expose
    private Provider provider;
    @SerializedName("map")
    @Expose
    private Map map;
    @SerializedName("player")
    @Expose
    private Player player;
    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("previously")
    @Expose
    private Previously previously;
    @SerializedName("round")
    @Expose
    private Round round;
    @SerializedName("added")
    @Expose
    private Added added;

    /**
     * @return The provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * @param provider The provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * @return The map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map The map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player The player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return The auth
     */
    public Auth getAuth() {
        return auth;
    }

    /**
     * @param auth The auth
     */
    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    /**
     * @return The previously
     */
    public Previously getPreviously() {
        return previously;
    }

    /**
     * @param previously The previously
     */
    public void setPreviously(Previously previously) {
        this.previously = previously;
    }

    /**
     * @return The round
     */
    public Round getRound() {
        return round;
    }

    /**
     * @param round The round
     */
    public void setRound(Round round) {
        this.round = round;
    }

    /**
     * @return The added
     */
    public Added getAdded() {
        return added;
    }

    /**
     * @param added The added
     */
    public void setAdded(Added added) {
        this.added = added;
    }

}
