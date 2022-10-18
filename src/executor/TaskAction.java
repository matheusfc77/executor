package executor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TaskAction implements Serializable {

    private final String id = UUID.randomUUID().toString();
    private final String executionPath;
    private long tempo;
    private TimeUnit timeUnit;
    private final boolean runOnce;
    private final boolean persistir;

    protected TaskAction(String executionPath, String executionTime, boolean runOnce, boolean shouldPersist) {
        try {
            String path = new File(".").getCanonicalPath();
            this.executionPath = path + "/scripts/" + executionPath;
            this.runOnce = runOnce;
            this.persistir = shouldPersist;
            this.parseExecutionTime(executionTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskAction ofScript(String executionPath, String executionTime, boolean runOnce, boolean shouldPersist) {
        return new TaskAction(executionPath, executionTime, runOnce, shouldPersist);
    }

    private void parseExecutionTime(String executionTime) {
        if (executionTime == null || executionTime.isEmpty()) {
            throw new RuntimeException("Tempo incorreto");
        }
        String[] parts = executionTime.split("");
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new RuntimeException("Tempo incorreto");
        }
        StringBuilder numberPart = new StringBuilder();
        String currentPart = parts[0];
        int index = 0;
        while (isNumeric(currentPart)) {
            numberPart.append(currentPart);
            currentPart = parts[++index];
        }
        long time = Long.parseLong(numberPart.toString());
        TimeUnit unit;
        switch (parts[index].toLowerCase().trim()) {
            case "mili":
                unit = TimeUnit.MILLISECONDS;
                break;
            case "s":
                unit = TimeUnit.SECONDS;
                break;
            case "m":
                unit = TimeUnit.MINUTES;
                break;
            case "d":
                unit = TimeUnit.DAYS;
                break;
            default:
                throw new RuntimeException("Tempo incorreto " + parts[1]);
        }
        this.tempo = time;
        this.timeUnit = unit;
    }

    private boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public String getExecutionPath() {
        return executionPath;
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return tempo;
    }

    public TimeUnit getUnit() {
        return timeUnit;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public boolean shouldPersist() {
        return persistir;
    }

    @Override
    public String toString() {
        return "TaskAction{" +
                "id='" + id + '\'' +
                ", executionPath='" + executionPath + '\'' +
                ", time=" + tempo +
                ", unit=" + timeUnit +
                ", runOnce=" + runOnce +
                '}';
    }
}
