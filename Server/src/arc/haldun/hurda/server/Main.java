package arc.haldun.hurda.server;

import arc.haldun.hurda.database.Connector;
import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.MariaDB;
import arc.haldun.hurda.server.context.*;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        String ip = "cryogold.com.tr";
        int port = 8080;

        connect();
        System.out.println("Veritabanı bağlantısı kuruldu.");

        System.out.println("Oturum yöneticisi başlatılıyor...");
        new SessionManager();
        System.out.println(SessionManager.instance.count() + " oturum yüklendi.");

        System.out.println("Sunucu başlatılıyor...");
        try {
            startServer(ip, port);
            System.out.printf("Sunucu %s adresinde çalışıyor.\n", ip + ":" + port);
        } catch (IOException e) {
            System.err.println("Sunucu başlatılırken bir hatayla karşılaşıldı.");
            e.printStackTrace(System.err);
        }
    }

    private static void startServer(String serverLocalIp, int port) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(serverLocalIp, port), 1);

        server.createContext("/scrap/add", new AddScrapContext());
        server.createContext("/scrap/get", new GetScrapContext());
        server.createContext("/scrap/update", new UpdateScrapContext());
        server.createContext("/scrap/delete", new DeleteScrapContext());

        server.createContext("/user/login", new LoginContext());
        server.createContext("/user/register", new RegisterContext());

        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();

        startCommandLineThread(server);
    }

    private static void startCommandLineThread(HttpServer server) {

        new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String cmd = scanner.nextLine();

                if (cmd.equals("stop")) {
                    System.err.println("Sunucu kapatılıyor.");

                    server.stop(0);

                    Connector.close();

                    System.exit(0);
                }
            }

        }).start();
    }

    private static void connect() {

        DatabaseManager.initDatabase(new MariaDB());

        File file = new File("server.properties");
        String databaseAddress, databaseName, userName, password;
        Scanner scanner = new Scanner(System.in);

        if (file.exists()) {

            try {
                FileInputStream fis = new FileInputStream(file);

                Properties properties = new Properties();
                properties.load(fis);

                databaseAddress = properties.getProperty("database-address");
                databaseName = properties.getProperty("database-name");
                userName = properties.getProperty("user-name");

                System.out.print("Şifre: ");
                password = scanner.nextLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {

            System.out.print("Veritabanı adresi: ");
            databaseAddress = scanner.nextLine();

            System.out.print("Veritabanı adı: ");
            databaseName = scanner.nextLine();

            System.out.print("Kullanıcı adı: ");
            userName = scanner.nextLine();

            System.out.print("Şifre: ");
            password = scanner.nextLine();

            System.out.println("Giriş bilgileri aydedilsin mi? (E/H)");
            String s = scanner.nextLine();

            s = s.toLowerCase();

            if (s.equals("e")) {
                // vertabanı adresi, veritabanı adı, kullanıcı adı kaydet. şifreyi soracaksın
                System.out.println("Kaydediliyor -> server.properties");

                Properties properties = new Properties();
                properties.put("database-address", databaseAddress);
                properties.put("database-name", databaseName);
                properties.put("user-name", userName);
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    properties.store(fos, "");
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } else if (!s.equals("h")) {

                System.out.println("Bunu hayır olarak kabul ediyorum.");
            }
        }

        //scanner.close();

        Connector.connect(databaseAddress, databaseName, userName, password);
    }
}
