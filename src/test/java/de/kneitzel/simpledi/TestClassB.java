package de.kneitzel.simpledi;

public class TestClassB implements Initializable {
    private ApplicationContext context;

    private TestClassA a;

    public TestClassB(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        System.out.println("Initialize: Getting instance for TestClassA");
        a = context.getBean(TestClassA.class);
    }

    public ApplicationContext getContext() {
        return context;
    }

    public TestClassA getA() {
        return a;
    }
}
