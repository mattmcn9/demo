package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {


        ComboBox<FileDisplay> folderComboBox = new ComboBox<>();

        ListView<String> folderContentsListView = new ListView<>();



        // Let's use the current directory for this example
        File currentDirectory = new File(System.getProperty("user.dir"));

        populateComboBox(currentDirectory, folderComboBox);

        // Custom display for selected item (without "--")
        folderComboBox.setButtonCell(new ListCell<FileDisplay>() {
            private final ImageView folderIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/folderIcon.png"));

            private final ImageView fileIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/Images/fileIcon.png"));

            @Override
            protected void updateItem(FileDisplay item, boolean empty) {
                super.updateItem(item, empty);
                folderIcon.setFitWidth(10);
                folderIcon.setFitHeight(10);
                fileIcon.setFitWidth(10);
                fileIcon.setFitHeight(10);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString().replaceAll("--", ""));
                    setGraphic(folderIcon);
                }
            }
        });


        // Custom display for dropdown items (with "--")
        folderComboBox.setCellFactory(lv -> new ListCell<FileDisplay>() {

            private final ImageView folderIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/folderIcon.png"));

            private final ImageView fileIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/Images/fileIcon.png"));

            @Override
            protected void updateItem(FileDisplay item, boolean empty) {
                super.updateItem(item, empty);
                folderIcon.setFitWidth(10);
                folderIcon.setFitHeight(10);
                fileIcon.setFitWidth(10);
                fileIcon.setFitHeight(10);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    setGraphic(folderIcon);  // As the ComboBox only lists directories, we only use the folder icon.
                }
            }
        });


        folderContentsListView.setCellFactory(lv -> new ListCell<String>() {
            private final ImageView folderIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/folderIcon.png"));

            private final ImageView fileIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/Images/fileIcon.png"));

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                folderIcon.setFitWidth(10);
                folderIcon.setFitHeight(10);
                fileIcon.setFitWidth(10);
                fileIcon.setFitHeight(10);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    File selectedDir = folderComboBox.getSelectionModel().getSelectedItem().getFile();
                    File file = new File(selectedDir, item);

                    setText(item);
                    if (file.isDirectory()) {
                        setGraphic(folderIcon);
                    } else {
                        setGraphic(fileIcon);
                    }
                }
            }
        });

        folderComboBox.getSelectionModel().selectLast();

        // Listen for changes in the ComboBox's selected item
        folderComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            File selectedDir = newVal.getFile();

            // Update the ListView with the contents of the selected directory
            if (selectedDir.isDirectory()) {
                File[] files = selectedDir.listFiles(file -> !file.isHidden());
                if (files != null) {
                    List<String> fileNames = Arrays.stream(files)
                            .map(File::getName)
                            .collect(Collectors.toList());
                    folderContentsListView.getItems().setAll(fileNames);
                } else {
                    folderContentsListView.getItems().clear();
                }
            } else {
                folderContentsListView.getItems().clear();
            }
        });


        VBox root = new VBox(10, folderComboBox, folderContentsListView); // Added a spacing of 10 between the nodes
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Folder Structure ComboBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populateComboBox(File currentDirectory, ComboBox<FileDisplay> comboBox) {
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

            comboBox.getItems().add(0, new FileDisplay(dir, repeatString("--", depth) + displayName));
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




