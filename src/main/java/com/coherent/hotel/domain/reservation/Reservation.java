package com.coherent.hotel.domain.reservation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Reservation representation class
 */
@Data
@Builder
public class Reservation implements Comparable<Reservation>{

    private int id;
    private String fullName;
    private int roomNumber;
    private List<LocalDate> reservationDates;

    @Override
    public String toString(){
        String datesStr = "";
        if (reservationDates.size() > 0) {
            StringBuilder reservationDateConcat = new StringBuilder();
            for (LocalDate reservationDate : reservationDates) {
                reservationDateConcat.append(reservationDate)
                        .append("@");
            }
         datesStr = reservationDateConcat.substring(0, reservationDateConcat.toString().length() -1);
        }

        return id + "," + fullName + "," + roomNumber + "," + datesStr;
    }

    @Override
    public int compareTo(Reservation o) {
        return 0;
    }
}