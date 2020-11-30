import java.io.*;
import java.util.*;

public class Lahendaja {

    //Linnadevahelised kaugused
    public int[][] kaugused;

    //Sisaldab linnade nimesid
    //Saab kasutada nii rea kui tulba nime leidmiseks
    public String [] nimed;
    public HashMap<String,Integer> linnaIndeksid;

    public Lahendaja(String fail) throws FileNotFoundException {
        loeKaugusedFailist(fail);
        System.out.println("nimed:");
        System.out.println(String.join(" | ", nimed));
        System.out.println("linnaIndeksid:");
        System.out.println(linnaIndeksid);
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
    Mugavusmeetod, mis võtab linna nime ning tagastab temale vastava indeksid massiivides "kaugused" ja "nimed".
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
