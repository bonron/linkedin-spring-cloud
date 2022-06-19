package com.bontech.practice.linkedin.reservationservices;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationWebservices {
    private ReservationRepository reservationRepository;

    public ReservationWebservices(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping
    public Iterable<Reservation> getAllReservations(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (date == null) return reservationRepository.findAll();
        return reservationRepository.findReservationsByResDate(date);
    }

    @RequestMapping("/{id}")
    public Reservation getReservation(@PathVariable("id") long id) {
        return reservationRepository.findById(id).get();
    }
}
