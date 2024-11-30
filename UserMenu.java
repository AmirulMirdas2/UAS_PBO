import java.io.*;
import java.util.*;

public class UserMenu {
    private Keranjang keranjang = new Keranjang();
    private LihatBarang lihatBarang = new LihatBarang();
    private String username;

    public UserMenu(String username) {
        this.username = username;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu User:");
            System.out.println("1. Lihat barang");
            System.out.println("2. Masukkan uang");
            System.out.println("3. Tambah barang ke keranjang");
            System.out.println("4. Bayar keranjang");
            System.out.println("5. Keluar");
            System.out.print("Pilih: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (pilihan) {
                case 1:
                    lihatBarang.cetakDaftarBarang();
                    break;
                case 2:
                    System.out.print("Masukkan jumlah uang: ");
                    double uang = scanner.nextDouble();
                    masukkanUang(uang);
                    break;
                case 3:
                    System.out.print("Nama barang yang ingin ditambahkan ke keranjang: ");
                    String namaBarang = scanner.nextLine();
                    tambahBarangKeKeranjang(namaBarang);
                    break;
                case 4:
                    bayarKeranjang();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void masukkanUang(double uang) {
        try (FileWriter writer = new FileWriter("data_uang.txt", true)) {
            writer.write(username + "," + uang + "\n");
            System.out.println("Uang berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void tambahBarangKeKeranjang(String namaBarang) {
        List<Barang> daftarBarang = lihatBarang.tampilkanSemuaBarang();
        for (Barang barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                System.out.print("Masukkan jumlah yang ingin dibeli: ");
                int jumlah = new Scanner(System.in).nextInt();

                if (jumlah > barang.getStok()) {
                    System.out.println("Jumlah melebihi stok tersedia.");
                    return;
                }

                keranjang.tambahKeKeranjang(new Barang(barang.getNama(), barang.getHarga(), jumlah));
                System.out.println("Barang berhasil ditambahkan ke keranjang.");
                return;
            }
        }
        System.out.println("Barang tidak ditemukan.");
    }

    private void bayarKeranjang() {
        try {
            double saldo = bacaSaldoUser(username);
            double totalHarga = keranjang.hitungTotalHarga();

            if (totalHarga > saldo) {
                System.out.println("Saldo tidak cukup untuk pembayaran.");
                return;
            }

            // Proses pengurangan stok barang
            List<Barang> daftarBarang = lihatBarang.tampilkanSemuaBarang();
            for (Barang barangKeranjang : keranjang.getDaftarBarang()) {
                for (Barang barangToko : daftarBarang) {
                    if (barangToko.getNama().equalsIgnoreCase(barangKeranjang.getNama())) {
                        if (barangToko.getStok() < barangKeranjang.getStok()) {
                            System.out.println("Stok barang " + barangKeranjang.getNama() + " tidak mencukupi.");
                            return;
                        }
                        barangToko.setStok(barangToko.getStok() - barangKeranjang.getStok());
                    }
                }
            }
            simpanDaftarBarang(daftarBarang);

            // Proses pengurangan saldo
            double saldoBaru = saldo - totalHarga;
            simpanSaldoUser(username, saldoBaru);

            keranjang.checkout();
            System.out.println("Pembayaran berhasil. Sisa saldo Anda: " + saldoBaru);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private double bacaSaldoUser(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data_uang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(username)) {
                    return Double.parseDouble(data[1]);
                }
            }
        }
        return 0.0;
    }

    private void simpanSaldoUser(String username, double saldoBaru) throws IOException {
        List<String> dataUang = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data_uang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(username)) {
                    dataUang.add(username + "," + saldoBaru);
                } else {
                    dataUang.add(line);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_uang.txt"))) {
            for (String record : dataUang) {
                writer.write(record);
                writer.newLine();
            }
        }
    }

    private void simpanDaftarBarang(List<Barang> daftarBarang) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_barang.txt"))) {
            for (Barang barang : daftarBarang) {
                writer.write(barang.toString());
                writer.newLine();
            }
        }
    }
}
