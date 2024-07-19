package de.bund.bva.isyfact.shop.service.rest.advice;

/**
 * Instances of ErrorMessages are created in Advice Classes.
 */
public class ErrorMessage {

    private String message;
    private int status;

    /**
     * Constructor
     */
    public ErrorMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
