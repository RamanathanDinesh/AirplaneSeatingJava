package com.squareshift;

import com.squareshift.models.Seat;
import com.squareshift.service.PassengerSeatAllocation;
import com.squareshift.utils.SeatType;
import com.squareshift.utils.Seating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

public class PassengerSeatAllocationTest {
  PassengerSeatAllocation passengerSeatAllocation;
  LinkedHashMap<Integer, Seating> actualSeating = null;

  @Before
  public void setUp() {
    // [[3,2],[4,3],[2,3],[3,4]]
    passengerSeatAllocation = new PassengerSeatAllocation();
    actualSeating =
        new AirplaneSeatingFinal().getZoneDetailsFromString("[[3,2],[4,3],[2,3],[3,4]]");
  }

  /**
   * Aisle should be occupied first. With the given Seating layout. up-to 18 passengers must be
   * filled in aisle seats. Expected OutPut: Given 5 passengers, all must be filled in aisle seats
   */
  @Test
  public void testAisleSeatAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 5);

    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 2 && seat.zone == 0) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(1, seat.value);
        Assert.assertEquals(0, seat.zone);
      } else if (seat.x == 0 && seat.y == 0 && seat.zone == 1) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(2, seat.value);
      } else if (seat.x == 0 && seat.y == 3 && seat.zone == 1) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(3, seat.value);
      } else if (seat.x == 0 && seat.y == 0 && seat.zone == 2) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(4, seat.value);
      } else if (seat.x == 0 && seat.y == 1 && seat.zone == 2) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(5, seat.value);
      }
    }
  }

  /**
   * Aisle should be occupied first. With the given Seating layout. up-to 18 passengers must be
   * filled in aisle seats. Expected OutPut: Given 5 passengers, only 5 passengers value must be
   * filled, 6th aisle value must be empty
   */
  @Test
  public void testAisleSeatEmptyAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 5);
    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 0 && seat.zone == 3) {
        Assert.assertEquals(SeatType.AISLE, seat.type);
        Assert.assertEquals(0, seat.value);
      }
    }
  }

  /**
   * Once Aisle is full, Window Seats must be filled in. As per the seat layout 6 Window seats
   * available. If total passengers 24, then 18 Aisle seats and 6 Window seats must be filled in.
   */
  @Test
  public void testWindowSeatAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 24);

    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 0 && seat.zone == 0) {
        Assert.assertEquals(SeatType.WINDOW, seat.type);
        Assert.assertEquals(19, seat.value);
      }

      if (seat.x == 3 && seat.y == 2 && seat.zone == 3) {
        Assert.assertEquals(SeatType.WINDOW, seat.type);
        Assert.assertEquals(24, seat.value);
      }
    }
  }

  /**
   * Once Aisle is full, Window Seats must be filled in. As per the seat layout 6 Window seats
   * available. If total passengers 20, then 18 Aisle seats and only 2 Window seats must be filled in.
   * Rest of the Window seats must be empty
   */
  @Test
  public void testWindowSeatEmptyAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 20);

    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 0 && seat.zone == 0) {
        Assert.assertEquals(SeatType.WINDOW, seat.type);
        Assert.assertEquals(19, seat.value);
      }

      if (seat.x == 1 && seat.y == 0 && seat.zone == 0) {
        Assert.assertEquals(SeatType.WINDOW, seat.type);
        Assert.assertEquals(0, seat.value);
      }
    }
  }

  /**
   * Once Aisle & Window is full, center Seats must be filled in. As per the seat layout 12 center seats
   * available. If total passengers 36, then 18 Aisle seats, 6 Window seat and 12 center seats must be filled in.
   */
  @Test
  public void testMiddleSeatAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 36);

    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 1 && seat.zone == 0) {
        Assert.assertEquals(SeatType.CENTER, seat.type);
        Assert.assertEquals(25, seat.value);
      }

      if (seat.x == 3 && seat.y == 1 && seat.zone == 3) {
        Assert.assertEquals(SeatType.CENTER, seat.type);
        Assert.assertEquals(36, seat.value);
      }
    }
  }

  /**
   * Once Aisle & Window is full, center Seats must be filled in. As per the seat layout 12 center seats
   * available. If total passengers 36, then 18 Aisle seats, 6 Window seats and 12 center seats must be filled in.
   * If total passengers are only about 25, then 11 Center seats must be empty.
   */

  @Test
  public void testMiddleSeatEmptyAllocation() {
    List<Seat> filledSeats = passengerSeatAllocation.fillSeatsWithPassengers(actualSeating, 25);

    for (Seat seat : filledSeats) {
      if (seat.x == 0 && seat.y == 1 && seat.zone == 0) {
        Assert.assertEquals(SeatType.CENTER, seat.type);
        Assert.assertEquals(25, seat.value);
      }

      if (seat.x == 0 && seat.y == 1 && seat.zone == 1) {
        Assert.assertEquals(SeatType.CENTER, seat.type);
        Assert.assertEquals(0, seat.value);
      }

      if (seat.x == 3 && seat.y == 1 && seat.zone == 3) {
        Assert.assertEquals(SeatType.CENTER, seat.type);
        Assert.assertEquals(0, seat.value);
      }
    }
  }
}
