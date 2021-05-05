package com.epam.mentoring.repo;

import com.epam.mentoring.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface EventRepo extends MongoRepository<Event, Long> {
    List<Event> findByTitleContaining(String title);
    List<Event> findByDate(Date date);
}
