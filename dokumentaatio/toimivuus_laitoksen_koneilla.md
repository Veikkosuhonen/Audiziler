### Toimivuus laitoksen koneilla

Harjoitustyö toimii mainioisti Cubbli-linuxilla omalla fuksiläppärilläni ja Windows-koneella buildattu versio toimii normaalisti
kaikilla testatuilla Windows 10-koneilla (joissa on Java 11). Laitoksen koneilla (joita olen käyttänyt VMware Horizonilla) sovellus kääntyy, 
sen pystyy buildaamaan ja suoritettava buildi käynnistyy normaalisti, mutta käyttöliittymä räjähtää: 

![käyttöliittymä_hajoaa](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/app_breaking.png)

Kaikki UI-elementit kyllä löytyvät sovelluksesta, esimerkiksi sliderit ovat näkymättömiä mutta kuitenkin vedettävissä, ja 
css on latautunut jotenkuten.
