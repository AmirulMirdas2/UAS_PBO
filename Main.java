import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        while (true) {
            Utility.clearScreen();
            System.out.println("\nSelamat datang di SUPERMARKET!");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer

            if (pilihan == 1) {
                // Proses Login
                System.out.print("Masukkan role (admin/user): ");
                String role = scanner.nextLine().toLowerCase();

                System.out.print("Masukkan username: ");
                String username = scanner.nextLine();

                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();
                Utility.showLoadingAnimation("LOADING", 3000);
                Utility.clearScreen();

                if (role.equals("admin")) {
                    login.setDataFile("data_admin.txt");
                    if (login.login(username, password)) {
                        System.out.println("Login sebagai admin berhasil!");
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.start();
                    } else {
                        System.out.println("Login gagal! Username atau password salah.");
                    }
                } else if (role.equals("user")) {
                    login.setDataFile("data_user.txt");
                    if (login.login(username, password)) {
                        System.out.println("Login sebagai user berhasil!");
                        UserMenu userMenu = new UserMenu(username);
                        userMenu.start();
                    } else {
                        System.out.println("Login gagal! Username atau password salah.");
                    }
                } else {
                    System.out.println("Role tidak valid!");
                }
            } else if (pilihan == 2) {
                // Proses Registrasi
                Utility.showLoadingAnimation("LOADING", 3000);
                Utility.clearScreen();
                System.out.print("Masukkan role (admin/user): ");
                String role = scanner.nextLine().toLowerCase();

                System.out.print("Masukkan username: ");
                String username = scanner.nextLine();

                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                if (role.equals("admin")) {
                    login.setDataFile("data_admin.txt");
                    login.register(username, password);
                } else if (role.equals("user")) {
                    login.setDataFile("data_user.txt");
                    login.register(username, password);
                } else {
                    System.out.println("Role tidak valid! Registrasi gagal.");
                }
            } else if (pilihan == 3) {
                Utility.clearScreen();
                Utility.showLoadingAnimation("LOADING", 3000);
                Utility.clearScreen();
                System.out.println("Terimakasih Telah Berbelanja.");
                break;
            } else {
                Utility.showLoadingAnimation("LOADING", 3000);
                Utility.clearScreen();
                System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
