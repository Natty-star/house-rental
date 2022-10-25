package edu.miu.cs590.reservation.exceptionResponse;

import edu.miu.cs590.reservation.exceptionResponse.reservationException.PropertyAlReadyReserved;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Controller
@ControllerAdvice
public class ResponseEntityException  extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            PropertyAlReadyReserved.class
    })
    public ResponseEntity<ExceptionResponse> PropertyAlreadyReserved(Exception e, WebRequest webRequest){
        ExceptionResponse message = new ExceptionResponse(
                new Date(), HttpStatus.BAD_REQUEST, e.getMessage()
        );
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

}
