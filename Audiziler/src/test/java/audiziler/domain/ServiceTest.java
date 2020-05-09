/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.domain.PlaybackService;
import audiziler.domain.SettingsService;
import audiziler.dao.FileSettingDao;
import audiziler.dao.SettingDao;
import audiziler.ui.WindowSize;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * A test class for testing <code>Service</code>. Why does this not contain any tests?
 * The <code>Service</code>-class controls <code>MPlayer</code> and <code>Visualizer</code>, which both require a JavaFX application context to run.
 * The methods would therefore require a framework for testing JavaFX applications, which is why the testing is skipped for now.
 * @author vesuvesu
 */
public class ServiceTest {
        PlaybackService service;
        static SettingDao settingdao;
        static SettingsService settingsService;
        static File file;
        static File unsupportedFile;
        static WindowSize windowSize;
        static String defaultSettingNames;
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
        defaultSettingNames = properties.getProperty("defaultSettingNames");
        //Construct settingdao...
        try {
            settingdao = new FileSettingDao(settingsFile, defaultSettingNames);
        } catch (IOException ioe) {
            fail("Could not read settings file");
        }
        settingsService = new SettingsService(settingdao);
        
        
        windowSize = new WindowSize();
        windowSize.bind(new ReadOnlyDoubleWrapper(1280), new ReadOnlyDoubleWrapper(720));
        file = new File("src/test/java/audilizer/domain/399801__johansmithi__lata-refresco.aiff");
        unsupportedFile = new File("src/test/java/audilizer/domain/unsupportedFile");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        service = new PlaybackService(settingsService, windowSize);
        
    }
    
    @After
    public void tearDown() {
    }
}
