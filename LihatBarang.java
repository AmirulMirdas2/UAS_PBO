import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LihatBarang {
    private static final String FILE_PATH = "data_barang.txt";

    public List<Barang> tampilkanSemuaBarang() {
        List<Barang> daftarBarang = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
            e.printStackTrace();
        }
        
        return daftarBarang;
    }

    public void cetakDaftarBarang() {
        List<Barang> daftarBarang = tampilkanSemuaBarang();
        
        if (daftarBarang.isEmpty()) {
            System.out.println("Tidak ada barang.");
            return;
        }

        System.out.println("Daftar Barang:");
        for (Barang barang : daftarBarang) {
            System.out.printf("Nama: %s, Harga: %.2f, Stok: %d%n", 
                barang.getNama(), barang.getHarga(), barang.getStok());
        }
    }
}