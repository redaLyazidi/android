package com.api.android.chronometer.model;

import com.api.android.constant.enums.SimpleTimeUnit;
import com.api.condition.MultipleOfConditiion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SeriesTest {


    @Test
    public void should_retrieveTimesToReactInSeconds_return_same_as_retrieveTimesToReact_when_series_in_seconds() {
        // GIVEN
        final int numbersSeconds = 15;
        final int times = 20;
        Series series = new Series(numbersSeconds,times,"s");
        // WHEN
        final List<Long> timesToReact = series.retrieveTimesToReact();
        final List<Long> timesToReactinSeconds = series.retrieveTimesToReactInSeconds();

        // THEN
        assertThat(timesToReactinSeconds).isNotEmpty().containsAll(timesToReact);
    }

    @Test
    public void should_retrieveTimesToReactInSeconds_return_correct_Value_in_seconds() {
        // GIVEN
        final int numbersSecondsInOneMinute = 60;
        final int numberMinutes = 15;
        final int times = 20;
        Series series = new Series(numberMinutes,times, "m");
        // WHEN
        final List<Long> timesToReactinSeconds = series.retrieveTimesToReactInSeconds();
        final List<Long> timesToReactMinutes = series.retrieveTimesToReact();
        series.setNumberUnit(numberMinutes * numbersSecondsInOneMinute);
        series.setUnitSymbol("s");
        final List<Long> timesToReactWithSeriesInSeconds = series.retrieveTimesToReact();

        // THEN
        assertThat(timesToReactinSeconds).isNotEmpty().containsAll(timesToReactWithSeriesInSeconds);
    }
}