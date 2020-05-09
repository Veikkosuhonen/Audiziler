# Arkkitehtuuri

## Pakkausrakenne
Sovelluksen pakkausrakenne on kolmitasoinen lukuunottamatta media-pakkausta:

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Pakkausrakenne.png)

Ui-pakkaus rakentaa sovelluksen näkymän ja käyttöliittymän sekä korkean tason tapahtumankäsittelyn. Domain-pakkaus vastaa sovelluslogiikasta ja abstrahoi media- ja dao-pakkaukset käyttöliittymältä. Dao vastaa tietojen pysyväistalletuksesta. Media-pakkaus sisältää mediasoittimesta vastaavan luokan ja visualisaation generoivat luokat. 

---
## Käyttöliittymä

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Audiziler_UI.png)

Käyttöliittymä koostuu yhdestä näkymästä, joka rakennetaan luokassa Player, ja jonka reunoilla on SidePane-hallintapaneelit. Keskelle näkymää piirretään visualisaatio.
Vasen reunapaneeli vastaa tiedostojen lisäämisestä ja valinnasta. Alareunan paneelilla hallitaan äänentoistoa. Oikeassa reunapaneelissa on VisualizationSelector-komponentti ja visualisaation Setting-parametreja muuttavat SettingSlider-komponentit. Käyttöliittymän pääluokka Player on siinä määrin eristetty muusta sovelluslogiikasta, että sen tapahtumakäsittelijät vain kutsuvat PlaybackService ja FileService -luokkien metodeja. Media-pakkauksen luokat rakentavat visualisaation ja siinä mielessä sisältävät käyttöliittymäkoodia, mutta niihin ei liity suoraa interaktiivista toimintaa vaan niitä kontrolloidaan sovelluslogiikkaluokkien kautta.

---
## Sovelluslogiikka
Luokat ja niiden väliset relaatiot

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.png)

Kaaviosta on jätetty käyttöliittymäluokat ja muutama yksinkertainen apuluokka selkeyden vuoksi pois.

### Toiminta

Sovelluksen toiminta tapahtuu käyttöliittymän tapahtumakäsittelijöiden tekemien kutsujen kautta. Käyttöliittymän kanssa kommunikoinnin hoitavat
- PlaybackService, joka hallinnoi äänentoistoa ja visualisaatiota
- FileService, joka hallinnoi tiedostojen lisäämistä, poistoa ja valintaa soitettavaksi. FileService myös kommunikoi
AudioFileDao-rajapinnan kanssa.

Lisäksi keskeinen sovelluslogiikkaluokka on SettingsService, joka hoitaa Settings-olioiden hallinnan ja kommunikoi SettingsDao-rajapinnan kanssa. Käyttöliittymä ei kutsu suoraan SettingServicen metodeja, vaan saa Settings-oliot PlaybackServiceltä.

Keskeiset sovelluslogiikan metodeja kutsuvat tapahtumat ovat
- Käynnistys: init-metodi (App-luokka rakentaa sovelluslogiikan ja injektio riippuvuudet)
- Äänitiedoston lisääminen (FileService)
- Äänitiedoston poistaminen (FileService)
- Äänitiedoston valinta soitettavaksi (FileService, PlaybackService)
- Äänitiedoston toiston hallinta (PlaybackService)
- Visualisaatiotyypin muuttaminen (PlaybackService)
- Lopetus: stop-metodi (App-luokka kutsuu sovelluslogiikkaa suorittamaan tietojen talletuksen)

Seuraava vuokaavio kuvaa ohjelman tyypillistä etenemistä korkealla tasolla erityisesti käyttäjän näkökulmasta, ja näyttää milloin keskeiset toiminnot tapahtuvat.

![flowchart](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Audiziler%20flowchart.png)

Seuraavat sekvenssikaaviot kuvaavat esiteltyjen toimintojen ohjelmallista suoritusta.

**Käynnistys:**

![init](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Application%20init()%20method.png)


**Tiedoston lisääminen ja poisto:**

![adding and removing files](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Communication%20between%20Player%20and%20FileService.png)


**Tiedoston valitseminen ja mediasoittimen ja visualisaation valmistelu:**

![file selection and media initialization](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/File%20selection%20sequence(1).png)


**Mediasoittimen toiston hallinta:**

![playback control](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/togglePlayback.png)


**Visualisaatiotyypin vaihto:**

