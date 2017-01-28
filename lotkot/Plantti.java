package lotkot;

import tyokalut.*;

/**
 * Lötköstä peritty Pläntti-luokka.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Plantti extends Lotko {
   
   /*
    * Attribuutit
    */
    
   /** Pläntin muoto. */
   private boolean muoto;
   
   /*
    * Rakentajat
    */ 
   
   public Plantti(int kok, StringBuilder perim, boolean muot) {
      super(kok, perim);
      muoto(muot);
   }
   
   /**
    * Rakentaja, joka syväkopioi pläntin. Syväkopioidun pläntin attribuutit muuttuvat:
    * kopion koko on vanhemman koon kahdella jaon kokonaisosa ja perimän merkit ovat päinvastoin.
    *
    * @param pläntti, josta kopio tehdään.
    * @throws IllegalArgumentException jos kopioitava ei ole Pläntti-olio.
    */
   public Plantti(Plantti kopioitava) throws IllegalArgumentException {
      //Kutsutaan Lötkö-luokan kopiorakentajaa.
      super(kopioitava);

      if(kopioitava instanceof Plantti) {
               
         //Tallennetaan vanhan pläntin tiedot muuttujiin muokattuina.
         int kok = kopioitava.koko() / 2;
         StringBuilder perim = new StringBuilder(kopioitava.perima());
         perim = perim.reverse();
      
         //Muokataan luodun lötkön tiedot oikeanlaisiksi.
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
    * Vertaillaan kahta plänttiä keskenään.
    *
    * @param pläntti, johon verrataan.
    * @return true jos samanlaiset, false jos tapahtui virhe tai pläntit ovat erilaiset.
    */
   public boolean equals(Object ob) {
      try {
         if(ob instanceof Plantti) {
            //Pläntit katsotaan samanlaisiksi, jos ne ovat samanmuotoisia.
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
    * Muutetaan pläntin tiedot tiedostoon tallennusta varten sopivaksi merkkijonoksi.
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