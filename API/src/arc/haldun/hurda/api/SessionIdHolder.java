package arc.haldun.hurda.api;

import java.io.*;
import java.util.Scanner;

public class SessionIdHolder {

    private static String sessionId = "";

    static {
        try {
            File file = new File("session");
            if (file.exists()) {
                //FileInputStream fis = new FileInputStream(file);
                //byte[] data = fis.readAllBytes();
                //fis.close();

                Scanner scanner = new Scanner(file);
                SessionIdHolder.sessionId = scanner.nextLine();
                scanner.close();

                //SessionIdHolder.sessionId = new String(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearSessionId() {
        File file = new File("session");
        if (file.exists()) {
            file.delete();
        }
        sessionId = "";
    }

    public static void setSessionId(String sessionId) {
        SessionIdHolder.sessionId = sessionId;
        save();
    }

    public static String getSessionId() {
        return SessionIdHolder.sessionId;
    }

    private static void save() {
        try {
            File file = new File("session");
            if (!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(sessionId);
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
