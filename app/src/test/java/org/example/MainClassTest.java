package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        MainClass mainClass = new MainClass();
        assertEquals(14, mainClass.getLocalNumber(), "getLocalNumber() does not return 14");
    }

}