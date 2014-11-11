package com.api.android.chronometer.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChronometerProgramTest {

    private ChronometerProgram chronometerProgram;

    @Before
    public void setUp() {
        chronometerProgram = new ChronometerProgram();
    }

    @Test
    public void should_RetrieveValueToReactFromSeriesInSeconds_return_empty_list() throws Exception {
        // GIVEN

        // WHEN
        List<Long> timesToReact = chronometerProgram.retrieveValueToReactFromSeriesInSeconds();

        //THEN
        assertThat(timesToReact).isEmpty();
    }

    @Test
    public void should_RetrieveValueToReactFromSeriesInSeconds_return_same_than_series() throws Exception {
        // GIVEN
        final String unitSymbol = "s";
        final int timesAndUnit = 2;
        Series series = new Series(timesAndUnit,timesAndUnit,unitSymbol);
        chronometerProgram.addSeries(series);

        // WHEN
        List<Long> timesToReactFromProgram = chronometerProgram.retrieveValueToReactFromSeriesInSeconds();
        List<Long> timesToReactFromSeries = series.retrieveTimesToReactInSeconds();

        //THEN
        assertThat(timesToReactFromProgram).isNotEmpty().containsAll(timesToReactFromSeries);
    }

    @Test
    public void should_RetrieveValueToReactFromSeriesInSeconds_return_correct_value() throws Exception {
        // GIVEN
        final String unitSymbol = "s";
        final int timesAndUnit = 2;
        final int timesOfSecondSerie = 15;
        final int timesOfThirdSerie = 8;
        Series series = new Series(timesAndUnit,timesAndUnit,unitSymbol);
        chronometerProgram.addSeries(series);
        chronometerProgram.addSeries(1,timesOfSecondSerie,"s");
        chronometerProgram.addSeries(8,timesOfThirdSerie,"s");

        // WHEN
        List<Long> timesToReactFromProgram = chronometerProgram.retrieveValueToReactFromSeriesInSeconds();
        List<Long> timesToReactFromSeries = series.retrieveTimesToReactInSeconds();

        //THEN
        System.out.println(timesToReactFromProgram);
        assertThat(timesToReactFromProgram).isNotEmpty().hasSize(timesAndUnit + timesOfSecondSerie + timesOfThirdSerie);
    }
}