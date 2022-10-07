package NAV;

import java.util.ArrayList;
import java.util.Collections;

/**
 * En klasse som representerer en bruker i et dagpengesystem i NAV
 * @author Markus Aleksander Råkil Johansen
 */
public class Bruker {

    private boolean rettTilDagpenger;
    private ArrayList<Double> inntektsÅr;
    private double dagpengerSats = 0;

    //Grunnbeløpet er offentlig statistikk og trenger derfor ikke å skjules
    public double G = 111477;

    /**
     * Initialiserer et bruker objekt: Et menneske som søker om dagpenger.
     * Kjører også metode behandleDagpenger() som tar seg av logikken rundt krav på.
     * Dagpenger, eventuellt utbetaling.
     * @param inntekt1  er inntekt i norske kroner for det siste kalenderåret (siste periode fra og med 1.januar til 31.desember).
     * @param inntekt2  er inntekt i norske kroner for det forrige kalenderåret (kalenderåret før det siste kalenderåret).
     * @param inntekt3  er inntekt i norske kroner for det tredje kalenderåret (kalenderåret før det forrige kalenderåret).
     * @see sjekkRettTilDagpenger()
     */
    public Bruker(double inntekt1, double inntekt2, double inntekt3) {
        this.inntektsÅr = new ArrayList<Double>();
        Collections.addAll(inntektsÅr, sjekkInntekt(inntekt1), sjekkInntekt(inntekt2), sjekkInntekt(inntekt3));
        behandleDagpenger();
    }


    /**
     * Metode som sjekker hva dagpengesatsen er.
     * @return dagpengerSats
     */
    public double getDagpengerSats() {
        return dagpengerSats;
    }


    /**
     * Returnerer hvorvidt brukeren har rett på dagpenger.
     * @return rettTilDagpenger
     */
    public boolean harRettTilDagpenger() {
        return rettTilDagpenger;
    }


    /**
     * Istedenfor tre felt for inntekter, så er det laget en liste med inntekter.
     * Det sørger også for at vi kun trenger en getter metode for inntekt.
     * Det gjør også at det senere kan implementeres en metode som registrerer nye inntektsår.
     * @return inntektÅr
     */
    public ArrayList<Double> getInntektsÅr() {
        return new ArrayList<Double>(inntektsÅr);
    }


    /**
     * Setter dagpengesatsen basert på inntekten siste år.
     * @param inntekt En inntektsverdi i norske kroner, hvis gyldighet skal avgjøres i Metode.
     * @return dagpengesatsen om den består alle kravene som stilles.
     * @throws IllegalArgumentException om inntekt er negativ.
     */
    private double sjekkInntekt(double inntekt) {
        if (inntekt < 0) {
            throw new IllegalArgumentException("Inntekt kan ikke være negativ");
        }
        return inntekt;
    }


    /**
     * Setter feltet rettTilDagpenger = True, dersom dette er tilfellet vil også dagsats feltet settes.
     * Dersom bruker ikke har rett på dagpenger vil feltet rettTilDagpenger settes til false og feltet dagsats forbli NULL.
     * @see rettTilDagpenger()
     * @see finnDagsats()
     */
    private void behandleDagpenger() {
        if (vurderRettPåDagpenger()) {
            this.rettTilDagpenger = true;
            this.dagpengerSats = finnDagsats();
        } else {
            rettTilDagpenger = false;
        }
    }


    /**
     * Avgjør om bruker har rett på dagpenger.
     * Avgjørelsen baseres på 3 boolske verdier: har man tjent 3G tilsammen de siste årene, har man tjent 1.5G forrige år, har man hatt inntekt forrige år.
     * Disse settes sammen til ett boolsk uttrykk som avgjør om du kvalifiserer for dagpenger eller ikke.
     * @return true - hvis bruker har rett på dagpenger. false ellers.
     */
    private boolean vurderRettPåDagpenger() {
        boolean merEnn3gSammenlagt = (inntektsÅr.get(0) + inntektsÅr.get(1) + inntektsÅr.get(2)) >= (3 * G);
        boolean forrigeÅrNok = inntektsÅr.get(0) >= (1.5 * G);
        boolean haddeinntektSisteÅr = inntektsÅr.get(0) != 0;
        return (merEnn3gSammenlagt || forrigeÅrNok) && haddeinntektSisteÅr;
    }


    /**
     * Finner dagpengeGrunnlaget basert på den største verdien av inntekt siste kalenderår og gjennomsnittsinntekten de siste tre kalenderårene.
     * Dersom den høyeste av disse to verdiene er høyere enn 6G skal det grunnlaget settes til 6G, ellers til verdien vi fant.
     * @return dagpengegrunnlaget
     */
    private double finnDagpengeGrunnlag() {
        double gjennomsnittAvTreSisteÅr = (inntektsÅr.get(0) + inntektsÅr.get(1) + inntektsÅr.get(2)) / 3;
        double temp = inntektsÅr.get(0) > gjennomsnittAvTreSisteÅr ? inntektsÅr.get(0) : gjennomsnittAvTreSisteÅr; 
        return temp > 6 * G ? 6 * G : temp;
    }


    /**
     * Kalkulerer dagsatsen etter formel: grunnlag/260, rundet opp.
     * @return dagsatsen
     * @see finnDagpengeGrunnlag()
     */
    private double finnDagsats() {
        return Math.ceil(finnDagpengeGrunnlag() / 260);
    }

    /**
     * ToString metode for testing av klassen.
     * @return String som representerer et bruker objekt. Viser oversikt over felter.
    */
    @Override
    public String toString() {
        return "Bruker{" +
                "\ndagpengerSats=" + dagpengerSats +
                "\nrettTilDagpenger=" + rettTilDagpenger +
                "\ninntektsÅr=" + inntektsÅr +
                "\n}";
    }
}
