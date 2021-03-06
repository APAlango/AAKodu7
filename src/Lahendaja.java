import java.io.*;
import java.util.*;

public class Lahendaja {

    //Linnadevahelised kaugused
    public int[][] kaugused;
    public int[][] vkaugused;

    //Sisaldab linnade nimesid
    //Saab kasutada nii rea kui tulba nime leidmiseks
    public String [] nimed;
    public String[] vnimed;
    public HashMap<String,Integer> linnaIndeksid;
    public HashMap<String,Integer> vlinnaIndeksid;

    public Lahendaja(String fail) throws FileNotFoundException {
        loeKaugusedFailist(fail);
    }

    public Vastus FWLeiaLyhimTee(String algus, String lopp) {
        List<String> tee = new ArrayList<>();
        int kaugus = -1;
        tee.add(algus);
        int algusIndeks = indeks(algus);
        int loppIndeks = indeks(lopp);
        // Vaatame labi koik tipud ja iga tipu kohta kontrollime eraldi
        for (int i = 0; i < kaugused.length; i++) {
            for (int x = 0; x < kaugused.length; x++) {
                if (x != i && x == algusIndeks) {
                    for (int y = 0; y < kaugused.length; y++) {
                        if (y != i && y == loppIndeks) {
                            int alt = kaugused[x][i] + kaugused[i][y];
                            if (alt < kaugused[x][y]) {
                                tee.add(nimed[i]);
                                kaugus = kaugused[algusIndeks][i] + kaugused[i][loppIndeks];
                            }
                        }
                    }
                }
            }
        }
        return new Vastus(tee, kaugus);
    }

    /**
     * Leiab lühima tee linnade 'algus' ja 'lõpp' vahel.
     * @param algus Alguslinn sõnena
     * @param lõpp Lõpplinn sõnena
     * @param max Maksimaalne kaugus, mida auto saab ühe tankimisega läbida
     * (ülesande tekstis tähistatud kui x)
     * @return Vastuse isend, mis sisaldab leitud lühimat teed ja teepikkust
     * Kui vastust ei leidu (algus/lõpp ebasobiv, ei saa läbida sellise 'max'ga),
     * tagastada kodu7.Vastus teepikkusega -1.
     */
    public Vastus leiaLühimTee(String algus, String lõpp, int max) {
        //TODO: Lahendada Dijkstra või Bellman-Ford algoritmiga

        //Dijkstra algoritm on õpikus lehekülg 100.
        //Soovituslikult implementeerida algoritm kasutades naabrusmaatriksit,
        //kuid võite lisada ka Tipp ja Serv klassid ning failist lugemist
        //selleks sobivaks töödelda

        // DIJKSTRA
        Kuhi<Vastus> koikTeed = leiaKoikTeed(algus, 0, lõpp, max, new Kuhi<>());
        return koikTeed.votaJuur();
    }

    /**
     * Leiab rekursiivselt kõik võimalikud teed sihtkohta.
     * @param tee - järjend linnanimedest, kus on juba peatus tehtud. Välja kutsumisel peaks alguspunkt ainuke element olema
     * @param teepikkus - seni läbitud tee pikkus
     * @param sihtkoht - linn, kuhu jõudes peaks töö lõppema
     * @param max - maksimaalne vahemaa, mis peatumata sõita saab
     * @param lahendid - leitud lahendid
     * @return List<Vastus> kõik võimalikud teed sihtkohta
     */
    private Kuhi<Vastus> leiaKoikTeed(String tee, int teepikkus, String sihtkoht, int max, Kuhi<Vastus> lahendid) {
        String[] teeJarj = tee.split(" ");
        String viimaneLinn = teeJarj[teeJarj.length-1];
        if (viimaneLinn.equals(sihtkoht)) {
            Vastus leitud = new Vastus(Arrays.asList(teeJarj), teepikkus);
            lahendid.lisaKirje(leitud);
            return lahendid;
        }
        // Siia lisame vaid linnad, mis on sihtkohale lahemal kui linn, kus praegu oleme.
        Kuhi<Naaberlinn> jargmised = new Kuhi<>();
        for (int i = 0; i < kaugused[indeks(viimaneLinn)].length; i++) {
            if (kaugused[indeks(viimaneLinn)][i] != 0
                    && kaugused[indeks(viimaneLinn)][i] <= max
                    && poleKainud(nimed[i], tee)
                    && kaugused[i][indeks(sihtkoht)] < kaugused[indeks(viimaneLinn)][indeks(sihtkoht)]) {
                jargmised.lisaKirje(new Naaberlinn(nimed[i], kaugused[indeks(viimaneLinn)][i]));
            }
        }
        if (jargmised.massiiv.isEmpty()) { return lahendid; }
        while (!jargmised.massiiv.isEmpty()) {
            Naaberlinn lahimNaaber = jargmised.votaJuur();
            if (!lahendid.massiiv.isEmpty()) {
                if (teepikkus + lahimNaaber.kaugus < lahendid.massiiv.get(0).teepikkus) {
                    lahendid = leiaKoikTeed(tee + " " + lahimNaaber.nimi, teepikkus + lahimNaaber.kaugus, sihtkoht, max, lahendid);
                }
            }
            else {
                lahendid = leiaKoikTeed(tee + " " + lahimNaaber.nimi, teepikkus + lahimNaaber.kaugus, sihtkoht, max, lahendid);
            }
        }
        return lahendid;
    }

    /**
     * Abifunktsioon kontrollimaks kas mingis linnas on juba käidud.
     * @param linn - linn, mille sisaldumist argumendis 'tee' kontrollime
     * @return true - kui linn ei sisaldu tees, muidu false
     */
    public boolean poleKainud(String linn, String tee) {
        for ( String l : tee.split(" ") ) {
            if (l.equals(linn)) { return false; }
        }
        return true;
    }

    /*
    Mugavusmeetod, mis võtab linna nime ning tagastab temale vastavad indeksid massiivides "kaugused" ja "nimed".
     */
    public int indeks(String linn){
        return linnaIndeksid.get(linn);
    }

    /*
    Mugavusmeetod, mis võtab linna nime ning tagastab temale vastavad indeksid massiivides "kaugused" ja "nimed".
     */
    public int vindeks(String linn){
        return vlinnaIndeksid.get(linn);
    }

    private void loeKaugusedFailist(String fail) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fail)))){
            nimed = br.readLine().split("\t");
            kaugused = new int[nimed.length][nimed.length];
            linnaIndeksid = new HashMap<>();
            for(int i=0; i < nimed.length; i++){
                linnaIndeksid.put(nimed[i],i);
            }
            String in = br.readLine();
            int i = 0;
            while(in != null){
                String[] reaJupid = in.split("\t");
                for(int j=1;j<reaJupid.length;j++){
                    if(!reaJupid[j].equals("")){
                        kaugused[i][j-1] = Integer.parseInt(reaJupid[j]);
                    } else {
                        kaugused[i][j-1] = 0;
                    }
                }
                i++;
                in = br.readLine();
            }
            //// DELETE ////
            vnimed = new String[9];
            vkaugused = new int[9][];
            vlinnaIndeksid = new HashMap<>();
            for (int j = 0; j < 9; j++) {
                vnimed[j] = nimed[50+j];
                vkaugused[j] = new int[]{kaugused[50+j][50], kaugused[50+j][51], kaugused[50+j][52], kaugused[50+j][53],kaugused[50+j][54],kaugused[50+j][55],kaugused[50+j][56],kaugused[50+j][57],kaugused[50+j][58]};
                vlinnaIndeksid.put(nimed[50+j], j);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
