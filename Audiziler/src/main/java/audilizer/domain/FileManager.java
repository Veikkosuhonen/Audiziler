/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


/**
 *
 * @author vesuvesu
 */
public class FileManager {
    ArrayList<File> files;
    public FileManager() {
        files = new ArrayList();
    }
    public boolean add(File file) {
        if (isSupported(file)) {
            files.add(file);
        } else {
            return false;
        }
        return true;
    }
    public ArrayList<File> getAll() {
        return files;
    }
    public File getFile(String name) {
        //returns a file with matching name and null if no file has matching name
        
        Optional<File> answer =
            files.stream()
            .filter(f -> f.getName().equals(name))
            .findAny();
        if (answer.isPresent()) {
            return answer.get();
        } else {
            return null;
        }
    }
    private boolean isSupported(File file) {
        if (file == null) {
            return false;
        }
        String name = file.getName();
        String extension = getExtension(name);
        if (extension.equals(".mp3") || extension.equals(".wav") || extension.equals(".aiff")) {
            return true;
        }
        return false;
    }
    private String getExtension(String name) {
        int indexOfExtension = name.lastIndexOf(".");
        if (indexOfExtension == -1) {
            indexOfExtension = 0;
        }
        return name.substring(indexOfExtension, name.length());
    }
}
