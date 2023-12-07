package com.coherent.hotel.service;

import com.coherent.hotel.domain.reservation.Reservation;

import java.io.IOException;
import java.util.List;

/**
 * Reservation service description
 */
public interface ReservationService {

    Reservation makeReservation(Reservation reservation) throws IOException;
    List<Reservation> readAllReservations();

    void updateReservation(Reservation reservation) throws IOException;

    void initializeData() throws IOException;
}
