# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus on ääntä ja erityisesti musiikkia visualisoiva mediasoitin. 
Ohjelman käyttöliittymästä valitaan äänilähteeksi äänitiedosto,
ja ohjelma piirtää äänilähteeseen reagoivan visualisoinnin. 
Visualisointia ohjaavia parametreja voi muuttaa käyttöliittymästä.

## Sovelluksen toiminnallisuus
#### Käyttöliittymä
Sovelluksessa on yksi päänäkymä, jonka keskelle piirretään visualisaatio. Ikkunan neljällä sivulla on aukeavat valikot:
  
  Vasemmalla: äänitiedostojen hallinta. Tästä voi lisätä valikkoon uusia äänitiedostoja (tehty), valita soitettava äänitiedosto (tehty) ja poistaa valikosta tiedostoja.
  Alhaalla: äänentoiston hallinta. Toistonappi (tehty) ja volyymislideri.
  Oikealla: visualisaation hallinta. Tästä voidaan valita eri visualisaatiomalleja ja hallita slidereilla jotain niiden parametreista.
  Ylhäällä: sovelluksen käyttöohje

Lisäksi äänentoistoa voi hallita klikkaamalla näkymää ja kokonäytön tilaan voidaan siirtyä painamalla f-näppäintä. (tehty)

#### Visualisaatio
Visualisaatio toteutetaan JavaFX:n MediaPlayer-olion audioSpectrumListener-observerilla. Tämä tarkkailee äänilähteen taajuuskanavien magnitudeja, joiden perusteella voidaan piirtää erilaisia visualisaatioita. Yksinkertainen visualisaatio piirtää rivin suorakulmioita, joiden korkeutta päivitetään jatkuvasti vastaamaan magnitudeja. (tehty)

### Perusversio
Perusversiossa toteutetaan vain kaikkein keskeisimmät toiminnot. Sovellukseen valitaan äänitiedostoja, joista valitaan yksi soitettavaksi. Näkymän keskiöön piirretään yksinkertainen visualisaatio.

### Jatkokehittely
Useita erilaisia visualisaatioita, joista voidaan valita sivupalkin valikolla. Visualisaatioiden parametreja voidaan säätää sivupalkista.
