public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.readTasksFromFile("tasks.md");
        manager.sortAndGroupTasks();
        manager.displayTasks();
    }
}