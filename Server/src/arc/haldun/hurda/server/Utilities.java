package arc.haldun.hurda.server;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Utilities {

    public static void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
        byte[] response = message.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
        System.err.println("Utilities -> HATA! -> " + message);
    }

    public static byte[] jsonToByteArray(JSONObject jsonObject) throws JSONException {
        return jsonObject.toString(4).getBytes(StandardCharsets.UTF_8);
    }

    public static JSONObject byteArrayToJson(byte[] data) throws JSONException {
        return new JSONObject(new String(data, StandardCharsets.UTF_8));
    }
}
