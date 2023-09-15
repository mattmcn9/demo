package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> folderComboBox = new ComboBox<>();
        VBox root = new VBox(folderComboBox);
        Scene scene = new Scene(root, 400, 200);

        // Let's use the current directory for this example
        File currentDirectory = new File(System.getProperty("user.dir"));

        populateComboBox(currentDirectory, folderComboBox);

        // Custom display for selected item (without "--")
        folderComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.replaceAll("--", ""));
                }
            }
        });

        // Custom display for dropdown items (with "--")
        folderComboBox.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        folderComboBox.getSelectionModel().selectLast();

        primaryStage.setTitle("Folder Structure ComboBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    private void populateComboBox(File currentDirectory, ComboBox<String> comboBox) {
        File dir = currentDirectory;
        int depth = 0;

        // Calculate depth first
        while (dir != null) {
            dir = dir.getParentFile();
            depth++;
        }

        dir = currentDirectory;  // Reset to the current directory

        // Now, populate the ComboBox
        while (dir != null) {
            depth--;

            String displayName = dir.getName();
            if (displayName.isEmpty()) {
                displayName = "/";
            }

            comboBox.getItems().add(0, repeatString("--", depth) + displayName);
            dir = dir.getParentFile();
        }
    }

    private String repeatString(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

