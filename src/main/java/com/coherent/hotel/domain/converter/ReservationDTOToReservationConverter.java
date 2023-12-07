package com.coherent.hotel.domain.converter;

import com.coherent.hotel.domain.dto.ReservationDTO;
import com.coherent.hotel.domain.reservation.Reservation;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * DTO converter function from ReservationDTO to Reservation domain object
 */
public class ReservationDTOToReservationConverter implements Function<ReservationDTO, Reservation> {
    @Override
    public Reservation apply(ReservationDTO reservationDTO) {

        List<LocalDate> dates = new ArrayList<>();
        if(Strings.isNotBlank(reservationDTO.getReservationDate1()))
            dates.add(LocalDate.parse(reservationDTO.getReservationDate1()));
        if(Strings.isNotBlank(reservationDTO.getReservationDate2()))
            dates.add(LocalDate.parse(reservationDTO.getReservationDate2()));
        if(Strings.isNotBlank(reservationDTO.getReservationDate3()))
            dates.add(LocalDate.parse(reservationDTO.getReservationDate3()));

        return Reservation.builder()
                .id(reservationDTO.getId())
                .fullName(reservationDTO.getFullName())
                .roomNumber(reservationDTO.getRoomNumber())
                .reservationDates(dates)
                .build();
    }
}
