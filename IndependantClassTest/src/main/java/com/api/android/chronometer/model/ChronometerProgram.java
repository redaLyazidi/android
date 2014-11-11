package com.api.android.chronometer.model;

import java.util.*;

/**
 * Created by reda on 9/21/14.
 */
public class ChronometerProgram {


    private int index;
    private List<Series> allSeries;

    public ChronometerProgram() {
        reset();
    }

    public List<Series> getAllSeries() {
        return allSeries;
    }

    public boolean addSeries(int numberUnit, int times, String unitSymbol) {
        Series series = new Series(numberUnit,times,unitSymbol);
        return allSeries.add(series);
    }

    public boolean addSeries(int position, int numberUnit, int times, String unitSymbol) {
        Series series = new Series(numberUnit,times,unitSymbol);
        allSeries.add(position,series);
        return allSeries.indexOf(series) == position;
    }

    public void reset() {
        index = 0;
        allSeries = new LinkedList<>();
    }

    public int getIndex() {
        return index;
    }

    public int incrementIndex() {
        return index++;
    }


    public static List<Long> retrieveValueToReactFromSeries(Series series) {
        if (series == null) {
            return null;
        }
        return series.retrieveTimesToReact();
    }

    public void addSeries(Series series) {
            allSeries.add(series);
    }


    public void addAllSeries(Collection<Series> series) {
        if (series != null) {
            series.removeAll(Collections.singleton(null));
            if (!series.isEmpty()) {
                allSeries.addAll(series);
            }
        }
    }


    public List<Long> retrieveValueToReactFromSeriesInSeconds() {
        if (allSeries == null || allSeries.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> timesToReact = new LinkedList<>();
        final int timesToReactLength = allSeries.size();
         timesToReact.addAll(allSeries.get(0).retrieveTimesToReactInSeconds());
        long lastValue = timesToReact.get(timesToReact.size() - 1);

        for (int i = 1; i < timesToReactLength; i++ ) {
            timesToReact.addAll(retrieveNextTimesToReactInSeconds(lastValue, allSeries.get(i)));
            lastValue = timesToReact.get(timesToReact.size() - 1);
        }
        return timesToReact;
    }

    private static List<Long> retrieveNextTimesToReactInSeconds(long lastValue, Series series) {
        if (series == null) {
            Collections.emptyList();
        }
            final int times = series.getTimes();
            List<Long> timesToReactFromSeries = new ArrayList<>(times);
            final int numberUnit = series.getNumberUnit();
            for (int i = 0 ; i < times; i++) {
                    lastValue += numberUnit;
                    timesToReactFromSeries.add(i,lastValue);
            }
        return timesToReactFromSeries;
     }

}