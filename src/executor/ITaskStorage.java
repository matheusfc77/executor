package executor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ITaskStorage {

    boolean put(TaskAction task) throws IOException;

    List<TaskAction> findAllTasks() throws IOException, ClassNotFoundException;
    
    Optional<TaskAction> findTask(String id);

    void mostrarTask(String id);

}
