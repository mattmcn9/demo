package com.example.demo;

import java.io.File;

public class FileDisplay {
    private final File file;
    private final String displayName;

    public FileDisplay(File file, String displayName) {
        this.file = file;
        this.displayName = displayName;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return displayName;
    }
}