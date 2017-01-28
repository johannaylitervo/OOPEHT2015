package tyokalut;

/**
 * Luokka ohjelmassa k�ytett�v�lle k�ytt�liittym�lle.
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Kayttoliittyma {
   
   /*
    *
    * Attribuutit
    *
    */
   
   public static final String OHJE = "Kirjoita komento:";
   public static final String VIRHE = "Virhe!";
   public static final String LOPETATULOSTE = "Ohjelma lopetettu.";
   
   public static final String LATAA = "lataa";
   public static final String LISTAA = "listaa";
   public static final String LIIKU = "liiku";
   public static final String LUO = "luo";
   public static final String TALLENNA = "tallenna";
   public static final String LOPETA = "lopeta"; 
      
   /*
    *
    * Metodit
    *
    */

   /** 
    * Metodit, joilla tulostetaan k�ytt�j�lle halutut tulosteet.
    */
   public static void tulostaAlku() {
      System.out.println("************");
      System.out.println("* ALKULIMA *");
      System.out.println("************");
   }
   
   /** 
    * Tulostetaan k�ytt�j�lle haluttu tuloste.
    *
    * @param tulostettava teksti.
    */   
   public static void tulosta(String tuloste) {
      System.out.println(tuloste);
   }

   
   
   /** Tutkitaan komentoriviparametrien lukum��r�, tallennetaan sy�tteet 
    * taulukkoon ja palautetaan taulukko.
    *
    * @param taulukko, jossa on k�ytt�j�n antama komennon parametrit.
    * @return sy�tteen muutetut arvot
    */ 
   public static int[] tutkiParametrit(String[] args) {
      
      //Tarkistetaan parametrien m��r�. 
      if(args.length == 0) {
         int[] parametrit1 = {};
         return parametrit1;
      }
      else if(args.length == 1) {
         //Luodaan uusi taulukko.
         int[] parametrit2 = new int[1];
         //Yritet��n muuttaa parametri luvuksi ja sijoitetaan luku
         //taulukkoon jos se onnistui.
         try {
            parametrit2[0] = Integer.parseInt(args[0]);
            
            //Palautetaan taulukko.
            return parametrit2;
         }
         //Jos muutos ei onnistunut palautetaan null-arvo.
         catch (NumberFormatException e) {
            return null;
         }
         
      }
      else if(args.length == 2) {
         //Luodaan uusi taulukko.
         int[] parametrit3 = new int[2];         
         //Yritet��n muuttaa parametrit luvuiksi ja sijoitetaan luku
         //taulukkoon jos se onnistui.
         try {
            parametrit3[0] = Integer.parseInt(args[0]);
            parametrit3[1] = Integer.parseInt(args[1]);
            
            //Palautetaan taulukko.
            return parametrit3;
         }
         //Jos muutos ei onnistunut palautetaan null-arvo.
         catch (NumberFormatException e) {
            return null;
         }
         
      }
      else if(args.length == 3) {
         //Luodaan uusi taulukko.
         int[] parametrit4 = new int[3];         
         //Yritet��n muuttaa parametrit luvuiksi ja sijoitetaan luku
         //taulukkoon jos se onnistui.
         try {
            parametrit4[0] = Integer.parseInt(args[0]);
            parametrit4[1] = Integer.parseInt(args[1]);
            parametrit4[2] = Integer.parseInt(args[2]);
            
            //Palautetaan taulukko.
            return parametrit4;
         }
         //Jos muutos ei onnistunut palautetaan null-arvo.
         catch (NumberFormatException e) {
            return null;
         }
         
      }
      else {
         return null;
      }
   }
   

}