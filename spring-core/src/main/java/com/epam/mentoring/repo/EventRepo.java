package com.epam.mentoring.repo;

import com.epam.mentoring.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository <Event, Long> {
    List<Event> findByTitleContaining(String title);
    List<Event> findByDate(Date date);
}
