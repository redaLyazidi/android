package com.api.android.constant.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTimeUnitTest {

    @Test
    public void should_Convert_Minutes_return_expected_seconds() throws Exception {
        // GIVEN
        final long numberMinutes = 1L;
        final long expextedSeconds = 60L;

        // WHEN
        final long numbersSeconds = SimpleTimeUnit.SECONDS.convert(numberMinutes,SimpleTimeUnit.MINUTES);

        // THEN
        assertTrue(numbersSeconds == expextedSeconds);
    }

    @Test
    public void should_ToSeconds_Minutes_return_expected_seconds() throws Exception {
        // GIVEN
        final long numberMinutes = 1L;
        final long expextedSeconds = 60L;

        // WHEN
        final long numbersSeconds = SimpleTimeUnit.MINUTES.toSeconds(numberMinutes);

        // THEN
        assertTrue(numbersSeconds == expextedSeconds);
    }
}