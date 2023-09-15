package com.example.demo;

import java.io.File;

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
}
