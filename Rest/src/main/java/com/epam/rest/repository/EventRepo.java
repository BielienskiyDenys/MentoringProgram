package com.epam.rest.repository;

import com.epam.rest.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    public List<Event> findAllByTitleContaining(String title);
}
