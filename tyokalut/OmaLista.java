package tyokalut;

import fi.uta.csjola.oope.lista.*;
//import tyokalut.*;
import lotkot.*;

/**
 * LinkitettyLista-luokasta peritty OmaLista-luokka.
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
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
    * @param olio, jota etsit��n listalta.
    * @return alkion indeksi String muotoisena, jotta se saadaan heti tulostettua.
    */   
   public String haeIndeksi(Object haettava) {
      if(haettava != null) {
         //K�yd��n lista alkio kerrallaan l�pi laskurin kanssa kunnes l�ydet��n etsitty
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
                  //Jos alkiota ei l�ytynyt, palautetaan null.
                  return null;
               }
            }
         }
         //Muutetaan indeksi haluttuun muotoon.
         String tulostettava = "" + laskuri;
         while(tulostettava.length() < 3) {
            tulostettava = tulostettava + Tyokalut.VALI;
         }
         //Lis�t��n erotin ja palautetaan merkkijono.
         tulostettava = tulostettava + Tyokalut.EROTIN;
         return tulostettava;
      }
      return null;
   }
   
   /**
    * Kopioi listan apulistaan. 
    *
    * @return viite luotuun kopiolistaan. Paluuarvo on null, jos lista oli tyhj�.
    */
   public OmaLista kopioiLista() {
      if(koko() > 0) {
         //Luodaan uusi lista johon kopio tallennetaan.
         OmaLista kopio = new OmaLista();
         //K�yd��n lista l�pi alkio kerrallaan ja kopioidaan jokainen alkio kopiolistaan.
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
    * Tyhjent�� listan. 
    *
    */
   public void tyhjenna() {
      while(!onkoTyhja()) {
         poistaLopusta();
      }
   } 

   /**
    * Lis�� listan loppuun parametrina annetun listan.
    *
    * @param lista, joka halutaan lis�t�.
    * @return false jos lis�tt�v� lista tyhj�.
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
    * @param lista alkioista, jotka halutaan poistaa p��listalta.
    * @return false jos tapahtui virhe, muuten true.
    */
   public boolean poistaListanAlkiot(OmaLista poistettavat) {
      try {
         //Tutkitaan ett� molemmissa listoissa on alkioita eik� poistettavassa listassa ole 
         //enemm�n alkioita kun listassa josta ne poistetaan.
         if(koko()>= poistettavat.koko() && koko() > 0 && poistettavat.koko() > 0) {
            //K�yd��n kahden sis�kk�isen for-silmukan avulla l�pi molemmat listat.
            for(int i = 0; i < poistettavat.koko(); i++) {
               for(int j = 0; j < koko(); j++) {
                  //Tutkitaan l�ytyyk� viite samaan alkioon listalta jolta halutaan poistaa.
                  if(alkio(j) == poistettavat.alkio(i)) {
                     //Poistetaan alkio p��listalta.
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
    * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhj�.
    */
   
   // Lis�m��re, jolla k��nt�j�lle vakuutetaan, ett� metodin koodi on turvallista.
   @SuppressWarnings({"unchecked"})
   
   public Object haeSuurin() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // T�m�n hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      int i = 0;
      while (i < koko()) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)alkio(i);

         // L�ydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sis�lt�v��n alkioon.
            suurin = vertailtava;

         // Siirryt��n seuraavaan paikkaan.
         i++;
      }

      // Palautetaan suurimpaan alkioon liittyv� viite.
      return suurin;
   }    
   
   /**
    * Etsii listalta alkioita. 
    *
    * @param alkio, jonka laisia etsit��n.
    * @return lista l�ydetyist� alkioista. Null, jos lista tyhj� tai ei samanlaisia alkioita.
    */
   public OmaLista haeSamanlaiset(Object haettava) {
      
      //Tarkistetaan ettei tutkittava lista ole tyhj�.
      if(koko() > 0) {
         //Luodaan uusi lista palautusta varten.
         OmaLista loydetyt = new OmaLista();
         
         //K�yd��n lista l�pi alkio kerrallaan, vertaillaan tutkittavaa alkiota
         //alkioon jonkalaista etsit��n ja lis�t��n se l�ydettyjen listaan jos
         //alkiot ovat samanlaiset.
         for(int i = 0; i < koko() ; i++) {
            if(alkio(i).equals(haettava)) {
               loydetyt.lisaaLoppuun(alkio(i));
            }
         }
         //Tutkitaan, onko luotu apulista tyhj�, jos ei palautetaan lista, jos on 
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
    * Tutkii kumpi alkioista l�ytyy ensimm�isen� listalta.
    *
    * @return 1 jos se oli a, tai 2 jos se oli b. Jos tapahtuu virhe, tai annettu 
    * lista/parametrit virheellisi�, palautetaan 0.
    */
   public int tutkiKumpiEnsin(Object a, Object b) {
      try {
         //Esitell��n muuttuja vertailun helpottamiseksi
         Object tutkittava;
         //Aletaan k�yd� listaa l�pi alkio kerrallaan ja tutkitaan alkioiden viitteit�.
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
      //Jos kumpaakaan alkiota ei l�ytynyt listalta, eik� tapahtunut virhett� palautetaan
      //null.
      return 0;
   } 
   
}