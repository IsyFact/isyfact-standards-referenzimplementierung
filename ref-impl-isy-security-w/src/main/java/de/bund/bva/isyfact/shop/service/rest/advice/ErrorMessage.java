package de.bund.bva.isyfact.shop.service.rest.advice;

/**
 * Instances of ErrorMessages are created in Advice Classes.
 * @param message
 * @param status
 */
public record ErrorMessage(String message, int status) {

    /**
     * Constructor
     */
    public ErrorMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
