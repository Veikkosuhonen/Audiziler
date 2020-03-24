
package com.mycompany.unicafe;

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
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kassapaateLuotuOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0,paate.edullisiaLounaitaMyyty());
        assertEquals(0,paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void maksuOnnistuuKunRahamaaraRiittaa() {
        assertEquals(0,paate.syoEdullisesti(240));
        assertEquals(60,paate.syoEdullisesti(300));
        assertEquals(0,paate.syoMaukkaasti(400));
        assertEquals(60,paate.syoMaukkaasti(460));
        assertEquals(2,paate.edullisiaLounaitaMyyty());
        assertEquals(2,paate.maukkaitaLounaitaMyyty());
        assertEquals(1280 + 100000, paate.kassassaRahaa());
    }
    @Test
    public void maksuaEiTapahduJosRahamaaraLiianPieni() {
        assertEquals(100, paate.syoEdullisesti(100));
        assertEquals(100, paate.syoMaukkaasti(100));
        assertEquals(0,paate.edullisiaLounaitaMyyty());
        assertEquals(0,paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void korttimaksuOnnistuuJosSaldoRiittaa() {
        kortti = new Maksukortti(640);
        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(400, kortti.saldo());
        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void korttimaksuEiTapahduJosSaldoLiianPieni() {
        kortti = new Maksukortti(230);
        assertTrue(!paate.syoEdullisesti(kortti));
        assertTrue(!paate.syoMaukkaasti(kortti));
        assertEquals(230, kortti.saldo());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void rahanLatausToimiiOikein() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100, kortti.saldo());
        assertEquals(100 + 100000, paate.kassassaRahaa());
        
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(100, kortti.saldo());
        assertEquals(100 + 100000, paate.kassassaRahaa());
    }
}
