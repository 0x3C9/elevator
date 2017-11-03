package com.github.jupp.elevator;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public class Elevator {
    private final int currentFloor;
    private final boolean goingUp;
    private final Set<Integer> pressedButtons;
    private final int floorsNumber;

    public Elevator(int floorsNumber) {
        this(floorsNumber, true, 1, ImmutableSet.of());
    }

    Elevator(
            int floorsNumber,
            boolean goingUp,
            int currentFloor,
            Set<Integer> pressedButtons) {
        if (floorsNumber < 2) {
            throw new IllegalArgumentException("To create an elevator you need at least 2 floors");
        }

        if (currentFloor > floorsNumber) {
            throw new IllegalArgumentException("Floor out of bound");
        }

        this.floorsNumber = floorsNumber;
        this.goingUp = goingUp;
        this.currentFloor = currentFloor;
        this.pressedButtons = pressedButtons;
    }

    public Elevator pressButton(int floor) {
        Set<Integer> pressedButtonsWithNew = new HashSet<>(pressedButtons);
        pressedButtonsWithNew.add(floor);

        return new Elevator(
                floorsNumber,
                goingUp,
                currentFloor,
                ImmutableSet.copyOf(pressedButtonsWithNew));
    }

    public Elevator openDoors() {
        Set<Integer> pressedButtonsWithoutCurrent = new HashSet<>(pressedButtons);
        pressedButtonsWithoutCurrent.remove(currentFloor);

        return new Elevator(
                floorsNumber,
                goingUp,
                currentFloor,
                ImmutableSet.copyOf(pressedButtonsWithoutCurrent));
    }

    public Elevator goUpwards() {
        return new Elevator(floorsNumber, true, currentFloor + 1, pressedButtons);
    }

    public Elevator goDownwards() {
        return new Elevator(floorsNumber, false, currentFloor - 1, pressedButtons);
    }

    public Action nextAction() {
        if (!pressedButtons.isEmpty()) {
            if (pressedButtons.contains(currentFloor)) {
                return Action.OPEN_DOORS;
            } else {
                if (goingUp) {
                    boolean buttonPressedAbove = pressedButtons
                            .stream()
                            .anyMatch(f -> f > currentFloor);
                    return buttonPressedAbove ? Action.GO_UPWARDS : Action.GO_DOWNWARDS;
                } else {
                    boolean buttonPressedBelow = pressedButtons
                            .stream()
                            .anyMatch(f -> f < currentFloor);
                    return buttonPressedBelow ? Action.GO_DOWNWARDS : Action.GO_UPWARDS;
                }
            }
        } else {
            return Action.STAY_STILL;
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
