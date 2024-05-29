package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainClassTest {
    @Test
    void testGetClassString() {
        MainClass mainClass = new MainClass();
        assertTrue(
                mainClass.getClassString().matches(".*[Hh]ello.*"),
                "The string does not contain 'Hello' or 'hello'"
        );
    }
}
