/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import audiziler.domain.Setting;
import audiziler.domain.Settings;
import audiziler.media.visualizer.VisualizationType;
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
public class FileSettingDaoTest {
    SettingDao settingdao;
    static String settingsFile;
    static String defaultSettingNames;
    static VisualizationType bars;
    
    public FileSettingDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            fail("Could not load properties");
        }
        settingsFile = properties.getProperty("testSettingsFile");
        defaultSettingNames = properties.getProperty("defaultSettingNames");
        bars = VisualizationType.BARS;
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            settingdao = new FileSettingDao(settingsFile, defaultSettingNames);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testConstructorWhenFileDoesNotExist() {
        File file = new File(settingsFile);
        file.delete();
        try {
            settingdao = new FileSettingDao(settingsFile, defaultSettingNames);
        } catch (IOException ioe) {
            fail("failed construction when no file existed: " + ioe.getMessage());
        }
    }
    

    /**
     * Test of getSettings method, of class FileSettingDao.
     */
    @Test
    public void testGetSettings() {
        Settings settings = settingdao.getSettings(bars);
        String expected = "magnitude color offset";
        String result = settings.get(expected).getName();
        assertEquals(expected, result);
    }
    
    /**
     * Test of save method, of class FileSettingDao.
     */
    @Test
    public void testSave() throws Exception {
        //Reset file to default
        File file = new File(settingsFile);
        file.delete();
        try {
            System.out.println("Creating settings file");
            settingdao = new FileSettingDao(settingsFile, defaultSettingNames);
        } catch (IOException ioe) {
            fail("failed construction when no file existed: " + ioe.getMessage());
        }
        Settings settings = settingdao.getSettings(bars);
        Setting setting = settings.get("threshold");
        System.out.println("setting "+ setting.getName() + " received");
        double value = 69.42;
        setting.set(value);
        assertEquals(settings, settingdao.getSettings(bars));
        assertTrue(value == setting.getValue());
        settingdao.save();
        System.out.println("Reading file after save");
        settingdao = new FileSettingDao(settingsFile, defaultSettingNames);
        settings = settingdao.getSettings(bars);
        setting = settings.get("threshold");
        if (value != setting.getValue()) {
            fail("Expected "+value+ " but was "+ setting.getValue());
        }
    }
    
}
