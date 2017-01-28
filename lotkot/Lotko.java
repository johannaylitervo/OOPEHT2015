package lotkot;

import tyokalut.*;

/**
 * Abstrakti Lötkö-luokka
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public abstract class Lotko implements Comparable<Lotko> {
   
   /**
    * Attribuutit
    */
   
   
   private Paikka paikka;
   
   private int koko;
   
   private StringBuilder perima;
   
   private String kokoS;
   public final String VIRHE = "Virhe!";
   public final char EROTIN = '|';
   public final char VALI = ' ';
   
   /**
    * Rakentaja
    */
   public Lotko() {
      StringBuilder p = new StringBuilder("ABCDEFGH");
      koko(1);
      perima(p);
      paikka = new Paikka();
   }
   
   public Lotko(int k, StringBuilder p) {
      koko(k);
      perima(p);
      paikka = new Paikka();
   }
   
   /** 
    * Lötkön kopiorakentaja.
    */
   public Lotko(Lotko kopioitava) throws IllegalArgumentException {
      //Tarkistetaan, että kopioitava olio on lötkö.
      if(kopioitava instanceof Lotko) {
         //Kopioidaan lötkön koko
         koko(kopioitava.koko());
         perima(kopioitava.perima());
      }
      else
         throw new IllegalArgumentException();
   }
   /*
    * Aksessorit
    */
    
   public int koko() {
      return koko;
   }

   public void koko(int k) {
      if(k >= 0) {
         koko = k;
      }
   }
     
   public StringBuilder perima() {
      return perima;
   }
   
   public void perima(StringBuilder p) {
      perima = p;
   }
   
   public Paikka paikka() {
      //Paikka paikkakopio = new Paikka(paikka.xKoord(), paikka.yKoord());
      return paikka;
   }
   
   public void paikka(Paikka p) {
      if(p != null) {
         paikka = p;
      }
   }  
   
   /*
    * Object-luokan korvatut metodit
    */
    
   /**
    * Verrataan kahden lötkön kokoja.
    *
    * @param Lötkö, johon verrataan.
    * @return -1, jos vertailtava lötkö on pienempi kuin parametrina saatu lötkö,
    * 0, jos lötköt saman kokoisia tai 1 jos vertailtava lötkö on suurempi kuin parametrina
    * saatu lötkö.
    */
   public int compareTo(Lotko toinenLotko) {
      
      
      //Jos vertailtava lötkö on pienempi kuin parametrina saatu lötkö 
      if(koko < toinenLotko.koko()){
         return -1;
      }
      //Jos lötköt samankokoisia
      else if(koko == toinenLotko.koko()) {
         return 0;
      }
      //Jos vertailtava lötkö on suurempi kuin parametrina saatu lötkö.
      else 
         return 1;
   }
    
   public String toString() {
      try {
         Class luokka = getClass();
         String nimi = luokka.getSimpleName() + " ";
         return paikka.toString() + nimi + EROTIN + kokoString() + EROTIN + perima() + EROTIN;
      }
      catch(Exception e) {
         return null;
      }
   }   
   
   /**
    * Muutetaan lötkön tiedot tiedoston tallennusta varten sopivaan muotoon.
    *
    * @return tallennettava merkkijono.
    */
   
   public String tallennusString() {
      try {
         Class luokka = getClass();
         String nimi = luokka.getSimpleName() + " ";

         return nimi + EROTIN + kokoString() + EROTIN + perima() + EROTIN;
      }
      catch(Exception e) {
         return null;
      }
   }
   
   /**
    * Muutetaan lötkön koko merkkijonoksi ja lisätään siihen tarvittava määrä välilyöntejä.
    *
    * @return muutettu merkkijono.
    */
   public String kokoString() {
      try {
         kokoS = Integer.toString(koko);
         //Lisätään tarpeellinen määrä välilyöntejä.
         for(int i = 0; kokoS.length() < 8; i++) {
            kokoS = kokoS + " ";
         }
         return kokoS;
      }
      catch(Exception e) {
         return " ";
      }
   } 
 }