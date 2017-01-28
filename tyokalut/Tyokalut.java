package tyokalut;

import java.io.*;
import fi.uta.csjola.oope.lista.*;
import apulaiset.*;
import lotkot.*;

/**
 *Työkalut-luokka. Vastaa kaikista syötteistä ym. joita ohjelman käytössä tarvitaan sekä 
 * niihin liittyvistä toiminnoista.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */

public class Tyokalut { 
   /*
    *
    * Attribuutit
    *
    */
   
   /** Tulostukseen käytettävä erotin. */
   public static final char EROTIN = '|';
   /** Välilyönti. */
   public static final char VALI = ' ';
   /** Klimpin merkkijonoesitys. */
   public static final String KLIMPPI = "Klimppi";
   /** Pläntin merkkijonoesitys. */
   public static final String PLANTTI = "Plantti";
   
   /** Tiedoston nimi, joka ladataan ja johon tallennetaan. */
   public static final String TIEDOSTO = "lotkot.txt";

   
   /*
    * Komentoihin liittyvät vakiot.
    */
   /** Listaa komennon merkkijono esitys. */ 
   public static final String LISTAA = "listaa";
   /** Liiku komennon merkkijono esitys. */
   public static final String LIIKU = "liiku";
   /** Luo komennon merkkijono esitys. */
   public static final String LUO = "luo";
   /** Lataa komennon merkkijono esitys. */
   public static final String LATAA = "lataa";
   /** Tallenna komennon merkkijono esitys. */
   public static final String TALLENNA = "tallenna";
   /** Lopeta komennon merkkijono esitys. */
   public static final String LOPETA = "lopeta"; 
   /** Virhetulosteteen merkkijono esitys. */
   public static final String VIRHE = "Virhe!";
   
   /*
    * Komentoihin liittyvät vakiot. 
    */
 
   /** Jos komento on lataa. */ 
   public static final int LATAASYOTE = 0;
   /** Jos komento on listaa ilman parametreja. */
   public static final int LISTAASYOTE = 1;
   /** Jos komento on listaa yhdellä parametrilla. */
   public static final int LISTAA1 = 2;
   /** Jos komento on listaa kahdella parametrilla. */
   public static final int LISTAA2 = 3;
   /** Jos komento on liiku ilman parametreja. */
   public static final int LIIKUSYOTE = 4;
   /** Jos komento on liiku kolmella parametrilla. */
   public static final int LIIKU3 = 5;
   /** Jos komento on luo.*/
   public static final int LUOSYOTE = 6;
   /** Jos komento on tallenna. */
   public static final int TALLENNASYOTE = 7;
   /** Jos komento on lopeta. */
   public static final int LOPETASYOTE = 8;
   
   /** Siemenluvun pienin mahdollinen arvo. */
   public static final int SIEMENMIN = -99;
   /** Koordinaattien pienin mahdollinen arvo. */
   public static final int KOORDMIN = 0; 
   /** Siemenluvun ja koordinaattien maksimiarvo. */
   public static final int KRPMAX = 999;
   
   /*
    * Attribuutit
    */
   
   /** x-koordinaatin max arvo, joka */  
   private static int xMax;
   /** */
   private static int yMax;
   
   /** */
   private static int siemen;
   
   /*
    *
    * Aksessorit
    *
    */
   
   public static int xMax() {
      return xMax;
   } 
   
   //Palautetaan totuusarvo, oliko arvo, joka haluttiin asettaa oikeanlainen.
   public static boolean xMax(int x) {
      if(x <= KRPMAX && x >= KOORDMIN) {
         xMax = x;
         return true;
      }
      return false;
   }
   
   public static int yMax() {
      return yMax;
   }
   
   //Palautetaan totuusarvo, oliko arvo, joka haluttiin asettaa oikeanlainen.
   public static boolean yMax(int y) {
      if(y <= KRPMAX && y >= KOORDMIN) {
         yMax = y;
         return true;
      }
      return false;
   }

   public static int siemen() {
      return siemen;
   }
   
