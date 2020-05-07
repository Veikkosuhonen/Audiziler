/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.domain.SettingsService;
import audiziler.domain.Setting;
import audiziler.dao.FileSettingDao;
import audiziler.dao.SettingDao;
import audiziler.media.visualizer.VisualizationType;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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
public class SettingsServiceTest {
        static SettingDao settingdao;
        static SettingsService service;
        public SettingsServiceTest() {
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
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        service = new SettingsService(settingdao);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setSettings method, of class SettingsService.
     */
    @Test
    public void testSetSettings() {
        VisualizationType type = VisualizationType.BARS;
        service.setSettings(type);
    }

    /**
     * Test of getSetting method, of class SettingsService.
     */
    @Test
    public void testGetSetting() {
        try {
            String expectedName = "threshold";
            String result = service.getSetting(expectedName).getName();
            assertEquals(expectedName, result);
        } catch (NullPointerException npe) {
            fail("setting was not found");
        }
    }

    /**
     * Test of getSettings method, of class SettingsService.
     */
    @Test
    public void testGetSettings() {
        
        String expectedToContain = "color offset";
        List<Setting> settingArray = service.getSettings().getAll();
        boolean found = false;
        for (Setting setting : settingArray) {
            if (setting.getName().equals(expectedToContain)) {
                found = true;
            }
        }
        assertTrue(found);
    }
    
}
