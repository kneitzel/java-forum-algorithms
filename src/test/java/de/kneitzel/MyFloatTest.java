package de.kneitzel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyFloatTest {
    @Test
    public void testMyFloatClass() {
        assertAll(
                () -> assertEquals(Float.NaN, new MyFloat("11111111101010110000000000000000").floatValue()),
                () -> assertEquals(Float.NEGATIVE_INFINITY, new MyFloat("11111111100000000000000000000000").floatValue()),
                () -> assertEquals(Float.POSITIVE_INFINITY, new MyFloat("01111111100000000000000000000000").floatValue()),
                () -> assertEquals(-123.456f, new MyFloat(Integer.toBinaryString(Float.floatToIntBits(-123.456f))).floatValue())
        );
    }
}