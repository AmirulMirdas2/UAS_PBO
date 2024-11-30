import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Registrasi {
    private static final String DATA_FILE = "data_akun.txt";

    public void register(String username, String password) {
        try (FileWriter writer = new FileWriter(DATA_FILE, true)) {
            writer.write(username + "," + password + "\n");
            System.out.println("Registrasi berhasil!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Registrasi reg = new Registrasi();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();

        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        reg.register(username, password);
    }
}