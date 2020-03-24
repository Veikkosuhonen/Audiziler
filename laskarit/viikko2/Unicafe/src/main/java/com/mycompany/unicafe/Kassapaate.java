
package com.mycompany.unicafe;

public class Kassapaate {

    private int kassassaRahaa;
    private int edulliset;
    private int maukkaat;

    public Kassapaate() {
        this.kassassaRahaa = 100000;
    }

    public int syoEdullisesti(int maksu) {
        if (maksu >= 240) {
            this.kassassaRahaa = kassassaRahaa + 240;
            ++this.edulliset;
            return maksu - 240;
        } else {
            return maksu;
        }
    }

    public int syoMaukkaasti(int maksu) {
        if (maksu >= 400) {
            this.kassassaRahaa = kassassaRahaa + 400;
            this.maukkaat++;
            return maksu - 400;
        } else {
            return maksu;
        }
    }

    public boolean syoEdullisesti(Maksukortti kortti) {
        if (kortti.saldo() >= 240) {
            kortti.otaRahaa(240);
            this.edulliset++;
            return true;
        } else {
            return false;
        }
    }

    public boolean syoMaukkaasti(Maksukortti kortti) {
        if (kortti.saldo() >= 400) {
            kortti.otaRahaa(400);
            this.maukkaat++;
            return true;
        } else {
            return false;
        }
    }

    public void lataaRahaaKortille(Maksukortti kortti, int summa) {
        if (summa >= 0) {
            kortti.lataaRahaa(summa);
            this.kassassaRahaa += summa;
        } else {
            return;
        }
    }

    public int kassassaRahaa() {
        return kassassaRahaa;
    }

    public int maukkaitaLounaitaMyyty() {
        return maukkaat;
    }

    public int edullisiaLounaitaMyyty() {
        return edulliset;
    }
}