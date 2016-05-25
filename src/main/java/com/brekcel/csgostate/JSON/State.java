
package com.brekcel.csgostate.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class State {

    @SerializedName("health")
    @Expose
    private Integer health;
    @SerializedName("armor")
    @Expose
    private Integer armor;
    @SerializedName("helmet")
    @Expose
    private Boolean helmet;
    @SerializedName("flashed")
    @Expose
    private Integer flashed;
    @SerializedName("smoked")
    @Expose
    private Integer smoked;
    @SerializedName("burning")
    @Expose
    private Integer burning;
    @SerializedName("money")
    @Expose
    private Integer money;
    @SerializedName("round_kills")
    @Expose
    private Integer roundKills;
    @SerializedName("round_killhs")
    @Expose
    private Integer roundKillhs;

    /**
     * @return The health
     */
    public Integer getHealth() {
        return health;
    }

    /**
     * @param health The health
     */
    public void setHealth(Integer health) {
        this.health = health;
    }

    /**
     * @return The armor
     */
    public Integer getArmor() {
        return armor;
    }

    /**
     * @param armor The armor
     */
    public void setArmor(Integer armor) {
        this.armor = armor;
    }

    /**
     * @return The helmet
     */
    public Boolean getHelmet() {
        return helmet;
    }

    /**
     * @param helmet The helmet
     */
    public void setHelmet(Boolean helmet) {
        this.helmet = helmet;
    }

    /**
     * @return The flashed
     */
    public Integer getFlashed() {
        return flashed;
    }

    /**
     * @param flashed The flashed
     */
    public void setFlashed(Integer flashed) {
        this.flashed = flashed;
    }

    /**
     * @return The smoked
     */
    public Integer getSmoked() {
        return smoked;
    }

    /**
     * @param smoked The smoked
     */
    public void setSmoked(Integer smoked) {
        this.smoked = smoked;
    }

    /**
     * @return The burning
     */
    public Integer getBurning() {
        return burning;
    }

    /**
     * @param burning The burning
     */
    public void setBurning(Integer burning) {
        this.burning = burning;
    }

    /**
     * @return The money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * @param money The money
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * @return The roundKills
     */
    public Integer getRoundKills() {
        return roundKills;
    }

    /**
     * @param roundKills The round_kills
     */
    public void setRoundKills(Integer roundKills) {
        this.roundKills = roundKills;
    }

    /**
     * @return The roundKillhs
     */
    public Integer getRoundKillhs() {
        return roundKillhs;
    }

    /**
     * @param roundKillhs The round_killhs
     */
    public void setRoundKillhs(Integer roundKillhs) {
        this.roundKillhs = roundKillhs;
    }

}