![switching visualization type](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Select%20Visualization%20Type.png)


**Lopetus:**

![stop](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Application%20stop()%20method.png)

## Tietojen talletus

Sovellus tallettaa tietoja tekstitiedostoihin sovelluksen hakemistoon. Tiedostot luetaan sovelluksen käynnistyessä ja uudet tiedot kirjoitetaan sovelluksen sulkeutuessa. Tilanteessa, jossa tiedostoja ei löydy (esimerkiksi ensimmäistä kertaa ajettaessa) tai tiedostoa ei pystytä lukemaan, sovellus luo uuden tiedoston tai kirjoittaa vanhan päälle.

### Settings

Sovellus tallettaa yhteen tekstitiedostoon Settings-olioiden tiedot. Tällöin Settingien arvot säilyvät samoina käyttäjän sulkiessa sovelluksen. Jokaista visualisaatiotyyppiä kohti talletetaan erillisen Settings-olion, joka sisältää useita Setting-olioita, tiedot seuraavanlaisessa formaatissa:

```
type;visualizationtype1
name of setting1;description of setting1;value;min_value;max_value
name of setting2;description of setting2;value;min_value;max_value
type;visualizationtype2
name of setting3;description of setting3;value;min_value;max_value
```

Olioiden kentät erotellaan puolipisteellä. Settings-olion määrittely alkaa rivistä, jonka ensimmäinen kenttä on `type`, ja toinen kenttä vastaa jotakin VisualizationType-enumeraattorin arvoa (Tällä hetkellä vaihtoehdot ovat `BARS`, `PARTICLES` ja `PHASES`). Tämän jälkeen seuraavat rivit määrittelevät kukin yhden Setting-olion, seuraavan Settings-olion määrittelyyn tai tiedoston loppuun saakka. Yhdellä Settings-oliolla voi periaatteessa olla kuinka monta Settingiä tahansa, mutta tällä hetkellä visualisaatioluokat vaativat tiettyjen Settingien olemassaoloa. Samoin kaikkia VisualizationType-enumeraattorin arvoja vastaavat Settings-oliot täytyy olla määriteltynä tiedostossa. FileSettingDao-luokkaan on kovakoodattu vakio-Settingsien kirjoitus, eli sovellus luo uuden toimivan tiedoston kun tiedostoa ei löydy ja kirjoittaa vanhan tiedoston sisällön uusiksi jos tiedoston sisältö on virheellinen.


### Äänitiedostot

Sovellukseen lisättyjen äänitiedostojen polku talletetaan tekstitiedostoon. Jokaiselle riville talletetaan yksi absoluuttinen polku. Tämän ansiosta sovellus muistaa aiemmin lisätyt tiedostot eikä niitä tarvitse lisätä uudelleen.

## Heikkouksia sovelluksen rakenteessa

Sovelluksen rakenteessa on muutamia kehitystä vaativia heikkouksia. 

Käyttöliittymän on haluttu kommunikoivan mahdollisimman harvan sovelluslogiikkaluokan kanssa, minkä takia PlaybackService-luokka hieman epäloogisesti palauttaa käyttöliittymälle Settings-olion, sen sijaan että käyttöliittymä kutsuisi suoraan SettingServiceä. Toisaalta käyttöliittymä kommunikoi suoraan äänitiedostojen käsittelyn tekevän FileServicen kanssa, eikä sitä relaatiota voi millään änkeä PlaybackServiceen. Tulisi ehkä joko luoda sovelluslogiikan ylimmäinen Service-luokka, joka hoitaisi yksinään käyttöliittymän kanssa kommunikoinnin, tai sitten antaa käyttöliittymän kutsua kolmea eri sovelluslogiikkaluokkaa.

MPlayer- eli mediasoitinluokan ja Visualizer-luokan erottaminen niitä kutsuvasta PlaybackServicestä olisi järkevämpää hoitaa jonkinlaisen rajapinnan kautta, sillä tällä hetkellä PlaybackService on riippuvainen JavaFX-sovelluskontekstin olemassaolosta, eli sen metodeja ei voida suorittaa ilman tätä, koska luokka kutsuu suoraan medialuokkia. Tämä ongelma tulee vahvimmin ilmi testauksessa: PlaybackService-luokalla ei ole testejä, koska testauksen suorittamiseksi tarvittaisiin JavaFX-testausympäristö.

