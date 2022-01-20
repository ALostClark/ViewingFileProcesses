package clark.tools;

import clark.storage.HeldFileData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {

    public static List<String> executeCommand(String command, String findVal) {

        List<String> lines = new ArrayList<>();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                if (line.contains(findVal)) lines.add(line);
            }

            while ((line = stdErr.readLine())!= null) {  System.err.println(line);  }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static List<String> executeCommand(String command) {
        return executeCommand(command, "");
    }

    public static List<String> lsofToCsv(List<String> data) {
        List<String> csvRows = new ArrayList<>();
        for (String line : data) {
            if (line.equals(data.get(0))) continue;
            String[] lineData = line.split("\\s+");
            String row = "1,"+lineData[2]+","+lineData[0]+","+lineData[1]+","+lineData[8];
            csvRows.add(row);
        }
        return csvRows;
    }

    public static List<HeldFileData> csvToHeldData(List<String> csv) {
        List<HeldFileData> data = new ArrayList<>();
        for (String line : csv) {
            String[] lineData = line.split(",");
            int pid = Integer.parseInt(lineData[3]);
            data.add(new HeldFileData(pid, lineData[4], lineData[1], lineData[2]));
        }
        return data;
    }

}
