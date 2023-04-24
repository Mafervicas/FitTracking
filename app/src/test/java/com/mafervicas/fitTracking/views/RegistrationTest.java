package com.mafervicas.fitTracking.views;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegistrationTest {

    @Test
    public void getInfoExercise() {
        Integer input = 1;
        Double output ;
        Double expected = 1.2;
        Double delta = .1;

        Registration registration = new Registration();
        output = registration.getInfoExercise(input);

        assertEquals(expected, output, delta);

    }
}