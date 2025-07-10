package arc.haldun.hurda.server.context;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.Scrap;
import arc.haldun.hurda.server.SessionManager;
import arc.haldun.hurda.server.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AddScrapContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            String requestStr = new String(requestData, StandardCharsets.UTF_8);
            JSONObject requestJson = new JSONObject(requestStr);

            String sessionId = requestJson.optString("session-id", "");
            boolean permitted = SessionManager.instance.has(sessionId);

            if (permitted) {
                Scrap scrap = new Scrap(requestJson.optJSONObject("scrap"));
                DatabaseManager.addScrap(scrap);
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("permitted", permitted);

            byte[] responseData = Utilities.jsonToByteArray(responseJson);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (JSONException | SQLException e) {
            System.err.println("OHA");
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {

            try {
                JSONObject responseJson = new JSONObject();
                responseJson.put("msg", e.getMessage());

                byte[] responseData = responseJson.toString().getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(200, responseData.length);

                OutputStream os = exchange.getResponseBody();
                os.write(responseData);
                os.close();

            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            exchange.close();
        }
    }
}
