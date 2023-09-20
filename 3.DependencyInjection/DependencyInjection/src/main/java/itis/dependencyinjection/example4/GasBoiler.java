package itis.dependencyinjection.example4;

public class GasBoiler implements Boiler {
    @Override
    public void warm() {
        System.out.println("GAS");
    }
}
