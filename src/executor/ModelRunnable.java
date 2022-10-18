package executor;

import java.io.Serializable;

public class ModelRunnable implements Runnable, Serializable {

    protected final TaskAction taskAction;
    protected final ITaskStorage taskStorage;

    protected Runnable runnable;

    public ModelRunnable(TaskAction task, ITaskStorage taskStorage) {
        this.taskAction = task;
        this.taskStorage = taskStorage;
    }

    public ModelRunnable(TaskAction task, ITaskStorage taskStorage, Runnable runnable) {
        this(task, taskStorage);
        this.runnable = runnable;
    }

    protected void cleanTask() {
        if (this.taskAction.isRunOnce()) {
            this.taskStorage.mostrarTask(this.taskAction.getId());
        }
    }

    @Override
    public void run() {
        if (this.runnable != null) {
            this.runnable.run();
            this.cleanTask();
        }
    }
}
