package de.bund.bva.isyfact.shop.service.rest.exception;

/**
 * This exception is thrown, if a Produkt which is expected to be in the database, cannot be found there.
 */
public class UmsatzNotFoundException extends Exception {

    /**
     * The constructor of this exception
     */
    public UmsatzNotFoundException() {
        super("umsatz not found");
    }
}
