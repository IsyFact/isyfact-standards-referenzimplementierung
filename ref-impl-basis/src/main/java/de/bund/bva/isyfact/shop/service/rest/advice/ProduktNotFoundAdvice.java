package de.bund.bva.isyfact.shop.service.rest.advice;

import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * This class defines the way how ProduktNotFoundExceptions are converted into ErrorMessage response objects.
 */
@RestControllerAdvice
class ProduktNotFoundAdvice {
    /**
     * Returns an error message object corresponding to a ProduktNotFoundException that has been thrown.
     *
     * @param ex the exception to be mapped
     * @return an ErrorMessage with information extracted from this exception
     */
    @ResponseBody
    @ExceptionHandler(ProduktNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage produktNotFoundHandler(ProduktNotFoundException ex) {
        return new ErrorMessage(ex.getMessage(), 404);
    }
}