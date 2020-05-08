Repositorio sisältää Helsingin yliopiston ohjelmistotekniikan kurssin harjoitustyön ja laskarit.

![](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/visualisaatio.png)

# Harjoitustyö

Harjoitustyö on äänen visualisointisovellus. Sovellukseen valitaan äänitiedosto ja sovellus piirtää äänen taajuusmagnitudeihin reagoivan visualisaation.

## Dokumentaatio

[INSTRUCTIONS](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/Instructions.md)

[Vaatimusmäärittely](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmäärittely.md)

[Arkkitehtuuri](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Latest release](https://github.com/Veikkosuhonen/ot-harjoitustyo/releases/tag/v0.1-alpha)

## Komentorivitoiminnot
### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn test jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html
### Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston Audiziler-1.0-SNAPSHOT.jar
### JavaDoc

JavaDoc generoidaan komennolla

`mvn javadoc:javadoc`

JavaDocia voi tarkastella avaamalla selaimella tiedosto target/site/apidocs/index.html
### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

 `mvn checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html
