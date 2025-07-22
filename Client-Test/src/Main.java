import arc.haldun.hurda.api.ScrapBridge;
import arc.haldun.hurda.api.SessionIdHolder;
import arc.haldun.hurda.database.*;
import arc.haldun.hurda.database.objects.GeneralParameter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, OperationFailedException {

        SessionIdHolder.initFile();

        GeneralParameter parameter = new GeneralParameter(
                "test-parameter",
                10
        );

        ScrapBridge.addGeneralParameter(parameter);
    }
}
