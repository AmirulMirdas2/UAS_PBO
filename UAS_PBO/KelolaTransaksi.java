import java.io.*;
import java.util.*;

public class KelolaTransaksi {

    public void kelolaTransaksi() {
        System.out.println("\nKelola Transaksi:");
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_menunggu.txt"))) {
            List<String> semuaTransaksi = new ArrayList<>();
            String line;

            // Membaca semua transaksi menunggu
            while ((line = reader.readLine()) != null) {
                semuaTransaksi.add(line);
            }

            if (semuaTransaksi.isEmpty()) {
                System.out.println("Tidak ada transaksi yang menunggu.");
                return;
            }

            // Menampilkan semua transaksi yang menunggu
            int index = 1;
            for (String transaksi : semuaTransaksi) {
                System.out.println(index + ". " + transaksi);
                index++;
            }

            // Memilih transaksi untuk disetujui
            Utility.clearScreen();
            Utility.showLoadingAnimation("LOADING", 3000);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Pilih nomor transaksi yang akan disetujui (0 untuk keluar): ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (pilihan == 0) {
                Utility.clearScreen();
                System.out.println("Keluar dari kelola transaksi.");
                return;
            }

            if (pilihan < 1 || pilihan > semuaTransaksi.size()) {
                Utility.clearScreen();
                System.out.println("Nomor transaksi tidak valid.");
                return;
            }

            // Proses transaksi yang disetujui
            String transaksiDisetujui = semuaTransaksi.get(pilihan - 1);
            pindahkanKeTransaksiDisetujui(transaksiDisetujui);

            // Hapus dari transaksi_menunggu.txt
            semuaTransaksi.remove(pilihan - 1);
            simpanKeFile("transaksi_menunggu.txt", semuaTransaksi);

            System.out.println("Transaksi berhasil disetujui dan dipindahkan ke transaksi_disetujui.txt.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mengelola transaksi: " + e.getMessage());
        }
    }

    private void pindahkanKeTransaksiDisetujui(String transaksi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_disetujui.txt", true))) {
            writer.write(transaksi);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memindahkan transaksi: " + e.getMessage());
        }
    }

    private void simpanKeFile(String filePath, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }
}
