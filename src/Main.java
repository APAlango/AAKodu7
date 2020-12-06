import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String algus = "Tõrva";
        String lopp = "Vändra";
        Lahendaja lahendaja = null;

        try {
            lahendaja = new Lahendaja("linnade_kaugused.txt");
            if (lahendaja.linnaIndeksid.containsKey(algus) && lahendaja.linnaIndeksid.containsKey(lopp)) {
                Vastus vastus1 = lahendaja.leiaLühimTee(algus, lopp, 100);
                System.out.println(vastus1);
            }
            else {
                System.out.println("Üks sisestatud linnadest ei leidu meie linnade hulgas!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Linnade kauguste faili ei leitud!");
        }
    }

}
