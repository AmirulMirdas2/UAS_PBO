public class Barang {
    private String nama;
    private double harga;
    private int stok;
    private int jumlah;  // Atribut baru untuk menyimpan jumlah barang yang dibeli

    // Constructor
    public Barang(String nama, double harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.jumlah = 0; // Inisialisasi jumlah dengan 0
    }

    // Getter and Setter untuk nama, harga, stok, dan jumlah
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}