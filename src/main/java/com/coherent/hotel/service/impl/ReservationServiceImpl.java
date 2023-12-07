package com.coherent.hotel.service.impl;

import com.coherent.hotel.domain.reservation.Reservation;
import com.coherent.hotel.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reservation service implementation class
 */
@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService, InitializingBean {

    private List<Reservation> reservations;
    private static final String RESERVATION_STORAGE = "src/main/resources/reservation-storage.txt";

    @Override
    public Reservation makeReservation(Reservation reservation) throws IOException {

        readAllReservations();
        reservation.setId(reservations.size() == 0 ? 1 :
                reservations.stream().sorted().toList().get(reservations.size() - 1).getId() + 1);
        Path path = Paths.get(RESERVATION_STORAGE);
        Files.write(path, (reservation +"\n").getBytes(), StandardOpenOption.APPEND);

        log.info("reservation added ok: {}", reservation);
        return reservation;
    }

    @Override
    public List<Reservation> readAllReservations() {
        this.initializeData();
        return reservations;
    }

    @Override
    public void updateReservation(Reservation reservation) throws IOException {

        File newFile = new File(RESERVATION_STORAGE);
        newFile.delete();
        newFile.createNewFile();

        reservations.forEach(res -> {
            if (res.getId() == reservation.getId()){
                res.setFullName(reservation.getFullName());
                res.setRoomNumber(reservation.getRoomNumber());
                res.setReservationDates(reservation.getReservationDates());
            }
            try {
                this.makeReservation(res);
            } catch (IOException e) {
                log.error("error updating data", e);
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initializeData() {
        try (Stream<String> lines = Files.lines(Paths.get(
                RESERVATION_STORAGE), StandardCharsets.UTF_8)) {
            reservations = new ArrayList<>();
            lines.forEach(lin -> {
                String[]  reservationArr = lin.split(",");
                List<LocalDate> dates = new ArrayList<>();

                if (reservationArr.length == 4){
                    String[] arrDates = reservationArr[3].split("@");
                    dates = Arrays.stream(arrDates)
                            .map(LocalDate::parse)
                            .collect(Collectors.toList());
                }

                reservations.add(Reservation.builder()
                        .id(Integer.parseInt(reservationArr[0]))
                        .fullName(reservationArr[1])
                        .roomNumber(Integer.parseInt(reservationArr[2]))
                        .reservationDates(dates)
                        .build());
            });
        }catch (IOException ex){
            log.error("Error reading reservations from storage: ", ex);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.initializeData();
        log.info("Reservation data loaded correctly...");
    }
}
