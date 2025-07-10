package arc.haldun.hurda.server.context;

import arc.haldun.hurda.server.SessionManager;
import arc.haldun.hurda.server.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LogoutContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            JSONObject requestJson = Utilities.byteArrayToJson(requestData);
            String sessionId = requestJson.optString("session-id");

            SessionManager.instance.removeSession(sessionId);

            exchange.sendResponseHeaders(200, 0);
        } catch (JSONException e) {
            Utilities.sendError(exchange, 400, "Invalid JSON: " + e.getMessage());
        } finally {
            exchange.close();
        }
    }
}
