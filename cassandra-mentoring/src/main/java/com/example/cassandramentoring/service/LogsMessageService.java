package com.example.cassandramentoring.service;

import com.example.cassandramentoring.entity.LogLevel;
import com.example.cassandramentoring.entity.LogMessage;
import com.example.cassandramentoring.entity.PerformanceMetrics;
import com.example.cassandramentoring.repository.LogMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LogsMessageService {
    @Autowired
    private LogMessageRepo repo;

    public LogMessage saveRecord(LogMessage logMessage) {
        LogMessage savedRecord = repo.save(logMessage);
        return savedRecord;
    }

    public LogMessage getRecordByLogLevelAndTimestamp(LogLevel level, Long timestamp) {
        Optional<LogMessage> logMessageOptional = repo.findByLogLevelAndTimestamp(level, timestamp);
        return logMessageOptional.orElse(new LogMessage());
    }

    public List<LogMessage> getAllRecords() {
        List<LogMessage> logMessageList = repo.findAll();
        return logMessageList;
    }

    public void deleteRecordByLogLevelAndTimestamp(LogLevel level, Long timestamp) {
        repo.deleteByLogLevelAndTimestamp(level, timestamp);
    }

    public PerformanceMetrics getPerformanceMetrics(int recordsCount) {
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setRecordsCount(recordsCount);

        List<LogMessage> messages = generateListOfRecords(recordsCount);

        long startTime = System.currentTimeMillis();
        saveListOfRecords(messages);
        long afterAddingTime = System.currentTimeMillis();
        metrics.setAddTime(afterAddingTime-startTime);
        metrics.setAddTimePerRecord((double) metrics.getAddTime()/ recordsCount);

        findListOfRecords(messages);
        long afterSearchTime = System.currentTimeMillis();
        metrics.setSearchTime((afterSearchTime-afterAddingTime));
        metrics.setSearchTimePerRecord((double) metrics.getSearchTime()/ recordsCount);

        deleteListOfRecords(messages);
        long afterDeleteTime = System.currentTimeMillis();
        metrics.setDeleteTime(afterDeleteTime-afterSearchTime);
        metrics.setDeleteTimePerRecord((double) metrics.getDeleteTime()/ recordsCount);

        return metrics;
    }

    private void deleteListOfRecords(List<LogMessage> messages) {
        for (LogMessage m: messages) {
            repo.deleteByLogLevelAndTimestamp(m.getLogLevel(), m.getTimestamp());
        }
    }

    private void findListOfRecords(List<LogMessage> messages) {
        for (LogMessage m: messages) {
            repo.findByLogLevelAndTimestamp(m.getLogLevel(), m.getTimestamp());
        }
    }

    private void saveListOfRecords(List<LogMessage> messages) {
        for (LogMessage m: messages) {
            repo.save(m);
        }
    }

    private List<LogMessage> generateListOfRecords(int recordsCount) {
        List<LogMessage> messages = new LinkedList<>();
        for (int i = 0; i < recordsCount; i++) {
            messages.add(generateRecord(i));
        }
        return messages;
    }

    private LogMessage generateRecord(int index) {
        LogLevel logLevelForIndex = LogLevel.values()[index%4];
        Long timestamp = (long) index;
        String message = "Generated message for test purposes number " + index;
        return new LogMessage(logLevelForIndex, timestamp, message);
    }
}
