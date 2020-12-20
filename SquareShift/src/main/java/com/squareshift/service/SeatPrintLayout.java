package com.squareshift.service;

import com.squareshift.models.Seat;
import com.squareshift.utils.Seating;

import java.util.LinkedHashMap;
import java.util.List;

public class SeatPrintLayout {
    public void printSeatLayout(List<Seat> filledSeats, LinkedHashMap<Integer, Seating> seatings) {
        Seat lastSeat = null;
        for (Seat seat : filledSeats) {
            printTabs(lastSeat, seat, seatings);
            System.out.print(String.format("%03d\t", seat.value)); // Digit
            lastSeat = seat;
        }
    }

    private void printTabs(Seat lastSeat, Seat seat, LinkedHashMap<Integer, Seating> seatings) {
        if (lastSeat == null) return;
        int lastzone = lastSeat.zone;
        boolean zonereset = false;
        for (int i = lastSeat.x; i < seat.x; i++) {
            System.out.print("\n");
            lastzone = 0;
            zonereset = true;
        }

        for (int itrZone = lastzone; itrZone < seat.zone; itrZone++) {
            if (zonereset && lastzone != seat.zone) {
                for (int k = 0; k < seatings.get(itrZone).col; k++) {
                    System.out.print("   \t"); // Digit
                }
            }
            System.out.print("\t|\t");
            if (!zonereset && lastzone + 1 != seat.zone) {
                for (int k = lastzone + 1; k < seat.zone; k++) {
                    for (int i = 0; i < seatings.get(k).col; i++) {
                        System.out.print("   \t"); // Digit
                    }
                }
                lastzone++;
            }
        }

        for (int i = lastSeat.y; i < seat.y - 1; i++) {
            System.out.print("000\t"); // Digit
        }
    }

}
