/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vesuvesu
 */
public class FileAudioFileDaoTest {
    AudioFileDao audioFileDao;
    static String filepath;
    static File audiosFile;
    static File file1;
    static File file2;
    static ArrayList<File> files;
    public FileAudioFileDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            fail("Could not load properties");
        }
        filepath = properties.getProperty("testAudioFiles");
        audiosFile = new File(filepath);
        
        //Doesn't matter if these File-objects don't represent any actual file
        file1 = new File("file1");
        file2 = new File("file2");
        files = new ArrayList();
        files.add(file1);
        files.add(file2);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //Always start with no file present
        audiosFile.delete();
        audioFileDao = new FileAudioFileDao(filepath);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFiles method, of class FileAudioFileDao.
     */
    @Test
    public void testGetFilesFromEmptyFile() {
        assertTrue(audioFileDao.getFiles().isEmpty());
    }

    /**
     * Test of save method, of class FileAudioFileDao.
     */
    @Test
    public void testSave() throws Exception {
        audioFileDao.save(files);
        audioFileDao = new FileAudioFileDao(filepath);
        
        assertTrue(2 == audioFileDao.getFiles().size());
        assertTrue(audioFileDao.getFiles().contains(file1));
        assertTrue(audioFileDao.getFiles().contains(file2));
    }

    /**
     * Test of setFiles method, of class FileAudioFileDao.
     */
    @Test
    public void testSaveFiles() {
        try {
            audioFileDao.save(files);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
        assertEquals(files, audioFileDao.getFiles());
    }
    
    @Test
    public void testFilesContainSavedFiles() {
        try {
            audioFileDao.save(files);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
        assertTrue(2 == audioFileDao.getFiles().size());
        assertTrue(audioFileDao.getFiles().contains(file1));
        assertTrue(audioFileDao.getFiles().contains(file2));
    }
    
}
