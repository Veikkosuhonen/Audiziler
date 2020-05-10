Repositorio sisältää Helsingin yliopiston ohjelmistotekniikan kurssin harjoitustyönä tehdyn sovelluksen.

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/visualisaatio.png)

# Harjoitustyö

Harjoitustyö on äänen visualisointisovellus. Sovellukseen valitaan äänitiedosto ja sovellus piirtää äänen taajuusmagnitudeihin reagoivan visualisaation.

[Loppupalautus](https://github.com/Veikkosuhonen/ot-harjoitustyo/releases/tag/loppupalautus)

[Pre-release](https://github.com/Veikkosuhonen/ot-harjoitustyo/releases/tag/v0.1-alpha)

[Harjoitustyö toimivuudesta laitoksen koneilla](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/toimivuus_laitoksen_koneilla.md)

## Dokumentaatio

[INSTRUCTIONS](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/Instructions.md)

[Vaatimusmäärittely](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmäärittely.md)

[Arkkitehtuuri](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausraportti](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/testausraportti.md)

[Työaikakirjanpito](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

## Komentorivitoiminnot
### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn test jacoco:report`

Kattavuusraportti avataan tiedostosta target/site/jacoco/index.html
### Jar-tiedoston generointi

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston Audiziler-1.0-SNAPSHOT.jar
### JavaDocin generointi

`mvn javadoc:javadoc`

JavaDoc avataan tiedostosta target/site/apidocs/index.html
### Checkstyle

 `mvn checkstyle:checkstyle`

Checkstyle-raportti avataan tiedostosta target/site/checkstyle.html
