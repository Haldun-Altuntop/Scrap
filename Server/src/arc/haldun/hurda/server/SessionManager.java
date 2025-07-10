package arc.haldun.hurda.server;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.User;
import arc.haldun.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class SessionManager {

    public static SessionManager instance;

    private final String SESSION_STORE_FILE_NAME = "session-store.json";
    private JSONObject sessionStore;

    public SessionManager() {
        if (instance != null) {
            System.err.println("Birden fazla Session Manager bulundu!");
        }
        instance = this;

        try {
            File file = new File(SESSION_STORE_FILE_NAME);
            if (!file.exists()) file.createNewFile();

            InputStream is = new FileInputStream(file);
            String str = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

            sessionStore = new JSONObject(str);
        } catch (FileNotFoundException e) {
            System.out.println(SESSION_STORE_FILE_NAME + " bulunamadı. Yenisi oluşturuluyor.");
            sessionStore = new JSONObject();
            saveSessionStore();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            System.out.println("Oturum deposu hasarlıydı. Yenisi oluşturuluyor...");
            sessionStore = new JSONObject();
            saveSessionStore();
        }
    }

    public int count() {
        return sessionStore.length();
    }

    public boolean has(String sessionId) {
        return sessionStore.has(sessionId);
    }

    public Session[] getAllSessions() {

        ArrayList<Session> sessions = new ArrayList<>();

        Iterator<String> keys = sessionStore.keys();

        while (keys.hasNext()) {
            String sessionKey = keys.next();

            try {
                Session session = new Session(sessionStore.getJSONObject(sessionKey));
                sessions.add(session);
            } catch (JSONException e) {
                e.printStackTrace(System.err);
            }
        }

        return sessions.toArray(new Session[0]);
    }

    private void saveSessionStore() {
        try {
            OutputStream os = new FileOutputStream(SESSION_STORE_FILE_NAME);
            byte[] data = sessionStore.toString(4).getBytes(StandardCharsets.UTF_8);
            os.write(data);
            os.close();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Session createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        int id = user.getId();

        return new Session(sessionId, id);
    }

    /**
     * user nesnesinden bir session oluşturur ve depoya ekler.
     * @param user Oturumu oluşturulacak kullanıcı
     * @return Oluşturulan oturum kimliğini döndürür
     */
    public String addSession(User user) {

        Session session = createSession(user);
        try {
            sessionStore.put(session.getSessionId(), session.toJson());

            saveSessionStore();

            return session.sessionId;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Session getSession(String sessionId) throws SessionNotFound {

        if (!sessionStore.has(sessionId)) throw new SessionNotFound("Oturum bulunamadı: " + sessionId);

        try {
            JSONObject session = sessionStore.getJSONObject(sessionId);
            return new Session(session);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeSession(String sessionId) throws SessionNotFound {
        sessionStore.remove(sessionId);
        saveSessionStore();
    }

    public static class Session {

        private final String sessionId;
        private final int targetUserId;
        private DateTime lastActionTime;

        // TAGS
        private final String TAG_SESSION_ID = "session-id";
        private final String TAG_TARGET_USER_ID = "target-user-id";
        private final String TAG_LAST_ACTION_TIME = "last-action-time";

        public Session(String sessionId, int targetUserId) {
            this.sessionId = sessionId;
            this.targetUserId = targetUserId;
            this.lastActionTime = DateTime.now();
        }

        public Session(JSONObject sessionJson) throws JSONException {
            this.sessionId = sessionJson.getString(TAG_SESSION_ID);
            this.targetUserId = sessionJson.getInt(TAG_TARGET_USER_ID);
            this.lastActionTime = new DateTime(sessionJson.getDouble(TAG_LAST_ACTION_TIME));
        }

        public String getSessionId() {
            return sessionId;
        }

        public int getTargetUserId() {
            return targetUserId;
        }

        public DateTime getLastActionTime() {
            return lastActionTime;
        }

        public void setLastActionTime(DateTime lastActionTime) {
            this.lastActionTime = lastActionTime;
        }

        public User getUser() throws OperationFailedException {
            return DatabaseManager.getUser(targetUserId);
        }

        public JSONObject toJson() throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_SESSION_ID, getSessionId());
            jsonObject.put(TAG_TARGET_USER_ID, getTargetUserId());
            jsonObject.put(TAG_LAST_ACTION_TIME, getLastActionTime().getNumericValue());
            return jsonObject;
        }
    }
}
