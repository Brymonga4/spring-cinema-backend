package org.example.controller;

import org.example.dto.*;
import org.example.model.*;
import org.example.service.*;
import org.example.service.email.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;
    private final QRCodeService qrCodeService;
    public BookingController(BookingService bookingService,QRCodeService qrCodeService){
        this.bookingService = bookingService;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/bookings/validate")
    public ResponseEntity<List<FullTicketWithDetailsDTO>> validateBooking(@RequestBody TicketValidateDTO ticketValidateDTO ){


        List<FullTicketWithDetailsDTO> fullTickets = this.bookingService.validateBooking(ticketValidateDTO.getIdentifier());
        return ResponseEntity.ok(fullTickets);

    }

    @PostMapping(value = "decodeQR", consumes = "multipart/form-data")
    public ResponseEntity<String> decodeQR(@RequestPart("file") MultipartFile file){

        System.out.println(file);
        String decoded = this.qrCodeService.decodeQRCodeInImage(file);
        return ResponseEntity.ok(decoded);
    }

    @PostMapping(value = "/bookings/decodeQR/validate", consumes = "multipart/form-data")
    public ResponseEntity<List<FullTicketWithDetailsDTO>> decodeQRandValidate(@RequestPart("file") MultipartFile file){

        String decoded = this.qrCodeService.decodeQRCodeInImage(file);
        List<FullTicketWithDetailsDTO> fullTickets = this.bookingService.validateBooking(decoded);
        return ResponseEntity.ok(fullTickets);
    }
}
