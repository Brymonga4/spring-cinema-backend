package org.example.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.example.exception.Messages.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("message", "Datos de entrada inválidos - verifica los campos requeridos y sus restricciones");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append("; ");
        });
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exceptions.EmailAlreadyUsedException.class)
    public ResponseEntity<String> handleEmailAlreadyUsedException(Exceptions.EmailAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_EMAIL_ALREADY_EXISTS+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.NicknameAlreadyUsedException.class)
    public ResponseEntity<String> handleNicknameAlreadyUsedException(Exceptions.NicknameAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_NICKNAME_ALREADY_EXISTS+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.BookingNotFoundException.class)
    public ResponseEntity<String> handleBookingNotFoundException(Exceptions.BookingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_BOOKING_NOT_FOUND + ex.getMessage());
    }
    @ExceptionHandler(Exceptions.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(Exceptions.UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_USER_NOT_FOUND + ex.getMessage());
    }
    @ExceptionHandler(Exceptions.SeatNotFoundException.class)
    public ResponseEntity<String> handleSeatNotFoundException(Exceptions.SeatNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SEAT_NOT_FOUND + ex.getMessage());
    }
    @ExceptionHandler(Exceptions.ScreeningNotFoundException.class)
    public ResponseEntity<String> handleScreeningNotFoundException(Exceptions.ScreeningNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SCREENINIG_NOT_FOUND+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.RowNotFoundException.class)
    public ResponseEntity<String> handleRowNotFoundException(Exceptions.RowNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_ROW_NOT_FOUND+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.MovieNotFoundException.class)
    public ResponseEntity<String> handleMovieNotFoundException(Exceptions.MovieNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_MOVIE_NOT_FOUND+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.FileErrorException.class)
    public ResponseEntity<String> handleFileErrorException(Exceptions.FileErrorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_FILE_ERROR+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.EmptyFileNameTitleException.class)
    public ResponseEntity<String> handleEmptyFileNameTitleException(Exceptions.EmptyFileNameTitleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_EMPTY_FILENAME+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.ScreenNotFoundException.class)
    public ResponseEntity<String> handleScreenNotFoundException(Exceptions.ScreenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SCREEN_NOT_FOUND+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.CinemaNotFoundException.class)
    public ResponseEntity<String> handleCinemaNotFoundException(Exceptions.CinemaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_CINEMA_NOT_FOUND+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.ScreeningTimeException.class)
    public ResponseEntity<String> handleScreeningTimeException(Exceptions.ScreeningTimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SCREENING_TIME_ERROR+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.ScreeningTimeOverlapException.class)
    public ResponseEntity<String> handleScreeningTimeOverlapException(Exceptions.ScreeningTimeOverlapException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SCREENING_TIME_OVERLAP+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.SeatAlreadyExistsException.class)
    public ResponseEntity<String> handleSeatAlreadyExistsException(Exceptions.SeatAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SEAT_ALREADY_EXISTS+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.SeatsLimitException.class)
    public ResponseEntity<String> handleSeatsLimitException(Exceptions.SeatsLimitException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SEATS_LIMIT+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.ScreeninAlreadyStartedtException.class)
    public ResponseEntity<String> handleScreeninAlreadyStartedtException(Exceptions.ScreeninAlreadyStartedtException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SCREENINIG_ALREADY_STARTED+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.SeatsNotAvailableException.class)
    public ResponseEntity<String> handleSeatsNotAvailableException(Exceptions.SeatsNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_SEATS_NOT_AVAILABLE+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.EmailErrorException.class)
    public ResponseEntity<String> handleEmailErrorException(Exceptions.EmailErrorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_EMAIL_SENT_ERROR+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.PDFErrorException.class)
    public ResponseEntity<String> handlePDFErrorException(Exceptions.PDFErrorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_PDF_GENERATED_ERROR+ex.getMessage());
    }

    @ExceptionHandler(Exceptions.RecoverErrorException.class)
    public ResponseEntity<String> handleRecoverErrorException(Exceptions.RecoverErrorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_RECOVER_CODE_ERROR+ex.getMessage());
    }
    @ExceptionHandler(Exceptions.UserCantReviewMovieException.class)
    public ResponseEntity<String> handleUserCantReviewMovieException(Exceptions.UserCantReviewMovieException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MSG_USER_REVIEW_ERROR+ex.getMessage());
    }


}
