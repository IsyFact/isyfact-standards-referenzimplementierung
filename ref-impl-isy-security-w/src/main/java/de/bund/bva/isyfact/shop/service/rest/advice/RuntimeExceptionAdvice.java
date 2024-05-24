package de.bund.bva.isyfact.shop.service.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class defines the way how NullPointerExceptions are converted into ErrorMessage response objects.
 */
@RestControllerAdvice
public class RuntimeExceptionAdvice {

    /**
     * Returns an error message object corresponding to a RuntimeException that has been thrown.
     * It is essential here, that business code methods only have Throw-Statements with non-Runtime-Exceptions,
     * for which in each case extra advice classes are supplied.
     *
     * @param ex the exception to be mapped
     * @return an ErrorMessage with information extracted from this exception
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage runtimeExceptionHandler(RuntimeException ex) {
       return new ErrorMessage(ex.getClass().getName(), 500);
    }
}
