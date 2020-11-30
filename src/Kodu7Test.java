import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Kodu7Test {

    private Lahendaja lahendaja = new Lahendaja("linnade_kaugused.txt");

    public Kodu7Test() throws FileNotFoundException {
    }

    @Test
    public void test01() {
        teeTest("Kuressaare","Narva",100,433);
    }

    public void teeTest(String algus,String lõpp,int piirang, int lyhimPikkus) {

        // Niimoodi töötavad ka meie automaatkontrollid - kood on sama, kuid meil on testjuhtumeid rohkem.
        // Võite endale teha teste juurde kasutades näiteks Floyd-Warshalli graafil, millelt on kaalu ületavad servad ära võetud.

        // Saame õpilase vastuse testjuhtumile
        Vastus vastus = lahendaja.leiaLühimTee(algus,lõpp,piirang);
        if(lyhimPikkus == -1){
            // Kui õige vastus on tee puudumine, siis õpilase vastus peaks seda kajastama
            if(vastus.teepikkus != -1){
                fail("Leidsid tee kahe linna vahel, mille vahel ei peaks olema teed.");
            }
        } else {
            // Kui tee peaks leiduma, siis arvutame õpilase Vastuses oleva teepikkuse välja kasutades
            // etteantud teed.
            int teepikkus = 0;
            String viimane = algus;
            // Käime tee sammupidi läbi
            for(String linn : vastus.tee){
                int sammuKaugus = lahendaja.kaugused[lahendaja.indeks(viimane)][lahendaja.indeks(linn)];
                // Ja kui ükski samm on pikem kui lubatud, siis kukutame testi läbi
                if(sammuKaugus > piirang){
                    fail("Teekond sisaldab liiga pikka kaart");
                }
                teepikkus += sammuKaugus;
                viimane = linn;
            }
            // Kui pikkuse piirangut pole ületatud, siis kontrollime et tegu on tõesti lühima teekonnaga
            assertEquals(teepikkus,lyhimPikkus);
        }
    }
}
