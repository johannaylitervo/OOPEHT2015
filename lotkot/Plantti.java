package lotkot;

import tyokalut.*;

/**
 * L�tk�st� peritty Pl�ntti-luokka.
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Plantti extends Lotko {
   
   /*
    * Attribuutit
    */
    
   /** Pl�ntin muoto. */
   private boolean muoto;
   
   /*
    * Rakentajat
    */ 
   
   public Plantti(int kok, StringBuilder perim, boolean muot) {
      super(kok, perim);
      muoto(muot);
   }
   
   /**
    * Rakentaja, joka syv�kopioi pl�ntin. Syv�kopioidun pl�ntin attribuutit muuttuvat:
    * kopion koko on vanhemman koon kahdella jaon kokonaisosa ja perim�n merkit ovat p�invastoin.
    *
    * @param pl�ntti, josta kopio tehd��n.
    * @throws IllegalArgumentException jos kopioitava ei ole Pl�ntti-olio.
    */
   public Plantti(Plantti kopioitava) throws IllegalArgumentException {
      //Kutsutaan L�tk�-luokan kopiorakentajaa.
      super(kopioitava);

      if(kopioitava instanceof Plantti) {
               
         //Tallennetaan vanhan pl�ntin tiedot muuttujiin muokattuina.
         int kok = kopioitava.koko() / 2;
         StringBuilder perim = new StringBuilder(kopioitava.perima());
         perim = perim.reverse();
      
         //Muokataan luodun l�tk�n tiedot oikeanlaisiksi.
         koko(kok);
         perima(perim);
         
         muoto(kopioitava.muoto());
         Paikka kopiopaikka = new Paikka(kopioitava.paikka().xKoord(), kopioitava.paikka().yKoord());
         //Asetetaan viite paikkakopioon.
         paikka(kopiopaikka);
         
      }
      else
         throw new IllegalArgumentException();
   } 
   
   
   
   /*
    * Aksessorit
    */
   
   public boolean muoto() {
      return muoto;
   }
   
   public void muoto(boolean m) {
      muoto = m;
   }
   
   public String muotoString() {
      String syote = String.valueOf(muoto());
      while(syote.length() < 8) {
         syote = syote + ' ';
      }
      return syote;
   }
   
   /*
    * Object-luokan korvatut metodit
    */
    
   /**
    * Vertaillaan kahta pl�ntti� kesken��n.
    *
    * @param pl�ntti, johon verrataan.
    * @return true jos samanlaiset, false jos tapahtui virhe tai pl�ntit ovat erilaiset.
    */
   public boolean equals(Object ob) {
      try {
         if(ob instanceof Plantti) {
            //Pl�ntit katsotaan samanlaisiksi, jos ne ovat samanmuotoisia.
            Plantti p = (Plantti)ob;
            return (muoto() == p.muoto()); 
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
         return super.toString() + muotoString() + Tyokalut.EROTIN;
      }
      catch(Exception e) {
         return null;
      } 
   }  

   /**
    * Muutetaan pl�ntin tiedot tiedostoon tallennusta varten sopivaksi merkkijonoksi.
    *
    * @return muutettu merkkijono. Jos tapahtui virhe, palautetaan "virhe" merkkijono.
    */
   public String tallennusString() {
      try {
         return super.tallennusString() + muotoString() + EROTIN;
      }
      catch(Exception e) {
         return null;
      } 
   }   

}