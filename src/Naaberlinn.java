public class Naaberlinn implements Comparable<Naaberlinn> {
    public String nimi;
    public int kaugus;

    public Naaberlinn(String nimi, int kaugus) {
        this.nimi = nimi;
        this.kaugus = kaugus;
    }

    @Override
    public int compareTo(Naaberlinn teineLinn) {
        return this.kaugus - teineLinn.kaugus;
    }

    @Override
    public String toString() {
        return "Naaberlinn{" +
                "nimi='" + nimi + '\'' +
                ", kaugus=" + kaugus +
                '}';
    }
}
