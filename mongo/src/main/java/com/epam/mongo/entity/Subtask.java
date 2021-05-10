package com.epam.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Subtask {
    @Id
    private Long id;
    private String subtaskName;
    private String description;

    @Override
    public String toString() {
        return "\n    " + subtaskName  +
                "\n      subtask id = " + id +
                "\n      subtask description = '" + description + "'";
    }
}
