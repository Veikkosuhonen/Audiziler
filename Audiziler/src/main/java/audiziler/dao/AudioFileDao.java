/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author vesuvesu
 */
public interface AudioFileDao {
    
    public ArrayList<File> getFiles();
    
    public void save(ArrayList<File> files) throws IOException;
    
    //public void setFiles(ArrayList<File> files);
}
