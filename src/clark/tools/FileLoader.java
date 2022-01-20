package clark.tools;

import clark.storage.HeldFileData;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static String loadFileDialog(boolean filter) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        if (filter) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Values (.csv)", "*.csv"));
            fileChooser.setInitialFileName("output.csv");
        }
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            throw new IOException("Cannot read data from null file");
        }
    }

    public static String saveFileDialog() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Values (.csv)", "*.csv"));
        fileChooser.setInitialFileName("output.csv");
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            throw new IOException("Cannot save data to null file");
        }
    }


    // Windows OpenFiles output: ID, user, PID, Process, File
    public static List<HeldFileData> scanDataWin(File file, String filter){
        List<HeldFileData> output = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine())!=null) {
                if (line.contains(filter)) {
                    String[] data = line.replaceAll("\"", "").split(",");
                    try {
                        output.add(new HeldFileData(Integer.parseInt(data[2]), data[4], data[1], data[3]));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { continue; }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }




}


