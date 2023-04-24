package com.mafervicas.fitTracking.views;

import static org.junit.Assert.*;

import org.junit.Test;

public class kcalTrackerTest {

    @Test
    public void kcalsToShow() {
        Double input1 = 1000.0;
        Double input2 = 1000.0;
        Double output ;
        Double expected = 0.0;

        kcalTracker kcalTracker = new kcalTracker();
        output = kcalTracker.kcalsToShow(input1,input2);

        assertEquals(expected, output);
    }
}