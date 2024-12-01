import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LihatBarang {
    private static final String FILE_PATH = "data_barang.txt";

    public void cetakDaftarBarang() {
        List<Barang> daftarBarang = tampilkanSemuaBarang();
        System.out.println("Daftar Barang:");
        for (Barang barang : daftarBarang) {
            System.out.println("- " + barang.getNama() + " | Harga: " + barang.getHarga() + " | Stok: " + barang.getStok());
        }
    }

    public List<Barang> tampilkanSemuaBarang() {
        List<Barang> daftarBarang = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                daftarBarang.add(new Barang(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca data barang: " + e.getMessage());
        }
        return daftarBarang;
    }
}
