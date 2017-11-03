package com.github.jupp.elevator;

public class Config {

    public final int floorsNumber;
    public final double floorHeightM;
    public final double elevatorSpeedMSec;
    public final int doorsDelaySec;

    public Config(Env env) {
        floorsNumber = env.integer("FLOORS_NUMBER", 5);
        if (floorsNumber < 5 || floorsNumber > 20) {
            throw new IllegalArgumentException("Incorrect floors number");
        }

        floorHeightM = env.floatingPoint("FLOOR_HEIGHT_M", 3.0);
        if (floorHeightM <= 0) {
            throw new IllegalArgumentException("Floor height must be positive");
        }

        elevatorSpeedMSec = env.floatingPoint("ELEVATOR_SPEED_M_SEC", 1.0);
        if (elevatorSpeedMSec <= 0) {
            throw new IllegalArgumentException("Elevator speed must be positive");
        }

        doorsDelaySec = env.integer("DOORS_DELAY_SEC", 5);
        if (doorsDelaySec <= 0) {
            throw new IllegalArgumentException("Delay to open and close elevator doors must be positive");
        }
    }
}
