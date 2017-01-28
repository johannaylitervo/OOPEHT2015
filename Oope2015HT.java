import fi.uta.csjola.oope.lista.*;
import apulaiset.*;
import lotkot.*;
import tyokalut.*;


/**
 * Luokka, jossa sijaitsee harjoitusty�n p��metodi.
 * <p>
 * Olio-ohjelmointi harjoitusty� kev�t 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Oope2015HT {
   
   public static void main(String[] args) {
      
      //Lippumuuttujat sek� tavalliset muuttujat ohjelman py�ritykseen. 
      String syote;
      int syoteApulainen;
      
      boolean jatketaan = true;
      boolean syoteOK = true;
         
      //Tulostetaan k�ytt�j�lle tervehdys.
      Kayttoliittyma.tulostaAlku();
      
      //Luodaan uusi OmaLista-tyyppinen lista, johon ladataan kaikki l�tk�t.
      OmaLista populaatio = new OmaLista();
      //Ladataan l�tk�t tiedostosta.
      syoteOK = Simulaattori.lataaLotkot(populaatio);
      
      //P��silmukka ohjelman py�ritykseen niin kauan kunnes k�ytt�j� haluaa lopettaa.
      do {
         //K��nnet��n sy�teOk-lippumuuttuja oikeelliseksi.
         syoteOK = true;
         
         //Tulostetaan ohje komennon sy�tt�miseen ja tallennetaan k�ytt�j�n sy�te muuttujaan.
         Kayttoliittyma.tulosta(Kayttoliittyma.OHJE);
         syote = In.readString();
         
         //Tutkitaan k�ytt�j�n antama kometo Tyokalut-luokan tutkiKomento-metodilla ja 
         //tallennetaan paluuarvo apumuuttujaan.
         syoteApulainen = Tyokalut.tutkiKomento(syote);
         
         //Jos sy�te oli lopeta:
         if(syoteApulainen == Tyokalut.LOPETASYOTE) {
            jatketaan = false;
         }
         //Jos sy�te oli lataa:
         else if(syoteApulainen == Tyokalut.LATAASYOTE) {
            syoteOK = Simulaattori.lataaLotkot(populaatio);
         }
         //Jos sy�te oli tallenna:
         else if(syoteApulainen == Tyokalut.TALLENNASYOTE) {
            syoteOK = Tyokalut.tallenna(populaatio);
         }
         //Jos sy�te oli listaa ilman parametreja:
         else if(syoteApulainen == Tyokalut.LISTAASYOTE) {
            //Listataan kaikki populaation l�tk�t.
            try {
               //Esimm�isen� tulostetaan ohjelma-arvot.
               
               System.out.println(Tyokalut.ohjelmaArvottoString());
               Simulaattori.listaaListalta(populaatio, populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos sy�te oli listaa yhdell� parametrilla:
         else if(syoteApulainen == Tyokalut.LISTAA1) {
            //Listataan kaikki parametrina annetun indeksin mukaisen l�tk�n kanssa saman-
            //laiset l�tk�t.
            try {
               int[] parametri = Tyokalut.muunnaKomento(syote);
               if(parametri != null && parametri[0] < populaatio.koko() ) {
                  Simulaattori.listaaListalta(populaatio.haeSamanlaiset(populaatio.alkio(parametri[0])),
                                              populaatio);
               }
               else
                  syoteOK = false;
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos sy�te oli listaa kahdella parametrilla:
         else if(syoteApulainen == Tyokalut.LISTAA2) {
            try {
               int[] parametrit = Tyokalut.muunnaKomento(syote);
               if(parametrit != null) {
                  //Tarkistetaan, ett� paikka on sallituissa rajoissa.
                  if(parametrit[0] <= Tyokalut.xMax() && parametrit[0] >= Tyokalut.KOORDMIN 
                  && parametrit[1] <= Tyokalut.yMax() && parametrit[0] >= Tyokalut.KOORDMIN) {
                  
                     //Luodaan uusi paikka olio parametrien arvoilla.
                     Paikka paikka = new Paikka(parametrit[0], parametrit[1]);
                     //Kutsutaan metodia, jolla listataan kaikki paikassa olevat alkiot.
                     Simulaattori.listaaListalta(Simulaattori.etsiPaikasta(populaatio, paikka), 
                                             populaatio);
                  }
                  else
                     syoteOK = false;
               }
               else
                  syoteOK = false;
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos sy�te oli liiku ilman parametreja:
         else if(syoteApulainen == Tyokalut.LIIKUSYOTE) {
            try {
               Simulaattori.liiku(populaatio);            
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            } 
         }
         //Jos sy�te oli liiku kolmella parametrilla.
         else if(syoteApulainen == Tyokalut.LIIKU3) {
            try {
               Simulaattori.liikutaLotkoa(syote, populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos sy�te oli luo.
         else if(syoteApulainen == Tyokalut.LUOSYOTE) {
            try {
               Simulaattori.lisaanny(populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos sy�te oli virheellinen.
         else {
            syoteOK = false;
         }
            
         //Tulostetaan virheilmoitus, jos sy�te oli virheellinen.
         if(!syoteOK) {
            Kayttoliittyma.tulosta(Kayttoliittyma.VIRHE);
         }
         
      }
      while(jatketaan);
      
      //Kun k�ytt�j� on antanut lopeta-komennon, tulostetaan j��hyv�iset ja poistutaan
      //ohjelmasta.
      Kayttoliittyma.tulosta(Kayttoliittyma.LOPETATULOSTE);
   }

}