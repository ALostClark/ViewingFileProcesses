package clark.controllers;

import clark.storage.HeldFileData;
import clark.tools.CommandExecutor;
import clark.tools.FileLoader;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static clark.tools.FileLoader.scanDataWin;

public class Controller {

    @FXML
    public TabPane tabView;
    private ObservableList<HeldFileData> dataList = FXCollections.observableList(new ArrayList<>());

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String fileName = "";

    public void scanFileSystem(ActionEvent e) {
        try {
            CommandExecutor.executeCommand("powershell start-process \"powershell\" -ArgumentList @('\"" + System.getProperty("user.dir") + "\\" + "output.bat\"','\"" + FileLoader.saveFileDialog() + "\"') -verb runAs");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadDataFile(ActionEvent e) {
        String filename;
        try {
            filename = FileLoader.loadFileDialog(false);
        } catch (IOException err) {
            err.printStackTrace();
            return;
        }

        dataList.clear();
        try { dataList.addAll(scanDataWin(new File(FileLoader.loadFileDialog(true)), filename)); } catch (IOException ex) { ex.printStackTrace(); return; }

        TableView<HeldFileData> tableView = new TableView<>();
        tableView.setItems(dataList);
        tableView.getColumns().clear();

        TableColumn<HeldFileData, String> fileNameColumn = new TableColumn<>("Filename");
        fileNameColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getFileName()));
        tableView.getColumns().add(fileNameColumn);

        TableColumn<HeldFileData, String> userAccountColumn = new TableColumn<>("User Account");
        userAccountColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getUser()));
        tableView.getColumns().add(userAccountColumn);

        TableColumn<HeldFileData, Integer> processIdColumn = new TableColumn<>("Process ID");
        processIdColumn.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getProcessId()).asObject());
        tableView.getColumns().add(processIdColumn);

        TableColumn<HeldFileData, String> processNameColumn = new TableColumn<>("Process Name");
        processNameColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getProcessName()));
        tableView.getColumns().add(processNameColumn);

        Tab tab = new Tab(JOptionPane.showInputDialog(null, "Enter a name for the tab"));
        tab.setContent(tableView);
        tabView.getTabs().add(tab);
    }

    private static List<HeldFileData> processWindows(String fileName) {
        return CommandExecutor.csvToHeldData(CommandExecutor.executeCommand("runas /profile /user:Administrator \"cmd.exe /c openfiles /query /fo csv /v\"", fileName));
    }

    private static List<HeldFileData> processLinux(String fileName) {
        return CommandExecutor.csvToHeldData(CommandExecutor.lsofToCsv(CommandExecutor.executeCommand("lsof " + fileName)));
    }



}
