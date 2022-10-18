package executor;

public interface ITaskScheduler {

    boolean scheduleTask(TaskAction task);

    void shutdown();

    void mostrarStoredSchedules();

}
