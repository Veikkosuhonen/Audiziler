/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author vesuvesu
 */
public class AppTest {
    
    public AppTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class App.
     */
    //@Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        App.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class App.
     * @throws java.lang.Exception
     */
    //@Test
    public void testStart() throws Exception {
        System.out.println("start");
        Stage stage = null;
        App instance = new App();
        instance.start(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fileButton method, of class App.
     */
    //@Test
    public void testFileButton() {
        System.out.println("fileButton");
        String text = "";
        Button expResult = null;
        Button result = App.fileButton(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mediaButton method, of class App.
     */
    @Test
    public void testMediaButton() {
        System.out.println("mediaButton");
        String name = "";
        Button result = App.mediaButton(name);
        assertTrue(result.isDisabled());
        
    }
    //@Test
    public void testFileSelection() {
        System.out.println("selectFile");
        App app = new App();
        try {
            App.main(null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        File file = new File("src/test/java/audilizer/ui/399801__johansmithi__lata-refresco.aiff");
        Media media = null;
        try {
            media = new Media(file.toURI().toURL().toString());
        } catch (MalformedURLException ex) {
        }
        app.selectFile(file);
        assertEquals(media.getSource(), app.mediaplayer.player.getMedia().getSource());
    }
    
}
