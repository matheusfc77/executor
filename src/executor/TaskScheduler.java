package executor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskScheduler implements ITaskScheduler {

    private final ITaskStorage storage;
    private final ScheduledExecutorService scheduler;

    public TaskScheduler(ITaskStorage storage, int numThreads) {
        this.storage = storage;
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
        this.onInit();
    }


    public boolean scheduleTask(TaskAction task) {
        ModelRunnable taskToBeScheduled;
        taskToBeScheduled = new ExecuteScript(task, this.storage);
        System.out.println("Agendando task");
        try {
            if (task.isRunOnce()) {
                this.scheduler.schedule(taskToBeScheduled, task.getTime(), task.getUnit());
            } else {
                this.scheduler.scheduleAtFixedRate(taskToBeScheduled, 0, task.getTime(), task.getUnit());
            }
            if (task.shouldPersist()) {
                this.storage.put(task);
            }
        } catch (Exception ex) {
            System.out.printf("Falha na task {} {}", taskToBeScheduled, ex);
            return false;
        }
        return true;
    }

    private void onInit() {
        try {
            List<TaskAction> storedTasks = this.storage.findAllTasks();
            storedTasks.forEach(this::scheduleTask);
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Erro ao agendar as tasks existentes", e);
        }
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

    public void mostrarStoredSchedules() {
        try {
            List<TaskAction> tasks = this.storage.findAllTasks();
            System.out.println("Tasks agendadas: " + tasks.size());
            tasks.forEach(task -> {
                System.out.println("Task | Tempo: " + task.getTime() + " | " + task.getUnit());
            });
        } catch (IOException | ClassNotFoundException e) {
        	System.out.printf("Erro ao carregar as tasks agendadas", e);
        }
    }
}
