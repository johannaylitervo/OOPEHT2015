package tyokalut;

import fi.uta.csjola.oope.lista.*;
//import tyokalut.*;
import lotkot.*;

/**
 * LinkitettyLista-luokasta peritty OmaLista-luokka.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class OmaLista extends LinkitettyLista {
   
   /*
    * Rakentaja
    */
    
   public OmaLista() {
      super();
   }
   
   /*
    * Metodit
    */

   /**
    * Hakee alkion indeksin listalta.
    *
    * @param olio, jota etsitään listalta.
    * @return alkion indeksi String muotoisena, jotta se saadaan heti tulostettua.
    */   
   public String haeIndeksi(Object haettava) {
      if(haettava != null) {
         //Käydään lista alkio kerrallaan läpi laskurin kanssa kunnes löydetään etsitty
         //alkio tai listan pituus loppuu.
         boolean jatketaan = true;
         int laskuri = 0;
         while(jatketaan) {
            if(haettava == (Object)alkio(laskuri)) {
               jatketaan = false;
            }
            else {
               laskuri++;
               if(laskuri == koko()) {
                  jatketaan = false;
                  //Jos alkiota ei löytynyt, palautetaan null.
                  return null;
               }
            }
         }
         //Muutetaan indeksi haluttuun muotoon.
         String tulostettava = "" + laskuri;
         while(tulostettava.length() < 3) {
            tulostettava = tulostettava + Tyokalut.VALI;
         }
         //Lisätään erotin ja palautetaan merkkijono.
         tulostettava = tulostettava + Tyokalut.EROTIN;
         return tulostettava;
      }
      return null;
   }
   
   /**
    * Kopioi listan apulistaan. 
    *
    * @return viite luotuun kopiolistaan. Paluuarvo on null, jos lista oli tyhjä.
    */
   public OmaLista kopioiLista() {
      if(koko() > 0) {
         //Luodaan uusi lista johon kopio tallennetaan.
         OmaLista kopio = new OmaLista();
         //Käydään lista läpi alkio kerrallaan ja kopioidaan jokainen alkio kopiolistaan.
         for(int i = 0; i < koko(); i++) {
            kopio.lisaaLoppuun(alkio(i));
         }
         //Palautetaan viite kopiolistaan.
         return kopio;
      }
      else
         return null;
   }
    
   /**
    * Tyhjentää listan. 
    *
    */
   public void tyhjenna() {
      while(!onkoTyhja()) {
         poistaLopusta();
      }
   } 

   /**
    * Lisää listan loppuun parametrina annetun listan.
    *
    * @param lista, joka halutaan lisätä.
    * @return false jos lisättävä lista tyhjä.
    */
   public boolean lisaaListaLoppuun(OmaLista lisattava) {
      if(lisattava.koko() > 0) {
         for(int i = 0; i < lisattava.koko(); i++) {
            lisaaLoppuun(lisattava.alkio(i));
         }
         
         return true;
      }
      else
         return false;
   }

   /**
    * Poistaa listalta parametrina annetun listan alkiot.
    *
    * @param lista alkioista, jotka halutaan poistaa päälistalta.
    * @return false jos tapahtui virhe, muuten true.
    */
   public boolean poistaListanAlkiot(OmaLista poistettavat) {
      try {
         //Tutkitaan että molemmissa listoissa on alkioita eikä poistettavassa listassa ole 
         //enemmän alkioita kun listassa josta ne poistetaan.
         if(koko()>= poistettavat.koko() && koko() > 0 && poistettavat.koko() > 0) {
            //Käydään kahden sisäkkäisen for-silmukan avulla läpi molemmat listat.
            for(int i = 0; i < poistettavat.koko(); i++) {
               for(int j = 0; j < koko(); j++) {
                  //Tutkitaan löytyykö viite samaan alkioon listalta jolta halutaan poistaa.
                  if(alkio(j) == poistettavat.alkio(i)) {
                     //Poistetaan alkio päälistalta.
                     poista(j);
                  }
               }
            }
            return true;
         }
         else
            return false;
      }
      catch(Exception e) {
         return false;
      }
   }   
   /**
    * Hakee listan suurimman alkion.
    *
    * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhjä.
    */
   
   // Lisämääre, jolla kääntäjälle vakuutetaan, että metodin koodi on turvallista.
   @SuppressWarnings({"unchecked"})
   
   public Object haeSuurin() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // Tämän hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      int i = 0;
      while (i < koko()) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)alkio(i);

         // Löydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sisältävään alkioon.
            suurin = vertailtava;

         // Siirrytään seuraavaan paikkaan.
         i++;
      }

      // Palautetaan suurimpaan alkioon liittyvä viite.
      return suurin;
   }    
   
   /**
    * Etsii listalta alkioita. 
    *
    * @param alkio, jonka laisia etsitään.
    * @return lista löydetyistä alkioista. Null, jos lista tyhjä tai ei samanlaisia alkioita.
    */
   public OmaLista haeSamanlaiset(Object haettava) {
      
      //Tarkistetaan ettei tutkittava lista ole tyhjä.
      if(koko() > 0) {
         //Luodaan uusi lista palautusta varten.
         OmaLista loydetyt = new OmaLista();
         
         //Käydään lista läpi alkio kerrallaan, vertaillaan tutkittavaa alkiota
         //alkioon jonkalaista etsitään ja lisätään se löydettyjen listaan jos
         //alkiot ovat samanlaiset.
         for(int i = 0; i < koko() ; i++) {
            if(alkio(i).equals(haettava)) {
               loydetyt.lisaaLoppuun(alkio(i));
            }
         }
         //Tutkitaan, onko luotu apulista tyhjä, jos ei palautetaan lista, jos on 
         //palautetaan null.
         if(loydetyt.koko() > 0) {
            return loydetyt;
         }
         else
            return null;
      }
      else
         return null; 
   }
   
   /**
    * Tutkii kumpi alkioista löytyy ensimmäisenä listalta.
    *
    * @return 1 jos se oli a, tai 2 jos se oli b. Jos tapahtuu virhe, tai annettu 
    * lista/parametrit virheellisiä, palautetaan 0.
    */
   public int tutkiKumpiEnsin(Object a, Object b) {
      try {
         //Esitellään muuttuja vertailun helpottamiseksi
         Object tutkittava;
         //Aletaan käydä listaa läpi alkio kerrallaan ja tutkitaan alkioiden viitteitä.
         for(int i = 0; i < koko(); i++) {
            tutkittava = (Object)alkio(i);
            if(tutkittava == a) {
               return 1;
            }
            else if(tutkittava == b) {
               return 2;
            }
         }
      }
      catch(Exception e) {
         return 0;
      }
      //Jos kumpaakaan alkiota ei löytynyt listalta, eikä tapahtunut virhettä palautetaan
      //null.
      return 0;
   } 
   
}