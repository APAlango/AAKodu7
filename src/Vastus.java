import java.util.List;

public class Vastus {

    public List<String> tee;
    public int teepikkus;

    public Vastus(List<String> tee,int teepikkus) {
        this.tee = tee;
        this.teepikkus = teepikkus;
    }

    public String toString() {
//        StringBuilder sb = new StringBuilder();

        if(teepikkus != -1) {
            return teepikkus+", "+String.join(" > ",tee);
        } else {
            return ("Linnade " + tee.get(0) + " ja " + tee.get(tee.size() - 1)
                    + " vahel sobivat teed ei leidunud.");
        }
    }
}
