import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
// Represents a single task with a name, due date, and priority
class Task {
    private String name;
    private LocalDate dueDate;
    private String priority;

    // Constructor to initialize a Task object
    public Task(String name, LocalDate dueDate, String priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }
    // Returns a string representation of the Task
    @Override
    public String toString() {
        return "Task: " + name + ", Due Date: " + dueDate + ", Priority: " + priority;
    }
}

// Manages a list of tasks and operations on them
class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    // Reads tasks from a markdown file and adds them to the task list
    public void readTasksFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("- [ ]")) { // Checks if the line represents a task
                    Task task = parseLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    // Parses a line from the markdown file to create a Task object
    private Task parseLine(String line) {
        try {
            String[] parts = line.split("\\(", 2);
            String name = parts[0].replace("- [ ]", "").trim();
            String[] details = parts[1].replace(")", "").split(",");
            LocalDate dueDate = LocalDate.parse(details[0].trim());
            String priority = details[1].trim();
            return new Task(name, dueDate, priority);
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            return null;
        }
    }
    // Sorts tasks by priority, then by date within each priority group
    public void sortAndGroupTasks() {
        Map<String, List<Task>> groupedTasks = new TreeMap<>();
        for (Task task : tasks) {
            groupedTasks.computeIfAbsent(task.getPriority(), k -> new ArrayList<>()).add(task);
        }

        tasks.clear();
        for (List<Task> taskList : groupedTasks.values()) {
            taskList.sort(Comparator.comparing(Task::getDueDate));
            tasks.addAll(taskList);
        }
    }

    public void displayTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}