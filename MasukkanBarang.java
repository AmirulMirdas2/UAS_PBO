import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MasukkanBarang {
    private static final String FILE_PATH = "data_barang.txt";

    public void tambahBarang(Barang barang) {
        try {
            List<Barang> daftarBarang = bacaDaftarBarang();
            daftarBarang.add(barang);
            simpanDaftarBarang(daftarBarang);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Barang> bacaDaftarBarang() throws IOException {
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