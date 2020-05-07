/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.dao.AudioFileDao;
import audiziler.dao.FileAudioFileDao;
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
 * A test class for testing the <code>FileService</code>-class. This test suite requires the <code>FileAudioFileDao</code> to function properly.
 * @author vesuvesu
 */
public class FileServiceTest {
    
    FileService fileService;
    static AudioFileDao audioFileDao;
    static File file;
    static File unsupportedFile;
    static String audioFiles;
    public FileServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            fail("Could not load properties");
        }
        audioFiles = properties.getProperty("testAudioFiles");
        
        file = new File("src/test/java/audilizer/domain/399801__johansmithi__lata-refresco.aiff");
        unsupportedFile = new File("src/test/java/audilizer/domain/unsupportedFile");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        File toBeDeleted = new File(audioFiles);
        //Delete the file before each test.
        if (toBeDeleted.exists()) {
            toBeDeleted.delete();
        }
        //The audioFileDao cannot find the file and thus does not load any File-objects to memory.
        audioFileDao = new FileAudioFileDao(audioFiles);
        //The tests are started with fileService having an empty list of files.
        fileService = new FileService(audioFileDao);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class FileService.
     */
    @Test
    public void testAddSupportedFileReturnsTrue() {
        assertTrue(fileService.add(file));
    }
    
    @Test
    public void testAddUnsupportedFileReturnsFalse() {
        assertTrue(!fileService.add(unsupportedFile));
    }
    
    @Test
    public void testAddNullReturnsFalse() {
        assertTrue(!fileService.add(null));
    }
    
    @Test
    public void testAddFileThatDoesNotExistReturnsFalse() {
        assertTrue(!fileService.add( new File("file of awesome") ));
    }
    
    @Test
    public void testCannotAddSameFileManyTimes() {
        fileService.add(file);
        assertTrue( !fileService.add(file) );
        assertTrue( !fileService.add(file) );
    }
    
    @Test
    public void testGetFile() {
        assertTrue( !file.equals( fileService.getFile(file.getName()) ) );
        fileService.add(file);
        assertEquals( file, fileService.getFile(file.getName()) );
    }
    
    @Test
    public void testGetFileReturnsNullIfNotFound() {
        assertTrue( null == fileService.getFile("foo") );
    }
    
    @Test
    public void testUnsupportedFileIsNotAdded() {
        fileService.add(unsupportedFile);
        assertTrue( null == fileService.getFile(unsupportedFile.getName()) );
    }
    
    @Test
    public void testCanRemoveFile() {
        fileService.add(file);
        fileService.remove(file.getName());
        assertTrue( null == fileService.getFile(file.getName()) );
    }
    
    @Test
    public void testRemovingUnknownFileDoesNothing() {
        fileService.add(file);
        fileService.remove("foo");
        assertTrue( file.equals(fileService.getFile(file.getName())) );
    }
    
    @Test
    public void testGetAll() {
        fileService.add(file);
        fileService.add(file);
        fileService.add(unsupportedFile);
        assertTrue(fileService.getAll().contains(file));
        assertTrue( !fileService.getAll().contains(unsupportedFile) );
        assertTrue( fileService.getAll().size() == 1 );
    }
    
    @Test
    public void testSave() {
        try {
            fileService.save();
        } catch (IOException ioe) {
            fail("Failed to save to file: " + audioFiles);
        }
    }
    
    @Test
    public void testConstructorReceivesFilesFromDaoAndAddsOnlySupported() {
        ArrayList<File> files = new ArrayList();
        files.add(file);
        files.add(unsupportedFile);
        files.add(new File("a_nice_file.jar"));
        
        audioFileDao.setFiles(files);
        //Dao now has both a supported and an unsupported file
        //Constructor tries to add those files, first checking if they are supported.
        fileService = new FileService(audioFileDao);
        
        //Should have the supported file
        assertTrue(fileService.getAll().contains(file));
        //Should not have the unsupported file
        assertTrue( !fileService.getAll().contains(unsupportedFile) );
        //And should only contain one
        assertTrue( fileService.getAll().size() == 1 );
    }
}
