package arc.haldun.hurda.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DatabaseConfig {

    private final String databaseAddress, databaseName, userName, password;

    public DatabaseConfig(String databaseAddress, String databaseName, String userName, String password) {
        this.databaseAddress = databaseAddress;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    public static DatabaseConfig load(InputStream is) throws IOException {

        byte[] data = is.readAllBytes();
        is.close();

        String str = new String(data);

        Scanner scanner = new Scanner(str);

        DatabaseConfig config = new DatabaseConfig(
                scanner.nextLine(),
                scanner.nextLine(),
                scanner.nextLine(),
                scanner.nextLine()
        );

        scanner.close();

        return config;
    }

    public void save(OutputStream os) throws IOException {

        String builder = databaseAddress + "\n" +
                databaseName + "\n" +
                userName + "\n" +
                password + "\n";

        byte[] data = builder.getBytes(StandardCharsets.UTF_8);

        os.write(data);

        os.close();
    }

    public String getDatabaseAddress() {
        return databaseAddress;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
