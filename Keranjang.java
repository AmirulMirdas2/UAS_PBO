import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private List<Barang> daftarBarang = new ArrayList<>();

    public void tambahKeKeranjang(Barang barang) {
        daftarBarang.add(barang);
    }

    public List<Barang> getDaftarBarang() {
        return daftarBarang;
    }

    public double hitungTotalHarga() {
        return daftarBarang.stream().mapToDouble(b -> b.getHarga() * b.getStok()).sum();
    }

    public void checkout() {
        daftarBarang.clear();
        System.out.println("Keranjang telah kosong.");
    }
}
