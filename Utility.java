public class Utility {
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("Gagal membersihkan layar.");
        }
    }

    public static void showLoadingAnimation(String message, int duration) {
        String[] spinner = { "|", "/", "-", "\\" }; // Animasi berbentuk spinner
        int interval = 200; // Durasi jeda antar frame dalam milidetik
        int cycles = duration / interval; // Berapa banyak frame selama durasi

        for (int i = 0; i < cycles; i++) {
            System.out.print("\r" + message + " " + spinner[i % spinner.length]); // Cetak spinner
            try {
                Thread.sleep(interval); // Tunggu sesuai interval
            } catch (InterruptedException e) {
                System.out.println("Animasi loading terhenti.");
                break;
            }
        }
        System.out.print("\r" + message + " Selesai!         \n"); // Hapus spinner setelah selesai
    }

}
