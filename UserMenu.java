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
            System.out.println("4. Checkout keranjang");
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
                    checkout();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void masukkanUang(double uang) {
        double saldoSaatIni = cekSaldo();
        double saldoBaru = saldoSaatIni + uang;

        updateSaldo(saldoBaru);  // Memperbarui saldo yang ada
        System.out.println("Saldo berhasil diperbarui! Saldo baru: " + saldoBaru);
    }

    private void tambahBarangKeKeranjang(String namaBarang) {
        List<Barang> daftarBarang = lihatBarang.tampilkanSemuaBarang();
        for (Barang barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Masukkan jumlah stok yang ingin dibeli: ");
                int jumlah = scanner.nextInt();

                if (jumlah <= barang.getStok()) {
                    double totalHarga = barang.getHarga() * jumlah;
                    keranjang.tambahKeKeranjang(new Barang(barang.getNama(), barang.getHarga(), jumlah));
                    barang.setStok(barang.getStok() - jumlah);  // Kurangi stok barang di memori
                    System.out.println("Barang berhasil ditambahkan ke keranjang. Total harga: " + totalHarga);
                    System.out.println("Stok sekarang: " + barang.getStok());
                } else {
                    System.out.println("Stok tidak mencukupi.");
                }
                return;
            }
        }
        System.out.println("Barang tidak ditemukan.");
    }

    public void checkout() {
        double totalHarga = keranjang.hitungTotalHarga();
        double saldoPengguna = cekSaldo();

        System.out.println("Total harga keranjang: " + totalHarga);
        System.out.println("Saldo pengguna saat ini: " + saldoPengguna);

        if (saldoPengguna >= totalHarga) {
            saldoPengguna -= totalHarga;
            updateSaldo(saldoPengguna);   // Update saldo setelah transaksi
            keranjang.checkout();        // Kosongkan keranjang setelah checkout
            updateStokBarang();          // Perbarui stok barang di file
            System.out.println("Transaksi berhasil! Saldo Anda sekarang: " + saldoPengguna);
        } else {
            System.out.println("Saldo tidak cukup untuk melakukan transaksi.");
        }
    }

    private double cekSaldo() {
        double saldo = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data_uang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    saldo = Double.parseDouble(data[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca saldo: " + e.getMessage());
        }
        return saldo;
    }

    private void updateSaldo(double saldoBaru) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("data_uang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    lines.add(username + "," + saldoBaru);
                    isUpdated = true;
                    System.out.println("Saldo berhasil diperbarui menjadi: " + saldoBaru);  // Debug
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }

        // Jika saldo pengguna belum ada, maka tambahkan saldo baru untuk pengguna tersebut
        if (!isUpdated) {
            lines.add(username + "," + saldoBaru);
            System.out.println("Saldo baru ditambahkan untuk user: " + saldoBaru);  // Debug
        }

        // Tulis ulang file data_uang.txt dengan saldo terbaru
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_uang.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File data_uang.txt berhasil diperbarui.");  // Debug
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan saldo: " + e.getMessage());
        }
    }

    private void updateStokBarang() {
        List<Barang> daftarBarang = lihatBarang.tampilkanSemuaBarang();
    
        // Proses update stok barang di dalam daftarBarang sesuai dengan keranjang
        for (Barang barangDiKeranjang : keranjang.getDaftarBarang()) {
            for (Barang barang : daftarBarang) {
                if (barang.getNama().equalsIgnoreCase(barangDiKeranjang.getNama())) {
                    // Mengurangi stok sesuai dengan jumlah barang yang dibeli
                    barang.setStok(barang.getStok() - barangDiKeranjang.getJumlah());
                    System.out.println("Stok barang " + barang.getNama() + " setelah pembelian: " + barang.getStok());  // Debugging
                }
            }
        }
    
        // Menyimpan stok barang terbaru ke dalam file data_barang.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_barang.txt"))) {
            for (Barang barang : daftarBarang) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok() + "\n");
            }
            System.out.println("File data_barang.txt berhasil diperbarui.");  // Debugging
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan stok barang: " + e.getMessage());
        }
    }
}