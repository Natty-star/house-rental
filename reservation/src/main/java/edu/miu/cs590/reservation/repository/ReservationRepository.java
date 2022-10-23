package edu.miu.cs590.reservation.repository;

import edu.miu.cs590.reservation.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation,String> {
    List<Reservation> findByUserEmail(String useEmail);
}
