package com.epam.module06boottask2.repository;

import com.epam.module06boottask2.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    public List<Event> findAllByTitleContaining(String title);
}
