package com.github.jupp.elevator;


public class Env {
    public int integer(String name, int defaultValue) {
        String envValue = System.getenv(name);
        return envValue != null ? Integer.parseInt(envValue) : defaultValue;
    }

    public double floatingPoint(String name, double defaultValue) {
        String envValue = System.getenv(name);
        return envValue != null ? Double.parseDouble(envValue) : defaultValue;
    }
}
