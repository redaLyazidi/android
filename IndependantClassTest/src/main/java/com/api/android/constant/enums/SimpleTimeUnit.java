package com.api.android.constant.enums;

import com.api.android.util.StringsUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by reda on 10/19/14.
 */
public enum SimpleTimeUnit {

    HOURS("h", TimeUnit.HOURS), MINUTES("m", TimeUnit.MINUTES), SECONDS("s", TimeUnit.SECONDS),
    MILLISECONDS("ms", TimeUnit.MILLISECONDS), MICROSECONDS("µs", TimeUnit.MICROSECONDS);

    private String symbol;

    private TimeUnit timeUnit;


    private SimpleTimeUnit(String symbol, TimeUnit timeUnit) {
        this.symbol = symbol;
        this.timeUnit = timeUnit;
    }

    public static SimpleTimeUnit retrieveSimpleTimeUnitAccordingToSymbol(String symbol) {
        if (StringsUtil.isReallyNullOrEmpty(symbol)) {
            return null;
        }
        for (SimpleTimeUnit currentSimpleTimeUnit : SimpleTimeUnit.values()) {
            if (currentSimpleTimeUnit.getSymbol().equals(symbol)) {
                return currentSimpleTimeUnit;
            }
        }
        return null;
    }

    public String getSymbol() {
        return symbol;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long convert(long sourceDuration, SimpleTimeUnit sourceUnit) {
        return timeUnit.convert(sourceDuration, sourceUnit.getTimeUnit());
    }

    public long toMicros(long duration) {
        return timeUnit.toMicros(duration);
    }

    public long toMillis(long duration) {
        return timeUnit.toMillis(duration);
    }

    public long toSeconds(long duration) {
        return timeUnit.toSeconds(duration);
    }

    public long toMinutes(long duration) {
        return timeUnit.toMinutes(duration);
    }

    public long toHours(long duration) {
        return timeUnit.toHours(duration);
    }
}
