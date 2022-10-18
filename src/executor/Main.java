package executor;

public class Main {
	public static void main(String[] args) {
        ITaskStorage taskStorage = new TaskStorage("ts");
        ITaskScheduler scheduler = new TaskScheduler(taskStorage, 10);

        TaskAction task = TaskAction.ofScript("script.js", "1s", true, true);

        scheduler.scheduleTask(task);

        scheduler.mostrarStoredSchedules();
    }
}
