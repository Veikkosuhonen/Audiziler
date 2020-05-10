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
 * DAO interface for classes handling the storing of audio file paths
 * @author vesuvesu
 */
public interface AudioFileDao {
    /**
     * 
     * @return <code>ArrayList</code> containing the files
     */
    public ArrayList<File> getFiles();
    /**
     * Writes the file paths to a text file
     * @param files
     * @throws IOException 
     */
    public void save(ArrayList<File> files) throws IOException;
}
