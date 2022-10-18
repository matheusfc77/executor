package executor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TaskStorage implements ITaskStorage {

    private final String storagePath;

    public TaskStorage(String name) {
        try {
            String basePath = new File(".").getCanonicalPath() + "/temp";
            this.storagePath = basePath + name + "__storage";
            File storageDir = new File(storagePath);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean put(TaskAction task) throws IOException {
        System.out.println("Salvando task no storage");
        String objectSerializationPath = this.storagePath + "/" + task.getId() + ".ser";
        FileOutputStream file = new FileOutputStream(objectSerializationPath);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(task);
        out.close();
        file.close();
        return true;
    }

    @Override
    public List<TaskAction> findAllTasks() throws IOException, ClassNotFoundException {
        List<String> filenames = FilesUtils.listFiles(this.storagePath);
        List<TaskAction> tasks = new ArrayList<>();
        for (String filename : filenames) {
            FileInputStream file = new FileInputStream(this.storagePath + "/" + filename);
            ObjectInputStream in = new ObjectInputStream(file);
            TaskAction task = (TaskAction) in.readObject();
            tasks.add(task);
            in.close();
            file.close();
        }
        return tasks;
    }

    @Override
    public Optional<TaskAction> findTask(String id) {
        return Optional.empty();
    }

    @Override
    public void mostrarTask(String id) {
        File file = new File(this.storagePath + "/" + id + ".ser");
        if(file.delete()) {
            System.out.println("Deletando task do storage");
        } else {
            System.out.println("Erro ao excluir task do storage");
        }
    }
}
