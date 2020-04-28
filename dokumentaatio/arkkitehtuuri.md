# Arkkitehtuuri

### Pakkausrakenne
Sovelluksen pakkausrakenne on kolmitasoinen lukuunottamatta media-pakkausta:

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Pakkausrakenne.png)

Ui-pakkaus rakentaa sovelluksen näkymän ja käyttöliittymän sekä korkean tason tapahtumankäsittelyn. Domain-pakkaus vastaa sovelluslogiikasta ja abstrahoi media- ja dao-pakkaukset käyttöliittymältä. Dao vastaa tietojen pysyväistalletuksesta. Media-pakkaus sisältää mediasoittimesta vastaavan luokan ja visualisaation generoivat luokat. 

### Käyttöliittymä

Käyttöliittymä koostuu yhdestä näkymästä, joka rakennetaan luokassa Player, ja jonka reunoilla on SidePane-hallintapaneelit. Keskelle näkymää piirretään visualisaatio.
Vasen reunapaneeli vastaa tiedostojen lisäämisestä ja valinnasta. Alareunan paneelilla hallitaan äänentoistoa. Oikeassa reunapaneelissa on visualisaation Setting-parametreja muuttavat SettingSlider-komponentit. Käyttöliittymä on siinä määrin eristetty muusta sovelluslogiikasta, että sen tapahtumakäsittelijät vain kutsuvat Service ja SettingService -luokkien metodeja. Media-pakkauksen luokat sisältävät käyttöliittymäkoodia, mutta niihin ei liity suoraa interaktiivista toimintaa vaan niitä kontrolloidaan sovelluslogiikkaluokkien kautta.

### Sovelluslogiikka
![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.png)

Sovelluslogiikan ydinluokka on Service. Service hallinnoi FileManager-luokkaa ja MPlayer ja Visualizer -luokkia, sekä SettingsService-luokan kommunikoinnin Media-pakkauksen luokkien kanssa. SettingsService abstrahoi Settings-olioiden tietojen pysyväistalletuksen hoitavan SettingDao-luokan kanssa kommunikoinnin. 

### Sekvenssikaavio mediasoittimen tiedoston valinnasta
![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/tiedostonvalintasekvenssi.png)
