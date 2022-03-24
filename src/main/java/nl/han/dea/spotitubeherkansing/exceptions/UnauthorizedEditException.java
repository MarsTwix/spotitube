package nl.han.dea.spotitubeherkansing.exceptions;

public class UnauthorizedEditException extends RuntimeException{
    public UnauthorizedEditException(String message) {
        super(message);
    }
}
