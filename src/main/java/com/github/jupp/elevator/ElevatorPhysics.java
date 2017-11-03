package com.github.jupp.elevator;

public class ElevatorPhysics {
    private static final int MILLIS_IN_SECOND = 1000;

    private final long millisToPassFloor;
    private final long doorsDelayMillis;

    public ElevatorPhysics(double floorHeightM, double elevatorSpeedMSec, int doorsDelaySec) {
        millisToPassFloor = Math.round(floorHeightM * MILLIS_IN_SECOND / elevatorSpeedMSec);
        doorsDelayMillis = doorsDelaySec * MILLIS_IN_SECOND;
    }

    public void passFloor() {
        try {
            Thread.sleep(millisToPassFloor);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void openDoors() {
        try {
            Thread.sleep(doorsDelayMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stayStill() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
