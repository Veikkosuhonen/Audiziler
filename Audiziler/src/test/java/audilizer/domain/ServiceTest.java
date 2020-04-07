/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import java.io.File;
import javafx.scene.layout.Pane;
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
    File file;
    File unsupportedFile;
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        service = new Service();
        file = new File("src/test/java/audilizer/domain/399801__johansmithi__lata-refresco.aiff");
        unsupportedFile = new File("src/test/java/audilizer/domain/unsupportedFile");
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
