package com.epam.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
    @Id
    private long id;
    private LocalDateTime dateOfCreation;
    private LocalDateTime deadline;
    private String taskName;
    private String description;
    private List<Subtask> subtasks;
    private String category;

    @Override
    public String toString() {
        return  "\n***** " + taskName + " *****" +
                "\n  task id = " + id +
                "\n  dateOfCreation = " + dateOfCreation +
                "\n  deadline = " + deadline +

                "\n  description = '" + description + "'" +
                "\n  subtasks = " + subtasks +
                "\n  category = '" + category + "'" +
                "\n***************";
    }
}
