package de.kneitzel.simpledi;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Dependency Injection
 * Thread: https://www.java-forum.org/thema/reflection-gone-wrong.198603/
 */
public class ApplicationContext {

    /**
     * default application context.
     */
    private static ApplicationContext defaultContext;

    /**
     * Already created instances.
     */
    private Map<String, Object> beans = new ConcurrentHashMap<>();

    /**
     * List of classes that we currently build.
     */
    private Set<Class> currentlyBuilding = new HashSet<>();

    public ApplicationContext() {
        beans.put(getClass().getName(), this);
    }

    /**
     * Gets the default application context.
     * @return Default application context.
     */
    public static ApplicationContext getDefaultContext() {
        if (defaultContext == null) {
            synchronized (ApplicationContext.class) {
                if (defaultContext == null) {
                    defaultContext = new ApplicationContext();
                }
            }
        }
        return defaultContext;
    }

    public synchronized <T> T getBean(final Class<? extends T> clazz) {
        List<Initializable> toInitialize = new ArrayList<>();
        try {
            return getBean(clazz, toInitialize);
        } finally {
            toInitialize.forEach(Initializable::initialize);
        }
    }
    private synchronized <T> T getBean(final Class<? extends T> clazz, final List<Initializable> toInitialize) {
        if (currentlyBuilding.contains(clazz)) {
            throw new IllegalStateException("Circular dependency when building " + clazz.getName());
        }

        System.out.println("Creating instance for " + clazz.getSimpleName());

        T instance = null;
        try {
            instance = (T) beans.get(clazz.getName());
            if (instance != null) {
                return instance;
            }

            Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
            Class[] parameterClasses = constructor.getParameterTypes();
            Object[] parameter = Arrays.stream(parameterClasses)
                    .map(c -> getBean(c, toInitialize))
                    .toArray();

            instance = constructor.newInstance(parameter);
            System.out.println("Added instance for " + clazz.getSimpleName() + " to beans (" + instance + ")");
            beans.put(clazz.getName(), instance);
            if (instance instanceof Initializable initializable) {
                toInitialize.add(initializable);
            }
        } catch (Exception ex) {
            currentlyBuilding.clear();
            throw new IllegalStateException("Unable to instantiate class " + clazz.getName(), ex);
        } finally {
            currentlyBuilding.remove(clazz);
        }

        return instance;
    }

}
