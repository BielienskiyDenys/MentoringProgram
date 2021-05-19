package com.epam.mongo;

import com.epam.mongo.entity.Subtask;
import com.epam.mongo.entity.Task;
import com.epam.mongo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@SpringBootApplication
public class MongoApplication
		implements CommandLineRunner {


	@Autowired
	private TaskService taskService;

	private static Logger LOG = LoggerFactory
			.getLogger(MongoApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(MongoApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) {
		System.out.println("====== DEMO RUN START =====");
		taskService.clearDatabase();
		taskService.prefillDatabase();

		searchAllEnteties();
		searchEntetiesByTimeFieldAccordingToNow();
		searchEntitiesByField();
		searchChildEntitiesByField();
		runCrudOperationsForEnities();
		runCrudOperationsForChildEnteties();
		searchBySecondaryField();
		searchByChildObjectField();

		System.out.println("====== DEMO RUN END =====");
	}

	private void searchAllEnteties() {
		System.out.println("----Task1----");
		System.out.println(taskService.getAllTasks());
	}

	private void searchEntetiesByTimeFieldAccordingToNow() {
		System.out.println("----Task2----");
		System.out.println(taskService.getAllOverdueTasks());
	}

	private void searchEntitiesByField() {
		System.out.println("----Task3----");
		System.out.println(taskService.getAllTasksForCategory("Misc"));
	}

	private void searchChildEntitiesByField() {
		System.out.println("----Task4----");
		System.out.println(taskService.getAllSubtasksForCategory("Gear"));
	}

	private void runCrudOperationsForEnities() {
		System.out.println("----Task5----");
		List<Subtask> subtasks = new LinkedList<>();
		subtasks.add(new Subtask(21L, "Do some stuff", "Some description"));
		subtasks.add(new Subtask(21L, "Do some more stuff", "Some description"));
		LocalDateTime createdAt = LocalDateTime.of(2021, 05, 01, 19, 00);
		LocalDateTime deadline = LocalDateTime.of(2021, 06, 01, 00, 00);
		Task newTask = new Task(11L, createdAt, deadline, "Some Task", "Test Task", subtasks, "Some Category");

		System.out.println("Before adding task: " + taskService.getAllTasks().size());
		taskService.addTask(newTask);
		System.out.println("After adding task: " + taskService.getAllTasks().size());
		newTask.setCategory("New Category");
		taskService.updateTask(newTask);
		Task receivedTask = taskService.getTaksById(newTask.getId());
		System.out.println(receivedTask);
		System.out.println("After updating task: " + taskService.getAllTasks().size());
		taskService.deleteTaskById(newTask.getId());
		System.out.println("After deleting task: " + taskService.getAllTasks().size());
	}

	private void runCrudOperationsForChildEnteties() {
		System.out.println("----Task6----");
		Subtask newSubtask = new Subtask(30L, "Testing subtasks", "Added new subtask to existing task");
		System.out.println("Task before adding subtask: "+taskService.getTaksById(6L));
		taskService.addSubtaskToTaskById(6L, newSubtask);
		System.out.println("Task after adding subtask: "+taskService.getTaksById(6L));
		newSubtask.setDescription("Brand new updated description");
		taskService.updateSubtaskToTaskById(6L, newSubtask);
		System.out.println("Task after updating subtask: "+taskService.getTaksById(6L));
		taskService.deleteSubtaskById(6L, newSubtask.getId());
		System.out.println("Task after deleting subtask: "+taskService.getTaksById(6L));
	}

	private void searchBySecondaryField() {
		System.out.println("----Task7----");
		System.out.println(taskService.findByDescription("Obtain"));
	}

	private void searchByChildObjectField() {
		System.out.println("----Task8----");
		taskService.findBySubtaskName("Gather 75 Magerose");
	}



}
