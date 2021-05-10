package com.epam.mongo.repo;

import com.epam.mongo.entity.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface TaskRepo extends MongoRepository<Task, Long> {
//    @Override
//    <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable);

    List<Task> findByDeadlineLessThan(LocalDateTime deadline);
    List<Task> findByCategoryLike(String category);
    List<Task> findByDescriptionLike(String description);
    @Query("{ 'subtasks.subtaskName' : ?0 }")
    List<Task> findBySubtaskNameLike(String subtaskName);

}
