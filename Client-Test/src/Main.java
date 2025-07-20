import arc.haldun.hurda.api.ScrapBridge;
import arc.haldun.hurda.api.SessionIdHolder;
import arc.haldun.hurda.database.*;
import arc.haldun.hurda.database.objects.ChargeMix;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, OperationFailedException {

        // TODO: hurdalar tablosuna melting_factor parametresini ekle
        // TODO: charge mix silmesen de olur ama kullanılmayacak
        // TODO: general_parameters tablosuna değerleri ekle


        Connector.connect(DatabaseConfig.load(new FileInputStream("config")));
        DatabaseManager.initDatabase(new MariaDB());

        SessionIdHolder.initFile();

        ChargeMix chargeMix = new ChargeMix(
                "HMS1",
                0d,
                34d,
                102d
        );

        System.out.println(ScrapBridge.getChargeMix("HMS1"));

        Connector.close();
    }
}
