package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    ComboBox<FileDisplay> folderComboBox = new ComboBox<>();

    ListView<String> folderContentsListView = new ListView<>();
    @Override
    public void start(Stage primaryStage) {

        // Let's use the current directory for this example
        File currentDirectory = new File(System.getProperty("user.dir"));

        populateComboBox(currentDirectory, folderComboBox);

        // Custom display for selected item (without "--")
        folderComboBox.setButtonCell(new ListCell<FileDisplay>() {
            private final ImageView folderIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/folderIcon.png"));

            @Override
            protected void updateItem(FileDisplay item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(2);  // 2 is the spacing between elements
                    folderIcon.setFitWidth(10);
                    folderIcon.setFitHeight(10);
                    Label indentLabel = new Label(item.toString().replaceAll("--", ""));  // Remove the '--' for the selected display
                    hbox.getChildren().addAll(folderIcon, indentLabel);

                    setText(null);  // We set the text to null because we're using the graphic for display
                    setGraphic(hbox);
                }
            }
        });



        // Custom display for dropdown items (with "--")
        folderComboBox.setCellFactory(lv -> new ListCell<FileDisplay>() {
            private final ImageView folderIcon = new ImageView(new Image("D:/Projects/JavaFX/JavaFx-playground/demo/src/main/java/com/example/demo/folderIcon.png"));

            @Override
            protected void updateItem(FileDisplay item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(2);
                    folderIcon.setFitWidth(10);
                    folderIcon.setFitHeight(10);
                    Label indentLabel = new Label(getIndentation(item.getDepth()));  // Use the helper method here
                    Label folderLabel = new Label(item.getFile().getName());
                    //hbox.setAlignment(Pos.CENTER);
                    hbox.getChildren().addAll(indentLabel, folderIcon, folderLabel);

                    setText(null);
                    setGraphic(hbox);
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
        updateContentsListView(folderComboBox.getItems().get(folderComboBox.getItems().size() -1));

        // Listen for changes in the ComboBox's selected item
        folderComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateContentsListView(newVal);
        });


        VBox root = new VBox(10, folderComboBox, folderContentsListView); // Added a spacing of 10 between the nodes
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Folder Structure ComboBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateContentsListView(FileDisplay newVal){
        File selectedDir = newVal.getFile();

        // Update the ListView with the contents of the selected directory
        if (selectedDir.isDirectory()) {
            File[] files = selectedDir.listFiles(file -> !file.isHidden());
            if (files != null) {
                List<String> fileNames = Arrays.stream(files)
                        .map(File::getName)
                        .collect(Collectors.toList());
                folderContentsListView.getItems().setAll(fileNames);
                folderContentsListView.getItems().setAll(fileNames);
            } else {
                folderContentsListView.getItems().clear();
            }
        } else {
            folderContentsListView.getItems().clear();
        }

    }

    private String getIndentation(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("--");
        }
        return indent.toString();
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

            comboBox.getItems().add(0, new FileDisplay(dir, depth));
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
