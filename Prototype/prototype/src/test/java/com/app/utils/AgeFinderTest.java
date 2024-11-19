package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgeFinderTest {


    @Test
    void ageFinderTest() {
        assertEquals(20, AgeFinder.findAge("2004-06-13", "2024-11-18"));
        assertEquals(0, AgeFinder.findAge("2024-11-18", "2024-11-18"));
    }
}