   public static boolean siemen(int s) {
      if(s <= KRPMAX && s >= SIEMENMIN) {
         siemen = s;
         return true;
      }
      else
         return false;
   }  
   /*
    *
    * Metodit
    *
    */
     



   /*
    * Metodi, jolla tarkistetaan siemenluku ja koordinaatiston maksimiarvot ja tallennetaan
    * ne muuttujiin jos ovat oikeanlaiset. Tässä metodissa alustetaan myös Automaatti-luokka.
    * Palauttaa null-arvon jos syöte virheellinen.
    */
   
   public static boolean tarkistaMaksimit(String[] param) {
            
      //Tarkistetaan tietojen määrä ja oikeellisuus. 
      if(param.length == 3) {
         //Luodaan uusi int-tyyppinen taulukko tuloksille.
         int[] parametrit = new int[3];         
         
         //Yritetään muuttaa tiedot Stringstä luvuiksi ja sijoitetaan luku
         //taulukkoon jos se onnistui.
         try {
            parametrit[0] = Integer.parseInt(param[0]);
            parametrit[1] = Integer.parseInt(param[1]);
            parametrit[2] = Integer.parseInt(param[2]);
         }
         //Jos muutos ei onnistunut palautetaan null-arvo.
         catch (NumberFormatException e) {
            return false;
         }
         
         //Tutkitaan arvojen oikeellisuus.
         if(parametrit[0] > KRPMAX || parametrit[1] > KRPMAX || parametrit[2] > KRPMAX
         || parametrit[0] < SIEMENMIN ||  parametrit[1] < KOORDMIN || parametrit[2] < KOORDMIN) {
            return false;
         }
         else {
            //Sijoitetaan arvot taulukosta ohjelma-arvojen attribuutteihin.
            siemen(parametrit[0]);
            xMax(parametrit[1]);
            yMax(parametrit[2]);

            Automaatti.alusta(siemen());
            return true;
         }    
      }
      else
         return false;

   }   
   /*
    * Metodi, jolla lasketaan tiedoston rivien lukumäärä. Palauttaa -1 jos tapahtui virhe.
    */
   
   public static int laskeRivit(String tiedostonNimi) {
      //Esitellään ja alustetaan muuttuja rivien laskurille.
      int rivit = 0;      
   
      try {          
         //Avataan syötevirta.
         FileInputStream syotevirta = new FileInputStream(tiedostonNimi);
         
         //Liitetään lukija virtaan.
         InputStreamReader lukija = new InputStreamReader(syotevirta);
         //Vaihdetaan lukija parempaan.
         BufferedReader puskuroitu = new BufferedReader(lukija);
         
         //Luetaan tiedosto riveittäin ja tallennetaan rivien määrä muuttujaan.
         while(puskuroitu.ready()) {
            String rivi = puskuroitu.readLine();
            rivit++;         
         }
         //Suljetaan lukija.
         puskuroitu.close();
      }
      catch(Exception e) {
         rivit = -1;
      }
      return rivit;
   }
   
   /*
    * Metodi, jolla luetaan syötetty tiedosto ja palautetaan arvot taulukossa. Palauttaa 
    * null-arvon jos tiedostolla ei ollut rivejä tai tapahtui virhe
    */
    
