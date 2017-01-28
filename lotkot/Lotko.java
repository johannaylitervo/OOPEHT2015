package lotkot;

import tyokalut.*;

/**
 * Abstrakti L�tk�-luokka
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
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
    * L�tk�n kopiorakentaja.
    */
   public Lotko(Lotko kopioitava) throws IllegalArgumentException {
      //Tarkistetaan, ett� kopioitava olio on l�tk�.
      if(kopioitava instanceof Lotko) {
         //Kopioidaan l�tk�n koko
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
    * Verrataan kahden l�tk�n kokoja.
    *
    * @param L�tk�, johon verrataan.
    * @return -1, jos vertailtava l�tk� on pienempi kuin parametrina saatu l�tk�,
    * 0, jos l�tk�t saman kokoisia tai 1 jos vertailtava l�tk� on suurempi kuin parametrina
    * saatu l�tk�.
    */
   public int compareTo(Lotko toinenLotko) {
      
      
      //Jos vertailtava l�tk� on pienempi kuin parametrina saatu l�tk� 
      if(koko < toinenLotko.koko()){
         return -1;
      }
      //Jos l�tk�t samankokoisia
      else if(koko == toinenLotko.koko()) {
         return 0;
      }
      //Jos vertailtava l�tk� on suurempi kuin parametrina saatu l�tk�.
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
    * Muutetaan l�tk�n tiedot tiedoston tallennusta varten sopivaan muotoon.
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
    * Muutetaan l�tk�n koko merkkijonoksi ja lis�t��n siihen tarvittava m��r� v�lily�ntej�.
    *
    * @return muutettu merkkijono.
    */
   public String kokoString() {
      try {
         kokoS = Integer.toString(koko);
         //Lis�t��n tarpeellinen m��r� v�lily�ntej�.
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