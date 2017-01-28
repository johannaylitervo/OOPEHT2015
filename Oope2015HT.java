import fi.uta.csjola.oope.lista.*;
import apulaiset.*;
import lotkot.*;
import tyokalut.*;


/**
 * Luokka, jossa sijaitsee harjoitustyön päämetodi.
 * <p>
 * Olio-ohjelmointi harjoitustyö kevät 2015 
 * <p>
 * @author Johanna Ylitervo, ylitervo.johanna.k@student.uta.fi
 */
 
public class Oope2015HT {
   
   public static void main(String[] args) {
      
      //Lippumuuttujat sekä tavalliset muuttujat ohjelman pyöritykseen. 
      String syote;
      int syoteApulainen;
      
      boolean jatketaan = true;
      boolean syoteOK = true;
         
      //Tulostetaan käyttäjälle tervehdys.
      Kayttoliittyma.tulostaAlku();
      
      //Luodaan uusi OmaLista-tyyppinen lista, johon ladataan kaikki lötköt.
      OmaLista populaatio = new OmaLista();
      //Ladataan lötköt tiedostosta.
      syoteOK = Simulaattori.lataaLotkot(populaatio);
      
      //Pääsilmukka ohjelman pyöritykseen niin kauan kunnes käyttäjä haluaa lopettaa.
      do {
         //Käännetään syöteOk-lippumuuttuja oikeelliseksi.
         syoteOK = true;
         
         //Tulostetaan ohje komennon syöttämiseen ja tallennetaan käyttäjän syöte muuttujaan.
         Kayttoliittyma.tulosta(Kayttoliittyma.OHJE);
         syote = In.readString();
         
         //Tutkitaan käyttäjän antama kometo Tyokalut-luokan tutkiKomento-metodilla ja 
         //tallennetaan paluuarvo apumuuttujaan.
         syoteApulainen = Tyokalut.tutkiKomento(syote);
         
         //Jos syöte oli lopeta:
         if(syoteApulainen == Tyokalut.LOPETASYOTE) {
            jatketaan = false;
         }
         //Jos syöte oli lataa:
         else if(syoteApulainen == Tyokalut.LATAASYOTE) {
            syoteOK = Simulaattori.lataaLotkot(populaatio);
         }
         //Jos syöte oli tallenna:
         else if(syoteApulainen == Tyokalut.TALLENNASYOTE) {
            syoteOK = Tyokalut.tallenna(populaatio);
         }
         //Jos syöte oli listaa ilman parametreja:
         else if(syoteApulainen == Tyokalut.LISTAASYOTE) {
            //Listataan kaikki populaation lötköt.
            try {
               //Esimmäisenä tulostetaan ohjelma-arvot.
               
               System.out.println(Tyokalut.ohjelmaArvottoString());
               Simulaattori.listaaListalta(populaatio, populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos syöte oli listaa yhdellä parametrilla:
         else if(syoteApulainen == Tyokalut.LISTAA1) {
            //Listataan kaikki parametrina annetun indeksin mukaisen lötkön kanssa saman-
            //laiset lötköt.
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
         //Jos syöte oli listaa kahdella parametrilla:
         else if(syoteApulainen == Tyokalut.LISTAA2) {
            try {
               int[] parametrit = Tyokalut.muunnaKomento(syote);
               if(parametrit != null) {
                  //Tarkistetaan, että paikka on sallituissa rajoissa.
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
         //Jos syöte oli liiku ilman parametreja:
         else if(syoteApulainen == Tyokalut.LIIKUSYOTE) {
            try {
               Simulaattori.liiku(populaatio);            
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            } 
         }
         //Jos syöte oli liiku kolmella parametrilla.
         else if(syoteApulainen == Tyokalut.LIIKU3) {
            try {
               Simulaattori.liikutaLotkoa(syote, populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos syöte oli luo.
         else if(syoteApulainen == Tyokalut.LUOSYOTE) {
            try {
               Simulaattori.lisaanny(populaatio);
            }
            catch(IllegalArgumentException e) {
               syoteOK = false;
            }
         }
         //Jos syöte oli virheellinen.
         else {
            syoteOK = false;
         }
            
         //Tulostetaan virheilmoitus, jos syöte oli virheellinen.
         if(!syoteOK) {
            Kayttoliittyma.tulosta(Kayttoliittyma.VIRHE);
         }
         
      }
      while(jatketaan);
      
      //Kun käyttäjä on antanut lopeta-komennon, tulostetaan jäähyväiset ja poistutaan
      //ohjelmasta.
      Kayttoliittyma.tulosta(Kayttoliittyma.LOPETATULOSTE);
   }

}