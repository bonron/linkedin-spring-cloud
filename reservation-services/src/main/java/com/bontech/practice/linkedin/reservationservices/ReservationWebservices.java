package com.bontech.practice.linkedin.reservationservices;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationWebservices {
    private ReservationRepository reservationRepository;

    public ReservationWebservices(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping
    public Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Reservation getReservation(@PathVariable("id") long id){
        return reservationRepository.findById(id).get();
    }
}
