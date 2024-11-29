import java.util.Scanner;

public class AdminMenu {
    private MasukkanBarang masukkanBarang = new MasukkanBarang();
    private HapusBarang hapusBarang = new HapusBarang();
    private LihatBarang lihatBarang = new LihatBarang();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Admin:");
            System.out.println("1. Tambah barang");
            System.out.println("2. Hapus barang");
            System.out.println("3. Lihat barang");
            System.out.println("4. Keluar");
            System.out.print("Pilih: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (pilihan) {
                case 1:
                    System.out.print("Nama barang: ");
                    String nama = scanner.nextLine();
                    System.out.print("Harga barang: ");
                    double harga = scanner.nextDouble();
                    System.out.print("Stok barang: ");
                    int stok = scanner.nextInt();
                    masukkanBarang.tambahBarang(new Barang(nama, harga, stok));
                    break;
                case 2:
                    System.out.print("Nama barang yang akan dihapus: ");
                    String namaHapus = scanner.nextLine();
                    hapusBarang.hapusBarang(namaHapus);
                    break;
                case 3:
                    lihatBarang.cetakDaftarBarang();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
