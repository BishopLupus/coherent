package com.coherent.hotel.controller;

import com.coherent.hotel.domain.converter.ReservationDTOToReservationConverter;
import com.coherent.hotel.domain.converter.ReservationToReservationDTOConverter;
import com.coherent.hotel.domain.dto.ReservationDTO;
import com.coherent.hotel.domain.dto.UserDTO;
import com.coherent.hotel.domain.reservation.Reservation;
import com.coherent.hotel.service.ReservationService;
import com.coherent.hotel.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Reservation controller class
 */
@Slf4j
@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SecurityService securityService;
    private String errorMessage;
    private String okMessage;
    private ReservationToReservationDTOConverter reservationToReservationDTOConverter = new ReservationToReservationDTOConverter();
    private ReservationDTOToReservationConverter reservationDTOToReservationConverter = new ReservationDTOToReservationConverter();
    @GetMapping("/index")
    public String indexView(UserDTO userDTO, BindingResult bindingResult, Model model) {
        return "index";
    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || !securityService.loginUser(userDTO.getUsername(), userDTO.getPassword())){
            model.addAttribute("errorMessage", "Bad credentials. Try again please");
            return "index";
        }
            return "redirect:/listAll";
    }

    @GetMapping("/add-reservation")
    public String addReservationView(ReservationDTO reservationDTO, BindingResult bindingResult, Model model) {
        return "addReservation";
    }

    @PostMapping("/save-reservation")
    public String saveReservation(ReservationDTO reservationDTO, BindingResult bindingResult, Model model) {
        Reservation reservation;
        try {
            reservation = reservationDTOToReservationConverter.apply(reservationDTO);
            reservationService.makeReservation(reservation);
            okMessage = "Reservation added correctly.";
        }catch (Exception e){
            log.error("Error processing a reservation: ", e);
            errorMessage = "Something went wrong, please try again later.";
        }
        return "redirect:/listAll";
    }

    @GetMapping("/listAll")
    public String listReservations(Reservation reservation, BindingResult bindingResult, Model model){
        model.addAttribute("reservations", reservationService.readAllReservations());
        if(okMessage != null)
            model.addAttribute("okMessage", okMessage);
        if (errorMessage != null)
            model.addAttribute("errorMessage", errorMessage);
        return "listAll";
    }

    @GetMapping("/update/{id}")
    public String updateReservationView(@PathVariable("id") int id, ReservationDTO reservationDTO,
                                         BindingResult bindingResult, Model model){
        model.addAttribute("reservationDTO",
                reservationToReservationDTOConverter.apply(reservationService.readAllReservations()
                .stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .get()));
        return "update";
    }

    @PostMapping("/update-reservation")
    public String updateReservation(ReservationDTO reservationDTO, BindingResult bindingResult, Model model) {
        Reservation reservation;
        try{
        reservation = reservationDTOToReservationConverter.apply(reservationDTO);
        reservationService.updateReservation(reservation);
        okMessage = "Reservation updated correctly.";
        }catch (Exception e){
            log.error("Error processing a reservation: ", e);
            errorMessage = "Something went wrong, please try again later.";
        }
        return "redirect:/listAll";
    }
}
