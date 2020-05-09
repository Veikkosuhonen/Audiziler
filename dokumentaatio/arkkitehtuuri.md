# Arkkitehtuuri

### Pakkausrakenne
Sovelluksen pakkausrakenne on kolmitasoinen lukuunottamatta media-pakkausta:

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Pakkausrakenne.png)

Ui-pakkaus rakentaa sovelluksen näkymän ja käyttöliittymän sekä korkean tason tapahtumankäsittelyn. Domain-pakkaus vastaa sovelluslogiikasta ja abstrahoi media- ja dao-pakkaukset käyttöliittymältä. Dao vastaa tietojen pysyväistalletuksesta. Media-pakkaus sisältää mediasoittimesta vastaavan luokan ja visualisaation generoivat luokat. 

### Käyttöliittymä

Käyttöliittymä koostuu yhdestä näkymästä, joka rakennetaan luokassa Player, ja jonka reunoilla on SidePane-hallintapaneelit. Keskelle näkymää piirretään visualisaatio.
Vasen reunapaneeli vastaa tiedostojen lisäämisestä ja valinnasta. Alareunan paneelilla hallitaan äänentoistoa. Oikeassa reunapaneelissa on VisualizationSelector-komponentti ja visualisaation Setting-parametreja muuttavat SettingSlider-komponentit. Käyttöliittymä on siinä määrin eristetty muusta sovelluslogiikasta, että sen tapahtumakäsittelijät vain kutsuvat PlaybackService ja FileService -luokkien metodeja. Media-pakkauksen luokat rakentavat visualisaation ja siinä mielessä sisältävät käyttöliittymäkoodia, mutta niihin ei liity suoraa interaktiivista toimintaa vaan niitä kontrolloidaan sovelluslogiikkaluokkien kautta.

### Sovelluslogiikka
#### Luokat ja niiden väliset relaatiot

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.png)

### Toiminta

Sovelluksen keskeiset toiminnot ovat
- Käynnistys: init-metodi
- Äänitiedoston lisääminen
- Äänitiedoston poistaminen
- Äänitiedoston valinta soitettavaksi
- Äänitiedoston toiston hallinta
- Visualisaatiotyypin muuttaminen

#### Vuokaavio kuvaa sovelluksen korkean tason toimintaa

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Audiziler_flowchart.png)

Seuraavat sekvenssikaaviot kuvaavat yksityiskohtaisesti eri toimintojen suoritusta.

**Käynnistys:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Application%20init()%20method.png)


**Tiedoston lisääminen ja poisto:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Communication%20between%20Player%20and%20FileService.png)


**Tiedoston valitseminen ja mediasoittimen ja visualisaation valmistelu:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/File%20selection%20sequence(1).png)


**Mediasoittimen toiston hallinta:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/togglePlayback.png)


**Visualisaatiotyypin vaihto:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Select%20Visualization%20Type.png)


**Lopetus:**

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Application%20stop()%20method.png)
