package com.coherent.hotel.domain.converter;

import com.coherent.hotel.domain.dto.ReservationDTO;
import com.coherent.hotel.domain.reservation.Reservation;

import java.util.function.Function;

/**
 *
 * DTO converter function from Reservation domain object to DTO
 */
public class ReservationToReservationDTOConverter implements Function<Reservation, ReservationDTO> {
    @Override
    public ReservationDTO apply(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .roomNumber(reservation.getRoomNumber())
                .fullName(reservation.getFullName())
                .reservationDate1(reservation.getReservationDates().size() > 0 ?
                reservation.getReservationDates().get(0).toString() : "")
                .reservationDate2(reservation.getReservationDates().size() > 1 ?
                        reservation.getReservationDates().get(1).toString() : "")
                .reservationDate3(reservation.getReservationDates().size() > 2 ?
                        reservation.getReservationDates().get(2).toString() : "")
                .build();
    }
}
