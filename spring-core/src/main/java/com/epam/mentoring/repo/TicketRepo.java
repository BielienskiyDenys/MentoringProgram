package com.epam.mentoring.repo;

import com.epam.mentoring.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Long> {
    List<Ticket> findByEventIdAndPlace(Long eventId, int place);
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByEventId(Long eventId);
}
