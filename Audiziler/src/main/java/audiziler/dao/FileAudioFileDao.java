/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vesuvesu
 */
public class FileAudioFileDao implements AudioFileDao {
    private File src;
    private Scanner reader;
    private FileWriter writer;
    private ArrayList<File> files;
    public FileAudioFileDao(String filepath) {
        
        files = new ArrayList();
        
        src = new File(filepath);
        
        try {
            reader = new Scanner(src);
            readFiles();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not find file: " + src.getPath());
        }
    }
    
    @Override
    public ArrayList<File> getFiles() {
        return files;
    }

    @Override
    public void save() throws IOException {
        writeFiles();
    }
    
    @Override
    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
    
    private void readFiles() {
        while (reader.hasNextLine()) {
            String filepath = reader.nextLine();
            File file = new File(filepath);
            
            //Dao does not check if the files actually exist. This is done by FileService
            files.add(file);
        }
    }
    
    private void writeFiles() throws IOException {
        //Creates the file if it does not exist
        src.createNewFile();

        writer = new FileWriter(src);

        for (File file : files) {
            writer.write(file.getPath() + "\n");
        }
        
        writer.close();
    }
    
}
