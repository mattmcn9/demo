package com.example.demo;

import javafx.scene.control.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDisplay {
    private final File file;
    private final int depth;

    public FileDisplay(File file, int depth) {
        this.file = file;
        this.depth = depth;
    }

    public File getFile() {
        return file;
    }

    public int getDepth() {
        return depth;
    }

    private String getIndentation() {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("--");
        }
        return indent.toString();
    }

    @Override
    public String toString() {
        return getIndentation() + file.getName();
    }

//    private List<Button> createButtons() {
//        List<Button> buttons = new ArrayList<>();
//        // Create and add buttons to the list
//        // For example:
//        // buttons.add(new Button("Button 1"));
//        // buttons.add(new Button("Button 2"));
//        String [] names = new String [] {"You", "might", "want", "further",  "refine", "logic", "if", "you", "have", "padding", "or", "margins", "to", "consider"};
////        ArrayList<String> names = new ArrayList<>("You", "might", "want", "further",  "refine", "logic", "if", "you", "have", "padding", "or", "margins", "to", "consider");
//        System.out.println("NAMES SIZE = " + names.length);
//        for(String s : names){
////        for(int i = 0; i < 10; i++){
//            Button btn = new Button(s);
////            btn.setPrefSize();
//            btn.setMinWidth(60);
//            btn.autosize();
//            buttons.add(btn);
//        }
//        // ...
//        return buttons;
//    }
}
