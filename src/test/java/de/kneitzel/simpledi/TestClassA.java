package de.kneitzel.simpledi;

public class TestClassA {
    private TestClassB b;

    public TestClassA(TestClassB b) {
        this.b = b;
    }

    public TestClassB getB() {
        return b;
    }
}
