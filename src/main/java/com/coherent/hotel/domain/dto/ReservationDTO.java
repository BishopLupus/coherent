package com.coherent.hotel.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationDTO {

    private int id;
    private String fullName;
    private int roomNumber;
    private String reservationDate1;
    private String reservationDate2;
    private String reservationDate3;
}
