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
        System.out.println("nimed.length == " + nimed.length);
        System.out.println("kaugused.length == " + kaugused.length);
//        System.out.println("nimed:");
//        System.out.println(String.join(" | ", nimed));
//        System.out.println("linnaIndeksid:");
//        System.out.println(linnaIndeksid);
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


        return new Vastus(Arrays.asList(algus,lõpp),-1);
    }

    /*
    Mugavusmeetod, mis võtab linna nime ning tagastab temale vastavad indeksid massiivides "kaugused" ja "nimed".
     */
    public int indeks(String linn){
        return linnaIndeksid.get(linn);
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
            for (int j = 0; j < 10; j++) {
                vnimed[j] = nimed[50+j];
                vkaugused[j] = kaugused[50+j];
                vlinnaIndeksid.put(nimed[50+j], 50+j);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
