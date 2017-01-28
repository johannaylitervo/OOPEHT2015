package tyokalut;

import java.io.*;
import apulaiset.*;
import lotkot.*;

/**
 * Simulaattori-luokka, joka huolehtii alkuliman ja lötköjen logiikasta.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Simulaattori {

   /*
    * 
    * Metodit
    *
    */
   
   /**
    * Luodaan lötköt populaatio-listalle.
    *
    * @param lista koko lötkö-populaatiosta.
    * @return jos taulukoinnissa tapahtuu virhe, palautetaan false-arvo, muuten true.
    */
   public static boolean lataaLotkot(OmaLista populaatio) {
         //Tyhjennetään lötkö lista jos halutaan ladata tiedot uudelleen.
            populaatio.tyhjenna();
            //Taulukoidaan tiedoston sisältö String-tyyppiseen taulukkoon ja tallennetaan
            //se.
            String[][] tiedostonSisalto = Tyokalut.taulukoiSisalto(Tyokalut.TIEDOSTO);
            
            //Jos taulukoinnissa tapahtui virhe, palautetaan false-arvo.
            if(tiedostonSisalto == null) {
               return false;
            }
            //Jos ei, luodaan ladatun tiedoston arvoilla uudet lötköt ja sijoitetaan ne 
            //lötköjen populaatio-listaan.
            else {
               //Luodaan uusi apu olio.
               Object apu = new Object();
               for(int i = 0; i < tiedostonSisalto.length; i++) {
                  apu = luoLotko(tiedostonSisalto[i]);
                  
                  //Jos lötkön luonti onnistui lisätään se populaatio-listaan.
                  if(apu != null) {
                     populaatio.lisaaLoppuun(apu);
                  }
               }
            }
            return true;
   
   
   
   }

   /**
    * Kaikki listan lötköt lisääntyvät.
    *
    * @param lista kaikista lötköistä.
    * @throws IllegalArgumentException poikkeuksen, jos lista on tyhjä.
    */
   public static void lisaanny(OmaLista populaatio) throws IllegalArgumentException {
      try {
         //Jos listalla on edes yksi lötkö jatketaan.
         if(populaatio.koko() > 0) {
            //Kopioidaan populaatio lista, jotta voidaan poistaa kopiolistalta jo käsitellyt
            //alkiot.
            OmaLista kopioPopulaatio = populaatio.kopioiLista();      
         
            if(kopioPopulaatio != null) {
            
               /*Käydään kopiolistaa niin kauan läpi, että kaikki eri paikoissa sijaitsevat
               *lötköt on käyty läpi.
               */
               //Lippumuuttuja.
               boolean jatketaan = true;
            
               //Luodaan apulistat lötköille, jotka sijaitsevat samassa paikassa, sekä
               //omat listat erikseen klimpeille ja plänteille. 
               OmaLista lotkotPaikassa = new OmaLista();
               OmaLista klimpitPaikassa = new OmaLista();
               OmaLista plantitPaikassa = new OmaLista();
            
               //Luodaan myös kaksi apulistaa, jos lisääntyminen onnistui tilanteelle, johon
               //sijoitetaan luotu jälkeläinen sekä vanhempi. (Klimppien tapauksessa listalle
               //lisätään isi-Klimppi.)
               OmaLista lisaantynytKlimppi = new OmaLista();
               OmaLista lisaantynytPlantti = new OmaLista();
            
               //Apuolio paikalle sekä muuttuja tiedolle, kumpi lötköistä lisääntyi ensin.
               Paikka paikka;

               int kumpiEnsin = 0;
            
               while(jatketaan) {
                  //Tyhjennetään kaikki listat.
                 
                  lotkotPaikassa.tyhjenna();
                  klimpitPaikassa.tyhjenna();
                  plantitPaikassa.tyhjenna();
                  lisaantynytKlimppi.tyhjenna();
                  lisaantynytPlantti.tyhjenna();
               
                  //Otetaan tutkittavaksi paikaksi kopioPopulaation ensimmäisen alkion paikka.
                  Lotko alkionPaikka = (Lotko)(kopioPopulaatio.alkio(0));
                  paikka = alkionPaikka.paikka();
                  //Listataan kopiolistan ensimmäisen alkion kanssa samassa paikassa
                  //olevat lötköt.               
                  lotkotPaikassa = etsiPaikasta(populaatio, paikka);
               
                  //Listataan paikassa sijaitsevat klimpit ja pläntit omille listoilleen.
                  klimpitPaikassa = listaaKlimpit(lotkotPaikassa);
                  plantitPaikassa = listaaPlantit(lotkotPaikassa);
               
               
                  if(plantitPaikassa.koko() >= 1) {
                     lisaantynytPlantti.lisaaListaLoppuun(lisaannyPlantti(plantitPaikassa));
                  }
               
                 if(klimpitPaikassa.koko() > 1) {
                     lisaantynytKlimppi.lisaaListaLoppuun(lisaannyKlimppi(klimpitPaikassa));                 
                  }               
                  //Tutkitaan kumpi lisääntyi ensin vertailemalla vanhempien alkioita populaatio
                  //listaan.
                  if((lisaantynytPlantti.koko() == 2 ) && (lisaantynytKlimppi.koko() == 2)) {
                     kumpiEnsin = populaatio.tutkiKumpiEnsin(lisaantynytPlantti.alkio(0),
                                 lisaantynytKlimppi.alkio(0));
                     if(kumpiEnsin == 1) {
                        populaatio.lisaaLoppuun(lisaantynytPlantti.alkio(1));
                        populaatio.lisaaLoppuun(lisaantynytKlimppi.alkio(1));
                     }
                     else if(kumpiEnsin == 2) {
                        populaatio.lisaaLoppuun(lisaantynytKlimppi.alkio(1));
                        populaatio.lisaaLoppuun(lisaantynytPlantti.alkio(1));
                     }
                  }
               
                  //Jos vain toinen lisääntyi, lisätään se populaatio-listaan.
                  else if(lisaantynytPlantti.koko() == 2 && lisaantynytKlimppi.koko() != 2 ) {
                     populaatio.lisaaLoppuun(lisaantynytPlantti.alkio(1));
                  }
                  else if(lisaantynytPlantti.koko() != 2 && lisaantynytKlimppi.koko() == 2) {
                     populaatio.lisaaLoppuun(lisaantynytKlimppi.alkio(1));
                  }

                  //Poistetaan jo tutkitut alkiot kopioPopulaatio-listalta.
                  kopioPopulaatio.poistaListanAlkiot(lotkotPaikassa);
               
                  //Tarkistetaan jatkon tarve.
                  if(kopioPopulaatio.koko() == 1 && !(kopioPopulaatio.alkio(0) instanceof Plantti)
                      || kopioPopulaatio.koko() == 0) {
                     jatketaan = false;
                  }
               }
            }
         }
         //Jos lista on tyhjä heitetään poikkeus.
         else
            throw new IllegalArgumentException();
      }
      catch(Exception e) {
         throw new IllegalArgumentException(e);
      }
   } 
   
   /**
    * Liikutetaan kaikkia lötköjä naapuri paikkoihin.
    *
    * @param lista kaikista lötköistä
    * @throws IllegalArgumentException:in jos tapahtuu joku poikkeus.
    */
   public static void liiku(OmaLista lotkot) throws IllegalArgumentException {
      //Alustetaan kaksialkioinen taulukko lötkön uudelle paikalle.
      //int[] paikat = new int[2];
      try {
         //Liikuttaa lötkölistan jokaista lötköä yksi kerrallaan.
         for(int i = 0; i < lotkot.koko(); i++) {
            //Otetaan tutkittavaksi 
            Lotko liikutettava = (Lotko)(lotkot.alkio(i));

            //Arvotaan lötkölle uusipaikka
            int[] paikat = Automaatti.annaPaikka(liikutettava.paikka().xKoord(), liikutettava.paikka().yKoord(),
                  Tyokalut.xMax(), Tyokalut.yMax());

            //Sijoitetaan uudet arvot lötkön paikka-olioon.
            liikutettava.paikka().xKoord(paikat[0]);
            liikutettava.paikka().yKoord(paikat[1]);
      
         }
      }
      catch(Exception e) {
         throw new IllegalArgumentException(e);
      }
      
   }
   /**
    * Liikutetaan yhtä lötköä haluttuun paikkaan.
    *
    * @param käyttäjän antama komento, josta tarvitaan lötkön indeksi ja uuden paikan 
    * koordinaatit
    * @param lista kaikista lötköistä
    * @throws IllegalArgumentException, jos liikkuminen ei onnistunut, parametrit eivät ole
    * sallituissa rajoissa tai komento on tyhjä.
    */
   public static void liikutaLotkoa(String komento, OmaLista populaatio) throws IllegalArgumentException {
      try {
         //Tallennetaan komennon muunnetut parametrit taulukkoon.
         int[] parametrit = Tyokalut.muunnaKomento(komento);
         if(parametrit != null) {
            //Tarkistetaan, että listalta löytyy indeksin mukainen lötkö.
            if(parametrit[0] <= populaatio.koko()) {
               //Liikutetaan haluttua lötköä uuteen paikkaan ja heitetään poikkeus jos 
               //koordinaatit eivät olleet sallituissa rajoissa.
               Lotko liikutettava = (Lotko)(populaatio.alkio(parametrit[0]));
               boolean onnistuiko = liikutettava.paikka().liikuPaikkaan(parametrit[1], 
                  parametrit[2]);
               if(!onnistuiko) {
                  throw new IllegalArgumentException();
               }
            }
            //Jos liikutettavan lötkön indeksi oli virheellinen heitetään poikkeus.
            else {
               throw new IllegalArgumentException();
            }
         }
         else {
            throw new IllegalArgumentException();
         }
      }
      catch (Exception e) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
    * Luodaan uusi lötkö, jonka arvoiksi asetetaan latauksessa saadun 
    * tiedoston yhden rivin arvot. Metodi palauttaa null-arvon jos tapahtui virhe.
    * 
    * @param arvot joiden laiseksi lötkö luodaan.
    * @return luotu lötkö, jos tiedostossa on ollut virheelliset arvot palautetaan null. 
    */
   
   public static Lotko luoLotko(String[] arvot) {
      try {
         if(arvot[0].equals(Tyokalut.KLIMPPI)) {
            
            //Luodaan uusi StringBuilder olio perimää varten. Perimä on taulukossa 3. alkiossa.
            StringBuilder perima = new StringBuilder(arvot[2]);
            
            //Luodaan uusi Klimppi.
            Klimppi lotko = new Klimppi(Integer.parseInt(arvot[1]), perima, arvot[3].charAt(0));
            
            //Asetetaan klimpille paikka.
            int[] paikkaArvot = Automaatti.annaPaikka(Tyokalut.xMax(), Tyokalut.yMax());
            if(paikkaArvot != null) {
               lotko.paikka().xKoord(paikkaArvot[0]);
               lotko.paikka().yKoord(paikkaArvot[1]);
            }
            
            //Palautetaan luotu lötkö.
            return lotko;
         }
         else if(arvot[0].equals(Tyokalut.PLANTTI)) {
            //Luodaan uusi StringBuilder olio perimää varten. Perimä on taulukossa 3. alkiossa.
            StringBuilder perima = new StringBuilder(arvot[2]);
            //Tutkitaan pläntin muoto ja tallennetaan se muuttujaan.
            Boolean muoto;
            if(arvot[3].equals("true")) {
               muoto = true;
            }
            else if(arvot[3].equals("false")) {
               muoto = false;
            }
            else
               return null;
            
            //Luodaan uusi Pläntti.
            Plantti lotko = new Plantti(Integer.parseInt(arvot[1]), perima, muoto);
            
            //Asetetaan pläntille paikka.
            int[] paikkaArvot = Automaatti.annaPaikka(Tyokalut.xMax(), Tyokalut.yMax());
            if(paikkaArvot != null) {
               lotko.paikka().xKoord(paikkaArvot[0]);
               lotko.paikka().yKoord(paikkaArvot[1]);
            }
           
                        
            //Palautetaan luotu lötkö.
            return lotko;            
         }
         else 
            return null;
      }
      catch(Exception e) {
         return null;
      }
   }
   
   /**
    * Hakee kaikki halutussa paikassa sijaitsevat lötköt.
    *
    * @param lista lötköistä joista haetaan.
    * @param paikka, jossa sijaitsevia etsitään.
    * @return listan paikassa sijaitsevista lötköistä. Lista on tyhjä jos paikassa ei 
    * sijaitse lötköjä tai palauttaa null-arvon jos tapahtui virhe.
    */
   public static OmaLista etsiPaikasta(OmaLista lotkot, Paikka p) {
      try {
         //Luodaan uusi palautettava lista.
         OmaLista paikassa = new OmaLista();
         //Käydään lotko lista läpi alkio kerrallaan ja lisätään lötkö palautettavalle
         //listalle jos se sijaitsee samassa paikassa.
         for(int i = 0; i < lotkot.koko(); i++) {
            Lotko lotko = (Lotko)lotkot.alkio(i);
            if(p.equals(lotko.paikka())) {
               paikassa.lisaaLoppuun(lotkot.alkio(i));
            }
         }
         
         //Palautetaan lista lötköistä, jotka sijaitsevat tutkittavassa paikassa.
         return paikassa;
      }
      catch(Exception e) {
         return null;
      }
   
   }      
   
   /**
    * Listataan kaikki Klimpit samaan listaan
    *
    * @param lista joista listataan klimpit.
    * @return lista klimpeistä. Null arvo, jos tapahtui virhe.
    */ 
   public static OmaLista listaaKlimpit(OmaLista paikassaOlevat) {
      try {
         OmaLista paikanKlimpit = new OmaLista();
         Lotko tutkittava;
         for(int i = 0; i < paikassaOlevat.koko(); i++) {
            tutkittava = (Lotko)paikassaOlevat.alkio(i);
            if(tutkittava instanceof Klimppi) {
               paikanKlimpit.lisaaLoppuun(tutkittava);
            }
         }
         return paikanKlimpit;
      }
      catch(Exception e) {
         return null;
      } 
   }

   /**
    * Listataan kaikki pläntit samaan listaan
    *
    * @param lista joista listataan pläntit.
    * @return lista plänteistä. Null arvo, jos tapahtui virhe.
    */
   public static OmaLista listaaPlantit(OmaLista paikassaOlevat) {
      try {
         OmaLista paikanPlantit = new OmaLista();
         Lotko tutkittava;
         for(int i = 0; i < paikassaOlevat.koko(); i++) {
            tutkittava = (Lotko)paikassaOlevat.alkio(i);
            if(tutkittava instanceof Plantti) {
               paikanPlantit.lisaaLoppuun(tutkittava);
            }
         }
         return paikanPlantit;
      }
      catch(Exception e) {
         return null;
      } 
   }
   
   /**
    * Pläntti lisääntyy.
    *
    * @param lista plänteistä joista joku mahdollisesti lisääntyy.
    * @return lista, jossa on ensimmäisenä pläntti-vanhempi ja toisena luotu jälkeläinen, 
    * jos ei päästy lisääntymään, lista on tyhjä.
    */
   public static OmaLista lisaannyPlantti(OmaLista paikanPlantit) {
      //try {
         Plantti jalkelainen = null;
         OmaLista lisaantyneetP = new OmaLista();
         
         //Jos paikassa on vain yksi pläntti, se lisääntyy heti.
         if(paikanPlantit.koko() == 1) {
            jalkelainen = new Plantti((Plantti)paikanPlantit.alkio(0));
            //Lisätään oliot listalle ja palautetaan se.
            lisaantyneetP.lisaaLoppuun((Plantti)paikanPlantit.alkio(0));
            lisaantyneetP.lisaaLoppuun(jalkelainen);
            return lisaantyneetP;
         }
         else {
            //Etsitään suurin pläntti.
            Plantti suurin = (Plantti)paikanPlantit.haeSuurin();
            //Jos löydettiin suurin:
            if(suurin != null) {
               OmaLista samat = paikanPlantit.haeSamanlaiset((Object)suurin);
               //Jos ei löytynyt toista samanlaista plänttiä, pläntti lisääntyy.
               if(samat.koko() == 1) {
                  jalkelainen = new Plantti(suurin);
                  lisaantyneetP.lisaaLoppuun(suurin);
                  lisaantyneetP.lisaaLoppuun(jalkelainen);
                  
                  return lisaantyneetP;
               }
            }
         }
         
         return lisaantyneetP;
      /*}
      catch(Exception e) {
         return null;
      }*/
   }
   
   /**
    * Klimppi lisääntyy. 
    *
    * @param lista klimpeistä, joista jotku mahdollisesti lisääntyvät.
    * @return lista, jossa on ensimmäisenä ensimmäinen klimppi-vanhempi ja toisena 
    * luotu jälkeläinen, jos ei päästy lisääntymään, lista on tyhjä.
    */ 
    
   public static OmaLista lisaannyKlimppi(OmaLista paikanKlimpit) {
         //Luodaan olio jälkeläiselle sekä palautettavalle listalle.
         Klimppi jalkelainen = null;
         OmaLista lisaantyneet = new OmaLista();
         
         /*Tutkitaan lista läpi löytyykö samanlaista klimppiä.
         */
         //Tehdään uusi lista samanlaisille alkioille.
         OmaLista samanlaiset = new OmaLista();
         boolean jatketaan = true;
         int i = 0;
         while(jatketaan) {
            samanlaiset = paikanKlimpit.haeSamanlaiset((Klimppi)paikanKlimpit.alkio(i));
            //Jos löytyi vähintään kaksi samanlaista, lopetetaan etsintä.
            if(samanlaiset.koko() > 1) {
               jatketaan = false;
            }
            //Jos ei, lisätään kierroslaskuriin yksi ja tyhjennetään apulista.
            else {
               //Tutkitaan onko viimeinen kierros. Jos on poistutaan silmukasta.
               if(i == (paikanKlimpit.koko() - 1)) {
                  jatketaan = false;
               }
               else {
                  i++;
                  samanlaiset.tyhjenna();
               }
            }
         }
      
         //Jos löydettiin ainakin kaksi samanlaista klimppiä, lisäännytään.
         if(samanlaiset.koko() > 1) {
            jalkelainen = new Klimppi((Klimppi)samanlaiset.alkio(0), (Klimppi)samanlaiset.alkio(1));
            //Lisätään isi ja jälkeläinen listalle.
            lisaantyneet.lisaaLoppuun((Klimppi)samanlaiset.alkio(0));
            lisaantyneet.lisaaLoppuun(jalkelainen);
            
            //Palautetaan lista.
            return lisaantyneet;
         }
         
         //Jos ei luotu uutta oliota, palautetaan null.
         return lisaantyneet;
   }
   
   
   /**
    * Tulostetaan kaikkien listan lötköjen merkkijonoesitys. Toisena parametrina populaatio-lista
    *
    * @param lista, jonka lötköt halutaan listata.
    * @param kaikki populaation lötköt.
    * @throws IllegalArgumentException jos tuloste on null-arvoinen.
    */   
   public static void listaaListalta(OmaLista listattavat, OmaLista populaatio) throws IllegalArgumentException {
      //Tutkitaan että listalla on sisältöä jolloin voidaan suorittaa jatko tutkimukset.
      if(listattavat != null && listattavat.koko() > 0) {
         //Esitellään muuttuja lötkön indeksille.
         String indeksiTuloste = "";
         //Aletaan listata lötköjä listattavalta listalta.
         for(int i = 0; i < listattavat.koko(); i++) {
            //Jos viitteet ovat samaan listaan.
            indeksiTuloste = populaatio.haeIndeksi(listattavat.alkio(i));
            
            if(indeksiTuloste != null) {
               //Tulostetaan alkio indeksin kanssa.
               System.out.println(indeksiTuloste + listattavat.alkio(i).toString());
            }
            else
               throw new IllegalArgumentException();
         }
      }
      //Jos tutkittavassa listassa ei ollut alkioita.
      else if(listattavat.koko()==0) {
         
      }   
   }    
         
}