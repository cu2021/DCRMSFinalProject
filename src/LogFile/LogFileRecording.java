package LogFile;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileRecording {

    private static LogFileRecording fileRecording;

    private LogFileRecording() {
    }

    public static LogFileRecording getLogFileRecording() {
        if (fileRecording == null) {
            fileRecording = new LogFileRecording();
        }
        return fileRecording;
    }

    public void writeActivity(String message) throws FileNotFoundException, IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            FileWriter fileWriter = new FileWriter("./src/LogFile/logFile.txt", true);
            fileWriter.write(dtf.format(now) + " : " + message+"\n");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
