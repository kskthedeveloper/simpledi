package org.sampledi;

/*
A Container to manage objects and their dependencies
 */

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    public ApplicationContext(String basePackage) throws Exception {
        ClassScanner.scan(basePackage).stream()
                .filter(cls -> cls.isAnnotationPresent(Component.class))
                .forEach(this::createAndRegisterBean);
    }

    private void createAndRegisterBean(Class<?> cls) {
        try {
            Object instance = cls.getDeclaredConstructor().newInstance();
            System.out.println("Registered bean: " + cls.getName());
            beanRegistry.put(cls, instance);
        } catch (Exception e)  {
            throw new RuntimeException("Failed to create bean for class " + cls, e);
        }
    }

    public void initializeBeans() {
        for (Object bean: beanRegistry.values()) {
            injectDependencies(bean);
        }
    }

    private void injectDependencies(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = beanRegistry.get(field.getType());

                if (dependency == null) {
                    throw new RuntimeException("Unsatisfied dependency: " + field.getType());
                }

                field.setAccessible(true);

                try {
                    field.set(bean, dependency);
                    System.out.println("Injected " + dependency.getClass().getName() + " into " + bean.getClass().getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency into field: " + field, e);
                }
            }
        }
    }

    public <T> T getBean(Class<T> cls) {
        return cls.cast(beanRegistry.get(cls));
    }
}
