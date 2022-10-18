package executor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesUtils {

    public static List<String> readFile(String filename) {

        List<String> result = new ArrayList<>();
        BufferedReader reader;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                result.add(line.trim());
                line = reader.readLine();
            }
            reader.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static void saveFile(String filename, List<String> content) throws IOException {
        File fout = new File(filename);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for (String line : content) {
            bw.write(line);
            bw.newLine();
        }
        
        bw.close();
    }


    public static List<String> listFiles(String dir) {
        
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    
}
