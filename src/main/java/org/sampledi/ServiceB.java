package org.sampledi;

@Component
public class ServiceB {
    @Autowired
    private ServiceA serviceA;

    public void callServiceA() {
        serviceA.sayHello();
    }
}
