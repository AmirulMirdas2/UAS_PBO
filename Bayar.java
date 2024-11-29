import java.io.*;

public class Bayar {
    private String username;
    private static final String DATA_FILE = "data_uang.txt";
    private static final String KERANJANG_FILE = "data_barang_di_keranjang.txt";

    public Bayar(String username) {
        this.username = username;
    }

    public double cekSaldo() {
        double saldo = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    saldo += Double.parseDouble(data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca data: " + e.getMessage());
        }
        return saldo;
    }

    public double hitungTotalHargaKeranjang() {
        double totalHarga = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(KERANJANG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                totalHarga += Double.parseDouble(data[1]);
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca keranjang: " + e.getMessage());
        }
        return totalHarga;
    }

    public boolean lakukanPembayaran(double totalHarga) {
        double saldo = cekSaldo();
        if (saldo >= totalHarga) {
            kurangiSaldo(totalHarga);
            return true;
        } else {
            return false;
        }
    }

    private void kurangiSaldo(double jumlah) {
        File tempFile = new File("data_uang.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE));
             FileWriter writer = new FileWriter(tempFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    double saldo = Double.parseDouble(data[1]) - jumlah;
                    writer.write(username + "," + saldo + "\n");
                    jumlah = 0; // Pastikan mengurangi hanya sekali
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memperbarui saldo: " + e.getMessage());
        }

        if (tempFile.renameTo(new File(DATA_FILE))) {
            System.out.println("Saldo berhasil diperbarui.");
        }
    }
}
