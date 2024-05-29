package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainClassTest {
    @Test
    void testGetClassNumber() {
        MainClass mainClass = new MainClass();
        assertTrue(mainClass.getClassNumber() > 45, "getClassNumber() value is not greater than 45");
    }

}