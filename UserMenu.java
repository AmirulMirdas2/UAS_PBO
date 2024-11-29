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
            System.out.println("5. Bayar");
            System.out.println("6. Keluar");
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
                    keranjang.checkout();
                    simpanKeranjang();
                    break;
                case 5:
                    bayar(scanner);
                    break;
                case 6:
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
        List<Barang> daftarBarang = new LihatBarang().tampilkanSemuaBarang();
        for (Barang barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                keranjang.tambahKeKeranjang(barang);
                System.out.println("Barang berhasil ditambahkan ke keranjang.");
                return;
            }
        }
        System.out.println("Barang tidak ditemukan.");
    }

    private void simpanKeranjang() {
        try (FileWriter writer = new FileWriter("data_barang_di_keranjang.txt")) {
            for (Barang barang : keranjang.getBarangList()) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "\n");
            }
            System.out.println("Keranjang disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan keranjang: " + e.getMessage());
        }
    }

    private void bayar(Scanner scanner) {
        Bayar pembayaran = new Bayar(username);
        double totalHarga = pembayaran.hitungTotalHargaKeranjang();
        System.out.println("Total yang harus dibayar: Rp" + totalHarga);
        System.out.print("Lanjutkan pembayaran? (ya/tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("ya")) {
            if (pembayaran.lakukanPembayaran(totalHarga)) {
                keranjang.kosongkanKeranjang();
                System.out.println("Pembayaran berhasil! Keranjang dikosongkan.");
            } else {
                System.out.println("Saldo tidak cukup.");
            }
        } else {
            System.out.println("Pembayaran dibatalkan.");
        }
    }
}