package com.example.cassandramentoring.repository;

import com.example.cassandramentoring.entity.LogLevel;
import com.example.cassandramentoring.entity.LogMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogMessageRepo extends CrudRepository<LogMessage, Serializable> {
    Optional<LogMessage> findByLogLevelAndTimestamp(LogLevel level, Long timestamp);
    List<LogMessage> findByLogLevel(LogLevel level);
    List<LogMessage> findAll();
    void deleteByLogLevelAndTimestamp(LogLevel level, Long timestamp);
}
