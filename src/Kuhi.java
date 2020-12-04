import java.util.ArrayList;

public class Kuhi<T extends Comparable<T>> {
    protected ArrayList<T> massiiv;

    public Kuhi() {
        this.massiiv = new ArrayList<>();
    }

    public Kuhi(ArrayList<T> massiiv) {
        this.massiiv = massiiv;
    }

    /*
    Ülesanne 1:
    Koostada funktsioonid, mis võtavad argumentideks kuhja ja indeksi k ja tagastavad k-nda elemendi
    * vasaku alluva indeksi
    * parema alluva indeksi
    * vanema indeksi
    Kuna me implementeerime seda isendimeetodina, siis võime kuhja argumendi jätta kaasamata.
     */

    private int vasak(int k){ return 2*k+1; }

    private int parem(int k){ return 2*k+2; }

    private int vanem(int k){ return k > 0 ? (int) Math.floor((k-1)/2.0) : -1; }

    /*
    Ülesanne 2:
    Koostada funktsioonid
    1) Kuhja elemendi mullina ülesviimiseks
    2) Kuhja elemendi mullina allaviimiseks
     */

    /**
     * Mullitab elemendi indeksil i ülespoole
     * <p>
     * Mullitame elemendi kohal i ülespoole, kui tema ülemus on temast suurem
     * <p>
     * @param i - elemendi indeks, mida ülespoole mullitame.
     */
    protected void mullitaYles(int i){
        if (i == 0 || i < 0 || i >= massiiv.size()) { return; }
        T see = massiiv.get(i);
        T ylemus = massiiv.get(vanem(i));
        if (see.compareTo(ylemus) >= 0) { return; }
        massiiv.set(i, ylemus);
        massiiv.set(vanem(i), see);
        mullitaYles(vanem(i));
    }

    /**
     * Mullitab elemendi indeksil i allapoole, vahetades elemendi indeksil i tema väiksema alluvaga.
     * <p>
     * Alla mullitamisel peab esmalt kontrollima, kas elemendil indeksil i leidub üldse alluvaid.
     * Kui ei leidu, töö lõpeb.
     * Kui leiduvad mõlemad alluvad, siis tuleb valida neist väiksem ja määrata see vahetusele.
     * Kui leidub vaid üks alluv, siis määrata see vahetusele.
     * Vahetuse tegemise eelduseks on, et vahetusele määratud element on väiksem elemendist indeksil i.
     * <p>
     * @param i - elemendi indeks, mida allapoole mullitame.
     */
    protected void mullitaAlla(int i){
        int alluvaIndeks = -1;
        if (i < 0 || parem(i) > massiiv.size() && vasak(i) > massiiv.size()) { return; }
        if (vasak(i) < massiiv.size()) {
            alluvaIndeks = vasak(i);
            if (parem(i) < massiiv.size()) {
                alluvaIndeks = massiiv.get(vasak(i)).compareTo(massiiv.get(parem(i))) < 0 ? vasak(i) : parem(i);
            }
        } else if (parem(i) < massiiv.size()) {
            alluvaIndeks = parem(i);
        }
        if (alluvaIndeks == -1 || massiiv.get(i).compareTo(massiiv.get(alluvaIndeks)) < 0) { return; }
        T alluv = massiiv.get(alluvaIndeks);
        massiiv.set(alluvaIndeks, massiiv.get(i));
        massiiv.set(i, alluv);
        mullitaAlla(alluvaIndeks);
    }

    /*
    Ülesanne 3:
    Koostada funktsioonid
    1. Etteantud väärtusega elemendi lisamiseks
    2. Väikseima väärtusega elemendi (juurtipu) eemaldamiseks
     */

    /**
     * Lisab massiivi lõppu kirje ja mullitab seda üles kuni kuhja tingimus hakkab kehtima.
     * @param kirje - loome selle kirjega T ja lisame selle massiivi.
     */
    public void lisaKirje(T kirje){
        if (kirje == null) { return; }
        massiiv.add(kirje);
        mullitaYles(massiiv.size()-1);
    }

    /**
     * Eemaldab juure, asendab selle eelnevalt viimasel indeksil olnud elemendiga.
     * @return - T eemaldatud juur.
     */
    public T votaJuur(){
        if (massiiv.size() < 1) { return null; }
        T eemaldatud = massiiv.remove(0);
        if (massiiv.size() > 0) {
            T viimane = massiiv.remove(massiiv.size() - 1);
            if (massiiv.size() == 0) {
                massiiv.add(viimane);
            } else massiiv.add(0, viimane);
            mullitaAlla(0);
        }
        return eemaldatud;
    }

    /*
    Ülesanne 4:
    Realiseerida meetod järjendi kuhjastamiseks
     */

    /**
     * Kuhjastab isendivälja 'massiiv' sisu MIN-heap'iks
     */
    public void kuhjasta(){
        ArrayList<T> kuhjastamata = new ArrayList<>();
        for (int i = massiiv.size()-1; i >= 0 ; i--) {
            kuhjastamata.add(massiiv.get(i));
            massiiv.remove(i);
        }
        for (T el:kuhjastamata) {
            this.lisaKirje(el);
        }
    }

    /*
    Ülesanne 5:
    Realiseerida kuhjameetodil sorteerimine
     */
    public static <T extends Comparable<T>> ArrayList<T> sorteeri(ArrayList<T> sisend){
        Kuhi<T> temp = new Kuhi<>(sisend);
        temp.kuhjasta();
        return temp.massiiv;
    }

    @Override
    public String toString() {
        String sisu = "";
        for (T e:massiiv) {
            sisu += "| " + e.toString() + " ";
        }
        sisu += "|";
        String lagi = "=".repeat(sisu.length());
        return lagi + "\n" + sisu + "\n" + lagi;
    }
}
