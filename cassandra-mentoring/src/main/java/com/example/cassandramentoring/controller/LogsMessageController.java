package com.example.cassandramentoring.controller;

import com.example.cassandramentoring.entity.LogLevel;
import com.example.cassandramentoring.entity.LogMessage;
import com.example.cassandramentoring.repository.LogMessageRepo;
import com.example.cassandramentoring.service.LogsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}
