package de.kneitzel.simpledi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextTest {
    @Test
    public void testApplicationContext() {
        ApplicationContext context = ApplicationContext.getDefaultContext();
        TestClassA a = context.getBean(TestClassA.class);
        assertNotNull(a);

        TestClassB b = a.getB();
        assertAll(
                () -> assertNotNull(b),
                () -> assertNotNull(b.getA()),
                () -> assertEquals(a, b.getA()),
                () -> assertNotNull(b.getContext()),
                () -> assertEquals(context, b.getContext())
        );
    }
}