/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import java.io.File;
import java.util.ArrayList;
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
public class FileManagerTest {
    FileManager filemanager;
    File file;
    File unsupportedFile;
    public FileManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        filemanager = new FileManager();
        file = new File("src/test/java/audilizer/domain/399801__johansmithi__lata-refresco.aiff");
        unsupportedFile = new File("src/test/java/audilizer/domain/unsupportedFile");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class FileManager.
     */
    @Test
    public void canAddSupportedFile() {
        assertTrue(filemanager.add(file));
    }
    @Test
    public void cantAddUnsupportedFile() {
        assertTrue(!filemanager.add(unsupportedFile));
    }

    /**
     * Test of getAll method, of class FileManager.
     */
    @Test
    public void cantAddDuplicateFiles() {
        filemanager.add(file);
        filemanager.add(file);
        ArrayList<File> files = filemanager.getAll();
        assertEquals(1, files.size());
        assertTrue(files.contains(file));
    }

    /**
     * Test of getFile method, of class FileManager.
     */
    @Test
    public void testGetFile() {
        filemanager.add(file);
        assertEquals(file,filemanager.getFile(file.getName()));
    }
    
}
