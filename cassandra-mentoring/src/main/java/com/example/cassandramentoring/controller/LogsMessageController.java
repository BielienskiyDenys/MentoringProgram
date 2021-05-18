package com.example.cassandramentoring.controller;

import com.example.cassandramentoring.entity.LogLevel;
import com.example.cassandramentoring.entity.LogMessage;
import com.example.cassandramentoring.entity.PerformanceMetrics;
import com.example.cassandramentoring.repository.LogMessageRepo;
import com.example.cassandramentoring.service.LogsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/logs")
public class LogsMessageController {

//    @Autowired
//    private LogsMessageService service;

    @Autowired
    private LogMessageRepo repo;

    @PostMapping
    public ResponseEntity<LogMessage> saveRecord(@RequestBody LogMessage logMessage) {
        LogMessage savedRecord = repo.save(logMessage);
        return new ResponseEntity<>(savedRecord, HttpStatus.OK);
    }

    @GetMapping(path = "{level}/{timestamp}")
    public ResponseEntity<LogMessage> getRecord(@PathVariable("level") LogLevel level, @PathVariable("timestamp")Long timestamp) {
        Optional<LogMessage> logMessageOptional = repo.findByLogLevelAndTimestamp(level, timestamp);
        return new ResponseEntity<>(logMessageOptional.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LogMessage>> getRecords() {
        List<LogMessage> logMessageList = repo.findAll();
        return new ResponseEntity<>(logMessageList, HttpStatus.OK);
    }

    @DeleteMapping(path="{level}/{timestamp}")
    public String deleteRecord(@PathVariable("level") LogLevel level, @PathVariable("timestamp")Long timestamp) {
        repo.deleteByLogLevelAndTimestamp(level, timestamp);
        return "Messages deleted.";
    }

    @GetMapping(path = "performance/{recordsCount}")
    public ResponseEntity<PerformanceMetrics> runPerformanceTest(@PathVariable("recordsCount") int recordsCount) {
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setRecordsCount(recordsCount);

        List<LogMessage> messages = new LinkedList<>();
        for (int i = 0; i < recordsCount; i++) {
            messages.add(generateRecord(i));
        }
        List<LogMessage> messagesToSearch = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            int randomIndex = getRandomNumberInRange(0, messages.size());
            LogMessage randomRecord = messages.get(randomIndex);
            messagesToSearch.add(randomRecord);
        }

        long startTime = System.currentTimeMillis();

        for (LogMessage m: messages) {
            repo.save(m);
        }
        long afterAddingTime = System.currentTimeMillis();
        metrics.setAddTime(afterAddingTime-startTime);
        metrics.setAddTimePerRecord((double) metrics.getAddTime()/recordsCount);

        for (LogMessage m: messages) {
            repo.findByLogLevelAndTimestamp(m.getLogLevel(), m.getTimestamp());
        }
        long afterSearchTime = System.currentTimeMillis();
        metrics.setSearchTime((afterSearchTime-afterAddingTime));
        metrics.setSearchTimePerRecord((double) metrics.getSearchTime()/recordsCount);

        for (LogMessage m: messages) {
            repo.deleteByLogLevelAndTimestamp(m.getLogLevel(), m.getTimestamp());
        }
        long afterDeleteTime = System.currentTimeMillis();
        metrics.setDeleteTime(afterDeleteTime-afterSearchTime);
        metrics.setDeleteTimePerRecord((double) metrics.getDeleteTime()/recordsCount);

        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    private LogMessage generateRecord(int index) {
        LogLevel logLevelForIndex = LogLevel.values()[index%4];
        Long timestamp = (long) index;
        String message = "Generated message for test purposes number " + index;
        return new LogMessage(logLevelForIndex, timestamp, message);
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
