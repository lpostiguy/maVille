package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgeFinderTest {

    @Test
    void ageFinderTest() {
        assertEquals(19, AgeFinder.findAge("2004-06-13", "2024-11-18"));
    }
}
