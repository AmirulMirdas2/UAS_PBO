import java.io.*;

public class Login {
    private String dataFile;

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca data: " + e.getMessage());
        }
        return false;
    }

    public void register(String username, String password) {
        try (FileWriter writer = new FileWriter(dataFile, true)) {
            writer.write(username + "," + password + "\n");
            System.out.println("Registrasi berhasil!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }
}
