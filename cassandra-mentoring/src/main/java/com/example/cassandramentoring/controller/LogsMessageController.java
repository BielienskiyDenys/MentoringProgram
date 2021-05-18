package com.example.cassandramentoring.controller;

import com.example.cassandramentoring.entity.LogLevel;
import com.example.cassandramentoring.entity.LogMessage;
import com.example.cassandramentoring.entity.PerformanceMetrics;
import com.example.cassandramentoring.service.LogsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsMessageController {
    @Autowired
    private LogsMessageService service;

    @PostMapping
    public ResponseEntity<LogMessage> saveRecord(@RequestBody LogMessage logMessage) {
        LogMessage savedRecord = service.saveRecord(logMessage);
        return new ResponseEntity<>(savedRecord, HttpStatus.OK);
    }

    @GetMapping(path = "{level}/{timestamp}")
    public ResponseEntity<LogMessage> getRecord(@PathVariable("level") LogLevel level, @PathVariable("timestamp")Long timestamp) {
        LogMessage logMessage = service.getRecordByLogLevelAndTimestamp(level, timestamp);
        return new ResponseEntity<>(logMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LogMessage>> getRecords() {
        List<LogMessage> logMessageList = service.getAllRecords();
        return new ResponseEntity<>(logMessageList, HttpStatus.OK);
    }

    @DeleteMapping(path="{level}/{timestamp}")
    public String deleteRecord(@PathVariable("level") LogLevel level, @PathVariable("timestamp")Long timestamp) {
        service.deleteRecordByLogLevelAndTimestamp(level, timestamp);
        return "Messages deleted.";
    }

    @GetMapping(path = "performance/{recordsCount}")
    public ResponseEntity<PerformanceMetrics> runPerformanceTest(@PathVariable("recordsCount") int recordsCount) {
        PerformanceMetrics metrics = service.getPerformanceMetrics(recordsCount);
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}