package com.example.cassandramentoring.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table("logs")
public class LogMessage {
//public class LogMessage implements Serializable {
//    private static final long serialVersionUID = 1L;

    @PrimaryKeyColumn(name = "log_level", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private LogLevel logLevel;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long timestamp;

    private String message;
}
