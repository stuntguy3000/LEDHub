package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weapon {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("paintkit")
    @Expose
    private String paintkit;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ammo_clip")
    @Expose
    private Integer ammoClip;
    @SerializedName("ammo_clip_max")
    @Expose
    private Integer ammoClipMax;
    @SerializedName("ammo_reserve")
    @Expose
    private Integer ammoReserve;
    @SerializedName("state")
    @Expose
    private String state;

    public Weapon() {
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
     * @return The paintkit
     */
    public String getPaintkit() {
        return paintkit;
    }

    /**
     * @param paintkit The paintkit
     */
    public void setPaintkit(String paintkit) {
        this.paintkit = paintkit;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The ammoClip
     */
    public Integer getAmmoClip() {
        return ammoClip;
    }

    /**
     * @param ammoClip The ammo_clip
     */
    public void setAmmoClip(Integer ammoClip) {
        this.ammoClip = ammoClip;
    }

    /**
     * @return The ammoClipMax
     */
    public Integer getAmmoClipMax() {
        return ammoClipMax;
    }

    /**
     * @param ammoClipMax The ammo_clip_max
     */
    public void setAmmoClipMax(Integer ammoClipMax) {
        this.ammoClipMax = ammoClipMax;
    }

    /**
     * @return The ammoReserve
     */
    public Integer getAmmoReserve() {
        return ammoReserve;
    }

    /**
     * @param ammoReserve The ammo_reserve
     */
    public void setAmmoReserve(Integer ammoReserve) {
        this.ammoReserve = ammoReserve;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }
}
