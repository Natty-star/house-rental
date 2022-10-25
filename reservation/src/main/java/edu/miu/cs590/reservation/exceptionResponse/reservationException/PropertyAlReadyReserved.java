package edu.miu.cs590.reservation.exceptionResponse.reservationException;

public class PropertyAlReadyReserved extends RuntimeException{
    public PropertyAlReadyReserved(String message){
        super(message);
    }
}
