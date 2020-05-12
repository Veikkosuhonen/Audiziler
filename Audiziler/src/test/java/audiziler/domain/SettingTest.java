/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public class SettingTest {
    Setting setting;
    DoubleProperty binding;
    double value;
    public SettingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        setting = new Setting("setting", "a setting", 1.0, 0.0, 3.0);
        binding = new SimpleDoubleProperty();
        value = 2.0;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of set method, of class Setting.
     */
    @Test
    public void testSet() {
        setting.set(value);
        assertTrue(value == setting.getValue());
    }

    /**
     * Test of bind method, of class Setting.
     */
    @Test
    public void testBind() {
        setting.bind(binding);
        binding.set(value);
        assertTrue(value == setting.getValue());
    }

    /**
     * Test of getProperty method, of class Setting.
     */
    @Test
    public void testGetProperty() {
        setting.bind(binding);
        assertTrue(setting.getProperty().isBound());
        assertTrue(setting.getProperty().get() == binding.get());
    }    
    
    @Test
    public void getValues() {
        assertEquals("setting", setting.getName());
        assertEquals("a setting", setting.getDescription());
        assertTrue(1.0 == setting.getValue());
        assertTrue(0.0 == setting.getMin());
        assertTrue(3.0 == setting.getMax());
    }
}
