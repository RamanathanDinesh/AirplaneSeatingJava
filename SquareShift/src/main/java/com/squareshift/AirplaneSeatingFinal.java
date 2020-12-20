package com.squareshift;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareshift.models.Seat;
import com.squareshift.models.SeatPrintSorter;
import com.squareshift.service.PassengerSeatAllocation;
import com.squareshift.service.SeatPrintLayout;
import com.squareshift.utils.Position;
import com.squareshift.utils.Seating;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class AirplaneSeatingFinal {

  public static void main(String[] args) {

    SeatPrintLayout seatPrintLayout = new SeatPrintLayout();
    PassengerSeatAllocation passengerSeatAllocation = new PassengerSeatAllocation();

    try {
      Scanner sc = new Scanner(System.in);

      System.out.println("Enter the airplane seat layout details: ");
      String seatLayoutString = "[[3,22],[4,13],[2,33],[3,43]]";
      //       seatLayoutString = sc.nextLine();
      LinkedHashMap<Integer, Seating> seatings = getZoneDetailsFromString(seatLayoutString);

      System.out.println("Enter the number of passengers: ");
      //      totalPassenger = sc.nextInt();
      Integer totalPassenger = 530;

      List<Seat> filledSeats =
              passengerSeatAllocation.fillSeatsWithPassengers(seatings, totalPassenger);
      Collections.sort(filledSeats, new SeatPrintSorter());
      seatPrintLayout.printSeatLayout(filledSeats, seatings);
      System.out.println();

    } catch (Exception e) {
      System.out.println("Given input is wrong. Please check");
      e.printStackTrace();
    }
  }

  public static LinkedHashMap<Integer, Seating> getZoneDetailsFromString(String seatLayoutString) {
    Gson gson = new GsonBuilder().create();
    Integer[][] seatlayout = gson.fromJson(seatLayoutString, Integer[][].class);
    LinkedHashMap<Integer, Seating> seatings = new LinkedHashMap<>();
    for (int i = 0; i < seatlayout.length; i++) {
      Position p = Position.MID;
      if (i == 0) {
        p = Position.START;
      }
      if (i == seatlayout.length - 1) {
        p = Position.END;
      }
      seatings.put(i, new Seating(seatlayout[i][0], seatlayout[i][1], p, i));
    }
    return seatings;
  }
}
