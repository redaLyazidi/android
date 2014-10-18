package com.api.android.chronometer.model;

/**
 * Created by reda on 10/18/14.
 * A Series represent a pattern which the device must react
 */
public class Series {

    private int numberUnit;
    private int times;
    private String unitSymbol;

    public Series() {
    }

    public Series(int numberUnit, int times, String unitSymbol) {
        this.numberUnit = numberUnit;
        this.times = times;
        this.unitSymbol = unitSymbol;
    }



    public int getNumberUnit() {
        return numberUnit;
    }

    public void setNumberUnit(int numberUnit) {
        this.numberUnit = numberUnit;
    }

    public void setNumberUnit(String numberUnit) {
        setNumberUnit(Integer.valueOf(numberUnit));
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setTimes(String times) {
        setTimes(Integer.valueOf(times));
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }
}

