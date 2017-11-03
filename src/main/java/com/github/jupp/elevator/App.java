package com.github.jupp.elevator;

import java.io.Console;

public class App {
    private final Config config;

    private App() {
        config = new Config(new Env());
    }

    private void printIntro() {
        System.out.println("==============================================================");
        System.out.println("Welcome to elevator simulation!");
        System.out.println("Simulation parameters (to overwrite set environment variable):");
        System.out.println("FLOORS_NUMBER = " + config.floorsNumber);
        System.out.println("FLOOR_HEIGHT_M = " + config.floorHeightM + " (meters)");
        System.out.println("ELEVATOR_SPEED_M_SEC = " + config.elevatorSpeedMSec + " (meters per second)");
        System.out.println("DOORS_DELAY_SEC = " + config.doorsDelaySec + " (seconds)");
        System.out.println("==============================================================");
        System.out.println("\nEnter floor number...");
    }

    private void start() {
        printIntro();

        UserInputChannel<Integer> channel = new UserInputChannel<>();
        Thread elevatorThread = new Thread(() -> elevatorLoop(channel));
        elevatorThread.start();

        inputLoop(channel);
    }

    private void elevatorLoop(UserInputChannel<Integer> userInputChannel) {
        Elevator elevator = new Elevator(config.floorsNumber);
        ElevatorPhysics elevatorPhysics =
                new ElevatorPhysics(
                        config.floorHeightM,
                        config.elevatorSpeedMSec,
                        config.doorsDelaySec);

        while (true) {
            elevator = userInputChannel.reduceOnNew(elevator, Elevator::pressButton);

            switch (elevator.nextAction()) {
                case STAY_STILL:
                    elevatorPhysics.stayStill();
                    break;
                case GO_UPWARDS:
                    elevator = elevator.goUpwards();
                    elevatorPhysics.passFloor();
                    System.out.println("At floor " + elevator.getCurrentFloor());
                    break;
                case GO_DOWNWARDS:
                    elevator = elevator.goDownwards();
                    elevatorPhysics.passFloor();
                    System.out.println("At floor " + elevator.getCurrentFloor());
                    break;
                case OPEN_DOORS:
                    System.out.println("Open doors at floor " + elevator.getCurrentFloor());
                    elevatorPhysics.openDoors();
                    System.out.println("Close doors at floor " + elevator.getCurrentFloor());
                    elevator = elevator.openDoors();
                    break;
            }
        }
    }

    private void inputLoop(UserInputChannel<Integer> channel) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Can't work without console :(");
            return;
        }

        while (true) {
            String buttonNumber = console.readLine();
            try {
                int floor = Integer.parseInt(buttonNumber);
                if (floor <= config.floorsNumber
                        && floor > 0) {
                    channel.publish(floor);
                } else {
                    System.out.println("Invalid floor!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a number!");
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}
