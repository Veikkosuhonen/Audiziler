/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.dao.AudioFileDao;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


/**
 * A class to abstract the handling of audio files
 * @author vesuvesu
 */
public class FileService {
    private final ArrayList<File> files;
    private final AudioFileDao audioFileDao;
    public FileService(AudioFileDao audioFileDao) {
        this.audioFileDao = audioFileDao; 
        files = new ArrayList();
        
        //Adds all the File-objects from the dao in a controlled fashion
        audioFileDao.getFiles().forEach(file -> {
            add(file);
        });
    }
    /**
     * Adds a file to the <code>FileService</code>
     * @param file to be added
     * @return a Boolean indicating whether the file was valid and was added. The file is invalid if it is not of supported format or has already been added. 
     */
    public boolean add(File file) {
        
        if (!isSupported(file)) {
            return false;
        } else if (getFile(file.getName()) != null) {
            return false;
        }
        files.add(file);
        return true;
    }
    /**
     * Returns all files as a List
     * @return 
     */
    public ArrayList<File> getAll() {
        return files;
    }
    /**
     * 
     * @param name
     * @return the File with a matching name or null if no such file is found
     */
    public File getFile(String name) {        
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
    /**
     * Removes a file with a matching name from <code>FileService</code> if it is found
     * @param name 
     */
    public void remove(String name) {        
        Optional<File> answer =
            files.stream()
            .filter(f -> f.getName().equals(name))
            .findAny();
        if (answer.isPresent()) {
            files.remove(answer.get());
        }
    }
    public void save() throws IOException {
        audioFileDao.setFiles(files);
        audioFileDao.save();
    }
    /**
     * Tests whether the given file has an extension of one of the supported formats
     * @param file
     * @return a Boolean indicating whether the file is supported
     */
    private boolean isSupported(File file) {
        if (file == null) {
            return false;
        }
        String name = file.getName();
        String extension = getExtension(name);
        return extension.equals(".mp3") || extension.equals(".wav") || extension.equals(".aiff");
    }
    private String getExtension(String name) {
        int indexOfExtension = name.lastIndexOf(".");
        if (indexOfExtension == -1) {
            indexOfExtension = 0;
        }
        return name.substring(indexOfExtension, name.length());
    }
    
}
