package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void alussaOikeaSaldo() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void lataaminenToimii() {
        kortti.lataaRahaa(90);
        assertEquals("saldo: 1.0", kortti.toString());
    }
    @Test
    public void saldoVaheneeKunRahaaRiittavasti() {
        kortti.otaRahaa(10);
        assertEquals("saldo: 0.0", kortti.toString());
    }
    @Test
    public void saldoEiVaheneKunRahaEiRiita() {
        kortti.otaRahaa(11);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void otaRahaaPalauttaaOikeanArvon() {
        assertTrue(kortti.otaRahaa(10));
        assertTrue(!kortti.otaRahaa(10));
    }
    @Test
    public void saldoPalauttaaOikean() {
        assertEquals(10, kortti.saldo());
    }
}
