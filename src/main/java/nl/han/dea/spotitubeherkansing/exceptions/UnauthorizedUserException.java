package nl.han.dea.spotitubeherkansing.exceptions;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
