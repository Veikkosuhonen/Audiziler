/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import audiziler.dao.FileSettingDao;
import audiziler.dao.SettingDao;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
public class ServiceTest {
        Service service;
        static SettingDao settingdao;
        static SettingsService settingsService;
        static FileManager filemanager;
        static File file;
        static File unsupportedFile;
        public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            fail("Could not load properties");
        }
        String settingsFile = properties.getProperty("testSettingsFile");
        
        try {
            settingdao = new FileSettingDao(settingsFile);
        } catch (IOException ioe) {
            fail("Could not read settings file");
        }
        settingsService = new SettingsService(settingdao);
        file = new File("src/test/java/audilizer/domain/399801__johansmithi__lata-refresco.aiff");
        unsupportedFile = new File("src/test/java/audilizer/domain/unsupportedFile");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        filemanager = new FileManager();
        service = new Service(settingsService, filemanager);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Service.
     */
    @Test
    public void canAddSupportedFile() {
        assertTrue(service.add(file));
    }
    @Test
    public void cantAddUnsupportedFile() {
        assertTrue(!service.add(unsupportedFile));
    }

    /**
     * Test of selectFile method, of class Service.
     */
    @Test
    public void testSelectFile() {
        System.out.println("selectFile");
        assertTrue(!service.selectFile(file.getName()));
        service.add(file);
        assertTrue(service.selectFile(file.getName()));
    }
}
