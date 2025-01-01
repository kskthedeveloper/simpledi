package org.sampledi;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ApplicationContext("org.sampledi");
        context.initializeBeans();

        ServiceB serviceB = context.getBean(ServiceB.class);
        serviceB.callServiceA();
    }
}