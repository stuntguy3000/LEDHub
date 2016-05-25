package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Provider {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("appid")
    @Expose
    private Integer appid;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("steamid")
    @Expose
    private String steamid;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

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
     * @return The appid
     */
    public Integer getAppid() {
        return appid;
    }

    /**
     * @param appid The appid
     */
    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    /**
     * @return The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

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
     * @return The timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp The timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
