package org.example.exception;

public class Exceptions {

    public static class EmailAlreadyUsedException extends RuntimeException {
        public EmailAlreadyUsedException(String message) {
            super(message);
        }
    }


    public static class NicknameAlreadyUsedException extends RuntimeException {
        public NicknameAlreadyUsedException(String message) {
            super(message);
        }
    }

    public static class BookingNotFoundException extends RuntimeException {
        public BookingNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class SeatNotFoundException extends RuntimeException {
        public SeatNotFoundException(String message) {
            super(message);
        }
    }
    public static class ScreeningNotFoundException extends RuntimeException {
        public ScreeningNotFoundException(String message) {
            super(message);
        }
    }
    public static class RowNotFoundException extends RuntimeException {
        public RowNotFoundException(String message) {
            super(message);
        }
    }

    public static class MovieNotFoundException extends RuntimeException {
        public MovieNotFoundException(String message) {
            super(message);
        }
    }

    public static class FileErrorException extends RuntimeException {
        public FileErrorException(String message) {
            super(message);
        }
    }

    public static class EmptyFileNameTitleException extends RuntimeException {
        public EmptyFileNameTitleException(String message) {
            super(message);
        }
    }

    public static class ScreenNotFoundException extends RuntimeException {
        public ScreenNotFoundException(String message) {
            super(message);
        }
    }

    public static class CinemaNotFoundException extends RuntimeException {
        public CinemaNotFoundException(String message) {
            super(message);
        }
    }

    public static class ScreeningTimeException extends RuntimeException {
        public ScreeningTimeException(String message) {
            super(message);
        }
    }

    public static class ScreeningTimeOverlapException extends RuntimeException {
        public ScreeningTimeOverlapException(String message) {
            super(message);
        }
    }

    public static class SeatAlreadyExistsException extends RuntimeException {
        public SeatAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class SeatsLimitException extends RuntimeException {
        public SeatsLimitException(String message) {
            super(message);
        }
    }

    public static class ScreeninAlreadyStartedtException extends RuntimeException {
        public ScreeninAlreadyStartedtException(String message) {
            super(message);
        }
    }

    public static class SeatsNotAvailableException extends RuntimeException {
        public SeatsNotAvailableException(String message) {
            super(message);
        }
    }

    public static class EmailErrorException extends RuntimeException {
        public EmailErrorException(String message) {
            super(message);
        }
    }

    public static class PDFErrorException extends RuntimeException {
        public PDFErrorException(String message) {
            super(message);
        }
    }

    public static class RecoverErrorException extends RuntimeException {
        public RecoverErrorException(String message) {
            super(message);
        }
    }

    public static class UserCantReviewMovieException extends RuntimeException {
        public UserCantReviewMovieException(String message) {
            super(message);
        }
    }
}
