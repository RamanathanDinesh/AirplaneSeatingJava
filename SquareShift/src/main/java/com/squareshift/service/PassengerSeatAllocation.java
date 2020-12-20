package com.squareshift.service;

import com.squareshift.models.Seat;
import com.squareshift.utils.SeatType;
import com.squareshift.utils.Seating;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PassengerSeatAllocation {

    public List<Seat> fillSeatsWithPassengers(LinkedHashMap<Integer, Seating> seatings, Integer totalPassenger) {
        List<Seat> result = new ArrayList<>();
        int fillingRow = 0;
        int fillingvalue = 1;
        SeatType fillingSeatType = SeatType.AISLE;
        while (fillingvalue <= totalPassenger) {
            boolean atleastOneseatFilled = false;
            for (Seating seatLayout : seatings.values()) {
                int defaultSeatsCountAvailable =
                        seatLayout.getDefaultSeatsCountAvailable(fillingRow, fillingSeatType);
                if (defaultSeatsCountAvailable > 0) {
                    atleastOneseatFilled = true;
                    List<Integer> defaultSeatsAvailable =
                            seatLayout.getDefaultSeatsAvailable(fillingRow, fillingSeatType);
                    for (Integer col : defaultSeatsAvailable) {
                        if (fillingvalue <= totalPassenger) {
                            result.add(new Seat(fillingRow, col, fillingvalue, fillingSeatType, seatLayout.zone));
                            fillingvalue++;
                        } else {
                            break;
                        }
                    }
                }
            }
            fillingRow++;
            if (!atleastOneseatFilled) {
                if (fillingvalue <= totalPassenger
                        && SeatType.values().length > fillingSeatType.ordinal() + 1) {
                    fillingSeatType = SeatType.values()[fillingSeatType.ordinal() + 1];
                    fillingRow = 0;
                } else {
                    System.out.println(">>> Seats are Full for " + (fillingvalue - 1) + " Persons");
                    break;
                }
            }
        }
        return result;
    }
}