   public static String[][] taulukoiSisalto(String tiedostonNimi) {
      //Lasketaan tiedoston rivien lukumäärä taulukon luontia varten.
      int riviLkm = laskeRivit(tiedostonNimi) - 1;
      
      //Tarkastetaan, että tiedostolla oli sisältöä.
      if(riviLkm >= 0) {
         //Luodaan taulukko saatujen rivien lukumäärän mukaan.
         String[][] sisalto = new String[riviLkm][4];
         boolean onnistui = true;

         //Käydään tiedosto läpi rivi kerrallaan.
         try {
            //Avataan syötevirta.
            FileInputStream syotevirta = new FileInputStream(tiedostonNimi);
         
            //Liitetään lukija virtaan.
            InputStreamReader lukija = new InputStreamReader(syotevirta);
            //Vaihdetaan lukija parempaan.
            BufferedReader puskuroitu = new BufferedReader(lukija);
            
            String[] riviPalat = new String[4];
            
            //Luetaan tiedoston ensimmäinen rivi erikseen, jotta saadaan tarkistettua 
            //ohjelma-arvot 
            String rivi = puskuroitu.readLine();
            riviPalat = rivi.split("["+EROTIN+"]");
            for(int k = 0; k < 3; k++) {
               riviPalat[k] = riviPalat[k].trim();
            } 
            //tarkistaMaksimit-metodissa alustetaan myös apuluokka.
            onnistui = tarkistaMaksimit(riviPalat);
            
            //Jatketaan jos ohjelma-arvojen muutos onnistui
            if(onnistui) {
               for(int i = 0; i < riviLkm; i++) {
                  //Luetaan tiedosto rivi kerrallaan, poistetaan ensin riviltä kaikki välilyönnit
                  //sen jälkeen paloitellaan se ja sijoitetaan taulukkoon.
                  rivi = puskuroitu.readLine();
                  riviPalat = rivi.split("["+EROTIN+"]");
               
                  for(int j = 0; j < riviPalat.length; j++) {
                     riviPalat[j] = riviPalat[j].trim();
                  
                     //Sijoitetaan rivin tiedot palautettavaan taulukkoon.
                     sisalto[i][j] = riviPalat[j];
                  
                  }
               //System.out.println("Lukiessa "+sisalto[5][2]);         
               }
               //Suljetaan lukija.
               puskuroitu.close();
            }            
         }
         catch (Exception e) {
            return null;
         }
         
         if(onnistui)
            //Palautetaan sisällöllinen taulukko.
            return sisalto;
      }
      return null; 
   }
   
   /*
    * Tutkitaan käyttäjän antama komentosyöte. 
    * return -1 = jos tapahtui virhe
    *       LISTAA1 jos komento on listaa yhdella parametrilla.
    *       LISTAA2 jos komento on listaa kahdella parametrilla.
    *       LIIKU3 jos komento on liiku kolmella parametrilla.
    *       LIIKUSYOTE/LUOSYOTE/LATAASYOTE/TALLENNASYOTE/LOPETASYOTE jos komento 
    *       on ilman parametreja.       
    */
   public static int tutkiKomento(String komento) {
      int palautus = -1;
      try {
         //Sijoitetaan komento paloiteltuna String-tyyppiseen taulukkoon.
         String[] osat = komento.split("["+VALI+"]");
                  
         //Tutkitaan taulukon ensimmäistä alkiota ja pituutta.
         if(osat.length > 1) {
            if(osat[0].equals(LISTAA) && osat.length == 2) {
               palautus = LISTAA1;
            }
            else if(osat[0].equals(LISTAA) && osat.length == 3) {
               palautus = LISTAA2;
            }
            else if(osat[0].equals(LIIKU) && osat.length == 4) {
               palautus = LIIKU3;
            }
         }
         else if(osat[0].equals(LIIKU)) {
            palautus = LIIKUSYOTE;
         }
         else if(osat[0].equals(LUO)) {
            palautus = LUOSYOTE;
         }
         else if(osat[0].equals(LATAA)) {
            palautus = LATAASYOTE;
         } 
         else if(osat[0].equals(TALLENNA)) {
            palautus = TALLENNASYOTE;
         }
         else if(osat[0].equals(LOPETA)) {
            palautus = LOPETASYOTE;      
         }
         else if(osat[0].equals(LISTAA)) {
            palautus = LISTAASYOTE;      
         }
      }
      catch (Exception e) {
         palautus = -1;
      }
      return palautus;
   } 
    
   /*
    * Metodi, jolla luetaan käyttäjän antama komento ja palautetaan sen parametrit int-
    * tyyppisessä taulukossa. Palauttaa null-arvon, jos tapahtui virhe.
    */
    
