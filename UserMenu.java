import java.io.*; // Untuk FileWriter, IOException
import java.util.*; // Untuk List, Scanner, ArrayList

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
                    keranjang.checkout();
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
}
