/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

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
public class SettingsTest {
    Settings settings;
    Setting setting;
    public SettingsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        settings = new Settings();
        setting = new Setting("setting", "a setting", 1.0, 0.0, 3.0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Settings.
     */
    @Test
    public void testAdd() {
        settings.add("setting", setting);
        assertEquals(setting, settings.get("setting"));
    }
    
    @Test
    public void testSettingsContainPredefinedSetting() {
        assertTrue(settings.get("threshold").getName().equals("threshold"));
    }
}
