# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus on ääntä ja erityisesti musiikkia visualisoiva mediasoitin. 
Ohjelman käyttöliittymästä valitaan äänilähteeksi äänitiedosto,
ja ohjelma piirtää äänilähteeseen reagoivan visualisoinnin. 
Visualisointia ohjaavia parametreja voi muuttaa käyttöliittymästä ja visualisaatiotyyppiä voi vaihtaa.

## Sovelluksen toiminnallisuus
#### Käyttöliittymä

Kurssin alussa tehty luonnos. Lopullinen tuotos on melko lähellä tätä, paitsi että äänilähteeksi voi valita vain tiedostoja ja totesin että koko sovellus oli kätevä piirtää yhteen näkymään.

![käyttöliittymäluonnos](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luonnos.jpg)
___

Nykyinen käyttöliittymä

![nykyinen käyttöliittymä](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Audiziler_UI.png)

Sovelluksessa on yksi päänäkymä, jonka keskelle piirretään visualisaatio. Ikkunan 3 sivulla on aukeavat valikot:
  
  Vasemmalla: äänitiedostojen hallinta. Tästä voi lisätä valikkoon uusia äänitiedostoja (tehty), valita soitettava äänitiedosto (tehty) ja poistaa valikosta tiedostoja (tehty).
  Alhaalla: äänentoiston hallinta: play- ja back -napit. (tehty)
  Oikealla: visualisaation hallinta. Tästä voidaan valita eri visualisaatiomalleja ja hallita slidereilla jotain niiden parametreista (tehty). 

Lisäksi äänentoistoa voi hallita klikkaamalla näkymää ja kokonäytön tilaan voidaan siirtyä painamalla f-näppäintä. (tehty)

#### Visualisaatio
Visualisaatio toteutetaan JavaFX:n MediaPlayer-olion audioSpectrumListener-observerilla. Tämä tarkkailee äänilähteen taajuuskanavien magnitudeja, joiden perusteella voidaan piirtää erilaisia visualisaatioita. Yksinkertainen visualisaatio piirtää rivin suorakulmioita, joiden korkeutta ja värejä päivitetään spektripäivitysten tahtiin. (tehty) Toinen malli piirtää spektripäivityksiin reagoivan partikkelijärjestelmän. Kaikissa visualisaatiossa animaatiopäivitys siis tapahtuu listener-olion päivityksissä.

#### Tietojen talletus
Sovellus tallettaa ja lukee tietoa tekstitiedostoista. Visualisaation parametreja kontrolloivien Settings-olioiden tiedot tallennetaan jotakuinkin luettavassa formaatissa tiedostoon, jolloin sovellus muistaa mihin arvoon parametrit on jätetty sovelluksen sulkeutuessa. Periaatteessa tiedostosta voi myös manuaalisesti muuttaa parametrien min- ja max-arvoja. Parametrien kustomointiin on muutama jatkokehitysidea. Sovellus tallettaa toiseen tekstitiedostoon sovellukseen lisättyjen äänitiedostojen polut. Tämän ansiosta sovellus lataa
käynnistyksen yhteydessä tiedot aikaisemmin valituista tiedostoista eikä niitä tarvitse käydä lisäämässä uudelleen (mikäli tiedostot löytyvät samasta sijainnista). Tallennustiedostojen nimet määritellään konfiguraatiotiedostossa.

### Perusversio
Perusversiossa toteutetaan vain kaikkein keskeisimmät toiminnot. Sovellukseen valitaan äänitiedostoja, joista valitaan yksi soitettavaksi. Näkymän keskiöön piirretään yksinkertainen visualisaatio.

### Jatkokehittely
#### Visualisaatio
Useita erilaisia visualisaatioita, joista voidaan valita sivupalkin valikolla (tehty). Visualisaatio reagoi hetkittäisten frekvenssien magnitudien lisäksi magnitudien muutoksiin eri tavoin, sekä vaihtaa tyyliä frekvenssijakauman periaatteella (esimerkiksi eri taustaväritys frekvenssijakauman muuttuvan keskiarvon perusteella). Ja lisää visualisaatiotyyppejä.

#### Parametrit
Tällä hetkellä kaikilla visualisaatiotyypeillä on samanlainen joukko parametrejä (jokaisella tyypillä kuitenkin uniikit arvot). Parempi olisi, jos jokaisella tyypillä olisi omainlainen joukko parametreja, jolloin parametrien nimet olisivat intuitiivisempia ja tyypeille spesifisiä ominaisuuksia voitaisiin kontrolloida paremmin. Lisäksi jokaiselle parametrille voitaisiin lisätä yksikkö. (Esimerkiksi `threshold` -parametrille dB ja `analyzer rate` -parametrille Hz).

#### Settings- (eli parametri-) tiedoston kustomointi
Tällä hetkellä tiedoston muuttaminen rikkoo sovelluksen varsin helposti. Esimerkiksi jos jokin Setting (parametri) puuttuu tietyn visualisaatiotyypin listasta, tapahtuu NullPointerException ja kyseistä visualisaatiota ei voida piirtää. Settingien sitomista visualisaatioihin tulisi muuttaa siten, että visualisaatioilla on tietty joukko kontrolloitavia lukuarvoja, ja tiedostossa määritellyissä Settingeissä on mukana tieto siitä, mihin arvoon Setting sidotaan. 

#### Asetukset -valikko
Tämä aukeaisi yläreunalla olevasta napista, ja tästä voitaisiin vaihtaa ainakin: Visualisaation kanvaasin kokoa (tällä hetkellä 1280x720) 
ja visualisaatioiden taajuuskanavien lukumäärää (tällä hetkellä 128 ensimmäistä kanavaa 1024:stä).