   public static int[] muunnaKomento(String komento) {
      
      try {
         //Sijoitetaan komento paloiteltuna String-tyyppiseen taulukkoon.
         String[] osat = komento.split("["+VALI+"]");
                  
         //Luodaan int-tyyppinen taulukko tuloksille. 
         int[] parametrit = new int[osat.length - 1];
         
         //Esitellään lippumuuttuja syötteen tarkistukseen.
         boolean onNumeroita = true;
           
         //Poistetaan välilyönnit osista ja tallennetaan arvot int-tyyppiseen taulukkoon
         //tyyppimuunnos tehden. Aloitetaan taulukon käsittely vasta toisesta arvosta, koska
         //ensimmäinen on komennon testiosa.
         for(int i = 1; i < osat.length; i++) {
            osat[i] = osat[i].trim();
            //Tarkistetaan, että kaikki syötteen osat ovat numeroita.
            for(int j = 0; j < osat[i].length(); j++) {
               if(osat[i].charAt(j) < 48 || osat[i].charAt(j) > 57) {
                  onNumeroita = false;
               }
            }
            //Jos syöte oli pelkkiä numeroita, muutetaan se, jos ei palautetaan null-arvo.
            if(onNumeroita) {
               parametrit[(i-1)] = Integer.parseInt(osat[i]);
            }
            else {
               return null;
            }
         }
         return parametrit;
      }
      catch (Exception e) {
         return null;
      }
   }
   
   /*
    * Metodi, jolla tallennetaan tiedosto. Palauttaa false-arvon jos tapahtui virhe.
    */
   public static boolean tallenna(OmaLista lotkot) {
      try {
         //Luodaan tiedosto-olio file-luokasta
         File tiedosto = new File(TIEDOSTO);
         //Luodaan tulostusvirta ja liitetään se tiedostoon.
         FileOutputStream tvirta = new FileOutputStream(tiedosto);
         //Luodaan kirjoittaja virtaan.
         PrintWriter kirjuri = new PrintWriter(tvirta, true);
         
         //Kirjoitetaan tiedostoon siemenluku, koordinaatiston max-arvot sekä kaikkien
         //listan lötköjen tiedot.
         
         //Tutkitaan, että ohjelmaArvot-muutos onnistui.
         if(!ohjelmaArvojenTallennusString().equals(VIRHE)) {
            kirjuri.println(ohjelmaArvojenTallennusString());
         
            for(int i = 0; i < lotkot.koko(); i++) {
               kirjuri.println(((Lotko)lotkot.alkio(i)).tallennusString());
            }
            return true;
         }
         else
            return false;
      }
      //Jos tapahtui poikkeus:
      catch(Exception e) {
         return false;
      }
   }
   
   /*
    * Metodi, jolla palautetaan siemenluku sekä koordinaatiston arvot String-merkkijonona.
    */
    
   public static String ohjelmaArvottoString() {
      try {   
         //Muutetaan int-tyyppiset arvot Stringeiksi.
         String siemen = Integer.toString(siemen());
         String x = Integer.toString(xMax());
         String y = Integer.toString(yMax());
         
         //Katsotaan, että jokainen merkkijono on kolme merkkiä pitkä.
         while(siemen.length() < 3){
            siemen = siemen + VALI;
         }
         while(x.length() < 3){
            x = x + VALI;
         }
         while(y.length() < 3){
            y = y + VALI;
         }
         return siemen + EROTIN + x + EROTIN + y + EROTIN;
      }
      catch (Exception e) {
         return VIRHE;
      }
   }
   
   //Tiedostoon tallennusta varten.
   public static String ohjelmaArvojenTallennusString() {
      try {   
         //Muutetaan int-tyyppiset arvot Stringeiksi.
         String siemen = Integer.toString(siemen());
         String x = Integer.toString(xMax());
         String y = Integer.toString(yMax());
         
         //Katsotaan, että jokainen merkkijono on kahdeksan merkkiä pitkä.
         while(siemen.length() < 8){
            siemen = siemen + VALI;
         }
         while(x.length() < 8){
            x = x + VALI;
         }
         while(y.length() < 8){
            y = y + VALI;
         }
         return siemen + EROTIN + x + EROTIN + y + EROTIN;
      }
      catch (Exception e) {
         return VIRHE;
      }
   }   
}