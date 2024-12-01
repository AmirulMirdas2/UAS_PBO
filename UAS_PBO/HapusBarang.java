import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HapusBarang {
    private static final String FILE_PATH = "data_barang.txt";

    public void hapusBarang(String namaBarang) {
        try {
            List<Barang> daftarBarang = bacaDaftarBarang();
            
            boolean dihapus = daftarBarang.removeIf(barang -> barang.getNama().equalsIgnoreCase(namaBarang));
            
            if (dihapus) {
                Utility.clearScreen();
                simpanDaftarBarang(daftarBarang);
                System.out.println("Barang berhasil dihapus.");
            } else {
                Utility.clearScreen();
                System.out.println("Barang tidak ditemukan.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Barang> bacaDaftarBarang() throws IOException {
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
        }
        return daftarBarang;
    }

    private void simpanDaftarBarang(List<Barang> daftarBarang) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Barang barang : daftarBarang) {
                writer.write(barang.toString());
                writer.newLine();
            }
        }
    }
}