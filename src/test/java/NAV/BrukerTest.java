package NAV;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrukerTest {

    @Test
    @DisplayName("Test for konstruktør()")
    public void konstruktørTest() {
        //ugyldige konstruktører som skal krasje
        assertThrows(IllegalArgumentException.class, () -> new Bruker(-500000, 450000, 400000));
        assertThrows(IllegalArgumentException.class, () -> new Bruker(500000, -450000, 400000));
        assertThrows(IllegalArgumentException.class, () -> new Bruker(500000, 450000, -400000));

        //gyldige konstruktører som ikke skal krasje
        new Bruker(500000, 450000, 400000);
        new Bruker(0, 0, 0);
    }

    @Test
    @DisplayName("Test retten til dagpenger")
    public void testDagpengerRett() {
        Bruker godkjentBruker = new Bruker(500000, 450000, 400000);
        Bruker ikkeGodkjentBruker = new Bruker(50000, 45000, 30000);

        //tester at brukerens rett til dagpenger er korrekt vurdert
        assertTrue(godkjentBruker.harRettTilDagpenger());
        assertFalse(ikkeGodkjentBruker.harRettTilDagpenger());
    }

    @Test
    @DisplayName("Test DagpengerSatsen")
    public void testDagpengerSats() {
        Bruker godkjentBruker = new Bruker(500000, 450000, 400000);
        Bruker ikkeGodkjentBruker = new Bruker(50000, 45000, 30000);

        //tester at brukerens dagpengesats er korrekt vurdert
        assertEquals(godkjentBruker.getDagpengerSats(), 1924);
        assertEquals(ikkeGodkjentBruker.getDagpengerSats(), 0);
    }

    @Test
    @DisplayName("Test getInntektsÅr()")
    public void testInntektsÅr() {
        Bruker godkjentBruker = new Bruker(500000, 450000, 400000);

        //sjekker om inntektsÅr er en liste med 3 verdier og at verdiene er rett
        assertEquals(godkjentBruker.getInntektsÅr().size(), 3);
        assertEquals(godkjentBruker.getInntektsÅr().get(0), 500000);
        assertEquals(godkjentBruker.getInntektsÅr().get(1), 450000);
        assertEquals(godkjentBruker.getInntektsÅr().get(2), 400000);
        
        //tester innkapsling. En skal ikke kunne endre på inntektsårene fra getter.
        godkjentBruker.getInntektsÅr().set(0, 100000.0);
        assertFalse(godkjentBruker.getInntektsÅr().get(0) == 100000);
    }
}
