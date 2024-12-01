import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LihatBarang {
    private static final String FILE_PATH = "data_barang.txt";

    public void cetakDaftarBarang() {
        List<Barang> daftarBarang = tampilkanSemuaBarang();

        if (daftarBarang.isEmpty()) {
            System.out.println("Tidak ada barang yang tersedia.");
            return;
        }

        // Cari lebar kolom berdasarkan data terpanjang
        int lebarNama = "Nama Barang".length();
        int lebarHarga = "Harga".length();
        int lebarStok = "Stok".length();

        for (Barang barang : daftarBarang) {
            lebarNama = Math.max(lebarNama, barang.getNama().length());
            lebarHarga = Math.max(lebarHarga, String.format("%.2f", barang.getHarga()).length());
            lebarStok = Math.max(lebarStok, String.valueOf(barang.getStok()).length());
        }

        // Cetak header tabel
        String formatBaris = "| %-" + lebarNama + "s | %" + lebarHarga + "s | %" + lebarStok + "s |\n";
        String garis = "+" + "-".repeat(lebarNama + 2) + "+" + "-".repeat(lebarHarga + 2) + "+" + "-".repeat(lebarStok + 2) + "+";

        System.out.println(garis);
        System.out.printf(formatBaris, "Nama Barang", "Harga", "Stok");
        System.out.println(garis);

        // Cetak isi tabel
        for (Barang barang : daftarBarang) {
            System.out.printf(formatBaris, barang.getNama(), String.format("%.2f", barang.getHarga()), barang.getStok());
        }

        System.out.println(garis);
    }

    public List<Barang> tampilkanSemuaBarang() {
        List<Barang> daftarBarang = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return daftarBarang;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                String[] data = baris.split(",");
                if (data.length == 3) {
                    Barang barang = new Barang(
                        data[0],
                        Double.parseDouble(data[1]),
                        Integer.parseInt(data[2])
                    );
                    daftarBarang.add(barang);
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }

        return daftarBarang;
    }
}
