package com.api.android.chronometer.model;


import com.api.android.constant.enums.SimpleTimeUnit;

import java.util.ArrayList;
import java.util.List;

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

    public List<Long> retrieveTimesToReact() {
        if (times <= 0 ) {
            return null;
        }
        List<Long> timesToReact = new ArrayList<>(times);
        for (int i = 0; i < times; i++) {
            final long currentValue = (i + 1) * numberUnit;
            timesToReact.add(i, currentValue);
        }
        return timesToReact;
    }

    /**
     * Retrieve times to react in specfic unit
     * @param simpleTimeUnit a simple unit for time
     * @return list of value to react the unit is the one specified
     */
    public List<Long> retrieveTimesToReactInSpecificUnit(SimpleTimeUnit simpleTimeUnit) {
        final SimpleTimeUnit seriesSimpleTimeUnit = SimpleTimeUnit.retrieveSimpleTimeUnitAccordingToSymbol(unitSymbol);
        if (simpleTimeUnit == null || simpleTimeUnit.equals(seriesSimpleTimeUnit)) {
            return retrieveTimesToReact();
        }
        if (times <= 0 ) {
            return null;
        }
        List<Long> timesToReact = new ArrayList<>(times);

        for (int i = 0; i < times; i++) {
            long currentValue = (i + 1) * numberUnit;
            currentValue = simpleTimeUnit.convert(currentValue,seriesSimpleTimeUnit);
            timesToReact.add(i, currentValue);
        }
        return timesToReact;
    }

    public List<Long> retrieveTimesToReactInSeconds() {
        return retrieveTimesToReactInSpecificUnit(SimpleTimeUnit.SECONDS);
    }
}