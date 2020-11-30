import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        Lahendaja lahendaja = null;
        try {
            lahendaja = new Lahendaja("linnade_kaugused.txt");
//            Vastus vastus1 = lahendaja.leiaLühimTee("Tallinn", "Tartu", 100);
//            System.out.println(vastus1);
        } catch (FileNotFoundException e) {
            System.out.println("Linnade kauguste faili ei leitud!");
        }
    }

}
