package com.example.cassandramentoring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("logs")
public class LogMessage {
    @PrimaryKeyColumn(name = "log_level", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private LogLevel logLevel;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long timestamp;

    private String message;
}
