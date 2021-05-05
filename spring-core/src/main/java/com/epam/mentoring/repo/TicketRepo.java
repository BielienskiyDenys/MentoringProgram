package com.epam.mentoring.repo;

import com.epam.mentoring.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TicketRepo extends MongoRepository<Ticket, Long> {
    List<Ticket> findByEventIdAndPlace(Long eventId, int place);
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByEventId(Long eventId);
}
