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
            System.out.println("5. Lihat riwayat belanja");
            System.out.println("6. Keluar");
            System.out.print("Pilih: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    lihatBarang.cetakDaftarBarang();
                    break;
                    case 2:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    System.out.print("Masukkan jumlah uang: ");
                    double uang = scanner.nextDouble();
                    masukkanUang(uang);
                    break;
                    case 3:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    lihatBarang.cetakDaftarBarang();
                    System.out.print("Nama barang yang ingin ditambahkan ke keranjang: ");
                    String namaBarang = scanner.nextLine();
                    tambahBarangKeKeranjang(namaBarang);
                    break;
                case 4:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    bayarKeranjang();
                    break;
                case 5:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    lihatRiwayatBelanja();
                    break;
                case 6:
                    Utility.clearScreen();
                    Utility.showLoadingAnimation("LOADING", 3000);
                    Utility.clearScreen();
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void masukkanUang(double uang) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_uang.txt", true))) {
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

    private void simpanTransaksiKeMenunggu(String transaksi) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_menunggu.txt", true))) {
            writer.write(transaksi);
            writer.newLine();
        }
    }
    private void bayarKeranjang() {
        try {
            // Baca saldo pengguna dari data_uang.txt
            double saldo = bacaSaldoUser(username);
            double totalHarga = keranjang.hitungTotalHarga();  // Total harga barang di keranjang
    
            // Cek apakah saldo cukup untuk pembayaran
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
            simpanDaftarBarang(daftarBarang);  // Update stok barang yang ada di toko
    
            // Proses pengurangan saldo pengguna
            double saldoBaru = saldo - totalHarga;
            simpanSaldoUser(username, saldoBaru);  // Simpan saldo baru ke file

            // Simpan transaksi yang menunggu persetujuan admin
            String transaksi = username + "," + keranjang.getDaftarBarang().toString() + "," + totalHarga;
            simpanTransaksiKeMenunggu(transaksi);
    
            keranjang.checkout();  // Proses checkout keranjang
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

    private void lihatRiwayatBelanja() {
        System.out.println("\nRiwayat Belanja Anda:");
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_disetujui.txt"))) {
            String line;
            boolean adaRiwayat = false;
            while ((line = reader.readLine()) != null) {
                // Format: user,[barang, harga, jumlah], totalHarga
                int firstComma = line.indexOf(","); // Index koma pertama
                int lastComma = line.lastIndexOf(","); // Index koma terakhir
    
                if (firstComma == -1 || lastComma == -1 || firstComma == lastComma) {
                    continue; // Lewati jika format tidak sesuai
                }
    
                String username = line.substring(0, firstComma); // Username
                String barang = line.substring(firstComma + 1, lastComma); // Daftar barang
                String totalHarga = line.substring(lastComma + 1); // Total harga
    
                if (username.equalsIgnoreCase(this.username)) {
                    adaRiwayat = true;
                    System.out.println("Barang: " + barang);
                    System.out.println("Total Harga: " + totalHarga);
                }
            }
            if (!adaRiwayat) {
                System.out.println("Tidak ada riwayat belanja yang ditemukan.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
    
}



