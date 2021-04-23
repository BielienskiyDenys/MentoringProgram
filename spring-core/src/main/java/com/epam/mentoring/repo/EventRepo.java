package com.epam.mentoring.repo;

import com.epam.mentoring.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {
    List<Event> findByTitleContaining(String title);
    List<Event> findByDate(Date date);
}
