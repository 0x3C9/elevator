package com.github.jupp.elevator;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ElevatorTest {

    @Test
    public void shouldStayStillIfNoButtonsPressed() throws Exception {
        // Given
        Elevator elevator = new Elevator(5);

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.STAY_STILL, action);
    }

    @Test
    public void shouldOpenDoorsOnCorrectFloor() throws Exception {
        // Given
        Elevator elevator = new Elevator(5, true, 3, Collections.singleton(3));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.OPEN_DOORS, action);
    }

    @Test
    public void shouldGoUpwardsIfTheOnlyButtonPressedIsAboveAndItWasGoingUp() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, true, 3, Collections.singleton(5));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_UPWARDS, action);
    }

    @Test
    public void shouldGoUpwardsIfTheOnlyButtonPressedIsAboveAndItWasGoingDown() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, false, 3, Collections.singleton(5));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_UPWARDS, action);
    }

    @Test
    public void shouldGoDownwardsIfTheOnlyButtonPressedIsAboveAndItWasGoingDown() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, false, 3, Collections.singleton(1));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_DOWNWARDS, action);
    }

    @Test
    public void shouldGoDownwardsIfTheOnlyButtonPressedIsAboveAndItWasGoingUp() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, true, 3, Collections.singleton(1));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_DOWNWARDS, action);
    }

    @Test
    public void shouldGoUpwardsIfAboveAndBelowButtonsPressedAndItWasGoingUp() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, true, 3, ImmutableSet.of(1, 5));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_UPWARDS, action);
    }

    @Test
    public void shouldGoDownwardsIfAboveAndBelowButtonsPressedAndItWasGoingDown() throws Exception {
        // Given
        Elevator elevator =
                new Elevator(5, false, 3, ImmutableSet.of(1, 5));

        // When
        Action action = elevator.nextAction();

        // Then
        assertEquals(Action.GO_DOWNWARDS, action);
    }
}