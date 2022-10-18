package executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ExecuteScript extends ModelRunnable {

    public ExecuteScript(TaskAction task, ITaskStorage taskStorage) {
        super(task, taskStorage);
    }

    @Override
    public void run() {
        try {
            File f = new File(this.taskAction.getExecutionPath());
            
            if (!f.exists() || f.isDirectory()) {
                throw new RuntimeException("N�o encontrado " + this.taskAction.getExecutionPath());
            }
            
            Process process = Runtime.getRuntime().exec("node " + this.taskAction.getExecutionPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            
                    String s;
            while (true) {
                if ((s = reader.readLine()) == null) break;
                System.out.println("Script: " + s);
            }
            this.cleanTask();
        
        } catch (Exception e) {
            System.out.printf("Erro ao executar script {}", e);
        }
    }
}
