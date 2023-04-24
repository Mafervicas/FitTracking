package com.mafervicas.fitTracking.views;

import static org.junit.Assert.*;

import org.junit.Test;

public class DashboardTest {

    @Test
    public void getFinalIMC() {
        Double input = 19.0;
        String output ;
        String expected = "Intervalo normal";

        Dashboard dashboard = new Dashboard();
        output = dashboard.getFinalIMC(input);

        assertEquals(expected, output);
    }
}