package lotkot;

import apulaiset.*;
import tyokalut.*;

/**
 * Lötkön paikan kertovat Paikka-olion luokka.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Paikka {

   /*
    *
    * Attribuutit
    *
    */

   /** Lötkön paikka-olio. */
   private Paikka paikka;
   /** Lötkön koko. */
   private int koko;
   /** Lötkön perimä. */
   private StringBuilder perima;
   
   /** Lötkön koko String-muotoisena tulostuksia varten. */
   private String kokoS;
   
   /** Vakio virhe-syötteelle. */
   public final String VIRHE = "Virhe!";
   /** Vakio tulostuksissa käytetylle erottimelle. */
   public final char EROTIN = '|';
   /** Vakio välilyönnille. */
   public final char VALI = ' ';
   
   /** Paikan x-koordinaatti. */
   private int xKoord;
   /** Paikan y-koordinaatti. */
   private int yKoord;
   
   
   /*
    *
    * Rakentajat
    *
    */
    
   public Paikka() {
      /*//Kutsutaan Automaatti-luokan metodia, joka arpoo lötkölle satunnaisen paikan 
      //alkulimassa.
      int[] paikka = Automaatti.annaPaikka(Tyokalut.xMax(), Tyokalut.yMax());
      if(paikka != null) {
         xKoord(paikka[0]);
         yKoord(paikka[1]);
      }*/
   }
   
   public Paikka(int x, int y) {
      xKoord(x);
      yKoord(y);
   }

   /*
    *
    * Aksessorit
    *
    */

   public int xKoord() {
      return xKoord;
   }
   
   public void xKoord(int x) {
      if((x >= 0) && (x <= Tyokalut.xMax())) {
         xKoord = x;
      }
   }
   
   public int yKoord() {
      return yKoord;
   }
   
   public void yKoord(int y) {
      if((y >= 0) && (y <= Tyokalut.yMax())) {
         yKoord = y;
      }
   }
   public Paikka paikka(Paikka p) {
      Paikka paikkaKopio = new Paikka(p.xKoord(), p.yKoord());
      
      return paikkaKopio;
   }
   
   /*
    *
    * Metodit
    *
    */
       
   /**
    * Liikutetaan tiettyä lötköä parametrina annettuun paikkaan.
    *
    * @return true jos onnistui, false, jos arvot eivät olleet sallituissa rajoissa 
    * tai tapahtui virhe.
    */
   public boolean liikuPaikkaan(int x, int y) {
      if(x <= Tyokalut.xMax() && x >= Tyokalut.KOORDMIN && y <= Tyokalut.yMax() 
         && y >= Tyokalut.KOORDMIN) {
         //Sijoitetaan paikka-olioon uudet arvot attribuuteille.
         xKoord(x);
         yKoord(y);
         
         return true;
      }
      else
         return false;
   }
   
   /** 
    * Muutetaan paikan tiedot tulostusta varten sopiviksi.
    *
    * @return merkkijono lötkön tiedoista.
    */
   
   public String toString() {
      try {   
         String x = Integer.toString(xKoord());
         String y = Integer.toString(yKoord());
      
         while(x.length() < 3){
            x = x + VALI;
         }
         while(y.length() < 3){
            y = y + VALI;
         }
         return x + EROTIN + y + EROTIN;
      }
      catch (Exception e) {
         return VIRHE;
      }
   }
  /**
   * Vertaillaan kahta paikkaa keskenään koordinaattien mukaan.
   *
   * @param olio, johon verrataan.
   * @return true jos olivat samanlaiset, false jos eivät.
   */ 
   public boolean equals(Object ob) {
      boolean tulos = false;
      try {
         if(ob instanceof Paikka) {
            Paikka p = (Paikka)ob;
            //Paikka on sama jos paikan x- ja y-koordinaatit vastaavat.
            if(xKoord() == p.xKoord() && yKoord() == p.yKoord()) {
               tulos = true;
            } 
         }
      }
      catch(Exception e) {
         return tulos;
      }
      return tulos;
   }
}