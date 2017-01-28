package lotkot;

import apulaiset.*;
import tyokalut.*;

/**
 * L�tk�n aliluokka Klimppi
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Klimppi extends Lotko {

   /*
    * Attribuutit
    */
    
   private char vari;
   
   /*
    * Rakentajat
    */
    
   public Klimppi(int kok, StringBuilder perim, char var) {
      super(kok, perim);
      vari(var);
   }
   
   /**
    * Lis��ntymiseen k�ytett�v� rakentaja. Parametreina annetaan uuden klimpin vanhemmat.
    *
    * @param Ensimm�inen olio lis��ntyv�st� klimppi parista.
    * @param Toinen olio lis��ntyv�st� klimppi parista.
    * @throws IllegalArgumentException jos molemmat oliot eiv�t olleet klimppej�.
    */
   public Klimppi(Klimppi isi, Klimppi aiti) throws IllegalArgumentException {
      if((isi instanceof Klimppi) && (aiti instanceof Klimppi)) {
         int paino = (isi.koko() + aiti.koko()) / 2;

         String uusip = (isi.perima()).substring(0,4) + (aiti.perima()).substring(4,8);
         StringBuilder perim = new StringBuilder(uusip);
         char var = ' ';
         if(isi.vari() == 'S') {
            var = 'P';
         }
         else
            var = 'S';
            
         koko(paino);
         perima(perim);
         vari(var);
         //J�lkel�inen luodaan samaan paikkaan vanhempien kanssa.
         Paikka kopiopaikka = new Paikka(isi.paikka().xKoord(), isi.paikka().yKoord());
         //Asetetaan viite paikkakopioon
         paikka(kopiopaikka);

      }
      else
         throw new IllegalArgumentException();         
   }
   
   /*
    * Aksessorit
    */
   
   public char vari() {
      return vari;
   }
   
   public String variString() {
      return (vari + "       ");      
   }
   
   public void vari(char v) {
      if(v == 'S' || v == 'P') {
         vari = v;
      }
   }

   /*
    * Object-luokan korvatut metodit
    */
    
   public boolean equals(Object ob) {
      try {
         if(ob instanceof Klimppi) {
            //Klimpit katsotaan samanlaisiksi, jos ne ovat saman v�risi�.
            Klimppi k = (Klimppi)ob;
            return (vari() == k.vari()); 
         }
         else
            return false;
      }
      catch(Exception e) {
         return false;
      }
   }   
   
   public String toString() {
      try {
         return super.toString() + variString() + EROTIN;
      }
      catch(Exception e) {
         return VIRHE;
      }
   }

   /**
    * String-merkkijonon palauttava metodi tiedostoon tallennusta varten.
    *
    * @return Merkkijono, joka tallennetaan tiedostoon.
    */
   public String tallennusString() {
      try {
         return super.tallennusString() + variString() + EROTIN;
      }
      catch(Exception e) {
         return VIRHE;
      }
   }
}