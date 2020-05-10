# Testausdokumentti

Sovelluksen testaus on suoritettu yksikkö- ja integraatiotason automatisoituina testeinä JUnit-kirjastolla, 
sekä manuaalisina järjestelmätason testeinä.

## Automatisoitu yksikkö- ja integraatiotestaus

Automatisoidusti on testattu sovelluksen luokkia, jotka eivät ole riippuvaisia JavaFX:stä. Ui- ja media-pakkaukset on 
jätetty testauksen ulkopuolelle.

### Sovelluslogiikka

Sovelluslogiikan FileService- ja SettingService-luokkien metodeja on testattu yhdessä dao-luokkien kanssa integraatiotesteinä,
joissa on pyritty varmistamaan odotusten mukainen toiminta olettaen, että dao-luokka käyttäytyy odotusten mukaisesti. Lisäksi
FileService- ja SettingService-luokille on tehty melko kattavat yksikkötestit. Myös Settings ja Setting-luokka on testattu 
yksikkötestein läpikotaisin. PlaybackService-luokalle ei ole tehty automatisoituja testejä, koska luokka on suoraan riippuvainen
media-pakkauksen luokista, jotka taas ovat riippuvaisia JavaFX-sovellusympäristöstä. [Arkkitehtuuridokumentissa](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md#heikkouksia-sovelluksen-rakenteessa)
on käsitelty tätä ongelmaa.

### Dao-luokat

AudioFileDao- ja SettingDao-rajapintojen toteutuksia FileAudioFileDao ja FileSettingDao on 
testattu yksikkö- ja integraatiotestein konfiguraatiotiedoston määrittelemien testitiedostojen kanssa. Testeissä 
on normaalitilanteiden yksikkö- ja integraatiotestien lisäksi simuloitu mm. tilanteita, joissa talletustiedostoja ei ole, ne ovat tyhjiä, 
niiden sisältö on täysin viallinen tai niiden sisällössä on pieniä virheitä. 

### Testikattavuus

Domain- ja dao-pakkausten testien rivikattavuus on 86% ja haarautumiskattavuus 78%. 

![testikattavuus](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testcoverage.png)

Jos PlaybackService-luokka jätetään pois testikattavuusraportista, rivikattavuus on 96% ja haarautumiskattavuus 91%.

![testikattavuus_ilman_playbackservice](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testcoverage_without_playbackservice.png)

## Manuaalinen järjestelmätestaus

Sovelluksen järjestelmätestaus on tehty linux-ympäristössä [viimeisimmällä releasella](https://github.com/Veikkosuhonen/ot-harjoitustyo/releases/tag/loppupalautus).
Sovellus ja releaseen liitetty config.properties on ladattu ja sovellusta on testattu käytössä [käyttöohjeessa](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/Instructions.md), 
[arkkitehtuurissa](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md) ja
[määrittelydokumentissa](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md)
kuvatulla tavalla. 

Poikkeuksellisen käytön testejä:
- Sovellukseen on yritetty JFileChooserin kautta lisätä vääräntyyppinen tiedosto. Sovellus ei lisää tiedostoa eikä
reagoi muullakaan tavalla.

Sovellusta on testattu tilanteissa jossa jompi kumpi tai molemmat
tallennustiedostot puuttuvat, tai tallennustiedostot ovat jollakin tapaa viallisia. Settingsien tallennustiedostoa
on kokeiltu muuttaa esimerkiksi siten että:
- jokin konfiguraatiotiedostossa määritellyistä Settingseistä puuttuu
- on lisätty jokin ylimääräinen Setting
- on poistettu jokin kolmesta Settings-tyypistä
- on lisätty typoja
- joitakin settingsien ääriarvoja on muutettu liian suuriksi tai pieniksi (esimerkiksi `analyzer rate` negatiiviseksi)

Useimmissa tapauksissa sovellus ilmoittaa virheestä tiedoston luvussa ja kirjoittaa vakio-Settingit, 
ja jatkaa toimintaa normaalisti. Settingien arvojen muutto ei missään tilanteessa aiheuta virhettä ja 
vakio-Settingien kirjoittamista, mutta järjettömät arvot eivät ole tähänastisissa testeissä kaataneet sovellusta tai
johtaneet virheviestien tulostamiseen, vaan tietyissä tilanteissa aiheuttaneet esimerkiksi sen, että visualisaatiota ei 
piirretä tai ääntä ei kuulu. Kun arvoa muutetaan sovelluksesta sliderilla takaisin kelvolliseksi, sovellus toimi jälleen 
normaalisti.

## Sovellukseen jääneitä laatuongelmia

Sovellus tulostaa virhetilanteissa ilmoituksen konsoliin. Tällä hetkellä ainoat virheet, jossa käyttäjältä vaaditaan toimia, on tilanne
jossa konfiguraatiotiedostoa ei löydy, ja tilanne, jossa käyttäjä yrittää lisätä vääräntyyppisen tiedoston (filechooserille
pitää saada syötettyä tiedoston polku, onnistuu vain Windowsilla). Tämänkaltaiset virheet olisi parasta näyttää esimerkiksi popup-ikkunalla. 

### Bugeja
joita ei ehditty korjata
- Kun mediasoitin soittaa äänitiedoston loppuun asti, toistonappia tai ruutua täytyy klikata kaksi kertaa ennen kuin samaa
tiedostoa aletaan soittamaan alusta. 
- Kun soitettavaa tiedostoa vaihdetaan kesken soiton ja toisto pysähtyy, toistonapin grafiikkaa ei muuteta takaisin play-kuvaan.
- `threshold` -Settingin arvo resettoituu MediaPlayer:n threshold-kentän vakioarvoon (-60) visualisaatiotyyppiä vaihdettaessa.
