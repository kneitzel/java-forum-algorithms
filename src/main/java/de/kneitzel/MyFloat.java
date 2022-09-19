package de.kneitzel;

/**
 * Simple class that scans a String of bits to parse an float.
 *
 * Important: This is just to demonstrate it. Float already has methods to get a float from bits!
 *
 * Thread: https://www.java-forum.org/thema/bin-to-float-convertieren-funktioniert-nicht.199086/
 * First version of user: planet_safe (https://www.java-forum.org/mitglied/planet_safe.73117/)
 *
 */
public class MyFloat extends Number {
    private final int sign;
    private int exponent;
    private int mantisse;

    public MyFloat(String bits) {
        bits = fitLength(bits);
        if (bits.charAt(0) == '0') {
            sign = 1;
        } else {
            sign = -1;
        }
        exponent = 0;
        for (int i = 1; i < 9; i++) {
            exponent |= (bits.charAt(i) - '0') << (8 - i);
        }
        mantisse = 0;
        for (int i = 9; i < 32; i++) {
            mantisse |= (bits.charAt(i) - '0') << (31 - i);
        }
        System.out.println(sign + " " + exponent + " " + mantisse);
    }

    private String fitLength(String s) {
        while (s.length() < 32) {
            s = "0" + s;
        }
        return s;
    }

    private double power2(int exponent) {
        double a = 1.;
        while (exponent > 0) {
            a *= 2;
            exponent--;
        }
        return a;
    }

    @Override
    public int intValue() {
        return (int) floatValue();
    }

    @Override
    public long longValue() {
        return (long) floatValue();
    }

    @Override
    public float floatValue() {
        if (exponent == 255) {
            if (mantisse != 0) return Float.NaN;
            if (sign < 0) return Float.NEGATIVE_INFINITY;
            return Float.POSITIVE_INFINITY;
        }

        if (exponent == 0) {
            return (float) (sign * mantisse / power2(23) * power2(1-127));
        }

        return (float) (sign * (1. + mantisse/power2(23)) * power2(exponent - 127));
    }

    @Override
    public double doubleValue() {
        return floatValue();
    }

}