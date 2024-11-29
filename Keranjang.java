import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private List<Barang> daftarBarangDiKeranjang;

    public Keranjang() {
        daftarBarangDiKeranjang = new ArrayList<>();
    }

    public void tambahKeKeranjang(Barang barang) {
        daftarBarangDiKeranjang.add(barang);
    }

    public void hapusDariKeranjang(String namaBarang) {
        daftarBarangDiKeranjang.removeIf(barang -> barang.getNama().equalsIgnoreCase(namaBarang));
    }

    public List<Barang> getDaftarBarang() {
        return daftarBarangDiKeranjang;
    }

    public double hitungTotalHarga() {
        return daftarBarangDiKeranjang.stream()
            .mapToDouble(Barang::getHarga)
            .sum();
    }

    public void checkout() {
        System.out.println("Checkout Berhasil");
        System.out.println("Total Harga: " + hitungTotalHarga());
        daftarBarangDiKeranjang.clear();
    }
}