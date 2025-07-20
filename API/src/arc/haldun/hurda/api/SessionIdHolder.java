package arc.haldun.hurda.api;

import android.content.Context;

import java.io.*;
import java.util.Scanner;

public class SessionIdHolder {

    private static String sessionId = "";
    private static final String FILE_NAME = "session";
    private static File sessionFile;

    public static void initFileForAndroid(Context c) {

        try {
            File file = new File(c.getFilesDir(), FILE_NAME);
            if (!file.exists()) {
                boolean res = file.createNewFile();
                if (!res) throw new IOException("Dosya oluşturulamadı!");
            } else {
                Scanner scanner = new Scanner(file);
                SessionIdHolder.sessionId = scanner.nextLine();
                scanner.close();
            }
            sessionFile = file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initFile() {
        try {
            sessionFile = new File("session");
            if (sessionFile.exists()) {
                //FileInputStream fis = new FileInputStream(file);
                //byte[] data = fis.readAllBytes();
                //fis.close();

                Scanner scanner = new Scanner(sessionFile);
                SessionIdHolder.sessionId = scanner.nextLine();
                scanner.close();

                //SessionIdHolder.sessionId = new String(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearSessionId() {
        if (sessionFile.exists()) {
            sessionFile.delete();
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
            if (!sessionFile.exists()) sessionFile.createNewFile();

            FileWriter fw = new FileWriter(sessionFile);
            fw.write(sessionId);
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
