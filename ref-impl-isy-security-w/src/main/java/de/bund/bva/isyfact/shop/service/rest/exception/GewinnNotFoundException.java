package de.bund.bva.isyfact.shop.service.rest.exception;

/**
 * This exception is thrown, if a Gewinn object which is expected to be in the database, cannot be found there.
 */
public class GewinnNotFoundException extends Exception {

    /**
     * The constructor of this exception
     */
    public GewinnNotFoundException() {
        super("gewinn not found");
    }
}
