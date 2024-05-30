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




}
