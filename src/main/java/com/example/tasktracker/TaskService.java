package com.example.tasktracker;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Task> findAll() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::getId))
                .toList();
    }

    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public Task create(Task input) {
        long id = nextId.getAndIncrement();
        Task task = new Task(id, input.getTitle().trim(),
                input.getDescription() == null ? "" : input.getDescription().trim(),
                input.getStatus() == null ? TaskStatus.TODO : input.getStatus());
        tasks.put(id, task);
        return task;
    }

    public Task update(Long id, Task input) {
        if (!tasks.containsKey(id)) {
            throw new TaskNotFoundException(id);
        }
        Task updated = new Task(id, input.getTitle().trim(),
                input.getDescription() == null ? "" : input.getDescription().trim(),
                input.getStatus() == null ? TaskStatus.TODO : input.getStatus());
        tasks.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        if (tasks.remove(id) == null) {
            throw new TaskNotFoundException(id);
        }
    }
}
