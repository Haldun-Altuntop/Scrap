package arc.haldun.hurda.api;

import android.speech.SpeechRecognizer;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.ChargeMix;
import arc.haldun.hurda.database.objects.Scrap;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.Utilities;
import java.io.InputStream;
import java.io.NotActiveException;
import java.net.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ScrapBridge {

    private static final String SERVER_URL = "http://cryogold.com.tr";

    public static void addScrap(Scrap scrap) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/scrap/add.php");
            HttpURLConnection connection =(HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("scrap", scrap.toJson());
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            String requestString = requestJson.toString();
            byte[] requestData = requestString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestData);
            }

            //int responseCode = connection.getResponseCode();

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            if (responseData.length != 0) {
                String responseStr = new String(responseData, StandardCharsets.UTF_8);

                JSONObject responseJson = new JSONObject(responseStr);

                if (!responseJson.getBoolean("permitted")) {
                    throw new OperationFailedException("Yetkisiz kullanıcı");
                }

                if (responseJson.has("msg")) {
                    String msg = responseJson.getString("msg");
                    throw new OperationFailedException(msg);
                }
            }

            connection.disconnect();

        } catch (IOException | JSONException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateScrap(Scrap scrap) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/scrap/update.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("scrap", scrap.toJson());
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            String requestStr = requestJson.toString();

            byte[] requestData = requestStr.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestData);
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            if (responseData.length != 0) {
                String responseStr = new String(responseData, StandardCharsets.UTF_8);

                JSONObject responseJson = new JSONObject(responseStr);

                if (!responseJson.getBoolean("permitted")) {
                    throw new OperationFailedException("Yetkisiz kullanıcı");
                }

                if (responseJson.has("msg")) {
                    String msg = responseJson.getString("msg");
                    throw new OperationFailedException(msg);
                }
            }

            connection.disconnect();

        } catch (URISyntaxException | IOException | JSONException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    public static Scrap getScrap(String scrapName) throws OperationFailedException {

        if (scrapName == null || scrapName.isEmpty()) throw new OperationFailedException("Hurda adı boş olamaz!");

        try {
            URI uri = new URI(SERVER_URL + "/api/scrap/get.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("scrapName", scrapName);
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            String requestStr = requestJson.toString();

            byte[] requestData = requestStr.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestData);
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            String responseStr = new String(responseData, StandardCharsets.UTF_8);

            JSONObject responseJson = new JSONObject(responseStr);

            if (!responseJson.getBoolean("permitted")) {
                throw new OperationFailedException("Yetkisiz kullanıcı");
            }

            return new Scrap(responseJson.getJSONObject("0"));

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Scrap[] getAllScraps() throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/scrap/get.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("scrapName", "");
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            String requestStr = requestJson.toString();

            byte[] requestData = requestStr.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestData);
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            String responseStr = new String(responseData, StandardCharsets.UTF_8);

            JSONObject responseJson = new JSONObject(responseStr);

            if (!responseJson.getBoolean("permitted")) {
                throw new OperationFailedException("Yetkisiz kullanıcı");
            }

            Scrap[] scraps = new Scrap[responseJson.length() - 1];

            for (int i = 0; i < responseJson.length() - 1; i++) { // Bir tanesi permitted için
                Scrap scrap = new Scrap(responseJson.getJSONObject(String.valueOf(i)));
                scraps[i] = scrap;
            }

            return scraps;

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteScrap(String scrapName) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/scrap/delete.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("scrapName", scrapName);
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            String requestStr = requestJson.toString();

            byte[] requestData = requestStr.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()){
                os.write(requestData);
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            if (responseData.length != 0) {
                String responseStr = new String(responseData, StandardCharsets.UTF_8);

                JSONObject responseJson = new JSONObject(responseStr);

                if (!responseJson.getBoolean("permitted")) {
                    throw new OperationFailedException("Yetkisiz kullanıcı");
                }

                if (responseJson.has("msg")) {
                    String msg = responseJson.getString("msg");
                    throw new OperationFailedException(msg);
                }
            }

            connection.disconnect();

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean login(String userName, String password) {

        if (!SessionIdHolder.getSessionId().isEmpty()) {
            throw new RuntimeException("ScrapBridge.login: Zaten bir oturum mevcut. Önce var olan oturumu kapatmalısınız.");
        }

        try {
            URI uri = new URI(SERVER_URL + "/api/user/login.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("user-name", userName);
            requestJson.put("password",  password);

            String requestString = requestJson.toString(4);
            byte[] requestData = requestString.getBytes(StandardCharsets.UTF_8);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();


            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            String responseString = new String(responseData);
            JSONObject responseJson = new JSONObject(responseString);

            boolean res = responseJson.optBoolean("result");

            if (res) {
                SessionIdHolder.setSessionId(responseJson.optString("session-id"));
            }

            connection.disconnect();

            return res;

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean register(String userName, String password) {

        try {
            URI uri = new URI(SERVER_URL + "/api/user/register.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("user-name", userName);
            requestJson.put("password", password);

            String requestString = requestJson.toString(4);
            byte[] requestData = requestString.getBytes(StandardCharsets.UTF_8);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            if (connection.getResponseCode() >= 300) {
                System.err.println("Kod: " + connection.getResponseCode());
                InputStream es = connection.getErrorStream();
                if (es != null) {
                    System.err.println(new String(es.readAllBytes(), StandardCharsets.UTF_8));
                    es.close();
                }

                return false;
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            String responseString = new String(responseData, StandardCharsets.UTF_8);
            JSONObject responseJson = new JSONObject(responseString);

            String sessionId = responseJson.optString("session-id", "");

            return !sessionId.isEmpty();

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasSession() {

        boolean res;

        try {
            URI uri = new URI(SERVER_URL + "/api/user/check-session.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            byte[] data = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.close();

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);
            res = responseJson.optBoolean("result");

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public static void logout() {

        if (SessionIdHolder.getSessionId().isEmpty()) {
            throw new RuntimeException("ScrapBridge.logout: Zaten bir oturum yok!");
        }

        try {
            URI uri = new URI(SERVER_URL + "/api/user/logout.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            int responseCode = connection.getResponseCode(); // bu satır olmayıncı sunucu oturumu silmiyor

            connection.disconnect();

            SessionIdHolder.clearSessionId();

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addChargeMix(ChargeMix cm) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/charge-mix/add.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());
            requestJson.put("charge-mix", cm.toJson());

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            InputStream es = connection.getErrorStream();
            if (es != null) {
                String msg = new String(es.readAllBytes(), StandardCharsets.UTF_8);
                es.close();
                System.err.println(msg);
                return;
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);

            boolean permitted = responseJson.optBoolean("permitted", false);
            if (!permitted) throw new OperationFailedException("Yetkisiz kullanıcı");

            boolean succeed = responseJson.optBoolean("succeed", false);
            if (!succeed) throw new OperationFailedException("İşlem gerçekleştirilemedi");

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateChargeMix(ChargeMix cm) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/charge-mix/update.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());
            requestJson.put("charge-mix", cm.toJson());

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            InputStream es = connection.getErrorStream();
            if (es != null) {
                String msg = new String(es.readAllBytes(), StandardCharsets.UTF_8);
                es.close();
                System.err.println(msg);
                return;
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);

            boolean permitted = responseJson.optBoolean("permitted", false);
            if (!permitted) throw new OperationFailedException("Yetkisiz kullanıcı");

            boolean succeed = responseJson.optBoolean("succeed", false);
            if (!succeed) throw new OperationFailedException("İşlem gerçekleştirilemedi");

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChargeMix[] getAllChargeMixes() throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/charge-mix/get.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            connection.getResponseCode();

            InputStream es = connection.getErrorStream();
            if (es != null) {
                String msg = new String(es.readAllBytes(), StandardCharsets.UTF_8);
                es.close();
                System.err.println(msg);
                return new ChargeMix[0];
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);

            boolean permitted = responseJson.optBoolean("permitted");
            if (!permitted) throw new OperationFailedException("Yetkisiz kullanıcı");

            ChargeMix[] chargeMixes = new ChargeMix[responseJson.length() - 1];

            for (int i = 0; i < responseJson.length() - 1; i++) {
                chargeMixes[i] = new ChargeMix(responseJson.getJSONObject(String.valueOf(i)));
            }

            return chargeMixes;

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChargeMix getChargeMix(String chargeMixName) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/charge-mix/get.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());
            requestJson.put("charge-mix-name", chargeMixName);

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            connection.getResponseCode();

            InputStream es = connection.getErrorStream();
            if (es != null) {
                String msg = new String(es.readAllBytes(), StandardCharsets.UTF_8);
                es.close();
                System.err.println(msg);
                return null;
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);

            boolean permitted = responseJson.optBoolean("permitted");
            if (!permitted) throw new OperationFailedException("Yetkisiz kullanıcı");

            ChargeMix[] chargeMixes = new ChargeMix[responseJson.length() - 1];

            for (int i = 0; i < responseJson.length() - 1; i++) {
                chargeMixes[i] = new ChargeMix(responseJson.getJSONObject(String.valueOf(i)));
            }

            return chargeMixes[0];

        } catch (IOException | URISyntaxException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteChargeMix(String chargeMixName) throws OperationFailedException {

        try {
            URI uri = new URI(SERVER_URL + "/api/charge-mix/delete.php");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject requestJson = new JSONObject();
            requestJson.put("session-id", SessionIdHolder.getSessionId());
            requestJson.put("charge-mix-name", chargeMixName);

            byte[] requestData = jsonToByteArray(requestJson);

            OutputStream os = connection.getOutputStream();
            os.write(requestData);
            os.close();

            connection.getResponseCode();

            InputStream es = connection.getErrorStream();
            if (es != null) {
                String msg = new String(es.readAllBytes(), StandardCharsets.UTF_8);
                es.close();
                System.err.println(msg);
                return;
            }

            InputStream is = connection.getInputStream();
            byte[] responseData = is.readAllBytes();
            is.close();

            connection.disconnect();

            JSONObject responseJson = byteArrayToJson(responseData);

            boolean permitted = responseJson.getBoolean("permitted");
            if (!permitted) throw new OperationFailedException("Yetkisiz kullanıcı");

            boolean succeed = responseJson.optBoolean("succeed", false);
            if (!succeed) throw new OperationFailedException("Charge mix silinemedi: " + chargeMixName);

        } catch (URISyntaxException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }



    private static byte[] jsonToByteArray(JSONObject jsonObject) throws JSONException {
        return jsonObject.toString(4).getBytes(StandardCharsets.UTF_8);
    }

    private static JSONObject byteArrayToJson(byte[] data) throws JSONException {
        String str = new String(data, StandardCharsets.UTF_8);
        return new JSONObject(str);
    }
}
