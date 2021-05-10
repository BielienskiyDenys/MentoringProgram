package com.epam.mongo.service;

import com.epam.mongo.entity.Subtask;
import com.epam.mongo.entity.Task;
import com.epam.mongo.repo.TaskRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepo repo;

    public void clearDatabase() {
        repo.deleteAll();
    }

    public Task getTaksById(long id) {
        return repo.findById(id).get();
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public List<Task> getAllOverdueTasks() {
        return repo.findByDeadlineLessThan(LocalDateTime.now());
    }

    public List<Task> getAllTasksForCategory(String category) {
        return repo.findByCategoryLike(category);
    }

    public List<Subtask> getAllSubtasksForCategory(String category) {
        List<Subtask> subtasks = new ArrayList<>();
        List<Task> tasks = repo.findByCategoryLike(category);
        for (Task t: tasks) {
            subtasks.addAll(t.getSubtasks());
        }
        return subtasks;
    }

    public Task addTask(Task task) {
        return repo.save(task);
    }

    public Task updateTask(Task task) {
        return repo.save(task);
    }

    public void deleteTaskById(Long id) {
        repo.deleteById(id);
    }

    public Task addSubtaskToTaskById(Long taskId, Subtask subtask) {
        Task task = repo.findById(taskId).get();
        task.getSubtasks().add(subtask);
        return repo.save(task);
    }

    public Task updateSubtaskToTaskById(Long taskId, Subtask subtask) {
        Task task = repo.findById(taskId).get();
        List<Subtask> subtasks = task.getSubtasks();
        List<Subtask> newSubtasks = subtasks.stream().map(sub -> {
            if(sub.getId().equals(subtask.getId())) {
                sub = subtask;
            }
            return sub;
        }).collect(Collectors.toList());
        task.setSubtasks(newSubtasks);
        return repo.save(task);
    }

    public Task deleteSubtaskById(Long taskId, Long subtaskId) {
        Task task = repo.findById(taskId).get();
        List<Subtask> subtasks = task.getSubtasks();
        List<Subtask> newSubtasks = subtasks.stream().filter(sub -> !sub.getId().equals(subtaskId)).collect(Collectors.toList());
        task.setSubtasks(newSubtasks);
        return repo.save(task);
    }

    public List<Task> findByDescription(String description) {
        return repo.findByDescriptionLike(description);
    }

    public List<Task> findBySubtaskName(String subtaskName) {
        return repo.findBySubtaskNameLike(subtaskName);
    }

    public void prefillDatabase() {
        List<Task> tasks = new LinkedList<>();

        List<Subtask> subtasks = new LinkedList<>();
        subtasks.add(new Subtask(1L, "Mine 100 pieces of Iron Ore", "Ore can be found in Scorched Plateau"));
        subtasks.add(new Subtask(2L, "Mine 40 pieces of Coal", "Coal can be found in Forgotten Mine"));
        subtasks.add(new Subtask(3L, "Mine or buy 3 Royal Ruby", "Random drop while mining or AH."));
        LocalDateTime createdAt = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline = LocalDateTime.of(2021, 06, 01, 00, 00);
        tasks.add(new Task(1L, createdAt, deadline, "Weapon", "Get resources for Great Sword", subtasks, "Gear"));

        List<Subtask> subtasks2 = new LinkedList<>();
        subtasks2.add(new Subtask(4L, "Loot Armor of Holy Knight", "Dropped by Alistar the Vile in Snowfang Keep"));
        subtasks2.add(new Subtask(5L, "Enchant with Angelic Guard", "Ask guild's enchanter to empower armour"));
        LocalDateTime createdAt2 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline2 = LocalDateTime.of(2021, 05, 15, 00, 00);
        tasks.add(new Task(2L, createdAt2, deadline2, "Armor", "Get an enchanted Armour of Holy Knight", subtasks2, "Gear"));

        List<Subtask> subtasks3 = new LinkedList<>();
        subtasks3.add(new Subtask(6L, "Get 130 Silver Sun tokens", "Dropped by skeletons in Scorched Plateau"));
        subtasks3.add(new Subtask(7L, "Trade tokens into Shield of Blinding Light", "Quartermaster of Silver Sun is available from 15:00 to 20:30 in their camp"));
        LocalDateTime createdAt3 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline3 = LocalDateTime.of(2021, 05, 15, 00, 00);
        tasks.add(new Task(3L, createdAt3, deadline3, "Shield", "Get a Shield of Blinding Light", subtasks3, "Gear"));

        List<Subtask> subtasks4 = new LinkedList<>();
        subtasks4.add(new Subtask(8L, "Buy the Winged Helmet in a Fairy Carnival", "Costs 6g 70s"));
        LocalDateTime createdAt4 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline4 = LocalDateTime.of(2021, 05, 07, 00, 00);
        tasks.add(new Task(4L, createdAt4, deadline4, "Helmet", "Obtain a Winged Helmet", subtasks4, "Gear"));

        List<Subtask> subtasks5 = new LinkedList<>();
        subtasks5.add(new Subtask(9L, "Gather 100 Roots of Blindweed", "Found at Scorched Plateau"));
        subtasks5.add(new Subtask(10L, "Gather 75 Magerose", "Found at Forgotten Camp"));
        subtasks5.add(new Subtask(11L, "Gather 120 Snowbloom", "Found at Frozen Plains"));
        LocalDateTime createdAt5 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline5 = LocalDateTime.of(2021, 05, 20, 00, 00);
        tasks.add(new Task(5L, createdAt5, deadline5, "Raid Elixirs", "Prepare elixirs for raiding", subtasks5, "Buffs"));

        List<Subtask> subtasks6 = new LinkedList<>();
        subtasks6.add(new Subtask(12L, "Earn 5g for guid clerics", "Farm anywhere"));
        LocalDateTime createdAt6 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline6 = LocalDateTime.of(2021, 05, 04, 00, 00);
        tasks.add(new Task(6L, createdAt6, deadline6, "Raid Clerics", "Get gold for guild clerics", subtasks6, "Buffs"));

        List<Subtask> subtasks7 = new LinkedList<>();
        LocalDateTime createdAt7 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline7 = LocalDateTime.of(2021, 05, 07, 00, 00);
        tasks.add(new Task(7L, createdAt7, deadline7, "Hermit's Blessing", "Talk to Lone Hermit in Swamps Of Sorrow", subtasks7, "Buffs"));

        List<Subtask> subtasks8 = new LinkedList<>();
        subtasks8.add(new Subtask(13L, "Buy Red Wood Fishing Pole", "Vendor can be found on Dark Shore from 23:00 to 00:00"));
        subtasks8.add(new Subtask(14L, "Register in competition", "Registration NPS can be found in Stardew Village"));
        LocalDateTime createdAt8 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline8 = LocalDateTime.of(2021, 05, 15, 00, 00);
        tasks.add(new Task(8L, createdAt8, deadline8, "Fishing Event", "Take part in Fishing Contest", subtasks8, "Misc"));

        List<Subtask> subtasks9 = new LinkedList<>();
        subtasks9.add(new Subtask(15L, "Buy Tuxedo", "Vendor can be found in Capital City"));
        subtasks9.add(new Subtask(16L, "Buy Whine Shirt", "Vendor can be found at Stardew Village"));
        subtasks9.add(new Subtask(17L, "Buy Black Boots", "Vendor can be found at Elven District"));
        LocalDateTime createdAt9 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline9 = LocalDateTime.of(2021, 06, 15, 00, 00);
        tasks.add(new Task(9L, createdAt9, deadline9, "Tuxedo", "Obtain Black Tuxedo for social events", subtasks9, "Misc"));

        List<Subtask> subtasks10 = new LinkedList<>();
        subtasks10.add(new Subtask(18L, "Mine 20 pieces of Gold Ore", "Ore can be found in Dark Mine"));
        subtasks10.add(new Subtask(19L, "Mine 40 pieces of Silver Ore", "Coal can be found in a mine near Hogsmith Village"));
        subtasks10.add(new Subtask(20L, "Mine or buy 1 Sun Diamond", "Random drop while mining or AH."));
        LocalDateTime createdAt10 = LocalDateTime.of(2021, 05, 01, 19, 00);
        LocalDateTime deadline10 = LocalDateTime.of(2021, 06, 10, 00, 00);
        tasks.add(new Task(10L, createdAt10, deadline10, "Solar Diadem", "Ger resources for Solar Diadem", subtasks10, "Misc"));

        repo.saveAll(tasks);
    }
}
