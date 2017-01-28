package tyokalut;

import java.io.*;
import apulaiset.*;
import lotkot.*;

/**
 * Simulaattori-luokka, joka huolehtii alkuliman ja l�tk�jen logiikasta.
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
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
    * Luodaan l�tk�t populaatio-listalle.
    *
    * @param lista koko l�tk�-populaatiosta.
    * @return jos taulukoinnissa tapahtuu virhe, palautetaan false-arvo, muuten true.
    */
   public static boolean lataaLotkot(OmaLista populaatio) {
         //Tyhjennet��n l�tk� lista jos halutaan ladata tiedot uudelleen.
            populaatio.tyhjenna();
            //Taulukoidaan tiedoston sis�lt� String-tyyppiseen taulukkoon ja tallennetaan
            //se.
            String[][] tiedostonSisalto = Tyokalut.taulukoiSisalto(Tyokalut.TIEDOSTO);
            
            //Jos taulukoinnissa tapahtui virhe, palautetaan false-arvo.
            if(tiedostonSisalto == null) {
               return false;
            }
            //Jos ei, luodaan ladatun tiedoston arvoilla uudet l�tk�t ja sijoitetaan ne 
            //l�tk�jen populaatio-listaan.
            else {
               //Luodaan uusi apu olio.
               Object apu = new Object();
               for(int i = 0; i < tiedostonSisalto.length; i++) {
                  apu = luoLotko(tiedostonSisalto[i]);
                  
                  //Jos l�tk�n luonti onnistui lis�t��n se populaatio-listaan.
                  if(apu != null) {
                     populaatio.lisaaLoppuun(apu);
                  }
               }
            }
            return true;
   
   
   
   }

   /**
    * Kaikki listan l�tk�t lis��ntyv�t.
    *
    * @param lista kaikista l�tk�ist�.
    * @throws IllegalArgumentException poikkeuksen, jos lista on tyhj�.
    */
   public static void lisaanny(OmaLista populaatio) throws IllegalArgumentException {
      try {
         //Jos listalla on edes yksi l�tk� jatketaan.
         if(populaatio.koko() > 0) {
            //Kopioidaan populaatio lista, jotta voidaan poistaa kopiolistalta jo k�sitellyt
            //alkiot.
            OmaLista kopioPopulaatio = populaatio.kopioiLista();      
         
            if(kopioPopulaatio != null) {
            
               /*K�yd��n kopiolistaa niin kauan l�pi, ett� kaikki eri paikoissa sijaitsevat
               *l�tk�t on k�yty l�pi.
               */
               //Lippumuuttuja.
               boolean jatketaan = true;
            
               //Luodaan apulistat l�tk�ille, jotka sijaitsevat samassa paikassa, sek�
               //omat listat erikseen klimpeille ja pl�nteille. 
               OmaLista lotkotPaikassa = new OmaLista();
               OmaLista klimpitPaikassa = new OmaLista();
               OmaLista plantitPaikassa = new OmaLista();
            
               //Luodaan my�s kaksi apulistaa, jos lis��ntyminen onnistui tilanteelle, johon
               //sijoitetaan luotu j�lkel�inen sek� vanhempi. (Klimppien tapauksessa listalle
               //lis�t��n isi-Klimppi.)
               OmaLista lisaantynytKlimppi = new OmaLista();
               OmaLista lisaantynytPlantti = new OmaLista();
            
               //Apuolio paikalle sek� muuttuja tiedolle, kumpi l�tk�ist� lis��ntyi ensin.
               Paikka paikka;

               int kumpiEnsin = 0;
            
               while(jatketaan) {
                  //Tyhjennet��n kaikki listat.
                 
                  lotkotPaikassa.tyhjenna();
                  klimpitPaikassa.tyhjenna();
                  plantitPaikassa.tyhjenna();
                  lisaantynytKlimppi.tyhjenna();
                  lisaantynytPlantti.tyhjenna();
               
                  //Otetaan tutkittavaksi paikaksi kopioPopulaation ensimm�isen alkion paikka.
                  Lotko alkionPaikka = (Lotko)(kopioPopulaatio.alkio(0));
                  paikka = alkionPaikka.paikka();
                  //Listataan kopiolistan ensimm�isen alkion kanssa samassa paikassa
                  //olevat l�tk�t.               
                  lotkotPaikassa = etsiPaikasta(populaatio, paikka);
               
                  //Listataan paikassa sijaitsevat klimpit ja pl�ntit omille listoilleen.
                  klimpitPaikassa = listaaKlimpit(lotkotPaikassa);
                  plantitPaikassa = listaaPlantit(lotkotPaikassa);
               
               
                  if(plantitPaikassa.koko() >= 1) {
                     lisaantynytPlantti.lisaaListaLoppuun(lisaannyPlantti(plantitPaikassa));
                  }
               
                 if(klimpitPaikassa.koko() > 1) {
                     lisaantynytKlimppi.lisaaListaLoppuun(lisaannyKlimppi(klimpitPaikassa));                 
                  }               
                  //Tutkitaan kumpi lis��ntyi ensin vertailemalla vanhempien alkioita populaatio
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
               
                  //Jos vain toinen lis��ntyi, lis�t��n se populaatio-listaan.
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
         //Jos lista on tyhj� heitet��n poikkeus.
         else
            throw new IllegalArgumentException();
      }
      catch(Exception e) {
         throw new IllegalArgumentException(e);
      }
   } 
   
   /**
    * Liikutetaan kaikkia l�tk�j� naapuri paikkoihin.
    *
    * @param lista kaikista l�tk�ist�
    * @throws IllegalArgumentException:in jos tapahtuu joku poikkeus.
    */
   public static void liiku(OmaLista lotkot) throws IllegalArgumentException {
      //Alustetaan kaksialkioinen taulukko l�tk�n uudelle paikalle.
      //int[] paikat = new int[2];
      try {
         //Liikuttaa l�tk�listan jokaista l�tk�� yksi kerrallaan.
         for(int i = 0; i < lotkot.koko(); i++) {
            //Otetaan tutkittavaksi 
            Lotko liikutettava = (Lotko)(lotkot.alkio(i));

            //Arvotaan l�tk�lle uusipaikka
            int[] paikat = Automaatti.annaPaikka(liikutettava.paikka().xKoord(), liikutettava.paikka().yKoord(),
                  Tyokalut.xMax(), Tyokalut.yMax());

            //Sijoitetaan uudet arvot l�tk�n paikka-olioon.
            liikutettava.paikka().xKoord(paikat[0]);
            liikutettava.paikka().yKoord(paikat[1]);
      
         }
      }
      catch(Exception e) {
         throw new IllegalArgumentException(e);
      }
      
   }
   /**
    * Liikutetaan yht� l�tk�� haluttuun paikkaan.
    *
    * @param k�ytt�j�n antama komento, josta tarvitaan l�tk�n indeksi ja uuden paikan 
    * koordinaatit
    * @param lista kaikista l�tk�ist�
    * @throws IllegalArgumentException, jos liikkuminen ei onnistunut, parametrit eiv�t ole
    * sallituissa rajoissa tai komento on tyhj�.
    */
   public static void liikutaLotkoa(String komento, OmaLista populaatio) throws IllegalArgumentException {
      try {
         //Tallennetaan komennon muunnetut parametrit taulukkoon.
         int[] parametrit = Tyokalut.muunnaKomento(komento);
         if(parametrit != null) {
            //Tarkistetaan, ett� listalta l�ytyy indeksin mukainen l�tk�.
            if(parametrit[0] <= populaatio.koko()) {
               //Liikutetaan haluttua l�tk�� uuteen paikkaan ja heitet��n poikkeus jos 
               //koordinaatit eiv�t olleet sallituissa rajoissa.
               Lotko liikutettava = (Lotko)(populaatio.alkio(parametrit[0]));
               boolean onnistuiko = liikutettava.paikka().liikuPaikkaan(parametrit[1], 
                  parametrit[2]);
               if(!onnistuiko) {
                  throw new IllegalArgumentException();
               }
            }
            //Jos liikutettavan l�tk�n indeksi oli virheellinen heitet��n poikkeus.
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
    * Luodaan uusi l�tk�, jonka arvoiksi asetetaan latauksessa saadun 
    * tiedoston yhden rivin arvot. Metodi palauttaa null-arvon jos tapahtui virhe.
    * 
    * @param arvot joiden laiseksi l�tk� luodaan.
    * @return luotu l�tk�, jos tiedostossa on ollut virheelliset arvot palautetaan null. 
    */
   
   public static Lotko luoLotko(String[] arvot) {
      try {
         if(arvot[0].equals(Tyokalut.KLIMPPI)) {
            
            //Luodaan uusi StringBuilder olio perim�� varten. Perim� on taulukossa 3. alkiossa.
            StringBuilder perima = new StringBuilder(arvot[2]);
            
            //Luodaan uusi Klimppi.
            Klimppi lotko = new Klimppi(Integer.parseInt(arvot[1]), perima, arvot[3].charAt(0));
            
            //Asetetaan klimpille paikka.
            int[] paikkaArvot = Automaatti.annaPaikka(Tyokalut.xMax(), Tyokalut.yMax());
            if(paikkaArvot != null) {
               lotko.paikka().xKoord(paikkaArvot[0]);
               lotko.paikka().yKoord(paikkaArvot[1]);
            }
            
            //Palautetaan luotu l�tk�.
            return lotko;
         }
         else if(arvot[0].equals(Tyokalut.PLANTTI)) {
            //Luodaan uusi StringBuilder olio perim�� varten. Perim� on taulukossa 3. alkiossa.
            StringBuilder perima = new StringBuilder(arvot[2]);
            //Tutkitaan pl�ntin muoto ja tallennetaan se muuttujaan.
            Boolean muoto;
            if(arvot[3].equals("true")) {
               muoto = true;
            }
            else if(arvot[3].equals("false")) {
               muoto = false;
            }
            else
               return null;
            
            //Luodaan uusi Pl�ntti.
            Plantti lotko = new Plantti(Integer.parseInt(arvot[1]), perima, muoto);
            
            //Asetetaan pl�ntille paikka.
            int[] paikkaArvot = Automaatti.annaPaikka(Tyokalut.xMax(), Tyokalut.yMax());
            if(paikkaArvot != null) {
               lotko.paikka().xKoord(paikkaArvot[0]);
               lotko.paikka().yKoord(paikkaArvot[1]);
            }
           
                        
            //Palautetaan luotu l�tk�.
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
    * Hakee kaikki halutussa paikassa sijaitsevat l�tk�t.
    *
    * @param lista l�tk�ist� joista haetaan.
    * @param paikka, jossa sijaitsevia etsit��n.
    * @return listan paikassa sijaitsevista l�tk�ist�. Lista on tyhj� jos paikassa ei 
    * sijaitse l�tk�j� tai palauttaa null-arvon jos tapahtui virhe.
    */
   public static OmaLista etsiPaikasta(OmaLista lotkot, Paikka p) {
      try {
         //Luodaan uusi palautettava lista.
         OmaLista paikassa = new OmaLista();
         //K�yd��n lotko lista l�pi alkio kerrallaan ja lis�t��n l�tk� palautettavalle
         //listalle jos se sijaitsee samassa paikassa.
         for(int i = 0; i < lotkot.koko(); i++) {
            Lotko lotko = (Lotko)lotkot.alkio(i);
            if(p.equals(lotko.paikka())) {
               paikassa.lisaaLoppuun(lotkot.alkio(i));
            }
         }
         
         //Palautetaan lista l�tk�ist�, jotka sijaitsevat tutkittavassa paikassa.
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
    * @return lista klimpeist�. Null arvo, jos tapahtui virhe.
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
    * Listataan kaikki pl�ntit samaan listaan
    *
    * @param lista joista listataan pl�ntit.
    * @return lista pl�nteist�. Null arvo, jos tapahtui virhe.
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
    * Pl�ntti lis��ntyy.
    *
    * @param lista pl�nteist� joista joku mahdollisesti lis��ntyy.
    * @return lista, jossa on ensimm�isen� pl�ntti-vanhempi ja toisena luotu j�lkel�inen, 
    * jos ei p��sty lis��ntym��n, lista on tyhj�.
    */
   public static OmaLista lisaannyPlantti(OmaLista paikanPlantit) {
      //try {
         Plantti jalkelainen = null;
         OmaLista lisaantyneetP = new OmaLista();
         
         //Jos paikassa on vain yksi pl�ntti, se lis��ntyy heti.
         if(paikanPlantit.koko() == 1) {
            jalkelainen = new Plantti((Plantti)paikanPlantit.alkio(0));
            //Lis�t��n oliot listalle ja palautetaan se.
            lisaantyneetP.lisaaLoppuun((Plantti)paikanPlantit.alkio(0));
            lisaantyneetP.lisaaLoppuun(jalkelainen);
            return lisaantyneetP;
         }
         else {
            //Etsit��n suurin pl�ntti.
            Plantti suurin = (Plantti)paikanPlantit.haeSuurin();
            //Jos l�ydettiin suurin:
            if(suurin != null) {
               OmaLista samat = paikanPlantit.haeSamanlaiset((Object)suurin);
               //Jos ei l�ytynyt toista samanlaista pl�ntti�, pl�ntti lis��ntyy.
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
    * Klimppi lis��ntyy. 
    *
    * @param lista klimpeist�, joista jotku mahdollisesti lis��ntyv�t.
    * @return lista, jossa on ensimm�isen� ensimm�inen klimppi-vanhempi ja toisena 
    * luotu j�lkel�inen, jos ei p��sty lis��ntym��n, lista on tyhj�.
    */ 
    
   public static OmaLista lisaannyKlimppi(OmaLista paikanKlimpit) {
         //Luodaan olio j�lkel�iselle sek� palautettavalle listalle.
         Klimppi jalkelainen = null;
         OmaLista lisaantyneet = new OmaLista();
         
         /*Tutkitaan lista l�pi l�ytyyk� samanlaista klimppi�.
         */
         //Tehd��n uusi lista samanlaisille alkioille.
         OmaLista samanlaiset = new OmaLista();
         boolean jatketaan = true;
         int i = 0;
         while(jatketaan) {
            samanlaiset = paikanKlimpit.haeSamanlaiset((Klimppi)paikanKlimpit.alkio(i));
            //Jos l�ytyi v�hint��n kaksi samanlaista, lopetetaan etsint�.
            if(samanlaiset.koko() > 1) {
               jatketaan = false;
            }
            //Jos ei, lis�t��n kierroslaskuriin yksi ja tyhjennet��n apulista.
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
      
         //Jos l�ydettiin ainakin kaksi samanlaista klimppi�, lis��nnyt��n.
         if(samanlaiset.koko() > 1) {
            jalkelainen = new Klimppi((Klimppi)samanlaiset.alkio(0), (Klimppi)samanlaiset.alkio(1));
            //Lis�t��n isi ja j�lkel�inen listalle.
            lisaantyneet.lisaaLoppuun((Klimppi)samanlaiset.alkio(0));
            lisaantyneet.lisaaLoppuun(jalkelainen);
            
            //Palautetaan lista.
            return lisaantyneet;
         }
         
         //Jos ei luotu uutta oliota, palautetaan null.
         return lisaantyneet;
   }
   
   
   /**
    * Tulostetaan kaikkien listan l�tk�jen merkkijonoesitys. Toisena parametrina populaatio-lista
    *
    * @param lista, jonka l�tk�t halutaan listata.
    * @param kaikki populaation l�tk�t.
    * @throws IllegalArgumentException jos tuloste on null-arvoinen.
    */   
   public static void listaaListalta(OmaLista listattavat, OmaLista populaatio) throws IllegalArgumentException {
      //Tutkitaan ett� listalla on sis�lt�� jolloin voidaan suorittaa jatko tutkimukset.
      if(listattavat != null && listattavat.koko() > 0) {
         //Esitell��n muuttuja l�tk�n indeksille.
         String indeksiTuloste = "";
         //Aletaan listata l�tk�j� listattavalta listalta.
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