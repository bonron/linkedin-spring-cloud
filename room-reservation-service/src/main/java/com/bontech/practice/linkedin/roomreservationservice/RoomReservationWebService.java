package com.bontech.practice.linkedin.roomreservationservice;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room-reservations")
public class RoomReservationWebService {

    private final RoomClient roomClient;
    private final GuestClient guestClient;
    private final ReservationClient reservationClient;

    public RoomReservationWebService(RoomClient roomClient, GuestClient guestClient, ReservationClient reservationClient) {
        super();
        this.roomClient = roomClient;
        this.guestClient = guestClient;
        this.reservationClient = reservationClient;
    }

    @GetMapping
    public List<RoomReservation> getRoomReservations(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        var guests = this.guestClient.getAllGuests().stream().collect(Collectors.groupingBy(Guest::getId));
        var rooms = this.roomClient.getAllRooms().stream().collect(Collectors.groupingBy(Room::getRoomId));
        return this.reservationClient.getAllReservations(date).stream().map(reservation -> {
            var roomReservation = new RoomReservation();
            var room = rooms.get(reservation.getRoomId()).get(0);
            var guest = guests.get(reservation.getGuestId()).get(0);
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setGuestId(guest.getId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setDate(reservation.getResDate());
            return roomReservation;
        }).toList();
    }

}
